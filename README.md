# ebnelhaythem

https://start.spring.io/

#tech
spring boot 2.4.2, java 17, maven 3.8.4, rest, jwt, Junit, lombok, mapstruct, hibernate, MySql 5.7, swagger, docker, 
docker_compose

#hal
http://localhost:8081/explorer/index.html#uri=/

#swagger doc
http://localhost:8080/v2/api-docs

#swagger ui
http://localhost:8080/swagger-ui.html

#run app 
mvn spring-boot:run

#start project using docker-compose
docker-compose up -d

#create token 
1-create user/password :
post http://localhost:8080/v1/users/signup with object
{
"username":"***",
"password":"***"
}
and  header   Content-Type:application/json
2-login with the same user  and header
Get http://localhost:8080/login
the token will be in the response in the header

#use profile :
mvn spring-boot:run -Dspring-boot.run.profiles=dev
or add to Environment Variable : spring.profiles.active=dev


#logs
#list containrs 
docker ps
#show logs
docker logs [OPTIONS] CONTAINER


