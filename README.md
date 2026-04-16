# Code Analyzer

A comprehensive code analysis tool built with **Java Spring Boot** and **Python FastAPI** that evaluates source code for metrics such as time and space complexity indicators, control structure counts, and overall code quality. It provides structured insights to help students understand performance, maintainability, and potential technical debt.

## 🎯 Features

- **Complexity Analysis**: Evaluates time and space complexity indicators
- **Code Metrics**: Analyzes control structure counts and code patterns
- **Quality Assessment**: Provides comprehensive code quality insights
- **Educational Focus**: Designed to help students understand code performance and maintainability
- **Technical Debt Detection**: Identifies areas for improvement and refactoring

## 🛠️ Tech Stack

- **Backend**: Java Spring Boot 3.5.10
- **Alternative Runtime**: Python FastAPI
- **Database**: MongoDB
- **Parser**: JavaParser (for Java code analysis)
- **Java Version**: 17+

## 📋 Prerequisites

- Java 17+
- Python 3.8+
- Maven 3.6+
- MongoDB (running instance)
- Node.js (if running frontend)

## 🚀 Getting Started

### Option 1: Run All Components (Windows)

```bash
run-all.bat
```

This batch file will start both the FastAPI analyzer and Spring Boot backend in separate command windows.

### Option 2: Manual Setup

#### Backend (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`

#### Analyzer (FastAPI)

```bash
cd analyzer
pip install -r requirements.txt
python -m uvicorn main:app --reload --port 8000
```

The analyzer API will be available at `http://localhost:8000`

## 📊 API Endpoints

### FastAPI Analyzer (Port 8000)

- `GET /docs` - Interactive API documentation
- `POST /analyze` - Submit code for analysis

### Spring Boot Backend (Port 8080)

- Health checks and API endpoints for managing analysis results
- MongoDB integration for storing analysis history

## 📁 Project Structure

```
code-analyzer/
├── backend/                    # Spring Boot application
│   ├── src/                   # Source code
│   ├── pom.xml               # Maven configuration
│   └── ...
├── analyzer/                   # FastAPI analyzer
│   ├── main.py               # FastAPI application entry
│   ├── requirements.txt       # Python dependencies
│   └── ...
├── run-all.bat               # Windows batch script to run all services
└── README.md                 # This file
```

## 🔧 Configuration

### Database Setup

Ensure MongoDB is running on your system. The Spring Boot application will connect to MongoDB for persistent storage.

Default MongoDB connection: `mongodb://localhost:27017`

### Environment Variables

Configure the following in `application.properties` (backend):

```properties
spring.data.mongodb.uri=mongodb://localhost:27017
spring.data.mongodb.database=code-analyzer
```

## 📖 Usage

1. Start all services using `run-all.bat` (Windows) or manually start each service
2. Submit Java or Python code for analysis through the FastAPI endpoint
3. View analysis results including:
   - Time complexity indicators
   - Space complexity indicators
   - Control structure counts
   - Code quality metrics

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is open source and available under the MIT License.

## 👤 Author

**Malay Goel**
- GitHub: [@MalayGoel07](https://github.com/MalayGoel07)

## 💡 Future Enhancements

- Support for additional programming languages
- Advanced ML-based code quality predictions
- Interactive visualization of code metrics
- Performance benchmarking tools
- Integration with CI/CD pipelines