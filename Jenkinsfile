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
                    sh 'mvn clean package'
                }
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
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"
                        docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:latest").push()
                    }
                    sh "docker rmi ${ECR_REGISTRY}/${ECR_REPOSITORY}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Use the kubeconfig stored as a secret in Jenkins
                    withKubeConfig(
                        caCertificate: '',
                        clusterName: '',
                        contextName: '',
                        credentialsId: 'K8S',
                        namespace: '',
                        restrictKubeConfigAccess: false,
                        serverUrl: ''
                    ) {
                        // Apply the Kubernetes YAML configuration file to deploy the app
                        sh "kubectl apply -f ${WORKSPACE}/eks-deploy-k8s.yml --validate=false"
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
