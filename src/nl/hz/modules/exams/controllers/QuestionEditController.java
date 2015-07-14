package nl.hz.modules.exams.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import nl.hz.bict.sor21314.team1.entities.Answer;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.modules.exams.views.QuestionEditView;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;

/**
 * This class handles all actions from the QuestionEditView. One of them is to handle the 
 * upload of images.
 * @author Kevin
 * @version 01-05-2014
 */
public class QuestionEditController implements Button.ClickListener, Upload.Receiver, Upload.SucceededListener, TextChangeListener {
	private static final long serialVersionUID = -1934830533145458994L;

	private QuestionEditView view;
	private QuestionService model;
	
	private Question question;
	private List<Answer> answers;
	
	private File image;
	private boolean correctImage;
	
	public QuestionEditController(QuestionEditView view, QuestionService model) {
		this.view = view;
		this.model = model;
		correctImage = false;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("ANSWER_ADD"))
			view.addAnswerField();
		if(event.getButton().getId().equals("SAVE"))
			save();
	}
	
	/**
	 * Save the input from the view
	 */
	private void save() {
		createMappings();
		
		if(!checkInput())
			return;
		
		question.setAnswers(answers);
		
		if(question.getQuestionId() != 0)
			update();
		else
			add();
		
		view.close();
	}
	
	/**
	 * Update an existing question
	 */
	private void update() {
		model.updateQuestion(question, answers);
		
		Notification.show("Success", "Question is edited", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Add a new question to the database
	 */
	private void add() {
		model.insertQuestion(question, answers);
		
		Notification.show("Success", "Question added to the database", Notification.Type.HUMANIZED_MESSAGE);
	}
	
	/**
	 * Create the mappings for answers and the question. 
	 * Note that this method doesn't attach the answers to the question!
	 */
	private void createMappings() {		
		question = new Question(view.getQuestion());
		
		if(view.getQuestionId() != 0)
			question.setQuestionId(view.getQuestionId());
		
		if(view.getImageName() != null && !view.getImageName().equals("")) {
			if(image == null) // image was already attached
				question.setImageName(view.getImageName());
			if(correctImage) // image was attached while editing
				question.setImageName(view.getImageName());
		}
		
		if(view.getCodeFragment() != null && !view.getCodeFragment().equals("")) {
			question.setCodeFragment(view.getCodeFragment());
		}
		
		if(view.getRanking() != 0) {
			question.setRanking(view.getRanking());
		}
		
		if(view.getCategory() != null && !view.getCategory().equals("")) {
			question.setCategory(view.getCategory());
		}		
		
		createAnswerMappings();
	}
	
	/**
	 * Create the mappings for the answers
	 */
	private void createAnswerMappings() {
		answers = new ArrayList<Answer>();
		
		for(int index = 0; index < view.getAnswers().size(); index++) {
			Answer answer = new Answer();
			answer.setAnswer(view.getAnswers().get(index));
			answer.setCorrectAnswer(view.getCorrects().get(index));
			answer.setQuestion(question);
			if(view.getAnswerIds().get(index) != 0)
				answer.setAnswerId(view.getAnswerIds().get(index));
			answers.add(answer);
		}
	}
	
	/**
	 * Checks the input if it's valid
	 * @return True if input is valid, false otherwise
	 */
	private boolean checkInput() {
		String errorMessage = "";
		
		// Question filled in?
		if(question.getQuestion().equals(""))
			errorMessage += "Please give a question!\n";
		
		// Question has category?
		if(question.getCategory() == null)
			errorMessage += "Please select a category!\n";
		
		// Remove empty answers
		List<Answer> duplicate = answers;
		answers = new ArrayList<Answer>();
		for(Answer answer : duplicate) {
			if(!answer.getAnswer().equals("")) 
				answers.add(answer);
		}
		
		for(Answer answer : answers) {
			if(answer.getAnswer().equals(""))
				answers.remove(answer);
		}
		
		// Any answers left now?
		if(answers.size() <= 1)
			errorMessage += "Please give at least two answers!\n";
		
		// At least one correct answer?
		boolean oneCorrect = false;
		for(Answer answer : answers) {
			if(answer.getCorrectAnswer()) {
				oneCorrect = true;
				break;
			}
		}
		
		if(!oneCorrect) 
			errorMessage += "Please check at least one answer as correct one!\n";
		
		// Return result of check
		if(errorMessage.equals("")) {
			return true;
		} else {
			Notification.show("Wrong input", errorMessage, Notification.Type.ERROR_MESSAGE);
			return false;
		}
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream stream = null;
		
		if(!mimeType.startsWith("image")) {
			Notification.show("Error", "File is not an image and can't be saved!", Notification.Type.ERROR_MESSAGE);
			correctImage = false;
		}
			
		try {
			image = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/question_images/" + filename);
			stream = new FileOutputStream(image);
			System.out.println(image.getAbsolutePath());
		} catch (FileNotFoundException e) {
			Notification.show("Error", "File not found", Notification.Type.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		
		view.loadImageName(filename);
		correctImage = true;
		return stream;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		view.loadImagePreview(image);
	}

	@Override
	public void textChange(TextChangeEvent event) {
		view.loadCodePreview(event.getText());
		
	}

}
