pipeline {
    agent { label  "agentfarm" }
    tools{
        maven 'maven 3'
    }
    stages {
        stage('Delete the Workspace') {
            steps {
                sh 'sudo rm -rf $WORKSPACE/*'
            }
        }
		stage('Installing JDK') {
            steps {
				script {
					def exists = fileExists '/usr/bin/java'
					if(exists){
						echo "Skipping JDK installation - already installed"
					} else{
						sh 'sudo apt-get install openjdk-8-jdk -y'
					}
				}			
            }
        }
		stage('Download Java Source Code') {
            steps {
				git credentialsId: 'hp', url: 'git@bitbucket.org:technotrainer-mod-dev/java-project.git'
            }
        }        
        stage ('Execute Tests and Build Artifact') {
            steps {
              sh '''                
                  mvn -version
                  mvn clean install
              '''
            }
            post {
                success {
                    //On success of job, saving unit test report.
                    junit 'target/surefire-reports/**/*.xml'
                    
                    //stashing artifact, it can be unstash, if required to access file in later satges and steps.
                    stash(name: "tt-java-artifact", includes: 'target/*.jar')
                    
                    //Saving artifact (jar file) in Jenkins artifact.
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
	    }
         stage('Deploy to staging') {
            steps {                
                unstash("tt-java-artifact")
                withCredentials([sshUserPrivateKey(credentialsId: 'python', keyFileVariable: 'privatefile', passphraseVariable: '', usernameVariable: 'username')]) {             
                        // Copying generated artifact to Deployment server machine.
                        sh 'scp -o StrictHostKeyChecking=no -i ${privatefile} ./target/*.jar ubuntu@3.12.104.242:~/app.jar'
                        
                        // Copying deploy.sh to Deployment server machine, so that automated deployment of an artifact can be done.
                        sh 'scp -o StrictHostKeyChecking=no -i ${privatefile} ./deploy.sh ubuntu@3.12.104.242:~/'
                        
                        //Triggering deployment
                        sh 'ssh -o StrictHostKeyChecking=no -i ${privatefile} ubuntu@3.12.104.242 bash ~/deploy.sh'
                    }
                }
            }
        }		    
	}
