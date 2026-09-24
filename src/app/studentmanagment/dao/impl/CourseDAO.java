package app.studentmanagment.dao.impl;


import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.model.Course;


public class CourseDAO {
	
	private CourseDAO courseDAO;
	
	
	public CourseDAO() {
	 
	courseDAO=new CourseDAO();

	}
	// Create
	public boolean addCourse(Course course) {
		 return true;
	}

	// Read
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		return courses;
	}

	public Course getCourse(int id) {
		Course course = null;
		return course;
	}

	// Update
	public boolean updateCourse(int id, Course course) {
	    return true;
	}

	// Delete
	public boolean deleteCourse(int id) {
	    return true;
	}
}
