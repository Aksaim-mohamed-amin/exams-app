# Web-Based Exam Application

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A secure web application designed for creating, distributing, and taking online exams. It features an intelligent AI service that generates plausible wrong answers for multiple-choice questions (MCQs), adding a dynamic layer to exam creation. The platform also includes a complete backend system with role-specific access for professors and students, email notifications, and real-time result tracking.

## Features

### Professor Features
- 🎓 Create and manage exams with multiple question types (MCQ/Direct Answer).
- 📤 Generate unique exam links for students with expiration dates and controlled access.
- 🤖 Utilize AI-powered question generation, including plausible wrong answers for MCQs.
- 📊 Real-time result tracking and detailed analytics for student performance.
- 📧 Send bulk enrollment emails to students and manage their access to exams.

### Student Features
- 🔒 Access exams securely through time-limited links with passcodes.
- ⏱ Timer-based questions with automatic submission to ensure fairness.
- 📝 Review answers before final submission for a comprehensive exam-taking experience.
- 📩 Receive instant result notifications via email after exam completion.
- 📈 Access detailed performance breakdown and scores upon exam completion.

## Technologies

**Frontend**  
![Javascript](https://img.shields.io/badge/JavaScript--F7DF1E?logo=javascript)
![Angular](https://img.shields.io/badge/Angular-19-DD0031?logo=angular)

**Backend**  
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-6DB33F?logo=springboot)

**AI Service**  
![Python](https://img.shields.io/badge/Python-3.11-3776AB?logo=python)
![FastAPI](https://img.shields.io/badge/FastAPI-0.68+-009688?logo=fastapi)

**Database**  
![MySQL](https://img.shields.io/badge/Mysql--336791?logo=mysql)

**Deployment**  
![Docker](https://img.shields.io/badge/Docker--2496ED?logo=docker)

## System Architecture

### Use Case Diagram
![Use Case Diagram](use-case-diagram.png)
### Class Diagram
![Class Diagram](class-diagram.png)
### Sequence Diagram
![Sequence Diagram](sequence-diagram.png)
### Deployment Diagram
![Deployment Diagram](deployment-diagram.png)

## Installation
### Prerequisites
- Docker
- Docker Compose
- Node.js 22.x
- Java 21
- Python 3.13

## Directory Structure
```
exams-app/
├── backend-api/           # Spring Boot application
├── frontend-website/      # Angular application 
├── ai-service/            # FastAPI service for AI question generation
├── mail-service/          # Email microservice
├── docker-compose.yml     # Main compose file
└── README.md 
```



## License
Distributed under the MIT License. See `LICENSE` for more information.

## Contact
Feel free to reach out for any questions or contributions!  
[LinkedIn](https://www.linkedin.com/in/aksaimamin)  
[Portfolio](https://aksaim.me)  
[aksaimmohamedamin@gmail.com](mailto:aksaimmohamedamin@gmail.com)
