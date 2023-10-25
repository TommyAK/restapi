# Spring Boot App

## About

This application represents an entity 'Person' for a REST API. This application includes the ability to perform CRUD operations on the entity with data validation to ensure duplicate information (e.g., usernames, emails, and phone numbers) is not used. An embedded H2 relational database is used to store information in-memory. User-based authentication using Spring Security 6 is implemented in-memory to protect data from users without the correct access level. All standard actuators have been exposed along with a custom one that displays the total number of persons in the database.

Spring dependencies used:
* Spring Web
* Spring Data JPA
* H2 Database
* Spring Security
* Spring Boot Actuator

## Running The App

The final built application can be found in: **app\build\libs\app-0.0.1-SNAPSHOT.jar**

Run using ``java -jar .\app-0.0.1-SNAPSHOT.jar``

Alternatively, you can build the application from source.

This application is designed to run locally and is accessible via: **localhost:8080**

## Example API Usage

I have created a collection of request methods using Postman which can be used to interact with the application and test its functionality. A link to this workpace can be accessed by the following URL: https://www.postman.com/tommya12/workspace/my-workspace/

![External Image](https://i.ibb.co/wwfD2fb/Screenshot-2023-10-25-190153.jpg)

## Test User Credentials:

**Admin User**

Username = Admin

Password = admin123



**Guest User**

Username = Guest

Password = guest123


## Core Endpoints

**/login**

**/actuator/personcount**

**/guest/users**

**/admin/users**

**/admin/users/add-person/**

**/admin/users/update-person/{id}**

**/admin/users/delete-person/{username}**


## Database

The H2 in-memory database can be accessed by **/h2-console**

Username = sa

Password = mypass
