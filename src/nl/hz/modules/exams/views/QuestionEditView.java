package nl.hz.modules.exams.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.hz.bict.sor21314.team1.entities.Answer;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.modules.exams.controllers.QuestionEditController;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class creates the view to create and edit questions.
 * @author Kevin
 * @version 01-05-2014
 */
public class QuestionEditView extends Window {
	private static final long serialVersionUID = -1635890248135120064L;
	
	// MVC
	private QuestionService model;
	private QuestionEditController controller;
	
	// Layout
	private TabSheet tabsheet;
	private FormLayout question_form;
	private FormLayout image_form;
	
	// Layout components
	private TextField question_id;
	private TextArea question_question;
	private ArrayList<TextField> question_answers;
	private ArrayList<CheckBox> question_correct;
	private int question_formIndex;
	private int question_answerCount;
	
	private ComboBox question_ranking;
	private ComboBox question_category;
	private Label image_src;
	private Upload image_upload;
	private Image image_image;

	private FormLayout code_form;
	private TextArea code_fragment;
	private Label code_preview;
	
	/**
	 * Constructor
	 * @param question Question for editing purpose. Set null for a new question
	 */
	public QuestionEditView(Question question) {
		model = QuestionService.getInstance();
		controller = new QuestionEditController(this, model);
		
		tabsheet = new TabSheet(getQuestionTab(), getImageTab(), getCodeTab());
		tabsheet.setStyleName(Reindeer.TABSHEET_SMALL);
		tabsheet.setSizeUndefined();
		
		setContent(tabsheet);
		setCaption("New Question");
		setResizable(false);
		setModal(true);
		center();
		
		if(question != null)
			loadQuestion(question);
	}
	
	/**
	 * Get the tab view for question and answers
	 * @return Layout that contains the content
	 */
	private Layout getQuestionTab() {
		VerticalLayout main = new VerticalLayout();
		main.setMargin(true);
		main.setCaption("Question");
		main.setSpacing(true);
		
		// Init instance fields
		question_form = new FormLayout();
		question_id = new TextField("ID");
		question_question = new TextArea("Question");
		question_answers = new ArrayList<TextField>();
		question_correct = new ArrayList<CheckBox>();
		question_ranking = new ComboBox("Select the weight");
		question_category = new ComboBox("Select the category");
		question_formIndex = 4;
		question_answerCount = 1;
		
		initQuestionForm();
		
		main.addComponents(question_form, createSaveButton());
		
		return main;
	}
	
	/**
	 * Get the tab view for image
	 * @return Layout that contains the content
	 */
	private Layout getImageTab() {
		VerticalLayout main = new VerticalLayout();
		main.setMargin(true);
		main.setCaption("Image");
		main.setSpacing(true);
		
		// Init instance fields
		image_form = new FormLayout();
		image_src = new Label();
		image_upload = new Upload();
		image_image = new Image("Preview");
		
		initImageForm();
		
		main.addComponents(image_form, createSaveButton());
		
		return main;
	}
	
	/**
	 * Get the tab view for code fragment
	 * @return Layout that contains the content
	 */
	private Layout getCodeTab() {
		VerticalLayout main = new VerticalLayout();
		main.setMargin(true);
		main.setCaption("Code fragment");
		main.setSpacing(true);
		
		// Init instance fields
		code_form = new FormLayout();
		code_fragment = new TextArea("Code");
		code_preview = new Label();
		
		initCodeForm();
		
		main.addComponents(code_form, createSaveButton());
		
		return main;
	}
	
	/**
	 * Initialize form that contains the question and answer fields
	 */
	private void initQuestionForm() {
		question_id.setVisible(false);
		question_id.setEnabled(false);
		question_id.setValue("0");
		
		question_question.setRequired(true);
		question_question.setWidth("500px");
		
		Button addAnswerButton = new Button("Add answer");
		addAnswerButton.setId("ANSWER_ADD");
		addAnswerButton.setStyleName(Reindeer.BUTTON_LINK);
		addAnswerButton.addClickListener(controller);
		
		question_ranking.setFilteringMode(FilteringMode.CONTAINS);
	    question_ranking.setImmediate(true);
		question_ranking.setNullSelectionAllowed(false);
		
		for(int i = 1; i<11; i++) {
			question_ranking.addItem(i);
		}
		
		question_ranking.select(1);
		
		question_category.setFilteringMode(FilteringMode.CONTAINS);
	    question_category.setImmediate(true);
		question_category.setNullSelectionAllowed(false);
		
		for(int c = 1; c<6; c++){
			question_category.addItem("Catagory " + c);
		}
		
		question_form.addComponents(question_id, question_question, createAnswerField(), 
				createAnswerField(), addAnswerButton, question_ranking, question_category);
	}
	
	/**
	 * Initialize form that contains the image field and preview
	 */
	private void initImageForm() {
		HorizontalLayout input = new HorizontalLayout();
		input.setCaption("Filename");
		input.setSpacing(true);
		
		image_upload.setButtonCaption("Browse");
		image_upload.setImmediate(true);
		image_upload.setReceiver(controller);
		image_upload.addSucceededListener(controller);
		
		image_image.setHeight("250px");
		image_image.setVisible(false);
		image_image.setAlternateText("Can't load image");
		
		input.addComponents(image_src, image_upload);
		input.setComponentAlignment(image_src, Alignment.MIDDLE_LEFT);
		image_form.addComponents(input, image_image);
	}
	
