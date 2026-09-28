package app.studentmanagment.service.impl;

import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.dao.StudentDAO;
import app.studentmanagment.dao.impl.StudentFileDAOImpl;
import app.studentmanagment.exception.StudentAlreadyExistsException;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides business operations for managing students using file storage.
 * This service is used as a fallback when database operations are unavailable.
 */
public class StudentFileDBServiceImpl implements StudentService {
	private StudentDAO studentDAO;

	private static final Logger logger = LogManager.getLogger(StudentFileDBServiceImpl.class);

	public StudentFileDBServiceImpl() {
		studentDAO = new StudentFileDAOImpl();
	}

	  /**
     * Adds a new student to file storage.
     * Checks whether the student's national ID already exists.
     *
     * @param student the student to be added
     * @return true if the student was added successfully; otherwise false
     * @throws StudentAlreadyExistsException if the national ID already exists
     */
	@Override
	public boolean addStudent(Student student) throws StudentAlreadyExistsException {

		try {

			if (studentDAO.getStudentByNationalId(student.getNationalId()) != null) {
				throw new StudentAlreadyExistsException("National ID already exists");
			}

			return studentDAO.addStudent(student);

		} catch (StudentAlreadyExistsException e) {

			throw e;

		} catch (Exception e) {
			logger.error("Failed to add student to file storage.", e);
			return false;
		}
	}

	 /**
     * Retrieves all students from file storage.
     *
     * @return a list containing all students
     */
	@Override
	public List<Student> showStudents() {
		List<Student> students = new ArrayList<Student>();
		logger.debug("Starting to retrieve students from students.txt.");
		try {
			students = studentDAO.getAllStudent();
		} catch (Exception e) {
			logger.error("Failed to retrieve students from students.txt.", e);
			System.out.println("[ERROR] " + e.getMessage());
		}
		return students;
	}

    /**
     * Searches for a student by name in file storage.
     *
     * @param studentName the name of the student to search for
     * @return the student's information if found; otherwise "Student not found"
     */
	@Override
	public String searchStudent(String studentName) {

		Student student = null;

		logger.debug("Searching for student in students.txt. Name: {}", studentName);

		try {
			student = studentDAO.getStudentByName(studentName);
		} catch (Exception e) {
			logger.error("Failed to retrieve student from students.txt. Name: {}", studentName, e);
			System.out.println("[ERROR] " + e.getMessage());
		}

		if (student == null) {
			logger.warn("Student not found. Name: {}", studentName);
			return "Student not found";

		} else {
			logger.info("Student found. Name: {}", studentName);

			return student.studentInfo();
		}
	}

	   /**
     * Updates an existing student's information in file storage.
     *
     * @param nationalId the national ID of the student to update
     * @param student the student containing the updated information
     * @return true if the student was updated successfully; otherwise false
     */
	@Override
	public boolean updateStudent(String nationalId, Student student) {

	    try {
	        logger.info("Updating student in file. National ID: {}", nationalId);

	        return studentDAO.updateStudent(nationalId, student);

	    } catch (Exception e) {
	        logger.error("Failed to update student in file. National ID: {}", nationalId, e);
	        return false;
	    }
	}
    /**
     * Deletes a student from file storage using the national ID.
     *
     * @param nationalId the national ID of the student to delete
     * @return true if the student was deleted successfully; otherwise false
     */
	@Override
	public boolean deleteStudent(String nationalId) {

	    try {
	        logger.info("Deleting student from file. National ID: {}", nationalId);

	        return studentDAO.deleteStudent(nationalId);

	    } catch (Exception e) {
	        logger.error("Failed to delete student from file. National ID: {}", nationalId, e);
	        return false;
	    }
	}
}