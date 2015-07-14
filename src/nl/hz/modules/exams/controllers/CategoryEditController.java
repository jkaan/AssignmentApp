package nl.hz.modules.exams.controllers;

import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.modules.exams.views.CategoryEditView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

/**
 * Controller for the categoryeditview
 * 
 * @author Jurgen,Kevin
 *
 */
public class CategoryEditController implements Button.ClickListener {
	private static final long serialVersionUID = -8433502459012969382L;

	private CategoryEditView view;
	private CategoryService model;
	
	private Category category;
	
	public CategoryEditController(CategoryEditView categoryEditView, CategoryService model2) {
		this.view = categoryEditView;
		this.model = model2;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("SAVE"))
			save();
	}
	
	/**
	 * Action to save category input
	 */
	private void save() {
		createMapping();
		
		if(!checkInput())
			return;
		
		if(category.getCategoryId() != 0)
			update();
		else
			add();
		
		view.close();
	}
	
	/**
	 * Update existing category with category input
	 */
	private void update() {
		model.updateCategory(category);
		
		Notification.show("Success", "Category is updated", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Add new category with category input
	 */
	private void add() {		
		model.insertCategory(category);
		
		Notification.show("Success", "Category added to the database", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Create the mapping
	 */
	private void createMapping() {
		category = new Category();
		
		if(view.getCategoryId() != 0)
			category.setCategoryId(view.getCategoryId());
		
		category.setCategory(view.getCategory());
	}
	
	/**
	 * Checks the input from view
	 * @return true if everything is OK, false if not
	 */
	private boolean checkInput() {
		String errorMessage = ""; 
		
		if(category.getCategory().equals("")) {
			errorMessage += "Please fill in all required fields";
			Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

}
