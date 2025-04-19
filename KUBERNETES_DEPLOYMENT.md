# Deploying to Kubernetes Cluster in VPS

This guide provides step-by-step instructions for deploying the Spring Boot Thymeleaf Sample application to a Kubernetes
cluster running on a Virtual Private Server (VPS).

## Prerequisites

Before you begin, ensure you have the following:

1. A VPS with a Kubernetes cluster installed and running
2. `kubectl` installed and configured to connect to your cluster
3. Docker registry access (this guide uses registry.cloudraya.com, but you can use any registry)
4. Java 21 and Maven installed on your local machine
5. Git installed on your local machine

## Step 1: Clone the Repository

```bash
git clone https://github.com/hendisantika/spring-boot-thymeleaf-sample2.git
cd spring-boot-thymeleaf-sample2
```

## Step 2: Configure Docker Registry

The project is configured to use a specific Docker registry (registry.cloudraya.com). If you want to use a different
registry:

1. Update the `jib-maven-plugin` configuration in `pom.xml`:

```xml

<configuration>
    <to>
        <image>your-registry/your-username/customer-app</image>
    </to>
    <!-- other configuration -->
</configuration>
```

2. Update the image reference in `k8s/deployment.yaml`:

```yaml
containers:
  - name: customer-app
    image: your-registry/your-username/customer-app:tag
```

3. Update the image reference in `skaffold.yaml`:

```yaml
build:
  artifacts:
    - image: your-registry/your-username/customer-app
```

## Step 3: Build and Push the Docker Image

You can use either Maven with Jib or Skaffold to build and push the Docker image:

### Option 1: Using Maven with Jib

```bash
# Authenticate with your Docker registry if needed
docker login registry.cloudraya.com

# Build and push the image
mvn clean package jib:build
```

### Option 2: Using Skaffold

```bash
# Authenticate with your Docker registry if needed
docker login registry.cloudraya.com

# Build and push the image
skaffold build
```

## Step 4: Deploy to Kubernetes

### Option 1: Using the Deployment Script

The repository includes a deployment script that automates the process of deploying the application to a Kubernetes
cluster:

```bash
# Make the script executable if it's not already
chmod +x deploy-to-k8s.sh

# Show usage information
./deploy-to-k8s.sh --help

# Example: Deploy with default settings
./deploy-to-k8s.sh

# Example: Deploy with custom settings
./deploy-to-k8s.sh --registry my-registry.com/my-username --tag v1.0.0 --namespace my-app --persistent --ingress --domain my-app.example.com
```

The script provides several options:

- `--registry`: Docker registry to use (default: registry.cloudraya.com/ir-cr-hendi-144)
- `--tag`: Image tag to use (default: latest)
- `--namespace`: Kubernetes namespace to deploy to (default: default)
- `--persistent`: Use persistent storage for customer photos
- `--ingress`: Deploy with ingress
- `--domain`: Domain for ingress (required if --ingress is specified)
- `--skip-build`: Skip building and pushing the image

### Option 2: Using kubectl

```bash
# Apply the Kubernetes manifests
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### Option 3: Using Skaffold

```bash
# Deploy to Kubernetes
skaffold run
```

## Step 5: Verify the Deployment

```bash
# Check if the pods are running
kubectl get pods -l app=customer-app

# Check if the service is created
kubectl get svc customer-app
```

## Step 6: Access the Application

By default, the application is exposed as a ClusterIP service, which is only accessible within the cluster. To access it
from outside the cluster, you have several options:

### Option 1: Port Forwarding (for testing)

```bash
kubectl port-forward svc/customer-app 8081:8081
```

Then access the application at http://localhost:8081

### Option 2: Create an Ingress Resource

An example ingress configuration is provided in the repository:

```bash
# Apply the ingress resource
kubectl apply -f k8s/ingress.yaml
```

This will expose your application at the domain specified in the ingress configuration (you'll need to update the host
in the file to match your domain).

### Option 3: Change Service Type to LoadBalancer

Edit `k8s/service.yaml` to change the service type from ClusterIP to LoadBalancer:

```yaml
spec:
  type: LoadBalancer
  # rest of the configuration remains the same
```

Apply the updated service:

```bash
kubectl apply -f k8s/service.yaml
```

## Step 7: Configure Persistent Storage (Optional)

The application uses an in-memory H2 database by default, which means data will be lost when the pod restarts. For a
production deployment, you might want to:

1. Use a persistent database like MySQL or PostgreSQL
2. Configure persistent volumes for storing customer photos

The repository includes example files for setting up persistent storage:

1. `k8s/persistent-volume-claim.yaml` - Defines a PersistentVolumeClaim for customer photos
2. `k8s/deployment-with-pvc.yaml` - A modified deployment that uses the PVC instead of an emptyDir volume

To use these files:

```bash
# Create the PersistentVolumeClaim
kubectl apply -f k8s/persistent-volume-claim.yaml

# Deploy the application with the PVC
kubectl apply -f k8s/deployment-with-pvc.yaml
kubectl apply -f k8s/service.yaml
```

This ensures that customer photos are stored on persistent storage and will survive pod restarts and rescheduling.

## Step 8: Set Up Monitoring and Logging (Optional)

For a production deployment, consider setting up:

1. Prometheus and Grafana for monitoring
2. ELK Stack or Loki for logging
3. Kubernetes Dashboard for cluster management

## Troubleshooting

### Check Pod Logs

```bash
kubectl logs -l app=customer-app
```

### Check Pod Description

```bash
kubectl describe pod -l app=customer-app
```

### Check Service

```bash
kubectl describe svc customer-app
```

## Continuous Deployment

For continuous deployment, you can:

1. Set up a CI/CD pipeline using GitHub Actions, Jenkins, or GitLab CI
2. Use Skaffold for development and testing
3. Use ArgoCD or Flux for GitOps-based deployments

## Security Considerations

1. Use Kubernetes Secrets for sensitive information like database credentials
2. Configure network policies to restrict pod-to-pod communication
3. Use RBAC to limit access to Kubernetes resources
4. Regularly update the application and dependencies
5. Scan container images for vulnerabilities
