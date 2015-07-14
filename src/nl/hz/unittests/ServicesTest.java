package nl.hz.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import nl.hz.bict.sor21314.team1.entities.Answer;
import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.bict.sor21314.team1.services.CategoryService;
import nl.hz.bict.sor21314.team1.services.QuestionService;
import nl.hz.bict.sor21314.team1.services.UserService;

import org.junit.Test;

public class ServicesTest {
	
	@Test
	public void questionServiceTest() {
		QuestionService questionService = new QuestionService();
		
		Question question = new Question();
		question.setQuestion("Vraag 1");
		question.setRanking(5);
		question.setCategory("Categorie moeilijk");
		
		List<Answer> answers = new ArrayList<Answer>();
		
		Answer answer = new Answer();
		answer.setAnswer("Antwoord 1");
		answer.setCorrectAnswer(true);
		
		answers.add(answer);
		
		assertEquals("Pass - should be the same", 0, questionService.getAllQuestions().size());
		
		questionService.insertQuestion(question, answers);
		
		assertEquals("Pass - should be the same", 1, questionService.getAllQuestions().size());
		assertNotSame("Pass - shouldnt be the same", 0, questionService.getAllQuestions().size());
	}
	
	@Test
	public void categoryServiceTest() {
		CategoryService categoryService = new CategoryService();
		
		Category category = new Category();
		category.setCategory("Categorie 1");
		
		assertEquals("Pass - should be the same", 0, categoryService.getAllCategories().size());
		
		categoryService.insertCategory(category);

		assertEquals("Pass - should be the same", 1, categoryService.getAllCategories().size());
		assertNotSame("Pass - shouldnt be the same", 0, categoryService.getAllCategories().size());
	}
	
	@Test
	public void userServiceTest() {
		UserService userService = new UserService();
		
		User user = new User();
		user.setUsername("joey");
		user.setPassword("joey");
		user.setRole("Rol");
		user.setEmail("joey@joeykaan.nl");
		
		assertEquals("Pass - should be the same", 0, userService.getAllUsers().size());
		
		userService.insertUser(user);
		
		assertEquals("Pass - should be the same", 1, userService.getAllUsers().size());
		assertNotSame("Pass - shouldnt be the same", 0, userService.getAllUsers().size());
	}
}
