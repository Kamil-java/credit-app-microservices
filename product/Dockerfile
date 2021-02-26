FROM maven:3.6.3-openjdk-11 AS build
COPY src /product/src
COPY pom.xml /product
RUN mvn -f /product/pom.xml clean package

FROM openjdk:11
#
# access for all
#RUN addgroup -S credit && adduser -S credit -G credit
#USER credit:credit
#
COPY --from=build /product/target/product-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
ENTRYPOINT ["java","-jar","product-0.0.1-SNAPSHOT.jar"]