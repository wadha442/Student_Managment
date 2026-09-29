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
import app.studentmanagment.model.Course;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.impl.CourseService;
import app.studentmanagment.service.impl.StudentDBServiceImpl;
import app.studentmanagment.service.impl.StudentFileDBServiceImpl;
import app.studentmanagment.util.DBConnection;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static StudentDBServiceImpl studentService = new StudentDBServiceImpl();
	static StudentFileDBServiceImpl fileService = new StudentFileDBServiceImpl();
	static CourseService courseService = new CourseService();
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		logger.info("Application started successfully");

		testConnection();

		int option;

		try {

			do {

				logger.debug("Displaying main menu.");

				showMainMenu();

				option = scanner.nextInt();

				logger.info("User selected main menu option: {}", option);

				switch (option) {

				case 1:
					studentMenu();
					break;

				case 2:
					courseMenu();
					break;

				case 3:
					logger.info("User selected Exit.");
					System.out.println("Thank you");
					break;

				default:
					logger.warn("Incorrect main menu option: {}", option);

					System.out.println("Incorrect option try again");
				}

			} while (option != 3);

		} catch (InputMismatchException e) {

			System.out.println("[ERROR] Invalid input");

			logger.error("Invalid menu input.", e);
		}

		logger.info("Application closed.");

		scanner.close();
	}

	private static void showMainMenu() {

		System.out.println("----------------------------\n" + "       MAIN MENU\n" + "----------------------------\n"
				+ "1. Student Management\n" + "2. Course Management\n" + "3. Exit\n"
				+ "Enter the number of the option you want: ");
	}

	private static void studentMenu() {

		int option;

		do {

			showStudentMenu();

			try {

				option = scanner.nextInt();

				switch (option) {

				case 1:
					addStudent();
					break;

				case 2:
					showStudents();
					break;

				case 3:
					searchStudent();
					break;

				case 4:
					updateStudent();
					break;

				case 5:
					deleteStudent();
					break;

				case 6:
					System.out.println("Returning to Main Menu...");
					break;

				default:
					System.out.println("Incorrect option try again");
				}

			} catch (InputMismatchException e) {

				logger.error("Invalid Student menu input.", e);

				System.out.println("[ERROR] Invalid input");

				scanner.nextLine();

				option = 0;
			}

		} while (option != 6);
	}

	/**
	 * Displays the Student Management menu.
	 */
	private static void showStudentMenu() {

		System.out.println(
				"\n----------------------------\n" + "    STUDENT MANAGEMENT\n" + "----------------------------\n"
						+ "1. Add Student\n" + "2. Show Students\n" + "3. Find Student\n" + "4. Update Student\n"
						+ "5. Delete Student\n" + "6. Back\n" + "Enter the number of the option you want: ");
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
	 * Displays the Course Management menu.
	 */
	private static void courseMenu() {

		int option;

		do {

			showCourseMenu();

			try {

				option = scanner.nextInt();

				switch (option) {

				case 1:

					logger.info("Starting Add Course operation.");

					addCourse();

					break;

				case 2:

					logger.info("Starting Show Courses operation.");

					showCourses();

					break;

				case 3:

					logger.info("Starting Find Course operation.");

					findCourse();

					break;

				case 4:

					logger.info("Starting Update Course operation.");

					updateCourse();

					break;

				case 5:

					logger.info("Starting Delete Course operation.");

					deleteCourse();

					break;

				case 6:

					System.out.println("Returning to Main Menu...");

					break;

				default:

					System.out.println("Incorrect option try again");
				}

			} catch (InputMismatchException e) {

				logger.error("Invalid Course menu input.", e);

				System.out.println("[ERROR] Invalid input");

				scanner.nextLine();

				option = 0;
			}

		} while (option != 6);
	}

	/**
	 * Displays the Course Management menu.
	 */
	private static void showCourseMenu() {

		System.out.println(
				"\n----------------------------\n" + "     COURSE MANAGEMENT\n" + "----------------------------\n"
						+ "1. Add Course\n" + "2. Show Courses\n" + "3. Find Course\n" + "4. Update Course\n"
						+ "5. Delete Course\n" + "6. Back\n" + "Enter the number of the option you want: ");
	}

	/**
	 * Adds a new course to the database.
	 */
	private static void addCourse() {

		try {

			scanner.nextLine();

			System.out.println("Enter course name:");

			String name = scanner.nextLine();

			System.out.println("Enter course description:");

			String description = scanner.nextLine();

			System.out.println("Enter course code:");

			String courseCode = scanner.nextLine();

			Course course = new Course(name, description, courseCode);

			boolean added = courseService.addCourse(course);

			if (added) {

				System.out.println("Course added successfully.");

			} else {

				System.out.println("Course was not added.");
			}

		} catch (IllegalArgumentException e) {

			System.out.println(e.getMessage());

		} catch (SQLException e) {

			logger.error("Failed to add course.", e);

			System.out.println("[ERROR] Database error: " + e.getMessage());
		}
	}

	/**
	 * Retrieves and displays all courses.
	 */
	private static void showCourses() {

		try {

			List<Course> courses = courseService.showCourses();

			for (Course course : courses) {

				System.out.println(course);
			}

		} catch (SQLException e) {

			logger.error("Failed to retrieve courses.", e);

			System.out.println("[ERROR] Database error: " + e.getMessage());
		}
	}

	/**
	 * Finds a course using its course code.
	 */
	private static void findCourse() {

		try {

			scanner.nextLine();

			System.out.println("Enter course code:");

			String courseCode = scanner.nextLine();

			Course course = courseService.findCourses(courseCode);

			if (course != null) {

				System.out.println(course);

			} else {

				System.out.println("Course not found.");
			}

		} catch (SQLException e) {

			logger.error("Failed to find course.", e);

			System.out.println("[ERROR] Database error: " + e.getMessage());
		}
	}

	/**
	 * Updates a course using its course code.
	 */
	private static void updateCourse() {

		try {

			scanner.nextLine();

			System.out.println("Enter course code:");

			String courseCode = scanner.nextLine();

			System.out.println("Enter new course name:");

			String name = scanner.nextLine();

			System.out.println("Enter new course description:");

			String description = scanner.nextLine();

			System.out.println("Enter new course code:");

			String newCourseCode = scanner.nextLine();

			Course course = new Course(name, description, newCourseCode);

			boolean updated = courseService.updateCourse(courseCode, course);

			if (updated) {

				System.out.println("Course updated successfully.");

			} else {

				System.out.println("Course not found or update failed.");
			}

		} catch (IllegalArgumentException e) {

			System.out.println(e.getMessage());

		} catch (SQLException e) {

			logger.error("Failed to update course.", e);

			System.out.println("[ERROR] Database error: " + e.getMessage());
		}
	}

	/**
	 * Deletes a course using its course code.
	 */
	private static void deleteCourse() {

		try {

			scanner.nextLine();

			System.out.println("Enter course code:");

			String courseCode = scanner.nextLine();

			boolean deleted = courseService.deleteCourse(courseCode);

			if (deleted) {

				System.out.println("Course deleted successfully.");

			} else {

				System.out.println("Course not found or delete failed.");
			}

		} catch (SQLException e) {

			logger.error("Failed to delete course.", e);

			System.out.println("[ERROR] Database error: " + e.getMessage());
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