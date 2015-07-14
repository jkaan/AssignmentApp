package nl.hz.bict.sor21314.team1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.persistency.PersistentStorage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vaadin.ui.Notification;

/**
 * This class handles all requests to the database related to users.
 * @author Kevin, Joey, Trinco, Joeri, Jurgen, Mick, Thomas
 * @version 20-04-2014
 */
public class UserService extends Observable {

	private static UserService instance;

	public UserService() {
		// Nothing to do
	}
	
	/**
	 * @return UsersService instance
	 */
	public static UserService getInstance() {
		if(instance == null)
			instance = new UserService();
		return instance;
	}

	/**
	 * Add a single user in the database
	 * @param username Username of the user
	 * @param email Email of the user
	 * @param password Password of the user
	 */
	public void insertUser(String username, String email, String role, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setRole(role);
		user.setPassword(password);
		
		insertUser(user);
	}
	
	/**
	 * Add a single user in the database
	 * @param user User object to be added
	 */
	public void insertUser(User user) {
		PersistentStorage storage = PersistentStorage.getInstance();
		storage.saveSingle(user);
		
		// Observer
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Update a single user in the datase
	 * @param user User object to be updated
	 */
	public void updateUser(User user) {
		PersistentStorage storage = PersistentStorage.getInstance();
		storage.saveOrUpdateSingle(user);
		
		// Observer
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return Returns a list with all users from the database
	 */
	public List<User> getAllUsers() {
		PersistentStorage storage = PersistentStorage.getInstance();
		ArrayList<User> users = new ArrayList<User>();
		
		for(Object o : storage.getAll(User.class)) {
			User user = (User) o;
			users.add(user);
		}
		
		return users;
	}
	
	/**
	 * Get a single User specified by the unique id
	 * @param id ID of the user
	 * @return User object
	 */
	public User getUser(long id) {
		PersistentStorage storage = PersistentStorage.getInstance();
		return (User) storage.getSingle(User.class, id);
	}
	
	/**
	 * Get a single user specified by the username and id. This is useful for a login.
	 * @param username Username of the user
	 * @param password Password of the user
	 * @return User object
	 */
	public User getUser(String username, String password) {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		return (User) session.createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password))
				.uniqueResult();
	}

	public List<User> getUsers() {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();

		storage.acquireSessionLock();
		Query query = session.createQuery("from User");
		@SuppressWarnings("unchecked")
		List<User> results = query.list();

		storage.releaseSessionLock();

		return results;
	}

	/* Method to UPDATE user */
	public void updateUser(long ID, String username, String mail, String pass, String newpass) {
		PersistentStorage storage = PersistentStorage.getInstance();
        
        Session session = storage.getSession();
        Object o=session.load(User.class, ID);
        User s=(User)o;
        if (pass.isEmpty() && !newpass.isEmpty()) {
        	Notification.show("Give current password!");
        }
        
        else if (pass.isEmpty() && newpass.isEmpty()) {
        	Transaction tx = session.beginTransaction();  
        	s.setUsername(username);
        	s.setEmail(mail);
        	tx.commit();
            Notification.show("Opgeslagen");
        }
        
        else if (s.getPassword().equals(pass) && !newpass.isEmpty()) {
        	Transaction tx = session.beginTransaction();  
        	s.setUsername(username);
        	s.setPassword(newpass);
        	tx.commit();
            Notification.show("Opgeslagen");
        }
        
        else {
        	Notification.show("Error");
        }
	}
}
