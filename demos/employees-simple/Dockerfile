FROM eclipse-temurin:17
WORKDIR /app
COPY target/libs ./libs
COPY target/${project.build.finalName}.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]