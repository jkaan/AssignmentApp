package nl.hz.bict.sor21314.team1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.persistency.PersistentStorage;

import org.hibernate.Query;
import org.hibernate.Session;

public class CategoryService extends Observable {
	
	private static CategoryService instance;

	public static CategoryService getInstance() {
		if(instance == null)
			instance = new CategoryService();
		
		return instance;
	}
	
	public List<Category> getCategories(){
		
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		storage.acquireSessionLock();
		Query query = session.createQuery("FROM Category");
		
		@SuppressWarnings("unchecked")
		List<Category> outList = query.list();
		
		storage.releaseSessionLock();
		
		return outList;
	}

	
	public Category getCategory(long id) {
		PersistentStorage storage = PersistentStorage.getInstance();
		return (Category) storage.getSingle(Category.class, id);
	}
	
	public List<Category> getAllCategories() {
		PersistentStorage storage = PersistentStorage.getInstance();
		ArrayList<Category> categories = new ArrayList<Category>();
		
		for(Object o : storage.getAll(Category.class)) {
			Category category = (Category) o;
			categories.add(category);
		}
		
		return categories;
	}
	
	public void insertCategory(Category category) {
		PersistentStorage storage = PersistentStorage.getInstance();
		storage.saveSingle(category);
		
		// Observer
		setChanged();
		notifyObservers();
	}
	
	public void updateCategory(Category category) {
		PersistentStorage storage = PersistentStorage.getInstance();
		storage.saveOrUpdateSingle(category);
		
		// Observer
		setChanged();
		notifyObservers();
	}

}
