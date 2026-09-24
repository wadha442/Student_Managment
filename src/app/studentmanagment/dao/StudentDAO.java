package app.studentmanagment.dao;


 
import java.util.List;

import app.studentmanagment.model.Student;
 

 
public interface StudentDAO {
 
	public boolean addStudent(Student student) throws Exception;
 
	public List<Student> getAllStudent() throws Exception;
 
	public Student getStudentByName(String searchName) throws Exception;
 
	public boolean updateStudent(int id, Student student);
 
	public boolean deleteStudent(int id);
	

}