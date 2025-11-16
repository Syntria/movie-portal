# 🎬 Movie Portal

<img width="1440" height="900" alt="Main Page" src="https://github.com/user-attachments/assets/0e97c704-bb39-4c6a-85e0-5f26b1791aab" />


## ✨ Overview

Movie Portal is a full-stack web application designed for film enthusiasts to discover, organize, and personalize their movie-watching experience. It offers robust features for general users to interact with movie content, alongside administrative tools for content management and user moderation.

This project showcases a modern web development stack, demonstrating efficient data handling, user authentication, external API integration, and a responsive user interface.

## 🚀 Features

### Admin Features

<img width="1440" height="900" alt="ser mov by tıt" src="https://github.com/user-attachments/assets/a32a51cb-03df-4770-ab24-21b128a11f95" />


As an administrator, you have powerful control over the content and users of the portal:

*   **🎬 Add Movies from TMDB:** Effortlessly import comprehensive movie details, including titles, overviews, poster paths, release dates, and cast/crew information, directly from The Movie Database (TMDB) API.
*   **➕ Add User to the Portal:** Add new users to the portal.
*   **✏️ Edit User Details:** Modify user information as needed.
*   **🚫 Ban/Unban Users:** Manage user access to the portal by banning or unbanning accounts.

### User Features

<img width="1440" height="810" alt="Movie Details new" src="https://github.com/user-attachments/assets/2062c71c-05cb-4218-9d5e-da22403b589b" />


For general users, Movie Portal offers a rich set of functionalities to enhance their movie journey:

*   **🔍 Browse Movies:** Explore a curated collection of movies added by the admin, complete with details like overview, director, and cast.
*   **👤 Browse Directors & Filmography:** View individual director profiles and see a list of their movies available within the portal.
*   **➕ Watchlist Management:** Add movies to your personal watchlist and track their status (e.g., "Will Watch," "Watched").
*   **❤️ Favorites Collection:** Mark and manage your all-time favorite films for quick access.
*   **📝 Private Notes:** Add personal notes or reviews to any movie, visible only to you.

## 🛠️ Technologies Used

This project is built with a robust and popular technology stack:

### Frontend
*   **React.js:** A declarative, component-based JavaScript library for building user interfaces.
*   **Next.js:** A React framework for production, enabling server-side rendering and static site generation.
*   **Ant Design:** An enterprise-class UI design language and React UI library.
*   **Axios:** Promise-based HTTP client for making API requests.

### Backend
*   **Spring Boot (Java):** A framework for building robust, stand-alone, production-grade Spring applications.
*   **Spring Data JPA / Hibernate:** For powerful and efficient database interaction and object-relational mapping.
*   **Spring Security:** For user authentication and authorization.

### Database
*   **MySQL:** A widely used open-source relational database management system.

### External APIs
*   **The Movie Database (TMDB) API:** Used for fetching extensive movie, cast, and crew data.

## ⚙️ Getting Started

Follow these steps to get the Movie Portal up and running on your local machine.

### Prerequisites
*   Java Development Kit (JDK) 17+
*   Node.js and npm/yarn
*   MySQL Server
*   A TMDB API Key (You can get one from [TMDB website](https://www.themoviedb.org/documentation/api))

### Backend Setup (Spring Boot)
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/movie-portal.git
    cd movie-portal
    ```
2.  **Navigate to the backend directory:**
    ```bash
    cd movieportal-backend
    ```
3.  **Configure `secrets.properties`:** Create a `secrets.properties` file in `src/main/resources/` with your database credentials and TMDB API key:
    ```properties
    MYSQL_DB_USER=your_mysql_username
    MYSQL_DB_PASS=your_mysql_password
    TMDB_API_KEY=your_tmdb_api_key
    ```
4.  **Database Configuration:** Ensure your `application.properties` points to your MySQL database:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/movieportal_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=${MYSQL_DB_USER}
    spring.datasource.password=${MYSQL_DB_PASS}
    spring.jpa.hibernate.ddl-auto=update # Use 'create-drop' for fresh schema, 'update' for existing
    ```
5.  **Build and Run:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The backend will start on `http://localhost:8080`.

### Frontend Setup (Next.js)
1.  **Navigate to the frontend directory:**
    ```bash
    cd movieportal-frontend # Assuming your frontend is in a 'movieportal-frontend' folder within the repo
    ```
2.  **Install dependencies:**
    ```bash
    npm install # or yarn install
    ```
3.  **Run the development server:**
    ```bash
    npm run dev # or yarn dev
    ```
    The frontend will start on `http://localhost:3000`.

## 🔐 Default Accounts

Upon first running the backend with `spring.jpa.hibernate.ddl-auto=create-drop` (or `create`), default user accounts will be created automatically in the database.

*   **Administrator Account:**
    *   **Username:** `admin`
    *   **Password:** `password`

*   **Standard User Account:**
    *   **Username:** `Ege`
    *   **Password:** `123`

You can use these credentials to log in after the application has successfully started and the database is initialized.
