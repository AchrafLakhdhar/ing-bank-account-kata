# Bank Account Kata
This bank account system is a spring boot application. The application exposes REST webservices to manage the clients, their accounts and the users which are the bank employees.
To run the embedded tomcat server, you must launch this command : **mvn spring-boot:run**

The source code is written with java 8 and the stack spring 5.2.6.RELEASE. The tests are written with JUnit5. We used the jwt token to secure the APIs, and Swagger to represent the APIs' documentation. The application is tested with MySQL as database server and for the tests we used an in memory database H2.

## User stories
We implemented the 4 user stories and we added some for manipulate the data and secure the application.

> As a bank, deposit money from a customer to his account, is allowed when superior to €0.01.
> As a bank, withdraw money from a customer account, is allowed when no overdraft used.
> As a bank, a customer can display its account balance.
> As a bank, a customer can display its account transactions history.
> As a bank, we can add, update, find or delete a customer.
> As a bank, we can have multiple clients.
> As a bank, a customer can create one or multiple account.
> As a bank, a customer can close an account.
> As a bank, we can add, update, find or delete a user (administrator).

The REST webservices are exposed in these three controllers located in the package **com.ing.controllers** :  ClientController, UserController and AuthenticationController. The implementation logic is in the service layer and the database access is done via the repositories (Spring Data JPA).


## Security
We implemented a jwt token solution to secure the application. A super user is created when the application is deployed for the first time, the default values of userName and password are **admin/admin** . But you can change them using the application.properties file as follow : 
security.admin.username=admin
security.admin.password=admin

The access token validity duration (in seconds) and the signing key can also be updated :
security.jwt.accessToken.validity=300
security.jwt.signingkey=signingKey

To get a token, you can consume the POST method with the url /security/token with a json object containing the userName and the password : 
{
	"userName":"admin",
	"password":"admin"
}
We added a context path in the file application.properties
server.servlet.context-path=/ING
So if the deployed port is 8080 , you can get an access token with this url : http://localhost:8080/ING/security/token

## Database
The deployement of the application was tested with the database server **MySQL 5.1.21**. To configure the database connection, you can update the connection properties in the file application.properties.

## Swagger 
The application contains multiple REST APIS, so in these cases, the swagger documentation is essential for the clients to facilitate the consumtion of the APIs.

You can get the swagger as json by accessing the url http://localhost:8080/ING/v2/api-docs or by using Swagger-UI by accessing this url with the browser http://localhost:8080/ING/swagger-ui.html.
**The security is disabled for the access of these URLs because they must be public.**

## Tests
We implemented multiple tests to coverage all the use cases, the expected and the unexpected scenarios using JUNIT5 and H2 in memory database to test the data access.

## Javadoc
You can access the application Javadoc via the file ing-bank-account-kata/doc/index.html.
