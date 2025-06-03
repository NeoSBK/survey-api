# Survey App

A full-stack Spring Boot RESTful API + Front-End project that collects and analyzes survey data related to food preferences and lifestyle habits using the **MVC architecture**.

## 📌 Features

- Survey form collects:
  - Full name, email, date of birth, contact number
  - Favorite foods (multiple choices)
  - Ratings for:
    - Watching movies
    - Listening to the radio
    - Eating out
    - Watching TV
- Backend calculates:
  - ✅ Total number of surveys
  - 📊 Average age of participants
  - 🧓 Oldest participant
  - 👶 Youngest participant
  - 🍕 Percentage of participants who like pizza
  - 🍝 Percentage of participants who like pasta
  - 🍖 Percentage of participants who like Pap and Wors
  - 🎬 Average rating for movies
  - 📻 Average rating for radio
  - 📺 Average rating for TV
  - 🍽️ Average rating for 'eat out'

## 🧱 Architecture

- **MVC (Model-View-Controller)** architecture separates concerns:
  - `Model` – data and entities
  - `View` – static HTML/CSS/JS frontend
  - `Controller` – handles HTTP requests/responses
  - `Service` – business logic
  - `Repository` – database interactions (Spring Data JPA)

## 🛠 Tech Stack

### Backend

- Java **21**
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- Mapstruct
- Gradle
- JUnit & Mockito (for unit testing)
- Postman (for API testing)

### Frontend

- HTML5
- CSS3
- JavaScript (ES Modules)

## 🚀 Getting Started

### Prerequisites

- Java **21+**
- Gradle
- Browser (for frontend)
- Postman (optional, for API testing)

### Run the App

1. **Clone the repo**:

   ```
   git clone https://github.com/NeoSBK/survey-api.git
   cd survey-app

2. **Start the backend server using Gradle:** ./gradlew bootRun

3. **Open your browser and visit the app at:** http://localhost:8080

**H2 Console**
- URL: http://localhost:8080/h2-console
- JDBC: jdbc:h2:mem:surveyDb;DB_CLOSE_DELAY=-1;MODE=MySQL
- Username: sa (no password)

**📬 API Endpoint**
-**POST** http://localhost:8080/v1/api/survey

```json
{
  "fullName": "Jane Doe",
  "email": "jane.doe@example.com",
  "dateOfBirth": "1995-08-25",
  "contactNumber": "0812345678",
  "favoriteFoods": ["Pizza", "Pasta", "Pap and Wors"],
  "ratingMovies": 4,
  "ratingRadio": 3,
  "ratingEatOut": 5,
  "ratingTV": 2
}
```

**📊 Survey Statistics**
**Automatically calculated:**

- 🔢 Total surveys submitted
- 👶 Youngest participant
- 🧓 Oldest participant
- 🧮 Average age
- 🍕 Percentage of participants who like pizza
- 🍝 Percentage of participants who like pasta
- 🍖 Percentage of participants who like Pap and Wors
- 🎬 Average rating for movies
- 📻 Average rating for radio
- 📺 Average rating for TV
- 🍽️ Average rating for 'eat out'

**🧪 Testing**
**✅ Unit Testing:** JUnit, Mockito

**📮 API Testing:** Postman

**Run tests:** ./gradlew test
