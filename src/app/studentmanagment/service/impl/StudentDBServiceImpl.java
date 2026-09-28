package app.studentmanagment.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.dao.impl.StudentDBDAOImpl;
import app.studentmanagment.exception.StudentAlreadyExistsException;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides business operations for managing students. Uses the database as the
 * main storage and file storage as a fallback when a database operation fails.
 */
public class StudentDBServiceImpl implements StudentService {

	private StudentDBDAOImpl studentDAO;

	private StudentFileDBServiceImpl studentFileService;

	private static final Logger logger = LogManager.getLogger(StudentDBServiceImpl.class);

	public StudentDBServiceImpl() {

		studentDAO = new StudentDBDAOImpl();

		studentFileService = new StudentFileDBServiceImpl();
	}

	/**
	 * Adds a new student to the system. Checks if a student with the same name
	 * already exists. If the database operation fails, the student is saved to file
	 * storage.
	 *
	 * @param student the student to be added
	 * @return true if the student was added successfully; otherwise false
	 * @throws StudentAlreadyExistsException if the student already exists
	 */
	@Override
	public boolean addStudent(Student student) throws StudentAlreadyExistsException {

		try {

			if (studentDAO.getStudentByName(student.getName()) != null) {
				throw new StudentAlreadyExistsException("Student " + student.getName() + " already exists");
			}

			return studentDAO.addStudent(student);

		} catch (StudentAlreadyExistsException e) {
			throw e;

		} catch (Exception e) {

			logger.warn("Failed to add student to database. Using file storage.", e);

			return studentFileService.addStudent(student);
		}
	}

	/**
	 * Retrieves all students from the database. If the database operation fails,
	 * students are retrieved from file storage.
	 *
	 * @return a list containing all students
	 * @throws Exception if an error occurs while retrieving students
	 */
	@Override
	public List<Student> showStudents() throws Exception {
		List<Student> students = new ArrayList<Student>();

		try {
			logger.info("Retrieving students from database.");
			students = studentDAO.getAllStudent();
			logger.info("Students retrieved successfully from database.");

		} catch (Exception e) {
			logger.error("Failed to retrieve students from database. Falling back to file.", e);
			students = studentFileService.showStudents();
		}

		return students;
	}

	/**
	 * Searches for a student by name. If the database search fails, the search is
	 * performed using file storage.
	 *
	 * @param studentName the name of the student to search for
	 * @return the student's information if found; otherwise "Student not found"
	 * @throws Exception if an error occurs while searching for the student
	 */
	@Override
	public String searchStudent(String studentName) throws Exception {

		try {
			logger.info("Searching for student: {}", studentName);

			Student student = studentDAO.getStudentByName(studentName);

			if (student == null) {
				logger.warn("Student not found: {}", studentName);
				return "Student not found";
			} else {
				logger.info("Student found: {}", studentName);
				return student.studentInfo();
			}
		} catch (Exception e) {
			logger.error("Failed to retrieve student from database.", e);
			return studentFileService.searchStudent(studentName);
		}

	}

	/**
	 * Updates an existing student's information using the national ID. If the
	 * database operation fails, the update is performed in file storage.
	 *
	 * @param nationalId the national ID of the student to update
	 * @param student    the student containing the updated information
	 * @return true if the student was updated successfully; otherwise false
	 */
	@Override
	public boolean updateStudent(String nationalId, Student student) {

		try {
			return studentDAO.updateStudent(nationalId, student);

		} catch (Exception e) {
			logger.warn("Failed to update student in database. Using file storage.", e);
			return studentFileService.updateStudent(nationalId, student);
		}
	}

	/**
	 * Deletes a student using the national ID. If the database operation fails, the
	 * student is deleted from file storage.
	 *
	 * @param nationalId the national ID of the student to delete
	 * @return true if the student was deleted successfully; otherwise false
	 */
	@Override
	public boolean deleteStudent(String nationalId) {

		try {
			return studentDAO.deleteStudent(nationalId);

		} catch (Exception e) {
			logger.warn("Failed to delete student in database. Using file storage.", e);
			return studentFileService.deleteStudent(nationalId);
		}
	}
}