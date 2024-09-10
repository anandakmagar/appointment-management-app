pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REGISTRY = '595000426613.dkr.ecr.us-east-2.amazonaws.com'  // ECR registry URL
        ECR_REPOSITORY = 'appointment-management-application-ecr'  // ECR repository
        IMAGE_TAG = "${env.BUILD_ID}"  // Jenkins build ID used as Docker image tag
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
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecr-credentials']]) {
                        // Authenticate Docker to ECR using AWS credentials
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                        // Push the Docker image to ECR
                        docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Set KUBECONFIG to point to the kubeconfig file in the workspace
                    env.KUBECONFIG = "${WORKSPACE}/.kube/config"

                    // Apply the Kubernetes deployment YAML
                    try {
                        echo "Applying Kubernetes deployment..."
                        sh "kubectl apply -f k8s/appointment-management-app-deployment.yml"

                        echo "Applying Kubernetes service..."
                        sh "kubectl apply -f k8s/appointment-management-app-service.yml"
                    } catch (Exception e) {
                        error("Deployment failed: ${e}")
                    }
                }
            }
        }
    }
}
