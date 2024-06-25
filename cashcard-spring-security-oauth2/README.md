# Spring Security OAuth 2.0 REST API Course

This [course](https://spring.academy/courses/spring-academy-secure-rest-api-oauth2) offered by Spring Academy comprehensively covers
the development of secure REST APIs using the OAuth 2.0 authorization protocol.


### GET One Cash Card by ID

```shell
curl -u user:{password-generated-by-spring} "http://localhost:8081/cashcards/1"
```

### GET All Cash Cards

```shell
curl -u user:{password-generated-by-spring} "http://localhost:8081/cashcards"
```

### POST a cashcard

```shell
curl -u user:{password-generated-by-spring} -X POST "http://localhost:8081/cashcards" \
-H "Content-Type: application/json" \
-d '{"amount": 50.89, "owner": "sarah1"}'
```