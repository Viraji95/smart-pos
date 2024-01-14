# Smart POS 

## Overview


https://github.com/Viraji95/smart-pos/assets/139902658/748bc4ca-b5d5-42b9-a26f-69d0b703657e


The Smart POS (Point of Sale) project is a Java-based application developed using JavaFX for the user interface and PostgreSQL as the database management system. This Point of Sale system is designed to streamline the sales process, manage inventory, and provide a user-friendly interface for both customers and staff.

## Features

- User-friendly graphical user interface (GUI) built with JavaFX.
- Efficient sales management system.
- Integration with PostgreSQL for secure and reliable data storage.

## Prerequisites

Before running the Smart POS application, ensure you have the following software installed on your machine:

- Java Development Kit (JDK) - [Download](https://www.oracle.com/java/technologies/javase-downloads.html)
- JavaFX SDK - Included with recent versions of JDK.
- PostgreSQL Database - [Download](https://www.postgresql.org/download/)

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/viraji95/smart-pos-project.git
    ```

2. Set up the PostgreSQL database:
    - Create a new database named `smart_pos`.
    - Update the database connection properties in the `application.properties` file.

3. Build and run the application:

    ```bash
    cd smart-pos-project
    ./gradlew run
    ```

## Configuration

Adjust configuration settings in the `application.properties` file:

```properties
# Database Configuration
db.url=jdbc:postgresql://localhost:5432/smart_pos
db.username=your_username
db.password=your_password
````
## License
This project is licensed under the MIT License.


