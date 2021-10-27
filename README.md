# back-ibentos
Simple CRUD Spring Boot REST API

## Event management
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
- Go here if you want to test the API:
```sh
localhost:8080/swagger-ui.html
```
## Enjoy It!
@see (FRONT) https://github.com/fabiostb/front-ibentos

Json de test:
```json
{
  "name": "Test",
  "description": "updated",
  "startDate": "2021-10-20",
  "endDate": "2021-10-20"
}
```

Improvements (TODOs):
- i18n
- error handling (user/server/bd validations)
- logging
- mappeur with mapstruct json->pojo
- env variable in Spring Boot's application.properties
- +lombok
- ...
