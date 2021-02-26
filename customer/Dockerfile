FROM maven:3.6.3-openjdk-11 AS build
COPY src /customer/src
COPY pom.xml /customer
RUN mvn -f /customer/pom.xml clean package

FROM openjdk:11
#
# access for all
#RUN addgroup -S credit && adduser -S credit -G credit
#USER credit:credit
#
COPY --from=build /customer/target/customer-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
ENTRYPOINT ["java","-jar","customer-0.0.1-SNAPSHOT.jar"]