package nl.hz.modules.users.views;

import java.io.File;
import java.io.FilenameFilter;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.modules.users.controllers.DatabaseController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


/**
 * Deze view is voor het veranderen van de database. Hierbij word de changeDb() methode aangeroepen in persistent storage
 * die de database zal veranderen naar de nieuwe database.
 * 
 * @author Jurgen
 */
@SuppressWarnings("serial")
public class DatabaseView extends AdministrationView implements View{
	public static final String NAME_NAV = "administration/databases";
	
	//mvc
	private DatabaseController controller;
	
	//layout
	private Button button1;
	private Table tabel1;
	private TextField textField1;
	private VerticalLayout pageContent;

	private File[] files;
	private String currentDb;

	public DatabaseView(){
		super();	

	}
	
	@Override
	protected void initContentLayout() {
		initTitle();
	
		controller = new DatabaseController(this);
		
		pageContent = new VerticalLayout();
		pageContent.setSpacing(true);
		
		setContentLayout(pageContent);
		
		innit();
	}
	
	private void initTitle() {
		setViewTitle("DatabaseConfig");
	}
	
	private void innit(){
		
		tabel1 = new Table();
		tabel1.addContainerProperty("Database", String.class,  null);
		tabel1.addContainerProperty("Active", Boolean.class,  null);
		tabel1.setWidth("100%");
		tabel1.setPageLength(0);
		
		tabel1.setSelectable(true);
		tabel1.setNullSelectionAllowed(false);
		tabel1.setImmediate(true);
		
		tabel1.addStyleName(Reindeer.TABLE_BORDERLESS);
		tabel1.addStyleName(Reindeer.TABLE_STRONG);
		tabel1.addItemClickListener(controller);
		
			
		textField1 = new TextField("Change to new or existing database.");
		button1 = new Button("Change database");
		button1.addClickListener(controller);
		
		
		pageContent.addComponent(tabel1);
		pageContent.addComponent(textField1);
		pageContent.addComponent(button1);
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		
		if(!getReturned())
			setup();
	}
	
	private void setup(){
		
		File dir = new File(".");
		files = dir.listFiles(new FilenameFilter() {
		    @Override 
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".sqlite");
		    }
		});
		
		int i = 1;
		
		for(File sqliteFile: files){
			String s = sqliteFile.toString();
			s = s.replace(".\\", "").replace(".sqlite","");
			
			if(s.equals(getCurrentDb())){
				tabel1.addItem(new Object[] {s,true}, new Integer(i));
			}
			else{
				tabel1.addItem(new Object[] {s,false}, new Integer(i));
			}
			
			i++;
		}
	}
	

	public Table getTabel1() {
		return tabel1;
	}

	public Button getButton1() {
		return button1;
	}
	
	public TextField getTextField1() {
		return textField1;
	}

	public void setTextField1(String database) {
		this.textField1.setValue(database);
	}

	public String getCurrentDb() {
		return currentDb;
	}

	public void setCurrentDb(String currentDb) {
		this.currentDb = currentDb;
	}


}

