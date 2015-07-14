package nl.hz.bict.sor21314.team1.administration;

import nl.hz.modules.exams.views.CategoryListView;
import nl.hz.modules.exams.views.QuestionListView;
import nl.hz.modules.users.views.UserImportView;
import nl.hz.modules.users.views.DatabaseView;
import nl.hz.modules.users.views.LoginView;
import nl.hz.modules.users.views.UserListView;

import com.vaadin.ui.MenuBar;

/**
 * This class is responsible for showing the menu bar
 * @author Kevin
 * @version 04-05-2014
 */
public class MenuView extends MenuBar {
	private static final long serialVersionUID = 8107344741072228036L;
	
	private MenuController controller;
	
	/**
	 * Constructor
	 */
	public MenuView() {
		controller = new MenuController(this);
		setWidth("100%");
		initMenuItems();
	}

	/**
	 * Initialize the menu items
	 */
	private void initMenuItems() {
		MenuItem test = addItem("Beheer", null);
		add("New test", "Exam", test);
		add("RandomExam", "RandomExam", test);

		MenuItem application = addItem("Application", null);
		
		add("Databases", DatabaseView.NAME_NAV, application);
		
		application.addSeparator();
		
		MenuItem users = add("Users", UserListView.NAME_NAV, application);
		add("New user...", UserListView.NAME_NAV + "/new", users);
		add("Import", UserImportView.NAME_NAV, users);
		
		MenuItem questions = add("Questions", QuestionListView.NAME_NAV, application);
		add("New question...", QuestionListView.NAME_NAV + "/new", questions);
		
		MenuItem category = add("Category", CategoryListView.NAME_NAV, application);
		add("New category...", CategoryListView.NAME_NAV + "/new", category);
		
		application.addSeparator();
		
		application.addItem(MenuController.COMMAND_EDIT_ACCOUNT, controller);
		add("Log out", LoginView.NAME_NAV + "/logout", application);
	}
	
	/**
	 * Add an item to the specified menu item which view is attached to the navigator
	 * @param caption Text to show in the menu
	 * @param navView View to navigate to
	 * @param item Item to add the item to
	 * @return MenuItem
	 */
	private MenuItem add(String caption, String navView, MenuItem item) {
		controller.addCommand(caption, navView);
		
		return item.addItem(caption, controller);
	}

}