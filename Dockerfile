FROM openjdk:15
EXPOSE 5001

COPY target/rms-submission-service-*.jar /rms-submission-service.jar

ENTRYPOINT ["java", "-jar", "/rms-submission-service.jar"]
