package app.studentmanagment.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.studentmanagement.dao.StudentDAO;
import app.studentmanagment.model.Student;
import app.studentmanagment.util.DBConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentDBDAOImpl implements StudentDAO {

	private static final Logger logger = LogManager.getLogger(StudentDBDAOImpl.class);

	// Add a new student to the database
	public boolean addStudent(Student student) {

		// SQL query used to insert a new student
		String sql = "INSERT INTO Student " + "(Student_name, Student_age, Student_grade) " + "VALUES (?, ?, ?)";

		logger.debug("Starting addStudent. Student name: {}", student.getName());

		try (
				// Open a connection to the database
				Connection connection = DBConnection.getConnection();

				// Prepare the SQL query
				PreparedStatement statement = connection.prepareStatement(sql)) {

			// Set the student name in the second ?
			statement.setString(1, student.getName());

			// Set the student age in the third ?
			statement.setInt(2, student.getAge());

			// Set the student grade in the fourth ?
			statement.setDouble(3, student.getGrade());

			// Execute the INSERT query
			statement.executeUpdate();

			logger.info("Student saved successfully. ID: {}", student.getId());
			return true;

		} catch (SQLException e) {

			// Print an error message if the operation fails
			logger.error("Failed to save student. ID: {}", student.getId(), e);
			return false;
		}
	}

	// Get all students from the database
	public List<Student> getAllStudent() throws SQLException {

		// Create a list to store the students
		List<Student> students = new ArrayList<Student>();

		// SQL query used to retrieve all students
		String sql = "SELECT * FROM Student";

		logger.debug("Starting to retrieve all students.");

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

			logger.info("Finished reading students. Count: {}", students.size());
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

				logger.info("Student found. Name: {}", name);
			} else {

				logger.warn("Student not found. Name: {}", searchName);
			}

		}

		// Return the student if found, otherwise return null
		return student;
	}

	// Update

	public boolean updateStudent(int id, Student student) {
		return false;

	}

// Delete

	public boolean deleteStudent(int id) {
		return false;

	}

}
