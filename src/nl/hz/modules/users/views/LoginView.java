package nl.hz.modules.users.views;

import nl.hz.modules.users.controllers.LoginController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


/**
 * This class displays a login form
 * @author Kevin
 * @version 16-04-2014
 */
public class LoginView extends VerticalLayout implements View {
	private static final long serialVersionUID = 6551390419972321298L;
	
	public static final String NAME_VIEW = "Login";
	public static final String NAME_NAV = "login";
	
	// MVC
	private LoginController controller;
	
	// Layout
	private Panel pageWrapper;
	private VerticalLayout pageLayout;
	private FormLayout form;
	
	// Layout Data
	private TextField user;
	private PasswordField password;
	private Button loginButton;
	
	/**
	 * Constructor
	 */
	public LoginView() {	 
		controller = new LoginController(this);
		
		setStyleName(Reindeer.LAYOUT_BLUE);
		setMargin(true);
		setSizeFull();
		
		initPageWrapper();
		initPageLayout();        
	}
	
	/**
	 * Initialize panel container
	 */
	private void initPageWrapper() {
		pageWrapper = new Panel();
		pageWrapper.setStyleName(Reindeer.LAYOUT_WHITE);
		pageWrapper.setSizeUndefined();
		
		addComponent(pageWrapper);
		setComponentAlignment(pageWrapper, Alignment.TOP_CENTER);
	}
	
	/**
	 * Initialize content layout
	 */
	private void initPageLayout() {
		pageLayout = new VerticalLayout();
		pageLayout.setMargin(true);	
		
		// Page title
		Label title = new Label(NAME_VIEW);
		title.setStyleName(Reindeer.LABEL_H2);
		pageLayout.addComponent(title);
		
		pageWrapper.setContent(pageLayout);
		
		initForm();
	}
	
	/**
	 * Initialize form
	 */
	private void initForm() {
		form = new FormLayout();
		pageLayout.addComponent(form);
		
		user = new TextField("Username");
        user.setWidth("300px");
        user.setRequired(true);
        form.addComponent(user);
        
        password = new PasswordField("Password");
        password.setWidth("300px");
        password.setRequired(true);
        form.addComponent(password);
        
        loginButton = new Button("Login", controller);
        form.addComponent(loginButton);
	}
	
	/**
	 * @return Username that is given by the user
	 */
	public String getUsername() {
		return user.getValue();
	}
	
	/**
	 * @return Password that is given by the user
	 */
	public String getPassword() {
		return password.getValue();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters().equals("logout"))
			controller.logout();
		else {
			if(UI.getCurrent().getSession().getAttribute("user") != null)
				controller.openLoggedInView();
		}
	}
}