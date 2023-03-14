# base image
FROM openjdk:17

# copy the jar file usually inside the target folder, into the container
# calling in app.jar
COPY target/*.jar app.jar

# entry point command java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]