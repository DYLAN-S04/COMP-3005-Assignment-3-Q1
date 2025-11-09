# COMP-3005-Assignment-3-Q1

Setup Instructions:

Install PostgreSQL and create a database named Assign3Q1.

Run the following SQL command in psql:

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    enrollment_date DATE NOT NULL
);


Update the database URL, username, and password in the code if needed:

private static final String URL = "jdbc:postgresql://localhost:5432/Assign3Q1";
private static final String USER = "postgres";
private static final String PASSWORD = "12345678";


Download the PostgreSQL JDBC driver (postgresql-<version>.jar) and add it to your classpath.

If all dependencies have been met, the code should run with no problem.

The code will reset the database, add a student, change the email of the first student, then remove the added student
