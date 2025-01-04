FROM openjdk:21-jdk-slim
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
