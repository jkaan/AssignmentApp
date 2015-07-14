package nl.hz.bict.sor21314.team1.student;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.modules.users.views.LoginView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class StudentView extends VerticalLayout implements View {
	private static final long serialVersionUID = 5841148061815462948L;
	
	private VerticalLayout pageContainer;
	private HorizontalLayout titleBar;
	private Panel contentPanel;
	
	private Label viewTitle;
	private Label studentInfo;
	private boolean returned;

	public StudentView() {
		pageContainer = new VerticalLayout();
		titleBar = new HorizontalLayout();
		contentPanel = new Panel();
		
		initTopLayout();
		initPageContainer();
		initTitleBar();
		initContentPanel();
		initContentLayout();
	}
	
	private void initTopLayout() {
		setStyleName(Reindeer.LAYOUT_BLUE);
		setSizeFull();
	}
	
	private void initPageContainer() {
		pageContainer.setWidth("1000px");
		pageContainer.setMargin(true);
		pageContainer.setSpacing(true);
		
		addComponent(pageContainer);
		setComponentAlignment(pageContainer, Alignment.TOP_CENTER);
	}
	
	private void initTitleBar() {
		titleBar.setSpacing(true);
		titleBar.setWidth("100%");
		
		viewTitle = new Label("Student");
		viewTitle.setStyleName(Reindeer.LABEL_H2);
		
		Layout student = getStudentInfo();
		
		titleBar.addComponents(viewTitle, student);
		titleBar.setComponentAlignment(viewTitle, Alignment.MIDDLE_LEFT);
		titleBar.setComponentAlignment(student, Alignment.MIDDLE_RIGHT);
		
		pageContainer.addComponent(titleBar);
	}
	
	private void initContentPanel() {
		contentPanel = new Panel();
		contentPanel.setStyleName(Reindeer.LAYOUT_WHITE);
		contentPanel.setSizeFull();
		
		pageContainer.addComponent(contentPanel);
		pageContainer.setExpandRatio(contentPanel, 1);
	}
	
	protected void initContentLayout() {
		setContentLayout(new VerticalLayout());
	}
	
	private Layout getStudentInfo() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		
		studentInfo = new Label("Welcome, ...");
		Link logout = new Link("[ Log out ]", new ExternalResource("#!login/logout"));
		
		layout.addComponents(studentInfo, logout);
		layout.setComponentAlignment(logout, Alignment.BOTTOM_RIGHT);
		
		return layout;
	}
	
	public void setContentLayout(Layout content) {
		contentPanel.setContent(content);
	}
	
	public void setViewTitle(String name) {
		viewTitle.setValue(name);
	}
	
	public boolean getReturned() {
		return returned;
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		User user = (User) UI.getCurrent().getSession().getAttribute("user");
		
		if(user == null || !user.getRole().equals("student")) {
			UI.getCurrent().getNavigator().navigateTo(LoginView.NAME_NAV);
			returned = true;
		} else {
			studentInfo.setValue("Welcome, " + user.getUsername());
			returned = false;
		}		
	}
	

}
