pipeline {
    agent any

    environment {
        BACKEND_DIR = 'backend'
        FRONTEND_DIT = 'frontend'
    }

    stages {
        stage('Start only db'){
            steps {
                sh 'docker-compose up -d postgres'
                sh 'sleep 15'
            }
        }

        stage('Run Spring Boot tests') {
            steps {
                dir(BACKEND_DIR) {
                    sh '/mvnw test'
                }
            }
        }

        stage('Build images') {
            steps {
                sh 'docker-compse build'
            }
        }
    }
}