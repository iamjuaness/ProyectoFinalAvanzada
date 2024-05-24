# Use an OpenJDK base image
FROM openjdk:17
# Set the working directory in the container
WORKDIR /app
# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle/ ./gradle/
# Copy the application source code
COPY src/ ./src/
# Build the application using Gradle
RUN ./gradlew build
# Set the port to expose
EXPOSE ${PORT}
<<<<<<< HEAD
ENTRYPOINT ["java","-jar","/app.jar"]
=======
# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "build/libs/unilocal-0.0.1-SNAPSHOT.jar"]
>>>>>>> parent of 60a91cf (Update DockerFile)
