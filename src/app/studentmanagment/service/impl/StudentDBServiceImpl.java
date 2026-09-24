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

public class StudentDBServiceImpl implements StudentService {
	
	private StudentDBDAOImpl studentDAO;

	private StudentFileDBServiceImpl studentFileService;

	private static final Logger logger = LogManager.getLogger(StudentDBServiceImpl.class);

	public StudentDBServiceImpl() {

	    studentDAO = new StudentDBDAOImpl();

	    studentFileService = new StudentFileDBServiceImpl();
	}
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
    

    @Override
    public String searchStudent(String studentName) throws Exception {

    	try {
    		   logger.info("Searching for student: {}", studentName);

			Student student = studentDAO.getStudentByName(studentName);
			studentDAO.updateStudent(1, student);

			if (student == null) {
				 logger.warn("Student not found: {}", studentName);
				return "Student not found";
			} else {
				 logger.info("Student found: {}", studentName);
				return student.studentInfo();
			}
		} catch (Exception e) {
			logger.error("Failed to retrieve student from database.",e);
			return studentFileService.searchStudent(studentName);
		}

	}
    @Override
    public boolean updateStudent(int id, Student student) {
        return studentDAO.updateStudent(id, student);
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }
}