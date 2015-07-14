package nl.hz.bict.sor21314.team1.toetsapp;

import javax.servlet.annotation.WebServlet;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.exams.views.CategoryListView;
import nl.hz.modules.exams.views.ChooseExamView;
import nl.hz.modules.exams.views.QuestionListView;
import nl.hz.modules.exams.views.RandomExamView;
import nl.hz.modules.exams.views.TakeExamView;
import nl.hz.modules.users.views.UserImportView;
import nl.hz.modules.users.views.DatabaseView;
import nl.hz.modules.users.views.LoginView;
import nl.hz.modules.users.views.UserListView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("toetsapp")
@PreserveOnRefresh
public class ToetsApp extends UI {
	private Navigator navigator;
	
	private RandomExamView randomExamView;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ToetsApp.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		initViews();
		initNavigator();
		initDefaultUser();
		
		navigator.navigateTo("login"); // First load is a login screen
		
	}
	
	/**
	 * Initialize a default admin account
	 */
	private void initDefaultUser(){
		UserService userService = new UserService();
		
		if(userService.getUsers().isEmpty()){
			User defaultUser = new User();		
			defaultUser.setUsername("admin");
			defaultUser.setEmail("default@hz.nl");
			defaultUser.setPassword("test");
			defaultUser.setRole("admin");		
			userService.insertUser(defaultUser);
		}
	}
	/**
	 * Initialize all existing views in this application
	 */
	private void initViews() {		
		randomExamView = new RandomExamView();
	}
	
	/**
	 * Initialize the navigator with all views
	 */
	private void initNavigator() {
		navigator = new Navigator(this, this);
		navigator.addViewChangeListener(new BackButtonController());
		
		navigator.setErrorView(new ErrorView());
		
		navigator.addView(LoginView.NAME_NAV, new LoginView());
		navigator.addView(ChooseExamView.NAME_NAV, new ChooseExamView());
		navigator.addView(AdministrationView.NAME_NAV, new AdministrationView());
		navigator.addView(UserListView.NAME_NAV, new UserListView());
		navigator.addView(UserImportView.NAME_NAV, new UserImportView());
		navigator.addView(QuestionListView.NAME_NAV, new QuestionListView());
		navigator.addView(DatabaseView.NAME_NAV, new DatabaseView());
		navigator.addView(CategoryListView.NAME_NAV, new CategoryListView());
		
		navigator.addView("RandomExam", randomExamView);
		
		navigator.addView("TakeExam", new TakeExamView());
	}
	
	public Navigator getNavigator() {
		return this.navigator;
	}
	
	
}