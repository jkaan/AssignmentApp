package nl.hz.modules.exams.views;

import nl.hz.bict.sor21314.team1.services.RandomExamService;
import nl.hz.modules.exams.controllers.TakeExamController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * TODO Make this view safe against back buttons. Should use URI fragments for
 * every question and save the questions in session for later use, when
 * back-buttoning e.g.
 * 
 * @author "Joey Kaan <joey94jk@gmail.com>"
 * 
 */
public class TakeExamView extends VerticalLayout implements View {
	private static final long serialVersionUID = 540491522134456769L;
	private RichTextArea questionLabel;
	private Label codeBlock;
	private Image imageBlock;
	private Button nextButton;
	private TakeExamController takeExamController;
	private RandomExamService randomExamService;
	private OptionGroup answersBox;

	public TakeExamView() {
		this.setMargin(true);
		
		questionLabel = new RichTextArea();
		questionLabel.setReadOnly(true);
		
		codeBlock = new Label();
		imageBlock = new Image();
		nextButton = new Button("Start Exam");
		answersBox = new OptionGroup();

		randomExamService = new RandomExamService();
		takeExamController = new TakeExamController(this, randomExamService);
		nextButton.addClickListener(takeExamController);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String[] parameters = event.getParameters().split("/");

		// TODO: Check if there are more than one parameters, if so generate an
		// error
		// TODO: Also check if this exam is valid, coupled to logged in user and
		// present in database
		// TODO: Switch rendering from controller to view and improve the
		// interaction between the two
		String randomExamId = parameters[0];
		String questionsString = takeExamController
				.renderInitialEntry(randomExamId);
		questionLabel.setReadOnly(false);
		questionLabel.setValue(questionsString);
		questionLabel.setReadOnly(true);

		this.addComponent(questionLabel);
		this.addComponent(answersBox);
		this.addComponent(nextButton);
	}

	public RichTextArea getQuestionLabel() {
		return questionLabel;
	}

	public Label getCodeBlock() {
		return codeBlock;
	}

	public Image getImageBlock() {
		return imageBlock;
	}

	public OptionGroup getAnswersBox() {
		return answersBox;
	}

	public Button getNextButton() {
		return nextButton;
	}
}
