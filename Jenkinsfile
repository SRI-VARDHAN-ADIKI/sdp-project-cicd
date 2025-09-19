pipeline {
    agent any

    stages {

        // ===== FRONTEND BUILD =====
        stage('Build Frontend') {
            steps {
                dir('frontend\\calisthenix') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        // ===== FRONTEND DEPLOY =====
        stage('Deploy Frontend to Tomcat') {
            steps {
                bat '''
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-frontend" (
                    rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-frontend"
                )
                mkdir "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-frontend"
                xcopy /E /I /Y frontend\\calisthenix\\dist\\* "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-frontend"
                '''
            }
        }

        // ===== BACKEND BUILD =====
        stage('Build Backend') {
            steps {
                dir('backend') {
                    bat '.\mvnw.cmd -DskipTests clean package'
                }
            }
        }

        // ===== BACKEND DEPLOY =====
        stage('Deploy Backend to Tomcat') {
            steps {
                bat '''
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-backend.war" (
                    del /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-backend.war"
                )
                if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-backend" (
                    rmdir /S /Q "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-backend"
                )
                copy backend\\target\\fitness-backend.war "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\fitness-backend.war"
                '''
            }
        }

        // ===== RESTART TOMCAT =====
        stage('Restart Tomcat') {
            steps {
                script {
                    // If installed as Windows Service
                    bat 'net stop Tomcat10 || exit 0'
                    bat 'net start Tomcat10 || exit 0'

                    // If ZIP install with bin scripts (only if they exist)
                    bat 'if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\shutdown.bat" ("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\shutdown.bat")'
                    bat 'if exist "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\startup.bat" ("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\startup.bat")'
                }
            }
        }
    }

    post {
        success {
            echo ' Deployment Successful!'
        }
        failure {
            echo ' Pipeline Failed.'
        }
    }
}
