# Recruitment & Placement Management System (RPMS)

## 📌 Project Overview
The **Recruitment & Placement Management System (RPMS)** is a Java-based desktop application developed to simplify the recruitment and placement process in colleges.

It helps manage student details, company information, job openings, and student job applications through a simple graphical user interface.

## 🎯 Objectives
- To manage student information.
- To store company details.
- To manage available job openings.
- To allow students to apply for jobs.
- To view and manage job applications.
- To store project data in a MySQL database.

## 🛠️ Technologies Used
- **Programming Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **IDE:** Visual Studio Code
- **Database Tool:** MySQL Workbench

## ✨ Features
1. **Admin Login** – Secure login screen for admin access.
2. **Student Management** – Add, view, and search student details.
3. **Company Management** – Add and view company information.
4. **Job Management** – Add and view available job openings.
5. **Job Application** – Apply for available jobs.
6. **Application Management** – View student job applications.
7. **Dashboard** – Display basic counts of students, companies, jobs, and applications.
8. **MySQL Database** – Store and retrieve project data using JDBC.

## 🗄️ Database
The project uses MySQL database named `rpms`.

### Tables
- `students`
- `companies`
- `jobs`
- `applications`

JDBC is used to connect the Java application with the MySQL database.

## ⚙️ How to Run the Project

### Requirements
- Java JDK
- MySQL Server
- MySQL Workbench
- MySQL Connector/J
- Visual Studio Code

### Steps
1. Clone or download this repository.
2. Open the project folder in VS Code.
3. Create a MySQL database named `rpms`.
4. Create the required database tables.
5. Place the MySQL Connector JAR file in the project folder.
6. Set your MySQL password in the `DB_PASSWORD` environment variable.

### Compile the Project
```bash
javac -cp ".;mysql-connector-j-26.10.0.jar" Dashboard.java
```

### Run the Project
```bash
java -cp ".;mysql-connector-j-26.10.0.jar" Dashboard
```

## 🔐 Admin Login
- **Username:** admin
- **Password:** 1234

## 🚀 Future Enhancements
- Student eligibility checking.
- Interview scheduling.
- Offer management.
- Placement tracking.
- Advanced reports.
- Separate admin and student login.

## 👨‍💻 Developed By
**Gundu Ekanath Bhagat**

- GitHub: https://github.com/gundubhagat64
- LinkedIn: https://www.linkedin.com/in/gundubhagat2004/

## 📄 License
This project is developed for academic and educational purposes.
