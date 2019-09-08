
FROM openjdk:11-jdk
EXPOSE 8084
ADD target/fantasy-0.0.1-SNAPSHOT.jar fantasy.jar
RUN bash -c 'touch /fantasy.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /fantasy.jar"]