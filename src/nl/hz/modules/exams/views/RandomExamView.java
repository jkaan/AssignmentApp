package nl.hz.modules.exams.views;
import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.modules.exams.controllers.RandomExamController;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RandomExamView extends AdministrationView implements Observer {
	
	private RandomExamController controller;

	private Label label;
	private Button button;
	
	public static final String NAME_VIEW = "RandomExam";
	public static final String NAME_NAV = "administration/randomexam";
	
	// Layout
	private VerticalLayout pageContent;
	private VerticalLayout checkbox;

	private CategoryService categorySevice;
	private List<Category> categoryList;

	private ArrayList<CheckBox> checkboxes;
	
	public RandomExamView(){
		super();
		this.controller = new RandomExamController();
	}
	
	@Override
	protected void initContentLayout() {
		categorySevice = new CategoryService();
		pageContent = new VerticalLayout();
		checkboxes = new ArrayList<CheckBox>();
		setContentLayout(pageContent);
		initTitle();
		initContent();
		
		
	}	
	
	/**
	 * Initialize title of this view
	 */
	private void initTitle() {		
		setViewTitle(NAME_VIEW);
	}
	
	public void initContent(){
		label = new Label("Generate a random exam by selecting categories:");
		
		checkbox = new VerticalLayout();
		
		categoryList = categorySevice.getCategories();

		for(Category category : categoryList){
			CheckBox ckbox = new CheckBox(category.getCategory());
			ckbox.setDescription(category.getCategory());
			checkboxes.add(ckbox);
			checkbox.addComponent(ckbox);
		}
		
		button = new Button("Genereren");
		
		button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				//TODO communicatie met model om toetsen willekeurig op te slaan toevoegen
				// Thomas: nein nein nein, doorgeven aan de controller -> die slaat het op in het model
				
				//Checkboxes die zijn aangevinkt worden gebruikt.
				ArrayList<CheckBox> checkedBoxes = new ArrayList<CheckBox>();
				boolean someChecked = false;
				for(CheckBox checked : checkboxes){
					if(checked.getValue())
					{
						checkedBoxes.add(checked);
						someChecked = true;
					}
				}
				if(someChecked){
					controller.randomizeExams(checkedBoxes);
					Notification.show("De willekeurige toetsen zijn opgeslagen");
				}else{
					Notification.show("Please select a category");
				}
				
				
				//TODO in de notification weergeven hoeveel willekeurige toetsen zijn opgeslagen
			}
		});
		pageContent.addComponent(label);
		pageContent.addComponent(checkbox);
		pageContent.addComponent(button);
		pageContent.setSpacing(true);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event); // User check

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}


}
