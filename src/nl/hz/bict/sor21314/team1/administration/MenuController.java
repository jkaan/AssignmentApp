package nl.hz.bict.sor21314.team1.administration;

import java.util.HashMap;

import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.users.views.UserEditView;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

/**
 * This class is responsible for handling all clicks on menu items
 * @author Kevin
 * @version 04-05-2014
 */
public class MenuController implements Command {
	private static final long serialVersionUID = 4800289399271073130L;
	
	@SuppressWarnings("unused")
	private MenuView view;
	
	private HashMap<String, String> commands;
	public static final String COMMAND_EDIT_ACCOUNT = "Edit account";
	
	/**
	 * Constructor
	 * @param view MenuView
	 */
	public MenuController(MenuView view) {
		this.view = view;
		
		commands = new HashMap<String, String>();
	}
	
	/**
	 * Add a command to this controller
	 * @param caption Caption of the menu item
	 * @param navView Navigation url of the view to navigate to
	 */
	public void addCommand(String caption, String navView) {
		commands.put(caption, navView);
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(selectedItem.getText().equals(COMMAND_EDIT_ACCOUNT))
			UI.getCurrent().addWindow(new UserEditView(UserService.getInstance().getUser(1)));
		else {
			String menuItem = commands.get(selectedItem.getText());
			if(menuItem != null) {
				UI.getCurrent().getNavigator().navigateTo(menuItem);
			}
		}
	}
	
	

}
