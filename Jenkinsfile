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
                            sh 'mvn clean deploy -Ptestcontainers'
                        } else {
                            sh 'mvn clean verify -Ptestcontainers'
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
                container('maven') {
                    sh "mvn -Pdocker docker:build"
                }
            }
        }
        stage('Publish Docs') {
            steps {
                publishHTML(target: [allowMissing         : true,
                                     alwaysLinkToLastBuild: false,
                                     keepAll              : true,
                                     reportDir            : 'target/generated-docs/asciidoc',
                                     reportFiles          : 'api-guide.html',
                                     reportName           : 'REST API'
                   ]
                )
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
