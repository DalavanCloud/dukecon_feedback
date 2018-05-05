#!/usr/bin/env groovy

pipeline {
    agent {
        label "jenkins-maven"
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Build') {
            steps {
                container('maven') {
                    script {
                        if (env.BRANCH_NAME == "develop") {
                            sh 'mvn clean deploy'
                        } else {
                            sh 'mvn clean verify'
                        }
                    }
                }
            }
        }
        stage('Promote Docker and restart latest') {
            when {
                branch 'develop'
            }
            steps {
                withMaven {
                    sh "mvn -Pdocker docker:build docker:push"
                    build 'docker_restart_develop_latest'
                }
            }
        }
    }
    post {
        failure {
            // notify users when the Pipeline fails
            mail to: 'gerd@aschemann.net',
                    subject: "Failed DukeCon Feedback Pipeline: ${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}
