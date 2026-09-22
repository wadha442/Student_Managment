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

public class StudentFileDAOImpl implements StudentDAO {

    public boolean addStudent(Student student) {

        try {

            System.out.println("[FILE] Opening students.txt for writing...");

            FileWriter writer = new FileWriter(FileConstant.FileBath, true);

            writer.write(student.getId() + ","
                    + student.getName() + ","
                    + student.getAge() + ","
                    + student.getGrade() + "\n");

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

            Scanner fileScanner = new Scanner(new File(FileConstant.FileBath));

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                // NEW: Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                // NEW: Check that the line contains ID, name, age and grade
                if (data.length < 4) {
                    System.out.println("[ERROR] Invalid student data: " + line);
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

            System.out.println("[INFO] Finished reading students.");

        } catch (IOException e) {

            System.out.println("[ERROR] Failed to read students file.");
        }

        return students;
    }

    public Student getStudentByName(String searchName) {

        Student student = null;

        try {

            Scanner fileScanner = new Scanner(new File(FileConstant.FileBath));

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                // NEW: Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                // NEW: Check data
                if (data.length < 4) {
                    continue;
                }

                int id = Integer.parseInt(data[FileConstant.ID_INDEX]);

                String name = data[FileConstant.NAME_INDEX];

                if (name.equalsIgnoreCase(searchName)) {

                    int age = Integer.parseInt(data[FileConstant.AGE_INDEX]);

                    double grade = Double.parseDouble(data[FileConstant.GRADE_INDEX]);

                    student = new Student(name, age, grade, id);

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
    public boolean updateStudent(int id, Student student) {

        return false;
    }

    // Delete
    public boolean deleteStudent(int id) {

        return false;
    }
}