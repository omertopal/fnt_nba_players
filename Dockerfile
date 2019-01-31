
FROM java:8

EXPOSE 8084
ADD target/players-0.0.1-SNAPSHOT.jar players.jar
RUN bash -c 'touch /players.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /players.jar"]



#CMD ["java", "-Dspring.datasource.url=jdbc:oracle:thin:@oracle_xe_11g://springboot-oracle:1521:XE","-Djava.security.egd=file:/dev/./urandom","-jar","./players.jar"]
#ENTRYPOINT java -jar /app/application.jar
#VOLUME /players
#ADD target/players-0.0.1-SNAPSHOT.jar players.jar
#RUN bash -c 'touch /players.jar'
#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /players.jar"]