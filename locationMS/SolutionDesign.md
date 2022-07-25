Designing APIs with OAS
You could directly start coding the API; however, this approach leads to many issues, such as frequent modifications, difficulty in API management, and difficulty in reviews specifically lead by non-technical domain teams. Therefore, you should use the design-first approach.
 there is no existing standard to govern the REST API implementation. OAS was introduced to solve at least the aspects of the REST API's specification and description. It allows you to write REST APIs in the YAML Ain't Markup Language (YAML) or JavaScript Object Notation (JSON) markup languages.
 OAS was earlier known as the Swagger Specification. However, OAS-supporting tools are still known as Swagger tools. Swagger tools are open source projects that help the overall development life cycle of REST APIs.
 
Tags are used for grouping the operations performed on the resources. 


Converting OAS to Spring code
 create a Spring project from scratch using Spring Initializr (https://start.spring.io/) 
 Step 1 – adding the Gradle plugin
 plugins {
  …
  …
  id 'org.hidetake.swagger.generator' version '2.18.2'
}
Step 2 – defining the OpenAPI config for code generation
Certain configurations, such as what model and API package names OpenAPI Generator's CLI should use, and other configurations can be defined in config.json (/src/main/resources/api/config.json):
{
  "library": "spring-mvc",
  "dateLibrary": "java8",
  "hideGenerationTimestamp": true,
  "modelPackage": "com.auspost.modern.api.model",
  "apiPackage": "com.auspost.modern.api",
  "invokerPackage": "com.auspost.modern.api",
  "serializableModel": true,
  "useTags": true,
  "useGzipFeature" : true,
  "hateoas": true,
  "withXml": true,
  "importMappings": {
      "Link": "org.springframework.hateoas.Link"
  }
}

Step 3 – defining the OpenAPI Generator ignore file
You can also add a .gitignore-like file to ignore certain code you don't want to generate. Add the following line of code to the file (/src/main/resources/api/.openapi-generator-ignore):

**/*Controller.java

We don't want to generate controllers. After its addition, only API interfaces and models will be generated. We'll add controllers manually.

Step 4 – defining a swaggerSources task in the Gradle build file
Now, let's add the logic to the swaggerSources task in the build.gradle file:
swaggerSources {
  def typeMappings = 'URI=URI'
  def importMappings = 'URI=java.net.URI'
  eStore {
    def apiYaml = "${rootDir}/src/main/resources/api                    /openapi.yaml"
    def configJson = "${rootDir}/src/main/resources/api                       /config.json"
    inputFile = file(apiYaml)
    def ignoreFile = file("${rootDir}/src/main/resources/api                            /.openapi-generator-ignore")
    code {
      language = 'spring'
      configFile = file(configJson)
      rawOptions = ['--ignore-file-override', ignoreFile,                     '--type-mappings',
          typeMappings, '--import-mappings', importMappings] as                         List<String>
      components = [models: true, apis: true, supportingFiles:                     'ApiUtil.java']
      //depends On validation // Should be uncommented once 
      //plugin starts supporting OA 3 validation
    }
 }
}
  
  Step 5 – adding swaggerSources to the compileJava task dependency
  
  compileJava.dependsOn swaggerSources.eStore.code
  
  Step 6 – adding the generated source code to Gradle sourceSets
sourceSets.main.java.srcDir "${swaggerSources.eStore.code.outputDir}/src/main/java"
sourceSets.main.resources.srcDir "${swaggerSources.eStore.code.outputDir}/src/main/resources"
  
  Step 7 – running the build for 
  
  generating, compiling, and building the code
  $ gradlew clean build

  Adding a Global Exception Handler
  
   class with @ControllerAdvice, which enables this class to trace all the request and response processing by the REST controllers and allows handling exceptions using @ExceptionHandler.

An Entity Tag (ETag) is an HTTP response header that contains a computed hash or equivalent value of the response entity and a minor change in the entity must change its value. HTTP request objects can then contain the If-None-Match and If-Match headers for receiving the conditional responses.
  
  
  Browsers restrict cross-origin requests from scripts for security reasons. For example, a call from http://mydomain.com to http://mydomain-2.com can't be made using a script. Also, an origin not only indicates a domain—in fact, it includes a scheme and a port too.
  Before hitting to any endpoint, the browser sends a preflight request using the HTTP method option to check whether the server would permit the actual request. This request contains the following headers:
  
  
  Add a CorsConfigurationSource bean that takes care of the CORS configuration using a CorsConfiguration instance.
Add the cors() method into HTTPSecurity in the configure() method. It uses CorsFilter if a corsFilter bean is added, else it uses CorsConfigurationSource. If neither is configured, then it uses the Spring MVC pattern inspector handler.
  
  Let's add cors() to HttpSecurity, as follows:

  Let's first understand what CSRF/XSRF is. CSRF or XSRF stands for Cross-Origin Request Forgery, which is a web security vulnerability. Let's assume you are a bank customer and are currently signed in to the bank. You may get an email and you click on a link in the email, or click on any malicious website's link that may contain a malicious script. This script then sends a request to your bank for a fund transfer. The bank then transfers the funds to a perpetrator's account because the bank thinks that the request has been sent by you as you are signed in. This is just an example.

To prevent such attacks, the application sends new unique CSRF tokens associated with the signed-in user for each new request. These tokens are stored in hidden form fields. When a user submits a form, the same token should be sent back with the request. The application then verifies the CSRF token and only processes the request if the verification is successful. This works because malicious scripts can't read the token due to the same origin policy,
  
  ou can disable CSRF protection for this web service by using csrf().disable() because it only provides REST endpoints
  
  
  
  Unit testing: Unit testing is performed by the developers to test the smallest unit (such as a class method) of code.
Integration testing: Integration testing is performed by the developers to test the integration of different layers of components.
Contract testing: Contract testing is performed by the developers to make sure any changes that are made to the API won't break the consumer code. The consumer code should always comply with the producer's contract (API). It is primarily required in microservices-based development.
End-to-End (E2E) testing: E2E testing is performed by the Quality Assurance (QA) team to test end-to-end scenarios, such as from the UI (consumer) to the backend.
User Acceptance Testing (UAT): This is performed by the business users from a business perspective, and may overlap with E2E testing.
You performed manual API testing by using the cURL and Postman tools earlier in this book. Every change requires the APIs to be completely tested – not only the impacted APIs. There is a reason for this. You may assume that it only impacts certain APIs, but what if your underlying assumptions are wrong? It may impact the other APIs that you skipped, which would lead to production issues. This can create panic and may require a release to be rolled over or a patch to be released with fix.

You don't want to be in such situations, so products have a separate QA team that ensures releases are delivered with the best possible quality. QA teams do the separate end-to-end and acceptance testing (along with business/domain users), apart from the testing that's done by the development team.

This extra assurance for high-quality deliverables need more time and effort. Therefore, software development cycles used to be huge in comparison to today's. Time to market (TTM) is a huge factor in today's competitive software industry. Today, you need faster release cycles. Moreover, quality checks, also known as testing, is an important and major part of release cycles.

You can reduce testing time by automating the testing process and making it an integral part of the CI/CD pipeline. CI stands for continuous integration, which means build > test > merge in a code repository. CD stands for continuous delivery and/or continuous deployment, both of which may be used interchangeably. Continuous delivery is a process where code is automatically tested and released (read and uploaded) to an artifact repository or container registry. Then, it can be picked and deployed to a production environment. Continuous deployment is one step ahead of continuous delivery in the pipeline, and code is deployed to the production environment once the previous steps have been successful. Products that don't release their code for public access use this approach, such as Facebook and Twitter. On the other hand, products/services that is available publicly, such as the Spring Framework and Java, use continuous delivery pipelines.

We'll automate the manual testing we have done so far in the next section.
  
  
  spring-boot-starter-test adds all the required test dependencies, not only for the unit tests, but also for the integration tests. 
  
  JUnit 5: JUnit 5 is a bundle of modules – the JUnit Platform, JUnit Jupiter, and JUnit Vintage:a. The JUnit Platform allows you to launch tests on JVM and its engine provides APIs for writing testing frameworks that run on the platform. The JUnit Platform consists of junit-platform-engine and junit-platform-commons.b. JUnit Jupiter provides the programming and extension models for writing tests and extensions. It has a separate library called junit-jupiter-engine that allows you to run Jupiter-based tests on JUnit Platform. It also provides the junit-jupiter, junit-jupitor-api, and junit-jupiter-params libraries.c. JUnit Vintage supports older versions of JUnit, such as versions 3 and 4. You are going to use latest version, which is 5, so you don't need it.You can find out more about JUnit at https://junit.org/.
AssertJ: AssertJ is a test assertion library that simplifies assertion writing by providing fluent APIs. It is also extendable. You can write custom assertions for your domain objects. You can find more about it at https://assertj.github.io/doc/.
Hamcrest: Hamcrest is another assertion library that provides assertions based on matchers. It also allows you to write custom matchers. You'll find an example of both of these in this chapter. Though AssertJ is preferable, you can choose one of them or both based on your use cases and liking. You can find out more about it at http://hamcrest.org/.
Mockito: Mockito is a mocking framework that allows you to mock objects (read dependencies) and stubs method calls. You can find out more about it at https://site.mockito.org/.





