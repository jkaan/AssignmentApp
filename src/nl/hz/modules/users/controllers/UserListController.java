package nl.hz.modules.users.controllers;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.UserService;
import nl.hz.modules.users.views.UserImportView;
import nl.hz.modules.users.views.UserEditView;
import nl.hz.modules.users.views.UserListView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

/**
 * This class handles all user interactions from the UserListView
 * @author Kevin
 * @version 17-04-2014
 */
public class UserListController implements Button.ClickListener, ItemClickListener {
	private static final long serialVersionUID = -8149651025935816021L;

	private UserListView view;
	private UserService model;
	
	public UserListController(UserListView view, UserService model) {
		this.view = view;
		this.model = model;		
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("USER_NEW"))
			UI.getCurrent().addWindow(new UserEditView(null));
		if(event.getButton().getId().equals("USER_IMPORT"))
			UI.getCurrent().getNavigator().navigateTo(UserImportView.NAME_NAV);
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
			for(User user : model.getAllUsers()) {
				if(user.getUserID() == view.getSelectedUserId()) {
					UI.getCurrent().addWindow(new UserEditView(user));
					return;
				}
			}
		}
	}
}
