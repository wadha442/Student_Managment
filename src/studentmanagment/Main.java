package studentmanagment;

import java.util.Scanner;



import java.io.*;
	

public class Main {

    static Scanner input = new Scanner(System.in);

    static Student[] students = new Student[5];

    /**
     * Entry point of the Student Management System.
     * Controls the application flow and handles user menu selections.
     */
    public static void main(String[] args) {

        int choice;

        do {

            printMenu();

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    addStudents();
                    break;

                case 2:
                    showStudents();
                    break;

                case 3:
                    findStudent();
                    break;

                case 4:
                    System.out.println(
                            "\nThank you for using Student Management System!");
                    break;

                default:
                    System.out.println(
                            "\nInvalid choice. Please choose 1, 2, 3, or 4.");
            }

        } while (choice != 4);

        input.close();
    }

    /**
     * Displays the main menu and prompts the user to select
     * one of the available operations.
     */
    private static void printMenu() {

        System.out.println(
                "\n================================"
                + "\nStudent Management System"
                + "\n================================"
                + "\n1. Add Students"
                + "\n2. Show Students"
                + "\n3. Find Student"
                + "\n4. Exit"
                + "\nEnter your choice:"
        );
    }

	/**
	 * Collects student details from the user and stores them in file.
	 */
	private static void addStudents() {

		System.out.println("\n========== Add Students ==========***");

		System.out.println("\nEnter student ");

		System.out.print("Name: ");
		String name = input.nextLine();

		System.out.print("Age: ");
		int age = input.nextInt();

		System.out.print("Grade: ");
		double grade = input.nextDouble();

		input.nextLine();

		try {

			FileWriter writer = new FileWriter("students.txt", true);

			writer.write(name + "," + age + "," + grade + "\n");

			writer.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Student added successfully!");

	}

	 /**
     * Reads all students from the text file
     * and displays their information.
     */
    private static void showStudents() {

        // Displays the All Students section.
        System.out.println("\n========== All Students ==========");

        try {

            // Creates a File object representing students.txt.
            File file = new File("students.txt");

            // Scanner reads data from the text file.
            Scanner fileReader = new Scanner(file);

            // Checks whether the file contains any students.
            boolean hasStudents = false;

            // Reads the file one line at a time.
            while (fileReader.hasNextLine()) {

                // Reads one student's record.
                String studentData = fileReader.nextLine();

                // Splits the record into name, age, and grade.
                String[] data = studentData.split(",");

                // Gets the student's name.
                String name = data[0];

                // Converts the age to int.
                int age = Integer.parseInt(data[1]);

                // Converts the grade to double.
                double grade = Double.parseDouble(data[2]);

                // Creates a Student object from the file data.
                Student student =
                        new Student(name, age, grade);

                // Displays the student's information.
                System.out.println(student.studentInfo());

                // Prints a separator between students.
                System.out.println("--------------------------------");

                // Indicates that at least one student was found.
                hasStudents = true;
            }

            // Closes the file reader.
            fileReader.close();

            // Displays a message if the file is empty.
            if (!hasStudents) {

                System.out.println("No students found.");
            }

        } catch (IOException e) {

            // Handles the case where the file does not exist.
            System.out.println("No students file found.");
        }
    }

    /**
     * Searches for a student by name in the text file
     * and displays the student's information if found.
     */
    private static void findStudent() {

        // Displays the Find Student section.
        System.out.println("\n========== Find Student ==========");

        // Asks the user for the student's name.
        System.out.print("Enter student name: ");

        // Reads the name entered by the user.
        String searchName = input.nextLine();

        // Stores whether a matching student was found.
        boolean found = false;

        try {

            // Creates a File object for students.txt.
            File file = new File("students.txt");

            // Scanner reads the file.
            Scanner fileReader = new Scanner(file);

            // Reads each student record from the file.
            while (fileReader.hasNextLine()) {

                // Reads one line from the file.
                String studentData = fileReader.nextLine();

                // Splits the line into separate values.
                String[] data = studentData.split(",");

                // Gets the student's name.
                String name = data[0];

                // Converts the age to int.
                int age = Integer.parseInt(data[1]);

                // Converts the grade to double.
                double grade = Double.parseDouble(data[2]);

                // Creates a Student object from the data.
                Student student =
                        new Student(name, age, grade);

                // Compares the stored name with the search name.
                // equalsIgnoreCase() ignores uppercase/lowercase differences.
                if (student.getName()
                        .equalsIgnoreCase(searchName)) {

                    // Displays the matching student's information.
                    System.out.println(student.studentInfo());

                    // Indicates that the student was found.
                    found = true;

                    // Stops searching after finding the student.
                    break;
                }
            }

            // Closes the file reader.
            fileReader.close();

        } catch (IOException e) {

            // Handles file reading errors.
            System.out.println("No students file found.");
        }

        // Displays a message if no student was found.
        if (!found) {

            System.out.println("Student not found.");
        }
    }    
}