pipeline {
    agent any

    environment {
        IMAGE_NAME = "vaibhav411007/newbank-app"
        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE_FULL = "${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
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

        stage('Build UI (Dockerized Node)') {
            steps {
                sh '''
                docker run --rm \
                -u $(id -u):$(id -g) \
                -v $PWD/NewBank-UI:/app \
                -w /app \
                node:20 \
                sh -c "npm install && npm run build"
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build --no-cache -t $IMAGE_FULL .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-cred',
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

        stage('Checkout Deployment Repo') {
            steps {
                dir('deployment') {
                    git branch: 'main',
                    url: 'https://github.com/iamvaibhavsutar/newbank-deployment.git'
                }
            }
        }

        stage('Deploy via Docker Compose') {
            steps {
                dir('deployment') {
                    sh '''
                    export IMAGE=$IMAGE_FULL
                    docker-compose down || true
                    docker-compose up -d
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ FULL CI/CD PIPELINE SUCCESS"
        }
        failure {
            echo "❌ PIPELINE FAILED - CHECK LOGS"
        }
    }
}
