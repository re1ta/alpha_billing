FROM openjdk:19
EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres
ENV PASSWORD_DB=123
ENV USERNAME_DB=postgres

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "app.jar"]