	/**
	 * Initialize form that contains code textfield and preview
	 */
	private void initCodeForm() {
		code_fragment.setWidth("500px");
		code_fragment.addTextChangeListener(controller);
		
		code_preview.setCaption("Preview");
		code_preview.setContentMode(ContentMode.PREFORMATTED);
		
		code_form.addComponents(code_fragment, code_preview);
	}
	
	/**
	 * Create a new answer row with textfield and checkbox
	 * @return HorizontalLayout with the two components
	 */
	private HorizontalLayout createAnswerField() {
		HorizontalLayout answerField = new HorizontalLayout();
		answerField.setCaption("Answer " + question_answerCount);
		answerField.setSpacing(true);
		
		question_answerCount++;
		
		TextField field = new TextField();
		field.setWidth("300px");
		field.setId("0");
		question_answers.add(field);
		
		CheckBox check = new CheckBox();
		question_correct.add(check);
		
		answerField.addComponent(field);
		answerField.addComponent(check);
		
		return answerField;
	}
	
	/**
	 * Create a button that saves the question
	 * @return Button object
	 */
	private Button createSaveButton() {
		Button saveButton = new Button("Save");
		saveButton.setId("SAVE");
		saveButton.addClickListener(controller);
		
		return saveButton;
	}
	
	/**
	 * Load an existing question into this for editing purpose
	 * @param question Question to load
	 */
	private void loadQuestion(Question question) {
		question_id.setValue(Long.toString(question.getQuestionId()));
		question_id.setVisible(true);
		
		question_question.setValue(question.getQuestion());
		
		// Create fields
		int answersToAdd = question.getAnswers().size()-2;
		int i = 1;
		while(answersToAdd > 0 && i <= answersToAdd) {
			addAnswerField();
			i++;
		}
		
		// Fill in fields
		int index = 0;
		for(Answer answer : question.getAnswers()) {
			question_answers.get(index).setValue(answer.getAnswer());
			question_answers.get(index).setId((Long.toString(answer.getAnswerId())));
			question_correct.get(index).setValue(answer.getCorrectAnswer());
			index++;
		}
		
		// Image
		if(question.getImageName() != null && !question.getImageName().equals("")) {
			loadImageName(question.getImageName());
			File image = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() 
					+ "/WEB-INF/question_images/" + question.getImageName());
			loadImagePreview(image);
		}	
		
		//Weight
		if(question.getRanking() != 0 ){
			question_ranking.setValue(question.getRanking());
		}
		
		//Category
		if(question.getCategory() != null && !question.getCategory().equals("")) {
			question_category.setValue(question.getCategory());
		}
		// Code fragment
		if(question.getCodeFragment() != null && !question.getCodeFragment().equals("")) {
			code_fragment.setValue(question.getCodeFragment());
			loadCodePreview(question.getCodeFragment());
		}
	}
	
	/**
	 * Add a new answer row to the form
	 */
	public void addAnswerField() {
		question_form.addComponent(createAnswerField(), question_formIndex);
		question_formIndex++;
	}
	
	/**
	 * Load the filename of the image in this view
	 * @param imageName Filename of the image
	 */
	public void loadImageName(String imageName) {
		image_src.setValue(imageName);
	}
	
	/**
	 * Load a preview of the image in the given file
	 * @param image File object of the image
	 */
	public void loadImagePreview(File image) {
		image_image.setSource(new FileResource(image));
		image_image.setVisible(true);
	}
	
	/**
	 * Loads a preview from the entered code fragment
	 */
	public void loadCodePreview(String text) {
		code_preview.setValue(text);
	}
	
	/**
	 * Get the id of the question currently loaded in this view
	 * @return The id of the question. Returns 0 if no question is loaded.
	 */
	public Long getQuestionId() {
		return Long.parseLong(question_id.getValue());
	}
	
	/**
	 * @return The question filled in
	 */
	public String getQuestion() {
		return question_question.getValue();
	}
	
	/**
	 * @return A list with the answers
	 */
	public List<String> getAnswers() {
		ArrayList<String> answers = new ArrayList<String>();
		
		for(TextField field : question_answers) {
			answers.add(field.getValue());
		}
		
		return answers;
	}
	
	/**
	 * @return A list with booleans that indicates whether the answer is correct or not
	 */
	public List<Boolean> getCorrects() {
		ArrayList<Boolean> correct = new ArrayList<Boolean>();
		
		for(CheckBox box : question_correct) {
			correct.add(box.getValue());
		}
		
		return correct;
	}
	
	/**
	 * Get the id's from the answers currently loaded in this view. 
	 * If the answer hasn't an id, then 0 will be returned in the list
	 * @return List with the id's.
	 */
	public List<Long> getAnswerIds() {
		ArrayList<Long> answerIds = new ArrayList<Long>();
		
		for(TextField field : question_answers) {
			answerIds.add(Long.parseLong(field.getId()));
		}
		
		return answerIds;
	}
	
	/**
	 * @return Filename of the image that is attached to this question
	 */
	public String getImageName() {
		return image_src.getValue();
	}
	
	/**
	 * @return Code fragment
	 */
	public String getCodeFragment() {
		return code_fragment.getValue();
	}
	
	public int getRanking()
	{
		return (Integer) question_ranking.getValue();
	}

	public String getCategory() {
		return (String) question_category.getValue();
	}
}
