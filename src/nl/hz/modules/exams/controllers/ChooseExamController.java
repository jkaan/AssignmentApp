package nl.hz.modules.exams.controllers;

import nl.hz.bict.sor21314.team1.entities.RandomExam;
import nl.hz.bict.sor21314.team1.services.RandomExamService;
import nl.hz.modules.exams.views.ChooseExamView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;

public class ChooseExamController implements ClickListener {
	private static final long serialVersionUID = 2325011044940824312L;
	
	private ChooseExamView chooseExamView;
	private RandomExamService randomExamService;

	public ChooseExamController(ChooseExamView chooseExamView,
			RandomExamService randomExamService) {
		this.chooseExamView = chooseExamView;
		this.randomExamService = randomExamService;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(chooseExamView.getSelectedExamId() != null) {
			RandomExam selectedRandomExam = randomExamService.getExam(chooseExamView.getSelectedExamId());
			UI.getCurrent().getNavigator().navigateTo("TakeExam/" + selectedRandomExam.getRandomExamId());
		}
	}
}
