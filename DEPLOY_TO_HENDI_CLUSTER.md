# Deploying to hendi-cluster on CloudRaya.com

This guide provides step-by-step instructions for deploying the Spring Boot Thymeleaf Sample application to your
Kubernetes cluster named "hendi-cluster" running on CloudRaya.com VPS.

## Prerequisites

Before you begin, ensure you have the following:

1. Access to your "hendi-cluster" Kubernetes cluster on CloudRaya.com
2. `kubectl` installed and configured to connect to your cluster
3. Docker registry access (this guide uses registry.cloudraya.com)
4. Java 21 and Maven installed (if building locally)

## Option 1: Manual Deployment

### Step 1: Clone the Repository

```bash
git clone https://github.com/hendisantika/spring-boot-thymeleaf-sample2.git
cd spring-boot-thymeleaf-sample2
```

### Step 2: Configure kubectl for Your Cluster

Ensure your kubectl is configured to connect to your hendi-cluster:

```bash
# Check current context
kubectl config current-context

# If needed, set the context to your hendi-cluster
kubectl config use-context hendi-cluster
```

### Step 3: Create Docker Registry Secret

Create a secret for pulling images from the CloudRaya registry:

```bash
kubectl create secret docker-registry cloud-raya-secret \
  --docker-server=registry.cloudraya.com \
  --docker-username=YOUR_CLOUDRAYA_USERNAME \
  --docker-password=YOUR_CLOUDRAYA_PASSWORD \
  --docker-email=YOUR_EMAIL \
  --namespace=hendi-cluster
```

### Step 4: Deploy Using the Script

The repository includes a deployment script that automates the process:

```bash
# Make the script executable
chmod +x deploy-to-k8s.sh

# Deploy to your hendi-cluster
./deploy-to-k8s.sh --namespace hendi-cluster --skip-build
```

For more options:

```bash
# Show all available options
./deploy-to-k8s.sh --help

# Example with persistent storage and ingress
./deploy-to-k8s.sh --namespace hendi-cluster --persistent --ingress --domain k8s.jvm.my.id --skip-build
```

### Step 5: Verify the Deployment

```bash
# Check if the pods are running
kubectl get pods -n hendi-cluster -l app=customer-app

# Check if the service is created
kubectl get svc -n hendi-cluster customer-app

# If using ingress
kubectl get ingress -n hendi-cluster
```

## Option 2: Deployment with GitHub Actions

You can also set up GitHub Actions to automatically deploy to your hendi-cluster whenever you push to the main branch.

### Step 1: Configure GitHub Secrets and Variables

In your GitHub repository, add the following secrets and variables:

**Secrets:**

- `CLOUDRAYA_USERNAME`: Your CloudRaya username
- `CLOUDRAYA_PASSWORD`: Your CloudRaya password
- `KUBE_CONFIG`: Your Kubernetes configuration file (base64 encoded)

**Variables:**

- `K8S_NAMESPACE`: Set to "hendi-cluster"
- `K8S_USE_PERSISTENT`: Set to "true" if you want persistent storage
- `K8S_USE_INGRESS`: Set to "true" if you want to use ingress
- `K8S_DOMAIN`: Your domain for ingress (if using ingress)

### Step 2: Get Your Kubernetes Configuration

To get your Kubernetes configuration in the required format:

```bash
# Get the kubeconfig for your cluster
kubectl config view --minify --flatten | base64
```

Add the output as the `KUBE_CONFIG` secret in your GitHub repository.

### Step 3: Push to GitHub

Push your code to GitHub, and the GitHub Actions workflow will automatically deploy to your hendi-cluster.

## Accessing Your Application

### If Using LoadBalancer Service

The service is configured as LoadBalancer by default. Get the external IP:

```bash
kubectl get svc -n hendi-cluster customer-app
```

Access your application at: http://EXTERNAL_IP:9003

### If Using Ingress

If you deployed with ingress, access your application at the domain you specified:

http://your-domain.com

### If Using Port Forwarding (for testing)

```bash
kubectl port-forward -n hendi-cluster svc/customer-app 9003:9003
```

Then access your application at: http://localhost:9003

## Troubleshooting

### Check Pod Logs

```bash
kubectl logs -n hendi-cluster -l app=customer-app
```

### Check Pod Description

```bash
kubectl describe pod -n hendi-cluster -l app=customer-app
```

### Check Service

```bash
kubectl describe svc -n hendi-cluster customer-app
```

### Check Ingress

```bash
kubectl describe ingress -n hendi-cluster customer-app-ingress
```
