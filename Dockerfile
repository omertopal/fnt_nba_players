FROM java:8
EXPOSE 8084
VOLUME /players
ADD target/players-0.0.1-SNAPSHOT.jar players.jar
RUN bash -c 'touch /players.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /players.jar"]