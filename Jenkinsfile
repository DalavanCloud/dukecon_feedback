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
                            sh 'mvn clean deploy -Pdocker docker:push'
                            build 'docker_restart_develop_latest'
                        } else {
                            sh 'mvn clean verify'
                        }
                    }
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
