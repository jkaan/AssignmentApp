package nl.hz.modules.users.controllers;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.users.views.UserEditView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

public class UserEditController implements Button.ClickListener {
	private static final long serialVersionUID = -8433502459012969382L;

	private UserEditView view;
	private UserService model;
	
	private User user;
	
	public UserEditController(UserEditView view, UserService model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("SAVE"))
			save();
	}
	
	/**
	 * Action to save user input
	 */
	private void save() {
		createMapping();
		
		if(!checkInput())
			return;
		
		if(user.getUserID() != 0)
			update();
		else
			add();
		
		view.close();
	}
	
	/**
	 * Update existing user with user input
	 */
	private void update() {
		model.updateUser(user);
		
		Notification.show("Success", "User is updated", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Add new user with user input
	 */
	private void add() {		
		model.insertUser(user);
		
		Notification.show("Success", "User added to the database", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Create the mapping
	 */
	private void createMapping() {
		user = new User();
		
		if(view.getUserId() != 0)
			user.setUserID(view.getUserId());
		
		user.setUsername(view.getUsername());
		user.setEmail(view.getEmail());
		user.setPassword(view.getPassword());
		user.setRole(view.getRole());
	}
	
	/**
	 * Checks the input from view
	 * @return true if everything is OK, false if not
	 */
	private boolean checkInput() {
		String errorMessage = ""; 
		
		if(user.getUsername().equals("") || user.getEmail().equals("") || user.getPassword().equals("")) {
			errorMessage += "Please fill in all required fields";
			Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		if(!user.getEmail().contains("@")) {
			errorMessage += "Email address must contain @";
			Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

}
