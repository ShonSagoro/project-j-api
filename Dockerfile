FROM openjdk:17

ADD target/juventudes.jar juventudes.jar

ENTRYPOINT ["java", "-jar","juventudes.jar"]
