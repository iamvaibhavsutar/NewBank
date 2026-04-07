pipeline {
    agent any

    parameters {
        string(name: 'IMAGE_NAME', defaultValue: 'vaibhav411007/newbank-app', description: 'Docker Image Name')
        string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Docker Image Tag')
        string(name: 'GIT_BRANCH', defaultValue: 'main', description: 'Git Branch')
    }

    environment {
        REGISTRY = "docker.io"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: "${params.GIT_BRANCH}",
                url: 'https://github.com/iamvaibhavsutar/NewBank.git'
            }
        }

        stage('Build Backend') {
    steps {
        sh '''
        docker run --rm \
        -v $PWD:/app \
        -v $PWD/.m2:/root/.m2 \
        -w /app \
        maven:3.9.9-eclipse-temurin-21 \
        mvn clean package -DskipTests
        '''
    }
}

        stage('Build Docker Image') {
            steps {
                script {
                    IMAGE_FULL = "${REGISTRY}/${params.IMAGE_NAME}:${params.IMAGE_TAG}"
                }
                sh '''
                docker build --no-cache -t $IMAGE_FULL .
                '''
            }
        }

        stage('Push Image') {
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

        stage('Trigger Deployment') {
            steps {
                build job: 'Newbank_deployment',
                parameters: [
                    string(name: 'IMAGE_NAME', value: params.IMAGE_NAME),
                    string(name: 'IMAGE_TAG', value: params.IMAGE_TAG)
                ]
            }
        }
    }

    post {
        success {
            echo "✅ Production Pipeline Successful!"
        }
        failure {
            echo "❌ Production Pipeline Failed!"
        }
    }
}
