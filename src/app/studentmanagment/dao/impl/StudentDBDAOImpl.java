package app.studentmanagment.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.dao.StudentDAO;
import app.studentmanagment.model.Student;
import app.studentmanagment.util.DBConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides database access operations for student records.
 * This class implements the StudentDAO interface and uses JDBC
 * to communicate with the SQL Server database.
 */

public class StudentDBDAOImpl implements StudentDAO {

	private static final Logger logger = LogManager.getLogger(StudentDBDAOImpl.class);

	 /**
     * Adds a new student to the database.
     *
     * @param student the student to be added
     * @return true if the student was added successfully, otherwise false
     */
	
	public boolean addStudent(Student student) {

	    String sql = "INSERT INTO Student "
	            + "(national_id, Student_name, Student_age, Student_grade) "
	            + "VALUES (?, ?, ?, ?)";

	    logger.debug("Starting addStudent. Student name: {}", student.getName());

	    try (
	        Connection connection = DBConnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql)
	    ) {

	        statement.setString(1, student.getNationalId());
	        statement.setString(2, student.getName());
	        statement.setInt(3, student.getAge());
	        statement.setDouble(4, student.getGrade());

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            logger.info("Student saved successfully. National ID: {}",
	                    student.getNationalId());
	            return true;
	        }

	    } catch (SQLException e) {

	        logger.error("Failed to save student. National ID: {}",
	                student.getNationalId(), e);

	        System.out.println("Database Error: " + e.getMessage());
	    }

	    return false;
	}
	// Get all students from the database
	public List<Student> getAllStudent() throws SQLException {

		List<Student> students = new ArrayList<Student>();

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

				int id = resultSet.getInt("Student_id");
				String nationalId = resultSet.getString("national_id");
				String name = resultSet.getString("Student_name");
				int age = resultSet.getInt("Student_age");
				double grade = resultSet.getDouble("Student_grade");

				Student student = new Student(name, nationalId, age, grade, id);
				// Add the student to the list
				students.add(student);
			}

			logger.info("Finished reading students. Count: {}", students.size());
		}

		// Return the list of students
		return students;
	}


	   /**
     * Retrieves all students from the database.
     *
     * @return a list containing all students
     * @throws SQLException if a database access error occurs
     */
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

				int id = resultSet.getInt("Student_id");

				String nationalId = resultSet.getString("national_id");

				String name = resultSet.getString("Student_name");

				int age = resultSet.getInt("Student_age");

				double grade = resultSet.getDouble("Student_grade");

				student = new Student(name, nationalId, age, grade, id);
				logger.info("Student found. Name: {}", name);
			} else {

				logger.warn("Student not found. Name: {}", searchName);
			}

		}

		// Return the student if found, otherwise return null
		return student;
	}

	
	   /**
     * Finds a student by name.
     *
     * @param searchName the name of the student to search for
     * @return the student if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
	public Student getStudentByNationalId(String nationalId) throws SQLException {

		String sql = "SELECT * FROM Student WHERE national_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, nationalId);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Student(resultSet.getString("Student_name"), resultSet.getString("national_id"),
						resultSet.getInt("Student_age"), resultSet.getDouble("Student_grade"),
						resultSet.getInt("Student_id"));
			}
		}

		return null;
	}

	   /**
     * Finds a student by national ID.
     *
     * @param nationalId the national ID of the student
     * @return the student if found, otherwise null
     * @throws SQLException if a database access error occurs
     */
	
	@Override
	public boolean updateStudent(String nationalId, Student student) throws SQLException {

	    String sql = "UPDATE Student "
	            + "SET Student_name = ?, "
	            + "Student_age = ?, "
	            + "Student_grade = ? "
	            + "WHERE national_id = ?";

	    try (
	        Connection connection = DBConnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql)
	    ) {

	        statement.setString(1, student.getName());
	        statement.setInt(2, student.getAge());
	        statement.setDouble(3, student.getGrade());
	        statement.setString(4, nationalId);

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            logger.info("Student updated successfully. National ID: {}", nationalId);
	            return true;
	        }

	        logger.warn("Student not found. National ID: {}", nationalId);
	        return false;
	    }
	}

	 /**
     * Deletes a student from the database using their national ID.
     *
     * @param nationalId the national ID of the student to delete
     * @return true if the student was deleted successfully, otherwise false
     * @throws SQLException if a database access error occurs
     */
	
	@Override
	public boolean deleteStudent(String nationalId) throws SQLException {

	    String sql = "DELETE FROM Student WHERE national_id = ?";

	    logger.debug("Deleting student from database: {}", nationalId);

	    try (
	        Connection connection = DBConnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql)
	    ) {

	        statement.setString(1, nationalId);

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            logger.info("Student deleted successfully: {}", nationalId);
	            return true;
	        }

	        logger.warn("Student not found: {}", nationalId);
	        return false;
	    }
	}

	

}
