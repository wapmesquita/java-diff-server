# Java Web Diff Server

Web application to compare 2 text files and respond the differences between them.

## Technologies

|Technology|Reference|
|----------|-------|
|Java 8|http://www.oracle.com/technetwork/pt/java/javase/documentation/index.html|
|Maven 3.3.9 |http://www.oracle.com/technetwork/pt/java/javase/documentation/index.html|
|Jersey 2.24.1|https://jersey.java.net/documentation/latest/user-guide.html|
|JUnit 4.12|http://junit.org/junit4/|

## Development
*The commands listed below should be executed in the root folder of the project*

### Compiling
To compile the application you need to execute the following maven command

    mvn clean install

### Run
To start the application you need to execute the following maven command

    mvn jetty:run -pl web1

When the terminal shows **[INFO] Started Jetty Server** the application is ready to use.

### Structure
The project has 3 modules: model, core and web.

#### MVC
It is a MVC project.

* The application is configured by class ApplicationConfig
* The endpoints are defined on classes \*Resource; The Resource classes invokes methods from \*Controller classes
* The Controller classes are responsible for invoke the Service classes.

#### Model
Model is the module responsible for the Data Persistence. It might only define how to store and store the Data.

It has 2 main packages: *(io.github.wapmesquita.diffmodel)* model and repositories. In package model you can see the domain classes; and in package repositories you can see the classes that persist the entities on some kind of database.

#### Core
Core is the module responsible for the logic of the application, it might be as covered as it is possible by the tests.
The main classes are in the package *io.github.wapmesquita.diffservice.service*

#### Web
Web is the module that contains the access endpoints to the application and the Data Transfer Objects that the application should receive and respond.

**The web application receive and respond JSON.**

## Usage
*This section understand the application is runnning. See the Development section for more information.*

### Uploading
The web application receive the both text files on the following PUT/JSON endpoints:

* (PUT) - application/json - [HOST]/v1/diff/[ID]/left
* (PUT) - application/json - [HOST]/v1/diff/[ID]/right

The data should follow the pattern below:

    {"encodeString":"[BASE64]"}

See the curl upload as example:

    curl -H 'Content-Type: application/json' -X PUT -d '{"encodedString":"YQ0KYg0KYw0KNQ0KNA0KZg0KaA0K"}' http://localhost:8080/v1/diff/1/right

    curl -H 'Content-Type: application/json' -X PUT -d '{"encodedString":"YQpiCmMKZAplCmYKaAo="}' http://localhost:8080/v1/diff/1/left

### Requesting the DIFF

The endpoint to request the diff is *[HOST]/v1/diff/[ID]*. You just need to execute a GET request to this URL and will receive a JSON as response. See the curl example below:

    curl -H 'Content-Type: application/json' -X GET  http://localhost:8080/v1/diff/1

The response should be like this:

    {
       "differentLines":[
          {
             "leftValue":"d",
             "rightValue":"5",
             "equal":false,
             "lineNumber":4
          },
          {
             "leftValue":"e",
             "rightValue":"4",
             "equal":false,
             "lineNumber":5
          }
       ],
       "linesLeft":[
          "a",
          "b",
          "c",
          "d",
          "e",
          "f",
          "h"
       ],
       "linesRight":[
          "a",
          "b",
          "c",
          "5",
          "4",
          "f",
          "h"
       ],
       "diffResult":"SAME_SIZE"
    }

##### JSON Structure

###### Line

    {
     "leftValue":"d", //String
     "rightValue":"5", //String
     "equal":false, //Boolean
     "lineNumber":4 //Integer
    }

###### Answer

    {
       "differentLines":[ Line Array],
       "linesLeft":[ String Array ],
       "linesRight":[ String Array ],
       "diffResult": (EQUAL|NOT_EQUAL_SIZE|SAME_SIZE)
    }
