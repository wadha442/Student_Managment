package app.studentmanagment.dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import app.studentmanagment.constants.FileConstant;
import app.studentmanagment.dao.StudentDAO;
import app.studentmanagment.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides file-based data access operations for student records.
 * This class implements the StudentDAO interface and uses a text file
 * to store and manage student information.
 */

public class StudentFileDAOImpl implements StudentDAO {

	private static final Logger logger = LogManager.getLogger(StudentFileDAOImpl.class);

	   /**
     * Adds a new student to the students file.
     *
     * @param student the student to be added
     * @return true if the student was saved successfully, otherwise false
     */
	public boolean addStudent(Student student) {

		try {

			logger.debug("Opening students.txt for writing.");

			FileWriter writer = new FileWriter(FileConstant.FileBath, true);

			writer.write(student.getName() + "," + student.getAge() + "," + student.getGrade() + ","
					+ student.getNationalId() + "\n");

			writer.close();

			logger.info("Student saved successfully to file. ID: {}", student.getId());

			return true;

		} catch (IOException e) {

			logger.error("Failed to save student to file. ID: {}", student.getId(), e);
		}

		return false;
	}

	   /**
     * Retrieves all students stored in the students file.
     *
     * @return a list containing all students stored in the file
     */
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

				String name = data[FileConstant.NAME_INDEX];
				int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);
				double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);
				String nationalId = data[FileConstant.nationalId_INDEX];

				Student student = new Student(name, nationalId, age, grade);
				students.add(student);
			}

			fileScanner.close();

			logger.info("Finished reading students from file. Count: {}", students.size());

		} catch (IOException e) {

			logger.error("Failed to read students file.", e);
		}

		return students;
	}

    /**
     * Searches for a student by name in the students file.
     *
     * @param searchName the name of the student to search for
     * @return the student if found, otherwise null
     */
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

				String name = data[FileConstant.NAME_INDEX];

				if (name.equalsIgnoreCase(searchName)) {

					int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

					double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

					student = new Student(name, age, grade);

					logger.info("Student found in file.Name: {}", name);

					break;
				}
			}

			fileScanner.close();

		} catch (IOException e) {

			logger.error("Failed to search students file. Name: {}", searchName, e);
		}

		return student;
	}

	   /**
     * Searches for a student by national ID in the students file.
     *
     * @param nationalId the national ID of the student to search for
     * @return the student if found, otherwise null
     */
	@Override
	public Student getStudentByNationalId(String nationalId) {

		Student student = null;

		try {
			Scanner fileScanner = new Scanner(new File(FileConstant.FileBath));

			while (fileScanner.hasNextLine()) {

				String line = fileScanner.nextLine();

				if (line.trim().isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				String name = data[FileConstant.NAME_INDEX];

				int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

				double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

				String fileNationalId = data[FileConstant.nationalId_INDEX];

				if (fileNationalId.equals(nationalId)) {

					student = new Student(name, nationalId, age, grade);

					break;
				}
			}

			fileScanner.close();

		} catch (Exception e) {
			logger.error("Failed to search student by national ID.", e);
		}

		return student;
	}

	  /**
     * Updates a student's name, age, and grade in the students file
     * using the student's national ID.
     *
     * @param nationalId the national ID of the student to update
     * @param student the updated student information
     * @return true if the student was updated successfully, otherwise false
     */
	
	@Override
	public boolean updateStudent(String nationalId, Student student) {

		List<Student> students = getAllStudent();

		boolean found = false;

		for (Student currentStudent : students) {

			if (currentStudent.getNationalId().equals(nationalId)) {

				currentStudent.setName(student.getName());

				currentStudent.setAge(student.getAge());

				currentStudent.setGrade(student.getGrade());

				found = true;
				break;
			}
		}

		if (!found) {

			logger.warn("Student not found in file. National ID: {}", nationalId);

			return false;
		}

		try {

			FileWriter writer = new FileWriter(FileConstant.FileBath);

			for (Student currentStudent : students) {

				writer.write(currentStudent.getName() + "," + currentStudent.getAge() + "," + currentStudent.getGrade()
						+ "," + currentStudent.getNationalId() + "\n");
			}

			writer.close();

			logger.info("Student updated successfully in file. National ID: {}", nationalId);

			return true;

		} catch (IOException e) {

			logger.error("Failed to update student in file. National ID: {}", nationalId, e);
		}

		return false;
	}


    /**
     * Deletes a student from the students file using the student's national ID.
     *
     * @param nationalId the national ID of the student to delete
     * @return true if the student was deleted successfully, otherwise false
     */
	
	@Override
	public boolean deleteStudent(String nationalId) {

		List<Student> students = getAllStudent();
		boolean found = false;

		Iterator<Student> iterator = students.iterator();

		while (iterator.hasNext()) {

			Student student = iterator.next();

			if (student.getNationalId().equals(nationalId)) {
				iterator.remove();
				found = true;
				break;
			}
		}

		if (!found) {
			return false;
		}

		try {
			FileWriter writer = new FileWriter(FileConstant.FileBath);

			for (Student student : students) {

				writer.write(student.getName() + "," + student.getAge() + "," + student.getGrade() + ","
						+ student.getNationalId() + "\n");
			}

			writer.close();

			logger.info("Student deleted successfully from file. National ID: {}", nationalId);

			return true;

		} catch (IOException e) {
			logger.error("Failed to delete student from file. National ID: {}", nationalId, e);
		}

		return false;
	}

}