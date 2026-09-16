package app.studentmanagment.dao;

import java.io.FileWriter;
import java.io.IOException;

import app.studentmanagment.model.Student;

public class StudentDAO {

	

	public void addStudent(Student student) {
		try {

            System.out.println("[FILE] Opening students.txt for writing...");

            FileWriter writer = new FileWriter("students.txt", true);

            writer.write(student.getName() + "," + student.getAge() + "," + student.getGrade() + "\n");

            writer.close();

            System.out.println("[INFO] Student saved successfully.");
            

        } catch (IOException e) {

            System.out.println("[ERROR] Failed to save student.");
            System.out.println("[ERROR] " + e.getMessage());
        }
}}
