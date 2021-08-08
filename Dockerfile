FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8088
COPY target/*.jar content-submit-api.jar
CMD ["java", "-jar", "content-submit-api.jar"]

#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c","", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /content-submit-api.jar" ]
#WORKDIR /app
#COPY target/*.jar /app/content-submit-api.jar
#ENTRYPOINT["java", "-jar", "/app/content-submit-api.jar"]