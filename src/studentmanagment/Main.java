package studentmanagment;

import java.util.Scanner;

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
     * Collects student details from the user, creates Student objects,
     * and stores them in the student array.
     */
    private static void addStudents() {

        System.out.println("\n========== Add Students ==========");

        for (int i = 0; i < students.length; i++) {

            System.out.println("\nEnter student " + (i + 1));

            System.out.print("Name: ");
            String name = input.nextLine();

            System.out.print("Age: ");
            int age = input.nextInt();

            System.out.print("Grade: ");
            double grade = input.nextDouble();

            input.nextLine();

            Student student = new Student(name, age, grade);

            students[i] = student;

            System.out.println("Student added successfully!");
        }
    }

    /**
     * Displays the information of all students currently
     * stored in the student array.
     */
    private static void showStudents() {

        System.out.println("\n========== All Students ==========");

        for (Student student : students) {

            if (student != null) {
                System.out.println(student.studentInfo());
                System.out.println("--------------------------------");
            }
        }
    }

    /**
     * Searches for a student by name and displays the student's
     * information when a matching record is found.
     */
    private static void findStudent() {

        System.out.println("\n========== Find Student ==========");

        System.out.print("Enter student name: ");

        String searchName = input.nextLine();

        boolean found = false;

        for (Student student : students) {

            if (student != null
                    && student.getName().equalsIgnoreCase(searchName)) {

                System.out.println(student.studentInfo());

                found = true;

                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }
}