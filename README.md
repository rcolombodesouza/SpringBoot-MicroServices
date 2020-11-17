# SpringBoot-MicroServices

Spring Boot Microservices + JWT + Eureka Server + MySql + Docker

Follow the steps to run this project:

- Java 11 or higher must be installed.
- Install Docker: https://www.docker.com/products/docker-desktop.
- Install Postman on your computer. https://www.postman.com/downloads/

1) Download the project to your computer.
2) Build the project using maven (mvn clean install).
3) Docker must be running in this step. On the root directory, execute "docker-compose docker-compose.yml --build -d". This will build the project on docker in different              containers.
4) Create two tables Book (Long id, String title, String author) and User (Long id, String username, String password, String role).
5) Add a few books into Book table.
6) To add an user, you will need to encrypt a password. To do that you can run the AuthenticationApplicationTests.java by changing the pre defined password which is there (in the    case it is root). This will give you an encrypted password. Add that to your database insertion.
7) Open Postman.
8) To login: http://localhost:8080/gateway/authentication/login -> POST
   Body: { "username":, "password": } We need to fetch, from the Header, the value Authorization. This is the encrypted and signed token to access the other microservices
9) To list the books: http://localhost:8080/gateway/book/v1/admin/book/list -> GET
   Add in the Header from the request a key "Authorization" and add the value you copied on the login process.
10) To list the logged user: http://localhost:8080/gateway/authentication/user/info -> GET
    Add in the Header from the request a key "Authorization" and add the value you copied on the login process. This project is intended for my personal study and I used the Dev       Dojo tutorials to help me on that.
