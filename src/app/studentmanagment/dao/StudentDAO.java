package app.studentmanagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.model.Student;
import app.studentmanagment.util.DBConnection;

public class StudentDAO {

	// Add a new student to the database
	public boolean addStudent(Student student) {

		// SQL query used to insert a new student
		String sql = "INSERT INTO Student " + "(Student_id, Student_name, Student_age, Student_grade) "
				+ "VALUES (?, ?, ?, ?)";

		try (
				// Open a connection to the database
				Connection connection = DBConnection.getConnection();

				// Prepare the SQL query
				PreparedStatement statement = connection.prepareStatement(sql)) {

			// Set the student ID in the first ?
			statement.setInt(1, student.getId());

			// Set the student name in the second ?
			statement.setString(2, student.getName());

			// Set the student age in the third ?
			statement.setInt(3, student.getAge());

			// Set the student grade in the fourth ?
			statement.setDouble(4, student.getGrade());

			// Execute the INSERT query
			statement.executeUpdate();

			System.out.println("[INFO] Student saved successfully.");

			return true;

		} catch (SQLException e) {

			// Print an error message if the operation fails
			System.out.println("[ERROR] Failed to save student.");
			System.out.println("[ERROR] " + e.getMessage());

			return false;
		}
	}

	// Get all students from the database
	public List<Student> getAllStudent() throws SQLException {

		// Create a list to store the students
		List<Student> students = new ArrayList<Student>();

		// SQL query used to retrieve all students
		String sql = "SELECT * FROM Student";

		try (
				// Open a connection to the database
				Connection connection = DBConnection.getConnection();

				// Prepare the SELECT query
				PreparedStatement statement = connection.prepareStatement(sql);

				// Execute the SELECT query and store the result
				ResultSet resultSet = statement.executeQuery()) {

			// Loop through all returned rows
			while (resultSet.next()) {

				// Get the student ID from the current row
				int id = resultSet.getInt("Student_id");

				// Get the student name from the current row
				String name = resultSet.getString("Student_name");

				// Get the student age from the current row
				int age = resultSet.getInt("Student_age");

				// Get the student grade from the current row
				double grade = resultSet.getDouble("Student_grade");

				// Create a Student object using the retrieved data
				Student student = new Student(name, age, grade, id);

				// Add the student to the list
				students.add(student);
			}

			System.out.println("[INFO] Finished reading students.");

		} 

		// Return the list of students
		return students;
	}

	// Find a student by name
	public Student getStudentByName(String searchName) throws SQLException {

		// Initialize the student object as null
		Student student = null;

		// SQL query used to search for a student by name
		String sql = "SELECT * FROM Student " + "WHERE Student_name = ?";

		try (
				// Open a connection to the database
				Connection connection = DBConnection.getConnection();

				// Prepare the SELECT query
				PreparedStatement statement = connection.prepareStatement(sql)) {

			// Set the search name in the ?
			statement.setString(1, searchName);

			// Execute the SELECT query
			ResultSet resultSet = statement.executeQuery();

			// Check if a student was found
			if (resultSet.next()) {

				// Get the student ID
				int id = resultSet.getInt("Student_id");

				// Get the student name
				String name = resultSet.getString("Student_name");

				// Get the student age
				int age = resultSet.getInt("Student_age");

				// Get the student grade
				double grade = resultSet.getDouble("Student_grade");

				// Create a Student object using the retrieved data
				student = new Student(name, age, grade, id);

				System.out.println("[INFO] Student found: " + name);
			}

		} 

		// Return the student if found, otherwise return null
		return student;
	}


	// Update

	public void updateStudent(int id, Student student) {

	}

// Delete

	public void deleteStudent(int id) {

	}

}
