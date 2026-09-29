package app.studentmanagment.model;


/**
 * Represents a course in the Student Management System.
 * Stores the course ID, name, description, and course code.
 */
public class Course {

	private int id;
	private String name;
	private String description;
	private String courseCode;
 
	// Constructors
	public Course(String courseName, String courseDescription) {
		this.name = courseName;
		this.description = courseDescription;
	}
 
	public Course(String name, String description, String courseCode) {
	    this.name = name;
	    this.description = description;
	    setCourseCode(courseCode);
	}
 
	public Course(int id, String courseName, String courseDescription) {
		this.id = id;
		this.name = courseName;
		this.description = courseDescription;
	}
 
	// Setters
	public void setId(int id) {
		this.id = id;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
 
	  /**
     * Updates the course code after validating its format.
     *
     * @param courseCode the new course code
     * @throws IllegalArgumentException if the course code is null
     *         or does not contain two letters followed by three digits
     */
	public void setCourseCode(String courseCode) {
		if (courseCode == null || !courseCode.matches("[a-zA-Z]{2}\\d{3}")) {
			throw new IllegalArgumentException("Course code must contain 2 letters followed by 3 digits, e.g. CS100");
		}
 
		this.courseCode = courseCode;
	}
 
	// Getters
	public int getId() {
		return id;
	}
 
	public String getName() {
		return name;
	}
 
	public String getDescription() {
		return description;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
 
	@Override
	public String toString() {
		return "Course Name: " + name +
				", description: " + description +
				", courseCode: " + courseCode;
	}
}
 

