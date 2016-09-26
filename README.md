# Homework

## Steps

### Implementing a REST API
At first, I needed to have a working application. So I modified the LoanApplication class to startup using Spring Boot.

I created a REST Endpoint in order to provide an interface to the RiskService implementation.

### Documenting the API
I added the Swagger library that creates the auto-documentation of the REST API. 
After starting up the project, the JSON documentation can be viewed at:

http://localhost:8080/v2/api-docs

And the Swagger interface to check the endpoints and make the queries is at:

http://localhost:8080/swagger-ui.html


## Execute the application
It can be executed from Intellij IDEA or Eclipse as a Java application.
It can also been executed from Maven using the Spring Boot plugin (Recommended).

Execute from Maven:

> mvn spring-boot:run

Execute as a Java application:

> java -jar transactions-queue-test.jar

## Testing
In order to setup the test environment, I used the Spring test libraries.

The class that provides the date and time is a bean inject into RiskService, so it can be mocked for the test using MockBean. The integration with Spring and Mockito.



