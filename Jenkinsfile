pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REGISTRY = '626635406112.dkr.ecr.us-east-2.amazonaws.com'  // ECR registry URL
        ECR_REPOSITORY = 'appointment-management-ecr-repo'  // ECR repository
        IMAGE_TAG = "${env.BUILD_ID}"  // Jenkins build ID used as Docker image tag
        KUBECONFIG = "${WORKSPACE}/.kube/config"  // Path to kubeconfig file in the workspace
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: 'github-credentials', branch: 'main', url: 'https://github.com/anandakmagar/appointment-management-app.git'
            }
        }

        stage('Build JAR') {
            steps {
                script {
                    // Run Maven to build the project and generate the JAR file
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the generated JAR file
                    docker.build("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // Use AWS credentials stored in Jenkins to authenticate with ECR
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
                        // Authenticate Docker to ECR using AWS credentials
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                        // Push the Docker image to ECR
                        docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}").push()
                    }

                    // Optional: Remove Docker images to free space after pushing
                    sh "docker rmi ${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Ensure kubeconfig file exists
                    if (fileExists(env.KUBECONFIG)) {
                        // Apply the Kubernetes deployment YAML
                        try {
                            echo "Applying Kubernetes deployment..."
                            sh "kubectl apply -f k8s/appointment-management-app-deployment.yml --validate=false"

                            echo "Applying Kubernetes service..."
                            sh "kubectl apply -f k8s/appointment-management-app-service.yml --validate=false"
                        } catch (Exception e) {
                            error("Deployment failed: ${e}")
                        }
                    } else {
                        error("kubeconfig file not found at: ${env.KUBECONFIG}")
                    }
                }
            }
        }
    }
}
