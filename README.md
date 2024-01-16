# Project Title

Book my consulting API serves backend services to manage doctors appointment. It has been built on Spring platform using spring-boot framework.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need following softwares before running this applic
ation.

1. Java 8 SDK
2. Apache Maven 3.3 build tool
3. MySQl version 8.14
4. Your favorite IDE such as Intellij/Eclipse (optional)
5. Postman Client for REST APIs testing (optional)

### Installing and Deployment

A step by step guide that helps you get a development env running

1. Create database named `consultation` with associated owner `root` and execute the SQL commands in the file `/src/main/resources/DBLoadScript.sql`
2. Checkout repo
3. Go to the folder where the above project has been checked out
4. update the user name password in the appliaction.properties file
     ```
        spring.datasource.username={uname}
        spring.datasource.password={pwd}

5. Build the project using maven command `.header {
   background-color: purple;
   height: 70px;
   padding: 11px;
   display: flex;
   justify-content: space-between;
   align-items: center;
   }

.logo {
background-color: #ff7f7f;
height: 35px;
width: auto;
}

.login-button, .logout-button {
background-color: #1976d2; /* Primary color */
color: white;
padding: 6px 16px;
font-size: 0.875rem;
min-width: 64px;
box-sizing: border-box;
transition: background-color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, box-shadow 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, border-color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms;
font-family: "Roboto", "Helvetica", "Arial", sans-serif;
font-weight: 500;
line-height: 1.75;
border-radius: 4px;
text-transform: uppercase;
}

/* Additional styles for modal and tabs can be added here */
`. First time build will consume time as it downloads all dependencies.     ```
6. start the application using command `mvn spring-boot:run`

7. Access the following healthcheck url `http://localhost:8080/actuator/health` that should result in following
output

    ```
    {
        "status": "UP"
    }
    ```

8. Use the postman collection in the repo  `bookmyconsultation.postman_collection.json`
> Best wishes!
# bookmyconsultation
