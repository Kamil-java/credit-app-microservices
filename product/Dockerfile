FROM maven:3.6.0-jdk-11 AS build
COPY src /product/src
COPY pom.xml /product
RUN mvn -f /product/pom.xml clean package

FROM openjdk:11
COPY --from=build /product/target/product-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
ENTRYPOINT ["java","-jar","product-0.0.1-SNAPSHOT.jar"]