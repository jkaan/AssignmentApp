package nl.hz.modules.users.views;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.users.controllers.UserEditController;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This view is used for editing a existing user or to create a new user.
 * In contrast to other views, you can't navigate to this view trough the navigator
 * because this view is based on a window.
 * @author Kevin
 * @version 17-04-2014
 */
public class UserEditView extends Window {
	private static final long serialVersionUID = -8427040007107810168L;
	
	// MVC
	private UserService model;
	private UserEditController controller;
	
	// Layout
	private FormLayout form;
	private TextField id;
	private TextField username;
	private TextField email;
	private TextField password;
	private String role;
	
	/**
	 * Constructor
	 * @param user User object used for editing purpose. Set null for a new user.
	 */
	public UserEditView(User user) {
		model = UserService.getInstance();
		controller = new UserEditController(this, model);
		
		VerticalLayout main = new VerticalLayout();
		main.setSpacing(true);
		main.setMargin(true);
		
		form = new FormLayout();
		initForm();
		
		Button saveButton = new Button("Save", controller);
		saveButton.setId("SAVE");
		
		main.addComponents(form, saveButton);
		
		role = "student";
		
		// Window
		setContent(main);
		setCaption("New User");
		setResizable(false);
		setModal(true);
		center();
		
		if(user != null)
			loadUser(user);
	}
	
	/**
	 * Initialize form
	 */
	private void initForm() {
		id = new TextField("ID");
		id.setWidth("300px");
		id.setEnabled(false);
		id.setVisible(false);
		id.setValue("0");
		
		username = new TextField("Username");
		username.setRequired(true);
		username.setWidth("300px");
		
		email = new TextField("Email");
		email.setRequired(true);
		email.setWidth("300px");
		
		password = new TextField("Password");
		password.setRequired(true);
		password.setWidth("300px");
		
		form.addComponents(id, username, email, password);
	}
	
	/**
	 * Load a user for editing purpose
	 */
	private void loadUser(User user) {
		id.setValue(Long.toString(user.getUserID()));
		id.setVisible(true);
		
		username.setValue(user.getUsername());
		email.setValue(user.getEmail());
		password.setValue(user.getPassword());
		
		role = user.getRole();
		
		setCaption("Edit User");
	}
	
	/**
	 * @return ID of the user loaded in the view. Returns 0 if no user is loaded
	 */
	public Long getUserId() {
		return Long.parseLong(id.getValue());
	}
	
	/**
	 * @return Username from the field
	 */
	public String getUsername() {
		return username.getValue();
	}
	
	/**
	 * @return Email from the field
	 */
	public String getEmail() {
		return email.getValue();
	}
	
	/**
	 * @return Password from the field
	 */
	public String getPassword() {
		return password.getValue();
	}
	
	/**
	 * Returns the role of the user that is opened in this view. 
	 * A new user is by default a student because there is just one admin account.
	 * In edit mode, the role will depend on the loaded user.
	 * @return Role of the user
	 */
	public String getRole() {
		return role;
	}
}
