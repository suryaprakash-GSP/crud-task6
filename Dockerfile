FROM openjdk:22-jdk
COPY target/crud-task6.jar crud-task6.jar
ENTRYPOINT ["java", "-jar","/crud-task6.jar"]
