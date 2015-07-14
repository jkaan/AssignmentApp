package nl.hz.bict.sor21314.team1.toetsapp;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ErrorView extends VerticalLayout implements View {
	private static final long serialVersionUID = 2160999516815051808L;
	
	public static final String NAME_VIEW = "Error";
	public static final String NAME_NAV = "error";
	
	// MVC
	private ErrorController controller;
	
	// Layout
	private Panel pageWrapper;
	private VerticalLayout pageLayout;
	
	public ErrorView() {
		controller = new ErrorController();
		
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
		pageLayout.setSpacing(true);
		
		// Page title
		Label title = new Label(NAME_VIEW);
		title.setStyleName(Reindeer.LABEL_H2);
		pageLayout.addComponent(title);
		
		pageWrapper.setContent(pageLayout);
		
		initPageContent();
	}
	
	private void initPageContent() {
		Label text = new Label("This page doesn't exist!");
		Button button = new Button("Go to login page");
		button.setStyleName(Reindeer.BUTTON_LINK);
		button.addClickListener(controller);
		
		pageLayout.addComponents(text, button);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
