package nl.hz.modules.users.controllers;


import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.exams.views.ChooseExamView;
import nl.hz.modules.users.views.LoginView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * This class handles all user input from the login view and checks if it's correct.
 * @author Kevin
 * @version 16-04-2014
 */
public class LoginController implements Button.ClickListener {
	private static final long serialVersionUID = 826301758267620581L;
	
	private LoginView view;
	private UserService model;
	
	public LoginController(LoginView view) {
		this.view = view;
		this.model = UserService.getInstance();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		login();
	}
	
	/**
	 * Login in the application
	 */
	private void login() {
		User user = model.getUser(view.getUsername(), view.getPassword());
		
		if(user == null) {
			Notification.show("Wrong combination!", Notification.Type.WARNING_MESSAGE);
			return;
		}
		
		UI.getCurrent().getSession().setAttribute("user", user);
		UI.getCurrent().getSession().setAttribute("userId", user.getUserID());
		
		openLoggedInView();
	}
	
	/**
	 * Go the main view based on the role of the logged in user
	 */
	public void openLoggedInView() {
		if(User.class.cast(UI.getCurrent().getSession().getAttribute("user")).getRole().equals("admin"))
			UI.getCurrent().getNavigator().navigateTo(AdministrationView.NAME_NAV);
		if(User.class.cast(UI.getCurrent().getSession().getAttribute("user")).getRole().equals("student"))
			UI.getCurrent().getNavigator().navigateTo(ChooseExamView.NAME_NAV);
	}
	
	
	/**
	 * Log out
	 */
	public void logout() {
		UI.getCurrent().getSession().close();
		UI.getCurrent().getPage().setLocation("");
	}

}
