# vix-exercise

This application is a Spring Boot application. It uses a controller to integration into a API contains services
that interact with a  MySQL database (db properties are in db.propeties file).

There is also an Observable class that POLLS http://vix.digital for a response.

I have include a configure-mysql file to aid the setup of a simple table (id, Status, ModifiedDate)

Spring Boot uses tomcat so the initial page can be loaded at http://localhost:8080/polling/polling-record-form

Finally you can build the project using Maven (mcn clean install). Then run spring boot (mvn spring-boot:run)
