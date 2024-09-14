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
                        docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}").push()
                    }
                    sh "docker rmi ${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Use the kubeconfig stored as a secret in Jenkins
                    withKubeConfig(
                        credentialsId: 'K8S',  // This should be the Jenkins secret with your kubeconfig
                        serverUrl: 'https://90BFFB881F7D38F052634E35DDF281B7.gr7.us-east-2.eks.amazonaws.com',  // Replace with your correct cluster API URL if necessary
                        clusterName: 'appt-eks',  // Match the cluster name in the kubeconfig
                        contextName: 'iam-root-account@appt-eks.us-east-2.eksctl.io',  // Correct context name from kubeconfig
                        namespace: 'default'  // Use the appropriate namespace
                    ) {
                        // Debugging step: View the kubeconfig being used
                        sh "kubectl config view"

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
