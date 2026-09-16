package app.studentmanagment;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
import app.studentmanagment.constants.FileConstant;
public class Main {

    static Scanner input = new Scanner(System.in);
    static StudentService studentService = new StudentService();

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

            System.out.println("[INFO] User selected option: " + choice);

            switch (choice) {

                case 1:
                    System.out.println("[INFO] Starting Add Student...");
                    addStudents();
                    break;

                case 2:
                    System.out.println("[INFO] Starting Show Students...");
                    showStudents();
                    break;

                case 3:
                    System.out.println("[INFO] Starting Find Student...");
                    findStudent();
                    break;

                case 4:
                    System.out.println("[INFO] Exiting Student Management System...");
                    System.out.println(
                            "\nThank you for using Student Management System!");
                    break;

                default:
                    System.out.println(
                            "[ERROR] Invalid menu choice: " + choice);
                    System.out.println(
                            "\nInvalid choice. Please choose 1, 2, 3, or 4.");
            }

        } while (choice != 4);

        input.close();
        System.out.println("[INFO] Application closed successfully.");
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

        System.out.println("[INFO] Main menu displayed.");
    }

    /**
     * Collects student details from the user and stores them in file.
     */
    private static void addStudents() {

        System.out.println("\n========== Add Students ==========");
        System.out.println("[INFO] Collecting student information...");

        System.out.println("\nEnter student ");

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Age: ");
        int age = input.nextInt();

        System.out.print("Grade: ");
        double grade = input.nextDouble();

        input.nextLine();

        Student student = new Student(name, age, grade);

        studentService.addStudent(student);
    }

    /**
     * Reads all students from the text file
     * and displays their information.
     */
    private static void showStudents() {

        System.out.println("\n========== All Students ==========");
        System.out.println("[FILE] Reading students.txt...");

        try {

            File file = new File("students.txt");
            Scanner fileReader = new Scanner(file);

            boolean hasStudents = false;

            while (fileReader.hasNextLine()) {

                String studentData = fileReader.nextLine();
                String[] data = studentData.split(",");

                String name = data[FileConstant.NAME_INDEX];

                int age = Integer.parseInt(
                        data[FileConstant.AGE_INDEX]);

                double grade = Double.parseDouble(
                        data[FileConstant.GRADE_INDEX]);

                Student student =
                        new Student(name, age, grade);

                System.out.println(student.studentInfo());
                System.out.println("--------------------------------");

                hasStudents = true;
            }

            fileReader.close();

            if (!hasStudents) {

                System.out.println("[INFO] No students found in file.");

            } else {

                System.out.println("[INFO] Students loaded successfully.");
            }

        } catch (IOException e) {

            System.out.println("[ERROR] Unable to read students file.");
            System.out.println("No students file found.");
        }
    }

    /**
     * Searches for a student by name in the text file
     * and displays the student's information if found.
     */
    private static void findStudent() {

        System.out.println("\n========== Find Student ==========");
        System.out.print("Enter student name: ");

        String searchName = input.nextLine();

        System.out.println(
                "[SEARCH] Searching for student: " + searchName);

        boolean found = false;

        try {

            File file = new File("students.txt");
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {

                String studentData = fileReader.nextLine();
                String[] data = studentData.split(",");

                String name = data[FileConstant.NAME_INDEX];

                int age = Integer.parseInt(
                        data[FileConstant.AGE_INDEX]);

                double grade = Double.parseDouble(
                        data[FileConstant.GRADE_INDEX]);

                Student student =
                        new Student(name, age, grade);

                if (student.getName()
                        .equalsIgnoreCase(searchName)) {

                    System.out.println(
                            "[SEARCH] Student found: " + name);

                    System.out.println(student.studentInfo());

                    found = true;
                    break;
                }
            }

            fileReader.close();

        } catch (IOException e) {

            System.out.println("[ERROR] Unable to read students file.");
            System.out.println("No students file found.");
        }

        if (!found) {

            System.out.println(
                    "[SEARCH] Student not found: " + searchName);
        }
    }
}