node  {
    def app  
    
	def mvnTool = tool 'maven_3.3.9'

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
        checkout scm
    }
    
    stage('Compile stage') {        
       // execute maven
		sh "${mvnTool}/bin/mvn clean install" 
    }
    
    
    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
        app = docker.build("omertopal/fnt_nba_players")
    }
    stage('Test image') {
        /* Ideally, we would run a test framework against our image.
         * For this example, we're using a Volkswagen-type approach ;-) */

        app.inside {
            sh 'echo "Tests passed"'
        }
    }
    stage('Run Image'){
    	sh "docker run -it -d -v /tmp:/tmp -p 8085:8084 --name application application"
    }    
    //stage('Push image') {
        /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
     //   docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
      //      app.push("${env.BUILD_NUMBER}")
       //     app.push("latest")
       // }
    //}	    
    stage('Deploy Image'){
    }	
}