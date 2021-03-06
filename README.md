# Credit-App-microservices
* [General info](#general-info)
* [Technological Stack](#technological-stack)
* [Endpoints](#endpoints)
* [Strat up Docker container](#strat-up-docker-container-as-one-application)
* [Using Kubernetes](#using-kubernetes)

## General info
The application consists of three smaller applications which makes it an application in a distributed structure.
The main unit is the Credit microservice which collects data from the user (in sequence: credit, customer, product) and
transfers according to the name to subsequent applications (in sequence: Customer, Product) where the data is processed and then saved in the database.
The application uses PostgreSQL as the database. Our API also offers to see all credits which are aggregated by credit.
The application also uses Docker for the convenience of its launch, where our entire application runs in Docker containers.
Everything is managed by Kubernetes.

The application uses <b>actuator</b> to facilitate checking during startup.
Endpoint documentation is available [here](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html).

(The actuator only has default endpoints enabled. The other endpoints are disabled)

## Technological Stack
### Back-End
* Java (11)
* Spring-Boot
* Spring-Data-JPA
* Spring-Web
* ModelMapper
* Actuator
#### Tests
* JUnit
* Mockito
* Spring RestDocs
### Data Base
* PostgreSQL
  - USER = admin
  - PASSWORD = password
  - DATABASE = credit_app
  - PORT = 5432
### Build Tools
* Maven
* Docker
* Kubernetes

## Endpoints

### Mainly endpoints

* Credit

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints)

```
Credit -> POST - http://localhost:8080/credit/create
Required body. Please read documentation below
```
[Detailed documentation - create credit](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints/create-credit)

```
Credit -> GET - http://localhost:8080/credit/all 
```
[Detailed documentation - get all credit](https://github.com/Kamil-java/credit-app-microservices/tree/master/credit/documentation/endpoints/get-all-credit-with-details)

### Additional endpoints

* Using them is not recommended!

* Customer

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints)

```
Customer -> POST - http://localhost:8080/customer/create 
Required body. Please read documentation below
```
[Detailed documentation - create customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints/customer-save)

```
Customer -> GET - http://localhost:8080/customer/all 
Required params. Please read documentation below
```
[Detailed documentation - get all customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/customer/documentation/endpoints/get-list-of-customer)

* Customer

[Full documentation](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints)

```
Customer -> POST - http://localhost:8080/product/create 
Required body. Please read documentation below
```
[Detailed documentation - create customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints/create-product)

```
Customer -> GET - http://localhost:8080/product/all
Required params. Please read documentation below
```
[Detailed documentation - get all customer](https://github.com/Kamil-java/credit-app-microservices/tree/master/product/documentation/endpoints/get-list-of-products)

## Strat up Docker container as one application

* Before you start
  - Make sure you have Docker service on your device.
  - If not:
   * [Doceker for Windows](https://docs.docker.com/docker-for-windows/install/)
   * [Docekr for Linux (Ubuntu)](https://docs.docker.com/engine/install/ubuntu/)
   * [Docker for MacOS](https://docs.docker.com/docker-for-mac/install/)

* All we have to do is open any cmd utility, then go to the project directory and call <b>docker-compose build</b>. Docker will build an image for the given sites. You do not need to have Apache Maven installed, everything will be taken care of by docker. If, on the other hand, you want to use the application quickly and easily, you should plug in <b>docker-compose up --build</b>.

* To run Docker without ROOT user, please follow the provided documentation which I attach [here](https://docs.docker.com/engine/install/linux-postinstall/).
  - (I set similar assumptions in the Dockerfile in each of the modules, unfortunately I use Windows and I was forced to comment on the given lines).

## Using Kubernetes

* Before you start
  - Make sure you have kubernetes service on your device.
  - If not, I recommend using the docker desktop tool and enabling the service for Kubernates there.
  - [Kubernetes Tutorial Docker Desktop](https://docs.docker.com/docker-for-windows/#kubernetes)  
  - You can also use a minikube tool which is also a great solution.
  - [Kubernetes Tutorial Minikube](https://minikube.sigs.k8s.io/docs/start/)
* (Kubernetes uses remote repositories from the docker hub)
* The solution from Kubernetes uses images posted on Docker Hub, we can also use local images.
First, build docker images and then in yaml files representing (* _app) containers in the kubernetes directory set the dependency <b>imagePullPolicy: Never</b>. After that, kubernetes will start searching our local images instead of looking for images in the repository. (Command invocation procedures do not change).

All you have to do is go to the main project directory and then call the command
```
kubectl apply -f ./kubernates
```
Po wykonaniu powyższej komendy należy uruchomić nasze usługi na wybranych portach.
  - Default for:
      * db = 5432 (PostgreSQL)
      * product = 8082
      * customer = 8081
      * credit = 8080
```
kubectl port-forward svc/product 8082:8082
kubectl port-forward svc/customer 8081:8081
kubectl port-forward svc/credit 8080:8080
```
