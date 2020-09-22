# Shopping recommend platform web
## Overview
You can check the views and orders in this website
## Project Technical Overview
Project Technical Overview:
This application is developed in Spring Framework by using Spring Boot, Hibernate, Restful web services, Postman, Maven, PostgresSql, Docker, Amazon SQS, Amazon S3 and Tomcat.
###   Business Rules
- Objects: ShoppingPlatform,Consumer,Order
- Relationship
    - One Shopping can accept many items
    - An Consumer can place many orders and reviews
    - A role can apply on many users
- Only admin has the access to change the roles table
- Authorization
    - `user` is the baseline role for new user
    - `admin` is the highest role, admin can access any API
###   Structure
- root
    - mvc(model view controller)
    - consumer(consume internal messages)    
### Development Approaches    
1. Create ShoppingPlatform,Consumer,Order databse 
2. Use Hibernate to do the database schema migration
3. Implement persistence layer, service layer and controller
1. Utilize Postman to test APIs
1. Add AWS S3 as cloud storage
1. Process internal messages with AWS SQS
## Configure local environment
### setup local database with docker
Refer to postgres docker [docker image](https://hub.docker.com/_/postgres) for environment option.
```$xslt
docker run --name training -db -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${databaseName} -p 5431:5432 -d postgres
```
### database config
```aidl
database.driver = ${driverName}
database.dialect = ${dialect}
database.url = ${databaseURL}
database.user = ${userName}
database.password = ${password}
```
### migrate database schema
Refer to [flyway setup](https://flywaydb.org/documentation/migrations) find all [migration schema](mvc/src/main/resources/db/migration)
```aidl
mvn clean compile flyway:migrate -Ddatabase.url -Ddatabase.name -Ddatabase.user -Ddatabase.password
```
### test
```aidl
mvn test -Ddatabase.url -Ddatabase.name -Ddatabase.user -Ddatabase.password
```
### package
package is only for mvc app war file
1. add `<packaging>war</packaging>` into mvc `pom.xml`
2. compile `mvn clean compile package -DskipTests=true`
3. copy `ROOT.war` from `mvc/target` to `Cellar/tomcat/9.0.35/libexec/webapps/`
4. start tomcat
## API Guide
- Create new user with unique email, password, unique username
by using `{prefix}/user POST`
- User login with the username and password
by using `{prefix}/auth/ POST` to get a JWT token
- Notice: JWT token will be expired after one day
- Include the valid JWT token in the headers to request other APIs