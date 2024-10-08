// pipeline {
//     agent any
//
//     environment {
//         AWS_REGION = 'us-east-2'
//         ECR_REGISTRY = '626635406112.dkr.ecr.us-east-2.amazonaws.com'  // ECR registry URL
//         ECR_REPOSITORY = 'appointment-management-ecr-repo'  // ECR repository
//         IMAGE_TAG = "${env.BUILD_ID}"  // Jenkins build ID used as Docker image tag
//         KUBECONFIG = "${WORKSPACE}/.kube/config"  // Path to kubeconfig file in the workspace
//     }
//
//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git credentialsId: 'github-credentials', branch: 'main', url: 'https://github.com/anandakmagar/appointment-management-app.git'
//             }
//         }
//
//         stage('Build JAR') {
//             steps {
//                 script {
//                     sh 'mvn clean package'
//                 }
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     docker.build("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}")
//                 }
//             }
//         }
//
//         stage('Push Docker Image to ECR') {
//             steps {
//                 script {
//                     withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
//                         sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"
//                         // Add a tag step to explicitly tag the image as 'latest'
//                         sh "docker tag ${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG} ${ECR_REGISTRY}/${ECR_REPOSITORY}:latest"
//                         // Push the 'latest' tag
//                         docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:latest").push()
//                         docker.image("${ECR_REGISTRY}/${ECR_REPOSITORY}:${IMAGE_TAG}").push()
//                     }
//                     sh "docker rmi ${ECR_REGISTRY}/${ECR_REPOSITORY}:latest"
//                 }
//             }
//         }
//
//         stage('Deploy to Kubernetes') {
//             steps {
//                 script {
//                     // Generate kubeconfig dynamically using AWS credentials
//                     withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
//                         // Generate kubeconfig dynamically
//                         sh 'aws eks update-kubeconfig --name appointment-eks --region us-east-2'
//                         // Apply Kubernetes deployment YAML
//                         sh "kubectl apply -f ${WORKSPACE}/eks-deploy-k8s.yml --validate=false"
//                     }
//                 }
//             }
//         }
//
//     }
//
//     post {
//         always {
//             cleanWs()
//         }
//     }
// }
