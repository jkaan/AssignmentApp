package nl.hz.bict.sor21314.team1.toetsapp;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class BackButtonPopupView extends Window {
	private static final long serialVersionUID = -4357211709756000852L;
	
	private BackButtonController controller;
	
	public BackButtonPopupView(BackButtonController controller) {
		this.controller = controller;
		
		setContent(getLayout());
		setStyleName(Reindeer.WINDOW_BLACK);
		setCaption("You're editing");
		setModal(true);
		setResizable(false);
		center();
	}
	
	private Layout getLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		
		Label label = new Label("Are you sure you want to close this window?");
		layout.addComponent(label);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		layout.addComponent(buttons);
		
		Button yes = new Button("Yes", controller);
		yes.setId("YES");
		Button no = new Button("No", controller);
		no.setId("NO");
		buttons.addComponents(yes, no);
		
		return layout;
	}

}
