# Deploying to Kubernetes with GitHub Actions

This guide explains how to deploy the Spring Boot Thymeleaf Sample application to a Kubernetes cluster using GitHub
Actions.

## Prerequisites

Before you begin, ensure you have the following:

1. A Kubernetes cluster (e.g., on a cloud provider like GKE, EKS, AKS, or a self-hosted cluster)
2. `kubectl` installed on your local machine
3. Access to a Docker registry (this project uses DockerHub and registry.cloudraya.com)
4. GitHub repository with the application code

## Setting Up GitHub Secrets and Variables

You need to configure the following secrets and variables in your GitHub repository:

### Required Secrets

1. `DOCKERHUB_USERNAME` - Your DockerHub username
2. `DOCKERHUB_TOKEN` - Your DockerHub access token
3. `CLOUDRAYA_USERNAME` - Your CloudRaya username
4. `CLOUDRAYA_PASSWORD` - Your CloudRaya password
5. `KUBE_CONFIG` - Your Kubernetes configuration file content (base64 encoded)

### Optional Variables

1. `K8S_NAMESPACE` - The Kubernetes namespace to deploy to (default: "default")
2. `K8S_USE_PERSISTENT` - Whether to use persistent storage (default: "false")
3. `K8S_USE_INGRESS` - Whether to deploy with ingress (default: "false")
4. `K8S_DOMAIN` - The domain for ingress (required if K8S_USE_INGRESS is "true")

## How to Set Up Kubernetes Configuration

To obtain your Kubernetes configuration file:

1. Run `kubectl config view --minify --flatten` on your local machine
2. Base64 encode the output: `kubectl config view --minify --flatten | base64`
3. Add the encoded output as a secret named `KUBE_CONFIG` in your GitHub repository

## GitHub Actions Workflow

The GitHub Actions workflow performs the following steps:

1. Builds the Java application with Maven
2. Builds and pushes Docker images to DockerHub and CloudRaya registry
3. Deploys the application to Kubernetes using the `deploy-to-k8s.sh` script

The workflow is triggered on pushes to the main branch and pull requests to the main branch.

## Deployment Options

The deployment can be customized using the following options:

1. **Namespace**: Set the `K8S_NAMESPACE` variable to deploy to a specific namespace
2. **Persistent Storage**: Set `K8S_USE_PERSISTENT` to "true" to use persistent storage for customer photos
3. **Ingress**: Set `K8S_USE_INGRESS` to "true" and provide a `K8S_DOMAIN` to expose the application via an ingress
   controller

## Monitoring and Troubleshooting

### Checking Deployment Status

You can check the status of your deployment in the GitHub Actions logs. The workflow includes a verification step that
displays:

- The status of the pods
- The status of the service
- The status of the ingress (if enabled)

### Common Issues and Solutions

1. **Image Pull Errors**: Ensure your Docker registry credentials are correct and the image exists
2. **Permission Errors**: Ensure your Kubernetes configuration has the necessary permissions
3. **Resource Constraints**: Check if your cluster has enough resources for the deployment
4. **Ingress Issues**: Ensure your ingress controller is properly set up and the domain is correctly configured

## Security Considerations

1. Store sensitive information like Kubernetes configuration and Docker registry credentials as GitHub secrets
2. Use a service account with limited permissions for the deployment
3. Regularly rotate access tokens and credentials
4. Consider using RBAC to limit access to Kubernetes resources

```shell
kubectl create secret docker-registry cloud-raya-secret \
  --docker-server=$CLOUDRAYA_REGISTRY_URL \
  --docker-username=$CLOUDRAYA_REGISTRY_USERNAME \
  --docker-password=$CLOUDRAYA_REGISTRY_PASSWORD \
  --docker-email=hendisantika@yahoo.co.id
```

## Additional Resources

- [Kubernetes Documentation](https://kubernetes.io/docs/home/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)