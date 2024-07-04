# Spring Security OAuth 2.0 REST API Course

This [course](https://spring.academy/courses/spring-academy-secure-rest-api-oauth2) offered by Spring Academy comprehensively covers
the development of secure REST APIs using the OAuth 2.0 authorization protocol.


### GET One Cash Card by ID

Status should be: **200**
```shell
curl -H "Authorization: Bearer $SARAH_TOKEN" "http://localhost:8081/cashcards/99"
```

Status should be: **403**
```shell
curl -H "Authorization: Bearer $ESUEZ_TOKEN" "http://localhost:8081/cashcards/99"
```

### GET All Cash Cards

```shell
curl -H "Authorization: Bearer $READ_ONLY_TOKEN" "http://localhost:8081/cashcards"
```

### POST a cashcard

```shell
curl -X POST "http://localhost:8081/cashcards" \
-H "Authorization: Bearer $READ_WRITE_TOKEN" \
-H "Content-Type: application/json" \
-d '{"amount": 50.89, "owner": "sarah1"}'
```

## Final Lab
1. Start the Authorization Server Docker image.
```shell
docker run --rm --name sso -p 9000:9000 ghcr.io/vmware-tanzu-learning/course-secure-rest-api-oauth2-code/sso:latest
```

2. Request a token.
```shell
curl -X POST \
     -u cashcard-client:secret \
     -d "grant_type=client_credentials" \
     -d "scope=cashcard:read" \
     http://localhost:9000/oauth2/token
```
_You can decode the token using [jwt.io](https://jwt.io/)_

3. Export it as REQUESTED_TOKEN
```shell
export REQUESTED_TOKEN=<paste the token here>
```
4. Use the token in a request.
```shell
curl -H "Authorization: Bearer $REQUESTED_TOKEN" "http://localhost:8081/cashcards"
```