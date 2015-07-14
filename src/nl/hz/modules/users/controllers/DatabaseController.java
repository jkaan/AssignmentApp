package nl.hz.modules.users.controllers;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.hz.modules.users.views.DatabaseView;
import nl.hz.persistency.PersistentStorage;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class DatabaseController implements ClickListener, ItemClickListener {
	private static final long serialVersionUID = -1505690478869650371L;
	
	private PersistentStorage ps;
	private DatabaseView view;

	//data
	private String filepath;

	public DatabaseController(DatabaseView databaseView){
		view = databaseView;
		ps = new PersistentStorage();
		
		try {
			getNode();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Method for getting the current database name.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private void getNode() throws SAXException, IOException, ParserConfigurationException{
		//gets the filepath for sqlite-hibernate.xml and removes file:/ from the url
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("nl/hz/persistency/sqlite-hibernate.xml");
	  	
		filepath = url.toString();
		filepath = filepath.replace("file:/", "");
		
		//builds the xml page and retrieves all of the nodes inside the session-factory node
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		Node staff = doc.getElementsByTagName("session-factory").item(0);
		
		NodeList list = staff.getChildNodes();
		 
		for (int i = 0; i < list.getLength(); i++) {
 
           Node node = list.item(i);
        
           //Checks if string starts with jdbc:sqlite: and returns true if so
           String nodeSuffix = "jdbc:sqlite:";
           String node1 = node.getTextContent();
          
		   if ( node1.startsWith(nodeSuffix)== true) {
			   String a = node1;
			   view.setCurrentDb(a.replace("jdbc:sqlite:", "").replace(".sqlite", ""));
			

		   }
		}
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(view.getTextField1().getValue() != ""){
			Notification.show("Please reload the application.", Notification.Type.WARNING_MESSAGE);		
			ps.changeDb(view.getCurrentDb(),view.getTextField1().getValue(),filepath);	
		} else {
			Notification.show("Database field can't be empty!", Notification.Type.WARNING_MESSAGE);		
		}	
	}
	
	public void refresh() {
		UI.getCurrent().getSession().close();
		UI.getCurrent().getPage().setLocation("?restartApplication");
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
		
			view.setTextField1((String)view.getTabel1().getContainerProperty(view.getTabel1().getValue(),"Database").getValue());
		}
	}



}
