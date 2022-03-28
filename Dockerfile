FROM openjdk:8
EXPOSE 8080
ADD target/crypto-investment-api.jar crypto-investment-api.jar
ENTRYPOINT ["java", "-jar", "/crypto-investment-api.jar"]
