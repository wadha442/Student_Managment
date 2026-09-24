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

public class StudentFileDBServiceImpl implements StudentService {
	private StudentDAO studentDAO;

	private static final Logger logger = LogManager.getLogger(StudentFileDBServiceImpl.class);

	public StudentFileDBServiceImpl() {
		studentDAO = new StudentFileDAOImpl();
	}

	// Create
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
			logger.error("Failed to add student to file storage.", e);
			return false;
		}
	}

	// Read
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

	// Update
	@Override
	public boolean updateStudent(int id, Student student) {

		boolean flag = studentDAO.updateStudent(id, student);
		return flag;
	}

	// Delete
	@Override
	public boolean deleteStudent(int id) {
		boolean flag = studentDAO.deleteStudent(id);
		return flag;
	}
}