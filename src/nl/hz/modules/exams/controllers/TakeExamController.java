package nl.hz.modules.exams.controllers;

import java.util.List;

import nl.hz.bict.sor21314.team1.entities.Answer;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.entities.RandomExam;
import nl.hz.bict.sor21314.team1.services.RandomExamService;
import nl.hz.modules.exams.views.TakeExamView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.RichTextArea;

/**
 * 
 * 
 * @author Joey Kaan <joey94jk@gmail.com>
 * 
 */
public class TakeExamController implements ClickListener {
	private static final long serialVersionUID = 1321209851149752540L;
	private TakeExamView takeExamView;
	private RandomExamService randomExamService;
	private RandomExam currentExam;
	private Integer currentQuestionIndex;

	public TakeExamController(TakeExamView takeExamView,
			RandomExamService randomExamService) {
		this.takeExamView = takeExamView;
		this.randomExamService = randomExamService;
		this.currentExam = null;
		this.currentQuestionIndex = 0;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		OptionGroup answersBox = this.takeExamView.getAnswersBox();
		RichTextArea questionLabel = this.takeExamView.getQuestionLabel();
		List<Question> questions = this.currentExam.getQuestions();

		if (!(this.currentQuestionIndex < questions.size())) {
			questionLabel.setReadOnly(false);
			questionLabel
					.setValue("Congrats! You have reached the end of the exam");
			questionLabel.setReadOnly(true);

			answersBox.removeAllItems();
			this.takeExamView.getNextButton().setVisible(false);
			return;
		}

		if (answersBox.getValue() == null && answersBox.size() > 0) {
			Notification.show("Select an answer to continue");
			return;
		}

		Question currentQuestion = questions.get(currentQuestionIndex);

		this.renderAnswers(questionLabel, answersBox, currentQuestion);

		this.currentQuestionIndex++;
	}

	private void renderAnswers(RichTextArea questionLabel,
			OptionGroup answersBox, Question currentQuestion) {
		// TODO: Render the image and codeblock too if they are present
		questionLabel.setReadOnly(false);
		questionLabel.setValue(currentQuestion.getQuestion());
		questionLabel.setReadOnly(true);
		answersBox.removeAllItems();
		for (Answer answer : currentQuestion.getAnswers()) {
			answersBox.addItem(answer.getAnswer());
		}
	}

	public String renderInitialEntry(String randomExamId) {
		// TODO: Improve
		// TODO: Set the next button text to Start Exam
		Long randomExamIdLong = Long.parseLong(randomExamId);
		RandomExam randomExam = this.randomExamService
				.getExam(randomExamIdLong);
		List<Question> questions = randomExam.getQuestions();
		String questionsString = "This Exam contains no questions";
		if (!questions.isEmpty()) {
			questionsString = "This Exam contains " + (questions.size() + 1)
					+ " questions";
			currentExam = randomExam;
		}

		return questionsString;
	}

}
