package app.studentmanagment.service;
 
import java.sql.SQLException;
import java.util.List;

import app.studentmanagment.model.Student;
 
/**
 * Defines the business operations for managing students.
 * Provides methods for adding, displaying, searching,
 * updating, and deleting student records.
 */

public interface StudentService {
 
	   /**
     * Adds a new student to the system.
     *
     * @param student the student to be added
     * @return true if the student was added successfully; otherwise false
     * @throws Exception if an error occurs while adding the student
     */
	public boolean addStudent(Student student)throws Exception;
 
	   /**
     * Retrieves all students from the system.
     *
     * @return a list containing all students
     * @throws Exception if an error occurs while retrieving students
     */
	public List<Student> showStudents() throws Exception;
 
	  /**
     * Searches for a student by their name.
     *
     * @param studentName the name of the student to search for
     * @return the student's information if found
     * @throws Exception if an error occurs while searching for the student
     */
	public String searchStudent(String studentName) throws Exception;
 
	  /**
     * Updates an existing student's information using their national ID.
     *
     * @param nationalId the national ID of the student to update
     * @param student the student object containing the updated information
     * @return true if the student was updated successfully; otherwise false
     */
	public boolean updateStudent(String nationalId, Student student);

	  /**
     * Deletes a student from the system using their national ID.
     *
     * @param nationalId the national ID of the student to delete
     * @return true if the student was deleted successfully; otherwise false
     */
	public boolean deleteStudent(String nationalId);
	
}