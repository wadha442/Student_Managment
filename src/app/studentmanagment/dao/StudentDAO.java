package app.studentmanagment.dao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.studentmanagment.constants.FileConstant;
import app.studentmanagment.model.Course;
import app.studentmanagment.model.Student;
import java.io.File;
public class StudentDAO {

	

	public boolean addStudent(Student student) {
		try {

            System.out.println("[FILE] Opening students.txt for writing...");

            FileWriter writer = new FileWriter("students.txt", true);

            writer.write(student.getName() + "," + student.getAge() + "," + student.getGrade() + "\n");

            writer.close();

            System.out.println("[INFO] Student saved successfully.");
            
          return true;
        } catch (IOException e) {

            System.out.println("[ERROR] Failed to save student.");
            System.out.println("[ERROR] " + e.getMessage());
        }
		 return false;
}
	
	public List<Student> getAllStudent() {
		List<Student> students = new ArrayList<Student>();

		System.out.println("[INFO] Reading students from students.txt.");

		try {

			Scanner fileScanner = new Scanner(new File("students.txt"));

			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				String[] data = line.split(",");

				String name = data[FileConstant.NAME_INDEX];

				int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

				double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

				Student student = new Student(name, age, grade);

				students.add(student);
			}

			fileScanner.close();

			System.out.println("[INFO] Finished reading students.");

		} catch (IOException e) {

			System.out.println("[ERROR] Failed to read students file.");
		}

		return students;

	}

	public Student getStudentByName(String searchName) {
		Student student = null;
		try {

			Scanner fileScanner = new Scanner(new File("students.txt"));

			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				String[] data = line.split(",");

				String name = data[FileConstant.NAME_INDEX];

				if (name.equalsIgnoreCase(searchName)) {

					int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

					double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

					student = new Student(name, age, grade);

					System.out.println("[INFO] Student found: " + name);

					break;
				}
			}

			fileScanner.close();

		} catch (IOException e) {

			System.out.println("[ERROR] Failed to search students file.");
		}

		return student;
	}
	// Update
	public void updateStudent(int id, Student student) {
		
	}

	// Delete
	public void deleteStudent(int id) {
		
	}
}
