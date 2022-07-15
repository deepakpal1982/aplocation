# Coding Challenge : Location API
Location Service is a backend API in Java that allows mobile clients to retrieve and add suburb and postcode combinations. This REST-based location service is built on JDK 11, Gradle 4+ , and spring-boot 2.3.5.
This application 
# REST API Features
- An API that allows mobile clients to retrieve the suburb information by postcode.
- An API that allows mobile clients to retrieve a postcode given a suburb
name
- A secured API to add new suburb and postcode combinations (you'll have
 to work out how this should work)
- Some form of persistence

# The development notes : 
The Application API has been designed using the OpenAPI Specification (OAS). We write API descriptions using 3.0 of OAS (https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md) and generate models and API interfaces using the Swagger Codegen tool (Using Gradle plugin).

We also implemented a Global Exception handler to centralize the handling of the exceptions.

To adhere to best practices, This API implemented database migration using Flyway, maintaining and persisting data using repositories, and writing business logic to services. Using Spring HATEOAS assemblers, the controller enhances the responses with HATEOAS and ETags.

This app uses the Spring Security framework to restrict authenticated users from posting and deleting location data. In addition, a necessary CQRS and CSRF have been configured for protection. 

For Testing the API, A unit and integration tests using JUnit, the Spring test libraries, AssertJ, and Hamcrest have been written to catch the bugs and gaps.

Finally, We use the Spring Boot plugin to generate a Docker image for a location service app. 

# How to start up the REST API

    Build
   
    $ ./gradlew clean build

    Run Tests

    $ ./gradlew tests
   
    Docker Build
   
    $ ./gradlew bootBuildImage
   
    Docker Run
   
    $ docker run -p 8080:8080 deepakpal1982/ap-location 
# Futher work Improvements
   - Autocomplete functionality
   
 # Database
   Loads sample data from `data.sql` into in-memory h2 database.
- Once the data is loaded, we could search by postcode or suburb with sample data. And it's ready to accept new location. Please follow below steps.

 
 # API DOCS
   
     http://localhost:8080/swagger-ui/

  # TEST REPORT
    Test report can be found at locationms/build/reports/index.html

