package app.studentmanagment.service.impl;

import java.sql.SQLException;
import java.util.List;

import app.studentmanagment.dao.impl.StudentDBDAOImpl;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;

public class StudentDBServiceImpl implements StudentService {
	private StudentDBDAOImpl studentDAO;

    public StudentDBServiceImpl() {
        studentDAO = new StudentDBDAOImpl();
    }

    @Override
    public boolean addStudent(Student student) {
        return studentDAO.addStudent(student);
    }

    @Override
    public List<Student> showStudents() throws Exception {
        return studentDAO.getAllStudent();
    }

    @Override
    public String searchStudent(String studentName) throws Exception {

        Student student = studentDAO.getStudentByName(studentName);

        if (student == null) {
            return "Student not found";
        }

        return student.studentInfo();
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