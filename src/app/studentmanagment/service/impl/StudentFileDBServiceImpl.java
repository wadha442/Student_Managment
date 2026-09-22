package app.studentmanagment.service.impl;

import java.util.List;

import app.studentmanagement.dao.StudentDAO;
import app.studentmanagment.dao.impl.StudentFileDAOImpl;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;

public class StudentFileDBServiceImpl implements StudentService {
	 private StudentFileDAOImpl studentFileDAO;

	    public StudentFileDBServiceImpl() {
	        studentFileDAO = new StudentFileDAOImpl();
	    }

	    @Override
	    public boolean addStudent(Student student) {
	        return studentFileDAO.addStudent(student);
	    }

	    @Override
	    public List<Student> showStudents() throws Exception {
	        return studentFileDAO.getAllStudent();
	    }

	    @Override
	    public String searchStudent(String studentName) throws Exception {

	        Student student = studentFileDAO.getStudentByName(studentName);

	        if (student == null) {
	            return "Student not found";
	        }

	        return student.studentInfo();
	    }

	    @Override
	    public boolean updateStudent(int id, Student student) {
	        return studentFileDAO.updateStudent(id, student);
	    }

	    @Override
	    public boolean deleteStudent(int id) {
	        return studentFileDAO.deleteStudent(id);
	    }
	}