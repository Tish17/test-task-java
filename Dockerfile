FROM --platform=linux/amd64 amazoncorretto:11-alpine-jdk

COPY build/libs/test-task-java-0.0.1-SNAPSHOT.jar test-task-java.jar

CMD ["java", "-jar", "test-task-java.jar"]