package nl.hz.modules.exams.views;

import java.util.Observable;
import java.util.Observer;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.modules.exams.controllers.QuestionListController;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class represents a overview of all questions
 * @author Kevin
 * @version 23-04-2014
 */
public class QuestionListView extends AdministrationView implements Observer {
	private static final long serialVersionUID = -6721704941140320636L;
	
	public static final String NAME_VIEW = "Questions";
	public static final String NAME_NAV = "administration/questions";
	
	// MVC Data
	private QuestionService model;
	private QuestionListController controller;
	
	// Layout
	private VerticalLayout pageContent;
	
	// Layout data
	private Table table;
	private Button newQuestionButton;
	
	public QuestionListView() {
		super();
	}
	
	@Override
	protected void initContentLayout() {
		model = QuestionService.getInstance();
		controller = new QuestionListController(this, model);
		
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
		newQuestionButton = new Button("New", controller);
		newQuestionButton.setId("QUESTION_NEW");
		
		setViewTitle(NAME_VIEW);
		addTitleBarComponent(newQuestionButton, Alignment.MIDDLE_RIGHT);
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
		table.addContainerProperty("Question", String.class, null);
		table.addContainerProperty("Answers", Integer.class, null);
		table.addContainerProperty("Weight", Integer.class, null);
		table.addContainerProperty("Category", String.class, null);
		table.addContainerProperty("Image", String.class, null);
		table.addContainerProperty("Code fragment", String.class, "No");
		
		pageContent.addComponent(table);
	}
	
	/**
	 * Reloads the table with questions
	 */
	private void reload() {
		table.removeAllItems();
		for(Question question : model.getQuestions()) {
			String code = "";
			if(question.getCodeFragment() != null && !question.getCodeFragment().equals(""))
				code = "Yes";
			
			table.addItem(new Object[] {
					question.getQuestionId(),  
					question.getQuestion(), 
					question.getAnswers().size(),
					question.getRanking(),
					question.getCategory(),
					question.getImageName(),
					code}, question.getQuestionId());
					
		}
	}
	
	/**
	 * @return Id of the selected question
	 */
	public long getSelectedQuestionId() {
		return (Long) table.getValue();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event); // User check
		
		if(!getReturned()) {
			if(table.size() == 0)
				reload();
		
			if(event.getParameters().equals("new"))
				newQuestionButton.click();
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		reload();	
	}

}
