# File Storage System

A file storage system built with Java and Spring Boot that supports file upload, download, user authentication
Session Management using JWT, file sharing, and versioning.

## Things Implemented (Implemented All mandatory features given in Assignment along with Testing)

- User authentication (signup and login)
- Session Management
- File upload and download
- File versioning
- File sharing with other users
- MySQL for storing file data, metadata and user information
- Performed Unit Testing using Mockito

## Additional Features Aimed But Not Implemented

I have implemented all the mandatory requirements mentioned in the assignment.However, I aimed to include the following
features as well but could not due to time constraints. As a backend developer (freelancer) in a startup, 
I have critical deadlines to meet since the startup is seeking funding.

- Instead of Storing Files in AWS S3 or Local file management System and use that metadata as columns in Relational
  Databases. I directly stored files in MySQL database itself.
- Planned to create UI using HTML,CSS and React.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- JWT
- Spring Security
- Hibernate
- Lombok
- Mockito

## Change Database configuration in application.properties for running this project in your local