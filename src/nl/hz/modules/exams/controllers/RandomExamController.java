package nl.hz.modules.exams.controllers;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.CheckBox;

import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.bict.sor21314.team1.services.RandomExamService;
import nl.hz.bict.sor21314.team1.services.UserService;

public class RandomExamController {
	private RandomExamService randomExamService;
	private UserService userService;
	private QuestionService questionsService;
	
	public RandomExamController() {
		this.randomExamService = new RandomExamService();
		this.userService = new UserService();
		this.questionsService = new QuestionService();
	}
	
	
	public void randomizeExams(ArrayList<CheckBox> boxes) {
		List<User> users = userService.getUsers();
		
		for(User user : users) {
			randomExamService.generateRandomExam(user, questionsService.getAllQuestions(), boxes);
		}
	}
}


