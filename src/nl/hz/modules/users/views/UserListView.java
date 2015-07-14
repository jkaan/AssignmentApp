package nl.hz.modules.users.views;

import java.util.Observable;
import java.util.Observer;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.users.controllers.UserListController;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class displays a table with all existing users in the database.
 * @author Kevin
 * @version 17-04-2014
 */
public class UserListView extends AdministrationView implements Observer {
	private static final long serialVersionUID = 5840481023878506584L;
	
	public static final String NAME_VIEW = "Users";
	public static final String NAME_NAV = "administration/users";
	
	// MVC Data
	private UserService model;
	private UserListController controller;
		
	// Layout
	private VerticalLayout pageContent;
	
	// Layout data
	private Table table;
	private Button newUserButton;
	private Button importButton;
	
	/**
	 * Constructor
	 */
	public UserListView() {		
		super();
	}
	
	@Override
	protected void initContentLayout() {
		model = UserService.getInstance();
		controller = new UserListController(this, model);
		
		model.addObserver(this);
		
		pageContent = new VerticalLayout();
		
		initTitle();
		initContent();	
		
		setContentLayout(pageContent);
	}
	
	/**
	 * Initialize title of this view
	 */
	private void initTitle() {
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		
		newUserButton = new Button("New", controller);
		newUserButton.setId("USER_NEW");
		
		importButton = new Button("Import", controller);
		importButton.setId("USER_IMPORT");
		
		buttons.addComponents(newUserButton, importButton);
		
		setViewTitle(NAME_VIEW);
		addTitleBarComponent(buttons, Alignment.MIDDLE_RIGHT);
	}
	
	/**
	 * Initialize the content of the page
	 */
	private void initContent() {
		table = new Table();
		table.setWidth("100%");
		table.setPageLength(0);
		table.addStyleName(Reindeer.TABLE_BORDERLESS);
		table.addStyleName(Reindeer.TABLE_STRONG);
		
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		
		table.setImmediate(true);
		
		table.addItemClickListener(controller);
		
		table.addContainerProperty("ID", Long.class, null);
		table.addContainerProperty("Username", String.class, null);
		table.addContainerProperty("Email", String.class, null);
		
		pageContent.addComponent(table);
	}
	
	/**
	 * Reloads the table with users
	 */
	private void reload() {
		table.removeAllItems();
		for(User user : model.getAllUsers()) {
			if(user.getRole().equals("student"))
				table.addItem(new Object[] {
						user.getUserID(),  
						user.getUsername(), 
						user.getEmail()}, user.getUserID());
		}
	}
	
	/**
	 * @return Id of the selected user
	 */
	public long getSelectedUserId() {
		return (Long) table.getValue();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event); // User check
		
		if(!getReturned()) {
			if(table.size() == 0)
				reload();
		
			if(event.getParameters().equals("new"))
				newUserButton.click();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		reload();
	}

}
