#!/bin/bash
set -e

# Display script usage
function show_usage {
  echo "Usage: $0 [options]"
  echo "Options:"
  echo "  -h, --help                 Show this help message"
  echo "  -r, --registry REGISTRY    Docker registry to use (default: registry.cloudraya.com/ir-cr-hendi-144)"
  echo "  -t, --tag TAG              Image tag to use (default: latest)"
  echo "  -n, --namespace NAMESPACE  Kubernetes namespace to deploy to (default: default)"
  echo "  -p, --persistent           Use persistent storage for customer photos"
  echo "  -i, --ingress              Deploy with ingress"
  echo "  -d, --domain DOMAIN        Domain for ingress (required if --ingress is specified)"
  echo "  -s, --skip-build           Skip building and pushing the image"
  echo ""
  echo "Example:"
  echo "  $0 --registry my-registry.com/my-username --tag v1.0.0 --namespace my-app --persistent --ingress --domain my-app.example.com"
}

# Default values
REGISTRY="registry.cloudraya.com/ir-cr-hendi-144"
TAG="latest"
NAMESPACE="hendi-cluster"
USE_PERSISTENT=false
USE_INGRESS=false
DOMAIN="k8s.jvm.my.id"
SKIP_BUILD=false

# Parse command line arguments
while [[ $# -gt 0 ]]; do
  key="$1"
  case $key in
    -h|--help)
      show_usage
      exit 0
      ;;
    -r|--registry)
      REGISTRY="$2"
      shift
      shift
      ;;
    -t|--tag)
      TAG="$2"
      shift
      shift
      ;;
    -n|--namespace)
      NAMESPACE="$2"
      shift
      shift
      ;;
    -p|--persistent)
      USE_PERSISTENT=true
      shift
      ;;
    -i|--ingress)
      USE_INGRESS=true
      shift
      ;;
    -d|--domain)
      DOMAIN="$2"
      shift
      shift
      ;;
    -s|--skip-build)
      SKIP_BUILD=true
      shift
      ;;
    *)
      echo "Unknown option: $1"
      show_usage
      exit 1
      ;;
  esac
done

# Validate arguments
if [ "$USE_INGRESS" = true ] && [ -z "$DOMAIN" ]; then
  echo "Error: --domain is required when --ingress is specified"
  show_usage
  exit 1
fi

# Create namespace if it doesn't exist
echo "Checking if namespace $NAMESPACE exists..."
if ! kubectl get namespace "$NAMESPACE" &> /dev/null; then
  echo "Creating namespace $NAMESPACE..."
  kubectl create namespace "$NAMESPACE"
fi

# Update image references in deployment files
echo "Updating image references in deployment files..."
sed -i.bak "s|image: registry.cloudraya.com/ir-cr-hendi-144/customer-app.*|image: $REGISTRY/customer-app:$TAG|g" k8s/deployment.yaml
sed -i.bak "s|image: registry.cloudraya.com/ir-cr-hendi-144/customer-app.*|image: $REGISTRY/customer-app:$TAG|g" k8s/deployment-with-pvc.yaml

# Update image reference in skaffold.yaml
sed -i.bak "s|image: registry.cloudraya.com/ir-cr-hendi-144/customer-app|image: $REGISTRY/customer-app|g" skaffold.yaml

# Update image reference in pom.xml
sed -i.bak "s|<image>registry.cloudraya.com/ir-cr-hendi-144/customer-app</image>|<image>$REGISTRY/customer-app</image>|g" pom.xml

# Build and push the image if not skipped
if [ "$SKIP_BUILD" = false ]; then
  echo "Building and pushing the image..."
  mvn clean package jib:build -Djib.to.tags=$TAG
fi

# Deploy to Kubernetes
echo "Deploying to Kubernetes..."

# Apply persistent volume claim if requested
if [ "$USE_PERSISTENT" = true ]; then
  echo "Creating persistent volume claim..."
  kubectl apply -f k8s/persistent-volume-claim.yaml -n "$NAMESPACE"

  echo "Deploying with persistent storage..."
  kubectl apply -f k8s/deployment-with-pvc.yaml -n "$NAMESPACE"
else
  echo "Deploying without persistent storage..."
  kubectl apply -f k8s/deployment.yaml -n "$NAMESPACE"
fi

# Apply service
kubectl apply -f k8s/service.yaml -n "$NAMESPACE"

# Apply ingress if requested
if [ "$USE_INGRESS" = true ]; then
  echo "Updating ingress domain to $DOMAIN..."
  sed -i.bak "s|host: k8s.jvm.my.id|host: $DOMAIN|g" k8s/ingress.yaml

  echo "Deploying ingress..."
  kubectl apply -f k8s/ingress.yaml -n "$NAMESPACE"
fi

# Clean up backup files
rm -f k8s/deployment.yaml.bak k8s/deployment-with-pvc.yaml.bak skaffold.yaml.bak pom.xml.bak k8s/ingress.yaml.bak

echo "Deployment completed successfully!"
echo "To check the status of your deployment, run:"
echo "  kubectl get pods -n $NAMESPACE -l app=customer-app"
echo "  kubectl get svc -n $NAMESPACE -l app=customer-app"

if [ "$USE_INGRESS" = true ]; then
  echo "  kubectl get ingress -n $NAMESPACE"
  echo ""
  echo "Your application will be available at: http://$DOMAIN"
else
  echo ""
  echo "To access your application, run:"
  echo "  kubectl port-forward svc/customer-app 9003:9003 -n $NAMESPACE"
  echo ""
  echo "Then open http://localhost:9003 in your browser"
fi
