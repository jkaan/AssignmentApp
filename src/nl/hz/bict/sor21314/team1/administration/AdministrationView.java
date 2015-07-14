package nl.hz.bict.sor21314.team1.administration;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.modules.users.views.LoginView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class contains all data for the administration module.
 * To create a new view that is part of the administration panel, extends this class in your new view.
 * Subclasses have to override the <code>initContentLayout()</code> method. All components have to 
 * be initialized in this method. Finally set your layout as content through the <code>setContentLayout(Layout)</code>
 * method. Use the <code>setViewTitle(String)</code> and <code>addTitleBarComponent(AbstractComponent, Alignment)</code> to modify
 * the title bar.
 * @author Kevin
 * @version 13-05-2014
 */
public class AdministrationView extends VerticalLayout implements View {
	private static final long serialVersionUID = 5303410375130818918L;
	
	public static final String NAME_VIEW = "Administration";
	public static final String NAME_NAV = "administration";
	
	// Layout
	private HorizontalLayout titleBar;
	private VerticalLayout contentPanelWrapper;
	private Panel contentPanel;
	private VerticalLayout contentLayout;
	
	// Components
	private MenuView menu;
	private Label viewTitle;
	private Label welcome;
	
	private boolean returned;
	
	public AdministrationView() {		
		// Initialize instances
		menu = new MenuView();
		titleBar = new HorizontalLayout();
		contentPanelWrapper = new VerticalLayout();
		contentPanel = new Panel();
		contentLayout = new VerticalLayout();
		
		// Initialize components
		initTopLayout();
		initMenuBar();
		initTitleBar();
		initContentPanelWrapper();
		initContentPanel();
		initContentLayout();
	}
	
	/**
	 * Initialize the top layout. 
	 * The top layout is the first layout and fills the whole screen.
	 * By default the top layout doesn't has margins or spacing.
	 */
	private void initTopLayout() {
		setStyleName(Reindeer.LAYOUT_BLUE);
		setSizeFull();
	}
	
	/**
	 * Initialize menu bar.
	 * Menu bar will be added to the top layout and fills the whole width.
	 */
	private void initMenuBar() {
		menu.setSizeUndefined();
		menu.setWidth("100%");
		addComponent(menu);
	}
	
	/**
	 * Initialize title bar.
	 * Title bar will be added to the top layout and has a full width. 
	 * The title bar is the component that will be displayed above the white content panel.
	 * Every component will appear in a horizontal order since the title bar is a HorizontalLayout.
	 * The title bar has by default margins and spacing.
	 */
	private void initTitleBar() {
		titleBar.setWidth("100%");
		
		titleBar.setMargin(true);
		titleBar.setSpacing(true);
		
		viewTitle = new Label("Administration Panel");
		viewTitle.setStyleName(Reindeer.LABEL_H2);
		titleBar.addComponent(viewTitle);
		
		addComponent(titleBar);
	}
	
	/**
	 * Initialize content panel wrapper.
	 * The wrapper will be added to the top layout and has a full size.
	 * This wrapper is only used to set margins around the content panel and only contains this panel.
	 */
	private void initContentPanelWrapper() {
		contentPanelWrapper.setSizeFull();
		contentPanelWrapper.setMargin(new MarginInfo(false, true, true, true));
		addComponent(contentPanelWrapper);
		setExpandRatio(contentPanelWrapper, 1);
	}
	
	/**
	 * Initialize content panel
	 * The content panel will be added to the wrapper and has a full size.
	 */
	private void initContentPanel() {
		contentPanel.setStyleName(Reindeer.LAYOUT_WHITE);
		contentPanel.setSizeFull();
		
		contentPanelWrapper.addComponent(contentPanel);
	}
	
	/**
	 * Initialize content layout.
	 * Subclasses have to override this method for creating their own content!
	 * The content layout will be added to the content panel.
	 * The add the layout to the panel, use the <code>setContentLayout(Layout content)</code> method
	 * in your subclass.
	 */
	protected void initContentLayout() {
		contentLayout.setSizeFull();
		contentLayout.setMargin(true);
		
		Layout welcome = getWelcome();
		Layout onethree = getStepOneToThree();
		
		contentLayout.addComponents(welcome, onethree);
		contentLayout.setExpandRatio(welcome, 1);
		contentLayout.setExpandRatio(onethree, 6);
		
		setContentLayout(contentLayout);
	}
	
