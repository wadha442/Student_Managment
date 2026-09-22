package app.studentmanagment.service;
 
import java.sql.SQLException;
import java.util.List;

import app.studentmanagment.model.Student;
 

public interface StudentService {
 
	public boolean addStudent(Student student);
 
	public List<Student> showStudents() throws Exception;
 
	public String searchStudent(String studentName) throws Exception;
 
	public boolean updateStudent(int id, Student student);

	public boolean deleteStudent(int id);
}