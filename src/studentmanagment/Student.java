package studentmanagment;


public class Student {

    private String name;
    private int age;
    private double grade;

    /**
     * Creates a Student object with the specified name, age, and grade.
     *
     * @param name the student's name
     * @param age the student's age
     * @param grade the student's grade
     */
    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    /**
     * Updates the student's name.
     *
     * @param name the new student name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the student's age.
     *
     * @param age the new student age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Updates the student's grade.
     *
     * @param grade the new student grade
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Returns the student's name.
     *
     * @return the student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the student's age.
     *
     * @return the student's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the student's grade.
     *
     * @return the student's grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Returns a formatted summary of the student's information,
     * including grade level and pass/fail status.
     *
     * @return formatted student information
     */
    public String studentInfo() {
        return "Student Name: " + this.name
                + ", Age: " + this.age
                + ", Grade: " + this.grade
                + ", Grade Level: " + getGradeLevel()
                + ", Status: " + getPassFailStatus();
    }

    /**
     * Determines whether the student has passed or failed.
     *
     * @return "Passed" when the grade is 60 or above; otherwise "Failed"
     */
    public String getPassFailStatus() {
        if (this.grade >= 60) {
            return "Passed";
        } else {
            return "Failed";
        }
    }

    /**
     * Determines the student's grade level based on the grade.
     *
     * @return the corresponding grade level
     */
    public String getGradeLevel() {
        if (this.grade >= 90) {
            return "Excellent";
        } else if (this.grade >= 80) {
            return "Very Good";
        } else if (this.grade >= 70) {
            return "Good";
        } else if (this.grade >= 60) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
