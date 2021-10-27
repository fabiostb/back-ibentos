# back-ibentos
Simple CRUD Spring Boot REST API

Event management
Ibento = 'Event' in Japanese :p

Spring Boot API REST

## Setup:
- Install Maven -> https://maven.apache.org/download.cgi
- Run:
```sh
mvn clean install
```
*If you have a good IDE it will do this on its own for you :) 
- DB <-- The data is stored in a MongoDb on the cloud (see IbentoDao). 
> Note: no configuration needed for DB.
- ``` Run IbentoApplication ```
- Go to here if you want to test the API:
```sh
localhost:8080/swagger-ui.htm
```
- Enjoy It!

@see (FRONT) https://github.com/fabiostb/front-ibentos
