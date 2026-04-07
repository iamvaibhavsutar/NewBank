pipeline {
    agent any

    environment {
        IMAGE_NAME = "vaibhav411007/newbank-backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE_FULL = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {

        stage('Clean Workspace') {
            steps { cleanWs() }
        }

        stage('Checkout Backend Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/iamvaibhavsutar/NewBank.git'
            }
        }

        stage('Build Backend (Dockerized Maven)') {
            steps {
                sh '''
                mkdir -p .m2

                docker run --rm \
                -u $(id -u):$(id -g) \
                -e HOME=/tmp \
                -v $PWD:/app \
                -v $PWD/.m2:/tmp/.m2 \
                -w /app \
                maven:3.9.9-eclipse-temurin-21 \
                mvn clean package -DskipTests -Dmaven.repo.local=/tmp/.m2
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build --no-cache -t $IMAGE_FULL .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $IMAGE_FULL
                    '''
                }
            }
        }

        stage('Trigger UI Pipeline') {
            steps {
                build job: 'Newbank_UI'
            }
        }
    }

    post {
        success { echo "✅ Backend Build Success" }
        failure { echo "❌ Backend Failed" }
    }
}
