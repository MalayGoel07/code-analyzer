@echo off

echo Starting Python FastAPI...
start cmd /k "cd analyzer && python -m uvicorn main:app --reload --port 8000"

timeout /t 2

echo Starting Spring Boot...
start cmd /k "cd backend && mvn spring-boot:run"

echo Done 🚀
pause