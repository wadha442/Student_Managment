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
		;
		try {
			students = studentDAO.getAllStudent();
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to retrieve students from database.");
			students = studentFileService.showStudents();
		}

		return students;
	}
    

    @Override
    public String searchStudent(String studentName) throws Exception {

    	try {
			Student student = studentDAO.getStudentByName(studentName);
			studentDAO.updateStudent(1, student);

			if (student == null) {
				return "Student not found";
			} else {
				return student.studentInfo();
			}
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to retrieve student from database.");
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