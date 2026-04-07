pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "vaibhav411007/newbank-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/iamvaibhavsutar/NewBank.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $DOCKER_IMAGE:latest ."
            }
        }

        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh "docker push $DOCKER_IMAGE:latest"
                }
            }
        }

        stage('Trigger Deployment') {
            steps {
                build job: 'newbank-deployment-pipeline'
            }
        }
    }
}
