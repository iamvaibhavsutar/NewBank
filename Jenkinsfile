pipeline {
    agent any

    environment {
        IMAGE_NAME = "vaibhav411007/newbank-backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE_FULL = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/iamvaibhavsutar/NewBank.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_FULL .'
            }
        }

        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    docker push $IMAGE_FULL
                    '''
                }
            }
        }

        stage('Trigger Deployment') {
            steps {
                build job: 'Newbank_deployment',
                      parameters: [
                        string(name: 'IMAGE_TAG', value: "${IMAGE_TAG}")
                      ]
            }
        }
    }
}
