pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-2'
        ECR_REGISTRY = '595000426613.dkr.ecr.us-east-2.amazonaws.com'  // ECR registry URL
        ECR_REPOSITORY = 'appointment-management-ecr-repo'  // ECR repository
        IMAGE_TAG = "${env.BUILD_ID}"  // Jenkins build ID used as Docker image tag
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: 'github-credentials', branch: 'main', url: 'https://github.com/anandakmagar/appointment-management-app.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
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
                    // Apply the Kubernetes deployment and service YAML files
                    sh "kubectl apply -f k8s/backend-deployment.yaml"
                    sh "kubectl apply -f k8s/backend-service.yaml"
                }
            }
        }
    }
}
