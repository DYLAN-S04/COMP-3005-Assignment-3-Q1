package com.Assignment3;

import java.sql.*;
import java.time.LocalDate;

public class StudentDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/Assign3Q1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345678";

    public static void main(String[] args) {
        resetDatabase();  // Step 1: Reset to IDs 1, 2, 3

        getAllStudents(); // Display after reset
        addStudent("Alice", "Cooper", "alice.cooper@example.com", LocalDate.now());
        getAllStudents();
        updateStudentEmail(1, "john.doe@updated.com");
        getAllStudents();
        deleteStudent(4);
        getAllStudents(); // Display after operations
    }

    // === RESET FUNCTION ===
    public static void resetDatabase() {
        String deleteSQL = "DELETE FROM students";
        String resetSequenceSQL = "ALTER SEQUENCE students_student_id_seq RESTART WITH 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Delete all existing rows
            stmt.executeUpdate(deleteSQL);

            // Reset auto-increment sequence (important for ID = 1, 2, 3)
            stmt.executeUpdate(resetSequenceSQL);

            // Insert three base students
            addStudent("John", "Doe", "john.doe@example.com", LocalDate.of(2024, 9, 1));
            addStudent("Jane", "Smith", "jane.smith@example.com", LocalDate.of(2024, 9, 1));
            addStudent("Bob", "Johnson", "bob.johnson@example.com", LocalDate.of(2024, 9, 1));

            System.out.println("Database reset to default students (IDs 1–3).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Students Table ===");
            while (rs.next()) {
                System.out.printf("%d | %s %s | %s | %s%n",
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("enrollment_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentEmail(int studentId, String newEmail) {
        String sql = "UPDATE students SET email = ? WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("No student found with ID " + studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with ID " + studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
