package app.studentmanagment.model;

/**
 * Represents a student in the Student Management System. Stores the student's
 * personal information, national ID, age, grade, and student ID.
 */

public class Student {
	private int id;
	private String name;
	private int age;
	private double grade;
	private String nationalId;

	public Student(String name, int age, double grade) {
		this.name = name;
		this.age = age;
		this.grade = grade;
	}

	public Student(String name, String nationalId, int age, double grade, int id) {
		this.name = name;
		this.nationalId = nationalId;
		this.age = age;
		this.grade = grade;
		this.id = id;
	}

	public Student(String name, String nationalId, int age, double grade) {
		this.name = name;
		setNationalId(nationalId);
		this.age = age;
		this.grade = grade;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getGrade() {
		return grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNationalId() {
		return nationalId;
	}

	/**
	 * Updates the student's national ID after validating it.
	 *
	 * @param nationalId the student's national ID
	 * @throws IllegalArgumentException if the national ID is null, empty, does not
	 *   contain 10 digits, or does not start with 1
	 *   or 2
	 */
	public void setNationalId(String nationalId) {

		if (nationalId == null || nationalId.isEmpty()) {
			throw new IllegalArgumentException("National ID is required");
		}

		if (nationalId.length() != 10) {
			throw new IllegalArgumentException("National ID must contain 10 digits");
		}

		if (nationalId.charAt(0) != '1' && nationalId.charAt(0) != '2') {
			throw new IllegalArgumentException("National ID must start with 1 or 2");
		}

		this.nationalId = nationalId;
	}

	/**
	 * Returns a formatted summary of the student's information, including grade
	 * level and pass/fail status.
	 *
	 * @return formatted student information
	 */
	public String studentInfo() {

		return "Student Name: " + this.name + ", National ID: " + this.nationalId + ", Age: " + this.age + ", Grade: "
				+ this.grade + ", Grade Level: " + getGradeLevel() + ", Status: " + getPassFailStatus();
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
