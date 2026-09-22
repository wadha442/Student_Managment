package app.studentmanagment;
 
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import app.studentmanagment.model.Student;
import app.studentmanagment.service.impl.StudentDBServiceImpl;
import app.studentmanagment.service.impl.StudentFileDBServiceImpl;
import app.studentmanagment.util.DBConnection;
 

 
public class Main {
 
	static Scanner scanner = new Scanner(System.in);
	static StudentDBServiceImpl studentService = new StudentDBServiceImpl();
	static StudentFileDBServiceImpl fileService = new StudentFileDBServiceImpl();

	public static void main(String[] args) throws Exception {
 
		testConnection() ;
		int option;
 
		do {
 
			System.out.println("[INFO] Displaying main menu.");
 
			showMenu();
			try {
			option = scanner.nextInt();
 
			System.out.println("[INFO] User selected option: " + option);
 
			switch (option) {
 
			case 1:
				System.out.println("[INFO] Starting Add Student operation.");
				addStudent();
				break;
 
			case 2:
				System.out.println("[INFO] Starting Show Students operation.");
				showStudent();
				System.out.println("[INFO] Students Showed successfully ");
				break;
 
			case 3:
				System.out.println("[INFO] Starting Search Student operation.");
				searchStudent();
				break;
 
			case 4:
				System.out.println("[INFO] User selected Exit.");
				System.out.println("Thank you");
				break;
 
			default:
				System.out.println("[ERROR] Incorrect option: " + option);
				System.out.println("Incorrect option try again");
			}
			} catch (InputMismatchException e) {

		        System.out.println("[ERROR] Invalid input.");
		        System.out.println("[ERROR] Please enter a number from 1 to 4.");

		        // Remove the invalid input
		        scanner.nextLine();

		        option = 0;
		    }
 
		} while (option != 4);
 
		System.out.println("[INFO] Application closed.");
 
		scanner.close();
	}
 
	/**
	 * Displays the main menu and asks the user to select an option.
	 */
	private static void showMenu() {
 
		System.out.println("----------------------------\n"
		+ "MENU:\n"
		+ "1. Add Student\n"
		+ "2. Show Students\n"
		+ "3. Find Student\n"
		+ "4. Exit\n"
		+ "Enter the number of the option you want: ");
	}
 
	/**
	 * Adds a new student to the students file.
	 */
	static void addStudent() {

	    try {

	        scanner.nextLine();

	        System.out.println("Enter student ID:");
	        int id = scanner.nextInt();

	        scanner.nextLine();

	        System.out.println("Enter student name:");
	        String name = scanner.nextLine();

	        System.out.println("Enter student age:");
	        int age = scanner.nextInt();

	        System.out.println("Enter student grade:");
	        double grade = scanner.nextDouble();

	        Student student = new Student(name, age, grade, id);

	        // Send the student to the DB Service
	        boolean flag = studentService.addStudent(student);

	        // NEW: If DB fails, use File Service
	        if (!flag) {

	            System.out.println("[ERROR] Database failed.");

	            System.out.println("[INFO] Switching to File Service...");

	            flag = fileService.addStudent(student);
	        }

	        if (flag) {

	            System.out.println("Student added successfully");

	        } else {

	            System.out.println("[ERROR] Failed to add student.");
	        }

	    } catch (InputMismatchException e) {

	        System.out.println("[ERROR] Invalid input.");
	        scanner.nextLine();
	    }
	}
	
	 static void showStudent() throws Exception {

	        try {

	            // Try Database Service first
	            List<Student> students = studentService.showStudents();

	            for (Student student : students) {

	                System.out.println(student.studentInfo());
	            }

	        } catch (SQLException e) {

	            // NEW: Database failed
	            System.out.println("[ERROR] Database failed.");

	            // NEW: Switch to File Service
	            System.out.println("[INFO] Switching to File Service...");

	            // NEW: Get students from File Service
	            List<Student> students = fileService.showStudents();

	            for (Student student : students) {

	                System.out.println(student.studentInfo());
	            }
	        }
	    }

	    /**
	     * Searches for a student by name.
	     */
	    private static void searchStudent() throws Exception {

	        System.out.println("enter student name:");

	        scanner.nextLine();

	        String searchName = scanner.nextLine();

	        System.out.println("[INFO] Searching for student: " + searchName);

	        try {

	            // Try Database Service first
	            String searchStudent = studentService.searchStudent(searchName);

	            System.out.println(searchStudent);

	        } catch (SQLException e) {

	            // NEW: Database failed
	            System.out.println("[ERROR] Database failed.");

	            // NEW: Switch to File Service
	            System.out.println("[INFO] Switching to File Service...");

	            // NEW: Search using File Service
	            String searchStudent = fileService.searchStudent(searchName);

	            System.out.println(searchStudent);
	        }
	    }
	
	public static void testConnection() {

	    try {
	        Connection connection = DBConnection.getConnection();

	        System.out.println("Connected successfully!");

	        connection.close();

	    } catch (SQLException e) {
	       System.out.println("[ERROR]"+e.getMessage());
	    }
	}
}