package nl.hz.modules.exams.controllers;

import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.modules.exams.views.CategoryEditView;
import nl.hz.modules.exams.views.CategoryListView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

/**
 * 
 * Controller for the categorylistview.
 * 
 * @author Jurgen,Kevin
 *
 */
public class CategoryListController implements Button.ClickListener, ItemClickListener {
	private static final long serialVersionUID = -3897718643478375655L;

	// MVC
	private CategoryListView view;
	private CategoryService model;
	
	public CategoryListController(CategoryListView categoryListView, CategoryService model2) {
		this.view = categoryListView;
		this.model = model2;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("CATEGORY_NEW"))
			UI.getCurrent().addWindow(new CategoryEditView(null));
		
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
			UI.getCurrent().addWindow(new CategoryEditView(model.getCategory(view.getSelectedCategoryID())));
		}
		
	}

}
