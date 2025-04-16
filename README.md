# Spring Boot Thymeleaf Sample

This is a sample project demonstrating the use of **Spring Boot** with **Thymeleaf** for building a simple web
application. The application manages a list of customers, allowing users to perform CRUD (Create, Read, Update, Delete)
operations.

## Features

- **Spring Boot** for backend development.
- **Thymeleaf** for server-side rendering of HTML templates.
- **Tailwind CSS** for responsive and modern UI design.
- **MySQL** as the database for storing customer data.
- **Docker Compose** for containerized MySQL setup.

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker (for running MySQL)
- MySQL 8 or higher (if not using Docker)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/hendisantika/spring-boot-thymeleaf-sample2.git
cd spring-boot-thymeleaf-sample2
```

2. Set Up the Database
   Using Docker Compose
   Run the following command to start a MySQL container:
    ```shell
    docker-compose up -d
    ```

   Manual Setup
   If you prefer to set up MySQL manually:
   Create a database named sampledb.
   Update the database connection details in `src/main/resources/application.yml.`

3. Build and Run the Application
    ```shell
    mvn clean install
    mvn clean spring-boot:run
    ```

   The application will be available at http://localhost:8080.

### Application Endpoints

1. Home Page: `/` - Displays the list of customers.
2. Add Customer: `/create` - Form to add a new customer.
3. View Customer: `/customer/{id}` - View details of a specific customer.
4. Edit Customer: `/customer/{id}/edit` - Edit details of a specific customer.
5. Delete Customer: `/customer/{id}/delete` - Delete a specific customer.

### Technologies Used

1. Java 17
2. Spring Boot 3.x
3. Thymeleaf
4. Tailwind CSS
5. MySQL
6. Docker Compose

### Project Structure

`src/main/java` - Contains the Java source code.
`src/main/resources/templates` - Thymeleaf templates for the UI.
`src/main/resources/application.yml` - Configuration file for the application.
`docker-compose.yaml` - Docker Compose configuration for MySQL.

### Screenshots

#### Home Page
Displays the list of customers with options to view, edit, or delete.

![Home Page](img/list.png "list page")

#### Add Customer
Form to add a new customer.

![Add Customer](img/home.png "Add Customer")

![Add Customer](img/add.png "Add Customer")

#### View Customer
Displays detailed information about a specific customer.

![View Customer](img/edit.png "View Customer")

### License
This project is licensed under the MIT License. See the LICENSE file for details.

### Author
Developed by Hendi Santika.
https://s.id/hendisantika