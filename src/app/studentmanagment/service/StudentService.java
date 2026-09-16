package app.studentmanagment.service;

import app.studentmanagment.dao.StudentDAO;
import app.studentmanagment.model.Student;

public class StudentService {

	
	private StudentDAO studentDAO;
	private final int ATTENDENT_GRADE=10;
	
	public StudentService() {
	 
	studentDAO=new StudentDAO();

	}
		
		public void addStudent(Student student) {
			
			double  totalGreade=student.getGrade()+ATTENDENT_GRADE;
			student.setGrade(student.getGrade()+totalGreade);
			
			studentDAO.addStudent(student);
		}
}