	private Layout getWelcome() {
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSpacing(true);
		
		// Title
		welcome = new Label("Welcome!");
		welcome.setStyleName(Reindeer.LABEL_H2);
		
		// Description
		Label desc = new Label("Welcome in the administration panel! Use the menu bar on top of the page to navigate to "
				+ "different pages. See the information below to learn how this application works.");
		
		// Add
		layout.addComponents(welcome, desc);
		
		return layout;
	}
	
	private Layout getStepOneToThree() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		
		Layout one = getStepOne();
		Layout two = getStepTwo();
		Layout three = getStepThree();
		
		layout.addComponents(one, two, three);
		layout.setExpandRatio(one, 1);
		layout.setExpandRatio(two, 1);
		layout.setExpandRatio(three, 1);
		
		return layout;
	}
	
	private Layout getStepOne() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		
		// Title
		Label title = new Label("Step 1: Add questions");
		title.setStyleName(Reindeer.LABEL_H2);
		
		// Description
		Label desc = new Label("First add questions to the database. You can add as many answers you want to. "
				+ "You can also add a picture or a code fragment to the question. The weight is used "
				+ "to determine the score. After eacht answer is a checkbo displayed. Check this box if the answer "
				+ "is a correct one. "
				+ "To edit questions, double click in the table on the question you want to edit.");
		
		// Link
		Link overview = new Link("> Go to the question overview", new ExternalResource("#!administration/questions"));
		Link add = new Link("> Add a new question", new ExternalResource("#!administration/questions/new"));
		
		VerticalLayout linkContainer = new VerticalLayout(overview, add);
		
		// Add
		layout.addComponents(title, desc, linkContainer);
		
		return layout;	
	}
	
	private Layout getStepTwo() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		
		// Title
		Label title = new Label("Step 2: Add users");
		title.setStyleName(Reindeer.LABEL_H2);
		
		// Description
		Label desc = new Label("Second, add some users. You can add them one by one, but you might want to import "
				+ "in one step all users by upload a csv file. Be sure that this file is in the right format. "
				+ "That means that the file need to have two columns in the specified order: username and email. "
				+ "To edit a user, double click in the table on the user to want to edit.");

		// Link
		Link overview = new Link("> Go to the user overview", new ExternalResource("#!administration/users"));
		Link add = new Link("> Add a new user", new ExternalResource("#!administration/users/new"));
		Link imp = new Link("> Import users from csv file", new ExternalResource("#!administration/users/import"));
				
		VerticalLayout linkContainer = new VerticalLayout(overview, add, imp);
		
		// Add
		layout.addComponents(title, desc, linkContainer);
		
		return layout;	
	}
	
	private Layout getStepThree() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		
		// Title
		Label title = new Label("Step 3: Generate exams");
		title.setStyleName(Reindeer.LABEL_H2);
		
		// Description
		Label desc = new Label("The third step is to generate exams for every user based on the existing questions "
				+ "in the database. This is a very easy step, just click to generate and choose from which categories"
				+ "the questions have to be chosen. That's all.");
			
		// Link
		Link gen = new Link("> Go to generate page", new ExternalResource("#!administration/exams/generate"));
		
		VerticalLayout linkContainer = new VerticalLayout(gen);
		
		// Add
		layout.addComponents(title, desc, linkContainer);
		
		return layout;	
	}
	
	/**
	 * Add the content layout to the panel. 
	 * @param content Layout that will be added. It can be any Layout class.
	 */
	public void setContentLayout(Layout content) {
		contentPanel.setContent(content);
	}
	
	/**
	 * Set the title for the view. The title is shown outside the content panel
	 * @param title
	 */
	public void setViewTitle(String title) {
		viewTitle.setValue(title);
	}
	
	/**
	 * Add a component (like a button or a layout) to the title bar.
	 * You can set an alignment if you want, otherwise set null.
	 * Be careful in which components you add to the title bar!
	 * @param component Component to add
	 * @param alignment Alignment in the title bar
	 */
	public void addTitleBarComponent(AbstractComponent component, Alignment alignment) {
		titleBar.addComponent(component);
		if(alignment != null)
			titleBar.setComponentAlignment(component, alignment);
	}
	
	/**
	 * @return True if this access to this page was denied, false otherwise
	 */
	public boolean getReturned() {
		return returned;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		User user = (User) UI.getCurrent().getSession().getAttribute("user");
		
		if(user == null || !user.getRole().equals("admin")) {
			UI.getCurrent().getNavigator().navigateTo(LoginView.NAME_NAV);
			returned = true;
		} else
			returned = false;	
	}

}
