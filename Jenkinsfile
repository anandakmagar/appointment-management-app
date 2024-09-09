pipeline {
    agent any

    environment {
        ECR_REGISTRY = '595000426613.dkr.ecr.us-east-2.amazonaws.com'
        ECR_REPOSITORY = 'appointment-management-ecr-repo'
        IMAGE_TAG = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/anandakmagar/appointment-management-app.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // Authenticate Docker to ECR using the IAM role
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                    // Build and push the Docker image to ECR
                    docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}").push()
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh "kubectl apply -f k8s/appointment-management-app-deployment.yaml"
                    sh "kubectl apply -f k8s/appointment-management-app-deployment.yaml"
                }
            }
        }
    }
}
