
# Contacts App Flow (Vue.js / Kotlin)

## Table of Contents

- [Technologies](#technologies)
- [Running the Project](#running-the-project)
  - [Running the Backend](#running-the-backend)
  - [Running the Frontend](#running-the-frontend)
- [Project Structure](#project-structure)
- [API endpoints](#api-endpoints)

---

## Technologies

### Backend:
- **Kotlin**
- **Ktor** (Web Framework)
- **SQLite** (Database)
- **Exposed** (ORM)
- **Koin** (Dependency Injection)
- **CORS** support for cross-origin requests

### Frontend:
- **Vue.js** (JavaScript Framework)
- **Pinia** (State Management)
- **Axios** (HTTP Client)

---

## Running the Project

### Running the Backend

1. **Install Java JDK (Version 17 or higher)**: Ensure you have the appropriate JDK installed.

   You can download JDK from:
   - [Oracle](https://www.oracle.com/java/technologies/downloads/)
   - [AdoptOpenJDK](https://adoptopenjdk.net/)
   - [OpenJDK](https://openjdk.java.net/)
2. Navigate to the `api` folder.
    ```bash
    cd ./api
    ```
3. Run the following commands:

   ```bash
   ./gradlew clean build
   ./gradlew run
   ```

The Ktor server will start on `http://localhost:8080`.

The database (**contacts.db**) is provided with some data in the root folder.

### Running the Frontend

1. **Install Node.js**: Ensure you have Node.js installed. You can download it from [here](https://nodejs.org/).

2. Navigate to the `frontend` folder.
    ```bash
    cd ./frontend
    ```
3. Run:

   ```bash
   npm install
   npm run dev
   ```

The Vue.js development server will start on `http://localhost:5173`.

---

## Project Structure

### Backend (Ktor API)

```bash
api/app
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── org/example/
│   │   │       ├── App.kt                 # Ktor entry point
│   │   │       ├── ContactsTable.kt       # Database configurations
│   │   │       ├── Controller.kt          # Route controllers
│   │   │       └── Service.kt             # DB operations (CRUD)
│   │   │       └── Router.kt              # Application routes
│   │   ├── resources/
│   │       └── application.conf           # Ktor configuration
└── build.gradle.kts                       # Gradle build configuration
```

### Frontend (Vue.js)

```bash
frontend/
├── public/
├── src/
│   ├── assets/                            # Static assets
│   ├── components/                        # Vue components
│           ├──elements/                   # Custom elements
│   ├── store/                             # Pinia store for state management
│   ├── pages/                             # Application views
│   └── App.vue                            # Main Vue component
```
## API Endpoints

#### a. Get All Contacts

- **Endpoint**: `GET /`
- **Description**: Fetch all contacts.

#### b. Create New Contact

- **Endpoint**: `POST /`
- **Description**: Create a new contact.
- **Body** (multipart/form-data):
    - `contact data`
    - `image` (optional, file)

#### c. Update Contact

- **Endpoint**: `PUT /{id}`
- **Description**: Update an existing contact.
- **Body** (multipart/form-data):
    - `contact data`
    - `image` (optional, file)

#### d. Delete Contact

- **Endpoint**: `DELETE /{id}`
- **Description**: Delete a contact by ID.

---