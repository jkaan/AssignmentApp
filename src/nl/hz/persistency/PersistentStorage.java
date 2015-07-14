package nl.hz.persistency;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class PersistentStorage {
	private static PersistentStorage __self;
	
	private Session singleSession;
	
	private Semaphore lock;
	
	private SessionFactory sessionFactory;
	
	public PersistentStorage(){
		init();
	}
	
	/**
	 * Initialize PersistentStorage
	 */
	private void init(){
		URL hibernateConfig = this.getClass().getResource("sqlite-hibernate.xml");
		Configuration config = new Configuration();
		config.configure(hibernateConfig);
		
		lock = new Semaphore(1);
		
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		singleSession = sessionFactory.openSession();
	}
	
	/**
	 * Methode voor het veranderen van de database waarbij de currentDb node in
	 * sqlite-hibernate.xml word veranderd met de newDb.
	 * 
	 * @param currentDb
	 * @param newDb
	 * @param filepath
	 */
	public void changeDb(String currentDb, String newDb, String filepath) {
		singleSession.close();
		sessionFactory.close();
		__self = null;

		try {
			// Parse the XML file using W3C library
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(getClass().getResource("sqlite-hibernate.xml").getPath());

			// Set connection.url property assuming it's the second node
			doc.getElementsByTagName("property").item(1).setTextContent("jdbc:sqlite:" + newDb + ".sqlite");

			// Write to the file
			DOMImplementationLS domImplementationLS = (DOMImplementationLS) doc
					.getImplementation().getFeature("LS", "3.0");
			LSOutput lsOutput = domImplementationLS.createLSOutput();
			FileOutputStream outputStream = new FileOutputStream(filepath);
			lsOutput.setByteStream((OutputStream) outputStream);
			LSSerializer lsSerializer = domImplementationLS
					.createLSSerializer();
			lsSerializer.write(doc, lsOutput);
			outputStream.close();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}

		//UI.getCurrent().getNavigator().navigateTo(LoginView.NAME_NAV + "/logout");
		//init();
	}
	
	private Throwable getRootCause(Exception e){
		Throwable t = e.getCause();
		
		if(t == null){
			return e;
		}
		
		while(e.getCause() != null){
			t = e.getCause();
		}
		
		return t;
	}
	
	private void commit(Session session){
		try {
			session.getTransaction().commit();
		} catch(TransactionException ex){
			Throwable root = getRootCause(ex);
			System.out.println('-' + root.getMessage() + '-');
			ex.printStackTrace();
		}
		releaseSessionLock();
	}
	
	private void begin(Session session){
		acquireSessionLock();
		
		while(session.isDirty()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Transaction trans = session.beginTransaction();
		while(!trans.isActive()){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Save a List with transient objects in the database
	 * @param objects Transient objects to save
	 */
	public void save(List<?> objects){
		if(objects == null || objects.size() <= 0){
			return;
		}
		
		Session session = getSession();
		
		begin(session);
		for(Object o : objects){
			session.save(o);
		}
		commit(session);
	}
	
	/**
	 * Save a Set with transient objects in the database
	 * @param objects Transient objects to save
	 */
	public void save(Set<?> objects){
		if(objects == null || objects.size() <= 0){
			return;
		}
		
		Session session = getSession();
		begin(session);
		for(Object o : objects){
			session.save(o);
		}
		commit(session);
	}

	/**
	 * Save a transient object to save
	 * @param o Transient object to save
	 */
	public void saveSingle(Object o){
		if(o == null){
			return;
		}
		
		Session session = getSession();
		begin(session);
		session.save(o);
		commit(session);
	}

	/**
	 * Save or update a list with objects.
	 * @param objects Objects to save or update.
	 */
	public void saveOrUpdate(List<?> objects){
		if(objects == null || objects.size() <= 0){
			return;
		}
		
		Session session = getSession();
		begin(session);
		for(Object o : objects){
			try {
				session.saveOrUpdate(o);
			} catch (NonUniqueObjectException e) {
				session.merge(o);
			}
		}
		commit(session);
	}

	/**
	 * Save or update an single object. It doesn't matter if the object is 
	 * transient or already attached to the session. If you're sure that 
	 * the object doesn't exist in the database, use saveSingle instead.
	 * @param o Object to save or update.
	 */
	public void saveOrUpdateSingle(Object o){
		if(o == null){
			return;
		}
		
		Session session = getSession();
		begin(session);
		try {
			session.saveOrUpdate(o);
		} catch (NonUniqueObjectException e) {
			session.merge(o);
		} 
		commit(session);
	}

	/**
	 * Get all objects from a specific mapping class
	 * @param c Class object. It should look like <code>YourClass.class</code>
	 * @return Array with objects
	 */
	public List<?> getAll(Class<?> c){
		Session session = getSession();
		return session.createCriteria(c).list();
	}
	
	/**
	 * Get a specific object from a specific mapping class.
	 * @param c Class object. It should look like <code>YourClass.clas</code>
	 * @param id Id of the object
	 * @return Unique object
	 */
	public Object getSingle(Class<?> c, long id) {
		Session session = getSession();
		return session.createCriteria(c).add(Restrictions.idEq(id)).uniqueResult();
	}
	
	public void acquireSessionLock(){
		System.err.println("Session lock acquired");
		System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		lock.acquireUninterruptibly();
	}
	public void releaseSessionLock(){
		System.err.println("Session lock released");
		System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
		lock.release();
	}

	public synchronized Session getSession(){
		return singleSession;
	}
	
	public static synchronized final PersistentStorage getInstance(){
		if(PersistentStorage.__self == null){
			PersistentStorage.__self = new PersistentStorage();
		}
		return PersistentStorage.__self;
	}
	
	public static String getTableName(Class<?> c){
		ClassMetadata hibernateMetadata = PersistentStorage.getInstance().sessionFactory.getClassMetadata(c);
		if (hibernateMetadata == null){
		    return null;
		}
		if(hibernateMetadata instanceof AbstractEntityPersister){
		     AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
		     return persister.getTableName();
		}
		return null;
	}
	public static String[] getColumns(Class<?> c){
		ClassMetadata hibernateMetadata = PersistentStorage.getInstance().sessionFactory.getClassMetadata(c);
		if (hibernateMetadata == null){
		    return null;
		}
		if(hibernateMetadata instanceof AbstractEntityPersister){
		     AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
		     return persister.getKeyColumnNames();
		}
		return null;
	}
}
