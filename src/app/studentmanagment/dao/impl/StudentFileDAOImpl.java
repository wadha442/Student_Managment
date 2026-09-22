package app.studentmanagment.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.studentmanagement.dao.StudentDAO;
import app.studentmanagment.constants.FileConstant;
import app.studentmanagment.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentFileDAOImpl implements StudentDAO {

	private static final Logger logger = LogManager.getLogger(StudentFileDAOImpl.class);

	public boolean addStudent(Student student) {

		try {

			logger.debug("Opening students.txt for writing.");

			FileWriter writer = new FileWriter(FileConstant.FileBath, true);

			writer.write(student.getId() + "," + student.getName() + "," + student.getAge() + "," + student.getGrade()
					+ "\n");

			writer.close();

			logger.info("Student saved successfully to file. ID: {}", student.getId());

			return true;

		} catch (IOException e) {

			logger.error("Failed to save student to file. ID: {}", student.getId(), e);
		}

		return false;
	}

	public List<Student> getAllStudent() {

		List<Student> students = new ArrayList<Student>();

		logger.debug("Starting to read students from students.txt.");

		try {

			Scanner fileScanner = new Scanner(new File(FileConstant.FileBath));

			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				if (line.trim().isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				if (data.length < 4) {
					logger.warn("Invalid student data found in file: {}", line);
					continue;
				}

				int id = Integer.parseInt(data[FileConstant.ID_INDEX]);

				String name = data[FileConstant.NAME_INDEX];

				int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

				double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

				Student student = new Student(name, age, grade, id);

				students.add(student);
			}

			fileScanner.close();

			logger.info("Finished reading students from file. Count: {}", students.size());

		} catch (IOException e) {

			logger.error("Failed to read students file.", e);
		}

		return students;
	}

	public Student getStudentByName(String searchName) {

		Student student = null;

		logger.debug("Searching for student in file. Name: {}", searchName);
		try {

			Scanner fileScanner = new Scanner(new File(FileConstant.FileBath));

			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				if (line.trim().isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				if (data.length < 4) {

					logger.warn("Invalid student data found in file: {}", line);

					continue;
				}

				int id = Integer.parseInt(data[FileConstant.ID_INDEX]);

				String name = data[FileConstant.NAME_INDEX];

				if (name.equalsIgnoreCase(searchName)) {

					int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

					double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

					student = new Student(name, age, grade, id);

					logger.info("Student found in file. ID: {} Name: {}", id, name);

					break;
				}
			}

			fileScanner.close();

		} catch (IOException e) {

			logger.error("Failed to search students file. Name: {}", searchName, e);
		}

		return student;
	}

	// Update
	public boolean updateStudent(int id, Student student) {

		return false;
	}

	// Delete
	public boolean deleteStudent(int id) {

		return false;
	}
}