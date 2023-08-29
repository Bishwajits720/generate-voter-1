FROM adoptopenjdk/openjdk17:alpine-jre
WORKDIR /app
COPY target/Pdf-Generator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
CMD ["java", "-jar", "app.jar"]
