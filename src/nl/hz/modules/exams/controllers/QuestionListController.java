package nl.hz.modules.exams.controllers;

import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.modules.exams.views.QuestionEditView;
import nl.hz.modules.exams.views.QuestionListView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

/**
 * This class handles all user actions from the QuestionListView
 * @author Kevin
 * @version 01-05-2014
 */
public class QuestionListController implements Button.ClickListener, ItemClickListener {
	private static final long serialVersionUID = -3897718643478375655L;

	// MVC
	private QuestionListView view;
	private QuestionService model;
	
	public QuestionListController(QuestionListView view, QuestionService model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("QUESTION_NEW"))
			UI.getCurrent().addWindow(new QuestionEditView(null));
		
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		if(event.isDoubleClick()) {
			UI.getCurrent().addWindow(new QuestionEditView(model.getQuestion(view.getSelectedQuestionId())));
		}
		
	}

}
