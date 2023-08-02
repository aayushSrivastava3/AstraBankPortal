FROM openjdk:8
EXPOSE 3000
ADD target/spring-astra-bank-portal.jar spring-astra-bank-portal.jar
ENTRYPOINT ["java","-jar","/spring-astra-bank-portal.jar"]