# SpringBoot-MicroServices

Spring Boot Microservices + JWT + Eureka Server + MySql + Docker

Follow the steps to run this project:

Java 11 or higher must be installed.
Download the project to your computer.
Install Docker: https://www.docker.com/products/docker-desktop
Open the Microservice "core" in your terminal and execute the command: docker-compose -f stack.yml up. This will configure MySql running on Docker. (The user and password were defined as "root").
Import this project into your IDE. I used Intellij for that.
Create two tables Book (Long id, String title, String author) and User (Long id, String username, String password, String role).
Add a few books into Book table.
To add an user, you will need to encrypt a password. To do that you can run the AuthenticationApplicationTests.java by changing the pre defined password which is there (in the case it is root). This will give you an encrypted password. Add that to your database insertion.
Install Postman on your computer. https://www.postman.com/downloads/
Start the microservices in the following order:
discovery
gateway / authentication
book
Now we can make user of Postman. I created only 3 requests
To login: http://localhost:8080/gateway/authentication/login -> POST
Body: { "username":, "password": } We need to fetch, from the Header, the value Authorization. This is the encrypted and signed token to access the other microservices
To list the books: http://localhost:8080/gateway/book/v1/admin/book -> GET
Add in the Header from the request a key "Authorization" and add the value you copied on the login process.
To list the logged user: http://localhost:8080/gateway/authentication/user/info -> GET
Add in the Header from the request a key "Authorization" and add the value you copied on the login process. This project is intended for my personal study and I used the Dev Dojo tutorials to help me on that.
