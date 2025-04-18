# Spring Boot Thymeleaf Sample

This is a sample project demonstrating the use of **Spring Boot** with **Thymeleaf** for building a simple web
application. The application manages a list of customers, allowing users to perform CRUD (Create, Read, Update, Delete)
operations.

## Features

- **Spring Boot** for backend development.
- **Thymeleaf** for server-side rendering of HTML templates.
- **Tailwind CSS** for responsive and modern UI design.
- **H2** in-memory database for storing customer data.
- **Docker Compose** for containerized application setup.

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker (optional, for containerized deployment)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/hendisantika/spring-boot-thymeleaf-sample2.git
cd spring-boot-thymeleaf-sample2
```

2. Database Setup
   The application uses H2 in-memory database by default, so no additional setup is required.
   The database configuration can be found in `src/main/resources/application.yml`.

3. Build and Run the Application

   **Option 1: Run with Maven**
    ```shell
    mvn clean install
    mvn clean spring-boot:run
    ```

   **Option 2: Run with Docker**
    ```shell
    mvn clean package
    docker-compose up -d
    ```

   When running with Docker, the `customer-photos` directory is mounted as a volume to persist photos outside the
   container. This ensures that uploaded customer photos are accessible from the web application.

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
5. H2 Database
6. Docker Compose

### Project Structure

`src/main/java` - Contains the Java source code.
`src/main/resources/templates` - Thymeleaf templates for the UI.
`src/main/resources/application.yml` - Configuration file for the application.
`Dockerfile` - Docker configuration for building the application image.
`docker-compose.yml` - Docker Compose configuration for running the application with volume mounts.

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
