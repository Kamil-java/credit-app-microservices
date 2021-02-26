FROM maven:3.6.0-jdk-11 AS build
COPY src /credit/src
COPY pom.xml /credit
RUN mvn -f /credit/pom.xml clean package

FROM openjdk:11
COPY --from=build /credit/target/credit-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","credit-0.0.1-SNAPSHOT.jar"]