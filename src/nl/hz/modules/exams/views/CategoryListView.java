package nl.hz.modules.exams.views;

import java.util.Observable;
import java.util.Observer;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.modules.exams.controllers.CategoryListController;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * List view for the categories
 * 
 * @author Jurgen ,kevin
 *
 */
public class CategoryListView extends AdministrationView implements Observer {
	private static final long serialVersionUID = -6721704941140320636L;
	
	public static final String NAME_VIEW = "Category";
	public static final String NAME_NAV = "administration/category";
	
	// MVC Data
	private CategoryService model;
	private CategoryListController controller;
	
	// Layout
	private VerticalLayout pageContent;
	
	// Layout data
	private Table table;
	private Button newCategoryButton;
	
	public CategoryListView() {
		super();
	}
	
	@Override
	protected void initContentLayout() {
		model = CategoryService.getInstance();
		controller = new CategoryListController(this, model);
		
		model.addObserver(this);
		
		pageContent = new VerticalLayout();
		
		initTitle();
		initContent();
		
		setContentLayout(pageContent);
	}
	
	/**
	 * Initialize title of this view
	 */
	private void initTitle() {
		newCategoryButton = new Button("New", controller);
		newCategoryButton.setId("CATEGORY_NEW");
		
		setViewTitle(NAME_VIEW);
		addTitleBarComponent(newCategoryButton, Alignment.MIDDLE_RIGHT);
	}
	
	/**
	 * Initialize the content of the page
	 */
	private void initContent() {
		table = new Table();
		table.setWidth("100%");
		table.setPageLength(0);
		table.addStyleName(Reindeer.TABLE_BORDERLESS);
		table.addStyleName(Reindeer.TABLE_STRONG);
		
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		
		table.setImmediate(true);
		
		table.addItemClickListener(controller);
		
		table.addContainerProperty("ID", Long.class, null);
		table.addContainerProperty("Category", String.class, null);
		
		pageContent.addComponent(table);
	}
	
	/**
	 * Reloads the table with categories
	 */
	private void reload() {
		table.removeAllItems();
		for(Category category : model.getCategories()) {
					
			table.addItem(new Object[] {
					category.getCategoryId(),  
					category.getCategory(),}, category.getCategoryId());
					
		}
	}
	
	/**
	 * @return Id of the selected category
	 */
	public long getSelectedCategoryID() {
		return (Long) table.getValue();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event); // User check
		if(table.size() == 0)
			reload();
		
		if(event.getParameters().equals("new"))
			newCategoryButton.click();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		reload();	
	}

}
