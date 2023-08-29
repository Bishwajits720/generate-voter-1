FROM openjdk:17-jre-alpine
WORKDIR /app
COPY pom.xml .
RUN apk add --no-cache curl tar
RUN curl -L https://github.com/AdoptOpenJDK/openjdk17-binaries/releases/download/jdk-17%2B35/OpenJDK17-jdk_x64_linux_hotspot_17_35.tar.gz | tar xz --strip-components=1 -C /usr/lib/jvm/default-jvm
RUN ["java", "-version"]
RUN java -XX:+UseSerialGC -jar /usr/share/spring-boot-cli/spring-cli-*.jar install /app
FROM openjdk:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/Pdf-Generator-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
CMD ["java", "-jar", "app.jar"]