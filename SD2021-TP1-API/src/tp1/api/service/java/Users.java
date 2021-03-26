package tp1.api.service.java;

import java.util.List;

import tp1.api.Result;
import tp1.api.User;

public interface Users {

	/**
	 * Creates a new user.
	 * 
	 * @param user User to be created.
	 * @return 200 the userId.
	 *         409 if the user id already exists. 
	 *         400 otherwise.
	 */
	Result<String> createUser(User user);
	
	/**
	 * Obtains the information on the user identified by name.
	 * @param userId the userId of the user
	 * @param password password of the user
	 * @return 200 the user object, if the userId exists and password matches the existing
	 *         password
	 *         403 if the password is incorrect
	 *         404 if no user exists with the provided userId
	 */
	Result<User> getUser(String userId, String password);
	
	/**
	 * Modifies the information of a user. Values of null in any field of the user will be 
	 * considered as if the the fields is not to be modified (the id cannot be modified).
	 * @param userId the userId of the user
	 * @param password password of the user
	 * @param user Updated information
	 * @return 200 the updated user object, if the name exists and password matches the
	 *         existing password 
	 *         403 if the password is incorrect
	 *         404 if no user exists with the provided userId
	 *         400 otherwise.
	 */
	Result<User> updateUser(String userId, String password, User user);
	
	/**
	 * Deletes the user identified by userId. The spreadsheets owned by the user should be eventually removed (asynchronous
	 * deletion is ok).
	 * @param nauserId the userId of the user
	 * @param password password of the user
	 * @return 200 the deleted user object, if the name exists and pwd matches the
	 *         existing password 
	 *         403 if the password is incorrect
	 *         404 if no user exists with the provided userId
	 */
	Result<User> deleteUser(String userId, String password);
	
	/**
	 * Returns the list of users for which the pattern is a substring of the name (of the user), case-insensitive.
	 * The password of the users returned by the query must be set to the empty string "".
	 * @param pattern substring to search
	 * @return 200 when the search was successful, regardless of the number of hits (including 0 hits).
	 *         400 otherwise.
	 */
	Result<List<User>> searchUsers(String pattern);
}
