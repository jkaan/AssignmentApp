package nl.hz.bict.sor21314.team1.toetsapp;

import nl.hz.modules.users.views.LoginView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

/**
 * This controller only forward to the login view when link is clicked
 * @author Kevin
 * @version 20-05-2014
 */
public class ErrorController implements Button.ClickListener{
	private static final long serialVersionUID = 6003189379898839859L;
	
	
	public ErrorController() {
		// Nothing to do
	}

	@Override
	public void buttonClick(ClickEvent event) {
		UI.getCurrent().getNavigator().navigateTo(LoginView.NAME_NAV);
	}

}
