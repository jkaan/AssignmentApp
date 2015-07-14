package nl.hz.modules.exams.views;

import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.modules.exams.controllers.CategoryEditController;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Editview for editing categories and making new ones.
 * 
 * @author Jurgen,Kevin
 *
 */
public class CategoryEditView extends Window {
	private static final long serialVersionUID = -8427040007107810168L;
	
	// MVC
	private CategoryService model;
	private CategoryEditController controller;
	
	// Layout
	private FormLayout form;
	private TextField id;
	private TextField category;


	
	/**
	 * Constructor
	 * @param category User object used for editing purpose. Set null for a new user.
	 */
	public CategoryEditView(Category category) {
		model = CategoryService.getInstance();
		controller = new CategoryEditController(this, model);
		
		VerticalLayout main = new VerticalLayout();
		main.setSpacing(true);
		main.setMargin(true);
		
		form = new FormLayout();
		initForm();
		
		Button saveButton = new Button("Save", controller);
		saveButton.setId("SAVE");
		
		main.addComponents(form, saveButton);
		
		// Window
		setContent(main);
		setCaption("New Category");
		setResizable(false);
		setModal(true);
		center();
		
		if(category != null)
			loadUser(category);
	}
	
	/**
	 * Initialize form
	 */
	private void initForm() {
		id = new TextField("ID");
		id.setWidth("300px");
		id.setEnabled(false);
		id.setVisible(false);
		id.setValue("0");
		
		category = new TextField("Category");
		category.setRequired(true);
		category.setWidth("300px");
		
		form.addComponents(id, category);
	}
	
	/**
	 * Load a category for editing purpose
	 */
	private void loadUser(Category category1) {
		id.setValue(Long.toString(category1.getCategoryId()));
		id.setVisible(true);
		
		category.setValue(category1.getCategory());
		
		setCaption("Edit category");
	}
	
	/**
	 * @return ID of the category loaded in the view. Returns 0 if no category is loaded
	 */
	public Long getCategoryId() {
		return Long.parseLong(id.getValue());
	}
	
	/**
	 * @return Category from the field
	 */
	public String getCategory() {
		return category.getValue();
	}
	
}
