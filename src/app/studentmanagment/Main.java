package app.studentmanagment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import app.studentmanagment.exception.StudentAlreadyExistsException;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.impl.StudentDBServiceImpl;
import app.studentmanagment.service.impl.StudentFileDBServiceImpl;
import app.studentmanagment.util.DBConnection;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static StudentDBServiceImpl studentService = new StudentDBServiceImpl();
	static StudentFileDBServiceImpl fileService = new StudentFileDBServiceImpl();
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		logger.info("Application started successfully");

		testConnection();

		int option;

		try {
			do {
				logger.debug("Displaying main menu.");

				showMenu();

				option = scanner.nextInt();

				logger.info("User selected option: {}", option);

				switch (option) {

				case 1:
					logger.info("Starting Add Student operation.");
					addStudent();
					break;

				case 2:
					logger.info("Starting Show Students operation.");
					showStudents();
					break;

				case 3:
					logger.info("Starting Search Student operation.");
					searchStudent();
					break;

				case 4:
					logger.info("Starting Update Student operation.");
					updateStudent();
					break;

				case 5:
					logger.info("Starting Delete Student operation.");
					deleteStudent();
					break;

				case 6:
					logger.info("User selected Exit.");
					System.out.println("Thank you");
					break;

				default:
					logger.warn("Incorrect menu option: {}", option);
					System.out.println("Incorrect option try again");
				}

			} while (option != 6);

		} catch (InputMismatchException e) {
			System.out.println("[ERROR] Invalid input");
			logger.error("Invalid menu input.", e);
		}

		logger.info("Application closed.");
		scanner.close();
	}

	/**
	 * Displays the main menu and asks the user to select an option.
	 */
	private static void showMenu() {
		System.out.println("----------------------------\n" + "MENU:\n" + "1. Add Student\n" + "2. Show Students\n"
				+ "3. Find Student\n" + "4. Update Student\n" + "5. Delete Student\n" + "6. Exit\n"
				+ "Enter the number of the option you want: ");
	}

	/**
	 * Adds a new student to the students file.
	 */
	static void addStudent() {

		try {

			System.out.println("Enter student national ID:");
			scanner.nextLine();
			String nationalId = scanner.nextLine();

			System.out.println("Enter student name:");
			String name = scanner.nextLine();

			System.out.println("Enter student age:");
			int age = scanner.nextInt();

			System.out.println("Enter student grade:");
			double grade = scanner.nextDouble();

			Student student = new Student(name, nationalId, age, grade);

			boolean flag = studentService.addStudent(student);

			if (flag) {
				System.out.println("Student added successfully.");
			}

		} catch (IllegalArgumentException e) {

			System.out.println(e.getMessage());

		} catch (StudentAlreadyExistsException e) {

			System.out.println(e.getMessage());

		} catch (Exception e) {

			System.out.println("An unexpected error occurred.");

			logger.error("Error while adding student.", e);
		}
	}

	/**
	 * Retrieves and displays all students.
	 */

	static void showStudents() {

		try {
			logger.debug("Starting to retrieve all students.");

			List<Student> students = studentService.showStudents();

			for (Student student : students) {
				System.out.println(student.studentInfo());
			}

			logger.info("Students displayed successfully. Count: {}", students.size());
		} catch (Exception e) {
			logger.error("Failed to display students.", e);
			System.out.println("[ERROR] " + e.getMessage());
		}
	}

	/**
	 * Searches for a student by name and displays the student's information if a
	 * matching student is found.
	 */
	private static void searchStudent() {

		System.out.println("enter student name:");

		scanner.nextLine();

		String searchName = scanner.nextLine();

		logger.info(" Searching for student: " + searchName);
		try {

			String searchResult = studentService.searchStudent(searchName);

			System.out.println(searchResult);

		} catch (Exception e) {

			logger.error("Failed to search for student: {}", searchName, e);
			System.out.println("[ERROR] " + e.getMessage());

		}
	}

	/**
	 * Updates a student's name, age, and grade using their national ID. The
	 * national ID is used to identify the student and cannot be changed.
	 */

	private static void updateStudent() {
		scanner.nextLine();

		System.out.println("Enter student national ID:");
		String nationalId = scanner.nextLine();

		System.out.println("Enter new student name:");
		String name = scanner.nextLine();

		System.out.println("Enter new student age:");
		int age = scanner.nextInt();

		System.out.println("Enter new student grade:");
		double grade = scanner.nextDouble();

		try {
			Student student = new Student(name, nationalId, age, grade);

			boolean updated = studentService.updateStudent(nationalId, student);

			if (updated) {
				System.out.println("Student updated successfully.");
			} else {
				System.out.println("Student not found or update failed.");
			}

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn("Invalid student data.", e);
		}
	}

	/**
	 * Deletes a student from the system using their national ID.
	 */
	private static void deleteStudent() {
		scanner.nextLine();

		System.out.println("Enter student national ID:");
		String nationalId = scanner.nextLine();

		boolean deleted = studentService.deleteStudent(nationalId);

		if (deleted) {
			System.out.println("Student deleted successfully.");
		} else {
			System.out.println("Student not found or delete failed.");
		}
	}

	/**
	 * Tests the database connection.
	 */
	public static void testConnection() {

		try {
			logger.info("Database connection started.");
			Connection connection = DBConnection.getConnection();

			logger.info("Connected to the database successfully.");
			System.out.println("Connected to the database successfully.");

			connection.close();
			logger.info("Database connection closed.");

		} catch (SQLException e) {
			logger.error("Failed to connect to the database.", e);
		}
	}
}