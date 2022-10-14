FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080

COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

COPY logging/file.log.lck logging/file.log.lck

CMD ["java", "-jar", "app.jar"]