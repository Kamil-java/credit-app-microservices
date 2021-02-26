# Credit-App-microservices
* [General info](#general-info)
* [Technological Stack](#tech)
* [Endpoints](#endpoints)

## General info
the application consists of three smaller applications which makes it an application in a distributed structure.
The main unit is the Credit microservice which collects data from the user (in sequence: credit, customer, product) and
transfers according to the name to subsequent applications (in sequence: Customer, Product) where the data is processed and then saved in the database.
The application uses PostgreSQL as the database. Our API also offers to see all credits which are aggregated by credit.
The application also uses Docker for the convenience of its launch, where our entire application runs in Docker containers.
Everything is managed by Kubernetes.

## Technological Stack
### Back-End
* Java (11)
* Spring-Boot
* Spring-Data-JPA
* Spring-Web
* ModelMapper
#### Tests
* JUnit
* Mockito
### Data Base
* PostgreSQL
### Build Tools
* Maven
* Docker
* Kubernetes

## Endpoints
* Credit

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints)

```
Credit -> POST - http://localhost:8080/credit/create 
```
[Detailed documentation - create credit](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints/create-credit)

```
Credit -> GET - http://localhost:8080/credit/all 
```
[Detailed documentation - get all credit](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints/get-all-credit-with-details)

* Customer

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints)

```
Customer -> POST - http://localhost:8080/customer/create 
```
[Detailed documentation - create customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints/customer-save)

```
Customer -> GET - http://localhost:8080/customer/all 
```
[Detailed documentation - get all customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints/get-list-of-customer)

* Customer

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints)

```
Customer -> POST - http://localhost:8080/product/create 
```
[Detailed documentation - create customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints/create-product)

```
Customer -> GET - http://localhost:8080/product/all 
```
[Detailed documentation - get all customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints/get-list-of-products)
