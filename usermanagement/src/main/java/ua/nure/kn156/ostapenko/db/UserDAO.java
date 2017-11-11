package ua.nure.kn156.ostapenko.db;

import java.util.Collection;

import ua.nure.kn156.ostapenko.User;

/**
 * Interface for User class which implements DAO pattern with all CRUD
 * operations
 * 
 * @author Maksym
 *
 */
public interface UserDao {
	/**
	 * Adds user into DB users table and get new user's id from db
	 * 
	 * @param user
	 *            all field of user must be non-null except of id field
	 * @return copy of user from db with id autocreated
	 * @throws DatabaseException
	 *             in case of any error with database
	 */
	User create(User user) throws DatabaseException;

	/**
	 * Finds user in DB users table
	 * 
	 * @param id
	 *            must be non-null
	 * @return the user with a particular id
	 * @throws DatabaseException
	 *             in case of any error with database
	 */
	User find(Long id) throws DatabaseException;

	/**
	 * Updates users table in DB
	 * 
	 * @param user
	 *            must be non-null as well as its fields
	 * @throws DatabaseException
	 *             in case of any error with database
	 */
	void update(User user) throws DatabaseException;

	/**
	 * Deletes user from the users table in DB
	 * 
	 * @param user
	 *            must be non-null as well as its fields
	 * @throws DatabaseException
	 *             in case of any error with database
	 */
	void delete(User user) throws DatabaseException;

	/**
	 * Finds all users in DB users table
	 * 
	 * @return a collection which contains all users from DB
	 * @throws DatabaseException
	 *             in case of any error with database
	 */
	Collection<User> findAll() throws DatabaseException;

	/**
	 * Sets a connection factory that is used
	 * 
	 * @param connectionFactory
	 *            that is used for creating connection factory
	 */
	void setConnectionFactory(ConnectionFactory connectionFactory);

}
