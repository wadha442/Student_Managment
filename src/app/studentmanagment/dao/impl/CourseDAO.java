package app.studentmanagment.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.studentmanagment.model.Course;
import app.studentmanagment.util.DBConnection;

/**
 * Provides data access operations for courses. Handles communication with the
 * Course database table using JDBC.
 */
public class CourseDAO {
	private static final Logger logger = LogManager.getLogger(CourseDAO.class);

	// Create
	public boolean addCourse(Course course) throws SQLException {

		String sql = "INSERT INTO Course " + "(Course_name, Course_description, course_code) " + "VALUES (?, ?, ?)";

		logger.debug("Writing course to database.");

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setString(3, course.getCourseCode());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {

				logger.info("Course added to database successfully.");

				return true;
			}

			return false;
		}
	}

	// Read
	public List<Course> getAllCourses() throws SQLException {

		List<Course> courses = new ArrayList<Course>();

		String sql = "SELECT Course_id, Course_name, " + "Course_description, course_code " + "FROM Course";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				int id = resultSet.getInt("Course_id");

				String name = resultSet.getString("Course_name");

				String description = resultSet.getString("Course_description");

				String courseCode = resultSet.getString("course_code");

				Course course = new Course(name, description, courseCode);

				course.setId(id);

				courses.add(course);
			}
		}

		return courses;
	}

	public Course getCourse(String courseCode) throws SQLException {

		String sql = "SELECT Course_id, Course_name, " + "Course_description, course_code " + "FROM Course "
				+ "WHERE course_code = ?";

		Course course = null;

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, courseCode);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {

					int id = resultSet.getInt("Course_id");

					String name = resultSet.getString("Course_name");

					String description = resultSet.getString("Course_description");

					String foundCourseCode = resultSet.getString("course_code");

					course = new Course(name, description, foundCourseCode);

					course.setId(id);
				}
			}
		}

		return course;
	}

	// Update
	public boolean updateCourse(String courseCode, Course course) throws SQLException {

		String sql = "UPDATE Course " + "SET Course_name = ?, " + "Course_description = ?, " + "course_code = ? "
				+ "WHERE course_code = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.setString(3, course.getCourseCode());
			preparedStatement.setString(4, courseCode);

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0;
		}
	}

	// Delete
	public boolean deleteCourse(String courseCode) throws SQLException {

		String sql = "DELETE FROM Course " + "WHERE course_code = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, courseCode);

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0;
		}
	}
}
