# ebnelhaythem

https://start.spring.io/

cmd

#create the image

https://rauf-rahman.medium.com/connect-docker-and-mysql-in-right-way-95602f833cb0

doker run -d -p 3306:3306 --name=HomeServer1 --env="MYSQL_ROOT_PASSWORD"="test" mysql


list of img :

docker ps


#go inside the container  

docker exec -it <docker container> mysql -uroot -p

#or

(if problem of connection)

docker exec -it HomeServer1 bash

mysql -u root -p

use mysql

select host, user from user;

ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'Your New Strong Password';

FLUSH PRIVILEGES;

#create dataBaseUser

test is the password

ALTER USER 'root'@'localhost' IDENTIFIED BY 'test';


#run
mvn spring-boot:run

#hal
http://localhost:8081/explorer/index.html#uri=/

#swagger doc
http://localhost:8080/v2/api-docs

#swagger ui

