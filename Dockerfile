#Base image with Java runtime
FROM openjdk:8-jdk-alpine

# Add jar file to container. JAR_FILE also provided as argument
ARG JAR_FILE='transactions-enrichment-service/target/*.jar'
ADD ${JAR_FILE} neo-service.jar

# Run the jar
ENTRYPOINT ["java","-javaagent:/sharedFiles/AppServerAgent/javaagent.jar","-Djava.security.egd=file:/dev/./urandom","-jar","/neo-service.jar"]
