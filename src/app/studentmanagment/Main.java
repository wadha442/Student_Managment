package app.studentmanagment;
 
import java.util.Scanner;

import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
 

 
public class Main {
 
	static Scanner scanner = new Scanner(System.in);
	static StudentService studentService = new StudentService();
 
	public static void main(String[] args) {
 
		int option;
 
		do {
 
			System.out.println("[INFO] Displaying main menu.");
 
			showMenu();
 
			option = scanner.nextInt();
 
			System.out.println("[INFO] User selected option: " + option);
 
			switch (option) {
 
			case 1:
				System.out.println("[INFO] Starting Add Student operation.");
				addStudent();
				break;
 
			case 2:
				System.out.println("[INFO] Starting Show Students operation.");
				studentService.showStudents();
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
 
		scanner.nextLine();
 
		System.out.println("Enter student name:");
		String name = scanner.nextLine();
 
		System.out.println("Enter student age:");
		int age = scanner.nextInt();
 
		System.out.println("Enter student grade:");
		double grade = scanner.nextDouble();
 
		Student student = new Student(name, age, grade);
 
		studentService.addStudent(student);
	}
 
	/**
	 * Searches for a student by name and displays the student's information if a
	 * matching student is found.
	 */
	private static void searchStudent() {
 
		System.out.println("enter student name:");
 
		scanner.nextLine();
 
		String searchName = scanner.nextLine();
 
		System.out.println("[INFO] Searching for student: " + searchName);
 
		studentService.searchStudent(searchName);
	}
}