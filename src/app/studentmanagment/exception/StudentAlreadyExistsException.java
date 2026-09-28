package app.studentmanagment.exception;

/**
 * Custom exception that is thrown when an attempt is made
 * to add a student who already exists in the system.
 */
public class StudentAlreadyExistsException extends Exception {

	public StudentAlreadyExistsException(String massage ) {
	super(massage);	
		
		
		
	}
	
}
