package app.studentmanagment.service;

import java.util.ArrayList;
import java.util.List;

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
		
		public void showStudents() {
			List<Student> students = new ArrayList<Student>();
			students = studentDAO.getAllStudent();

			for (Student student : students) {
				System.out.println(student.studentInfo());
			}
		}

		public void searchStudent(String studentName) {
			Student student = studentDAO.getStudentByName(studentName);

			if (student == null) {
				System.out.println("Student not found");
			} else {
				System.out.println(student.studentInfo());
			}
		}
}
