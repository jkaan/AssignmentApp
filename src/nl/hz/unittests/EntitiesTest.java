package nl.hz.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import nl.hz.bict.sor21314.team1.entities.Category;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.entities.RandomExam;
import nl.hz.bict.sor21314.team1.entities.User;

import org.junit.Test;

public class EntitiesTest {
	
	@Test
	public void questionTest() {
		Question question = new Question();
		
		question.setQuestion("Question 1");
		question.setRanking(5);
		
		assertNotNull(question.getQuestion());
		assertNotNull(question.getRanking());
		
		assertEquals("Pass - should be the same", "Question 1", question.getQuestion());
		assertEquals("Pass - should be the same", 5, question.getRanking());
	}
	
	@Test
	public void userTest() {
		User user = new User();
		
		//Initialize user
		user.setUsername("Gebruiker");
		user.setPassword("Wachtwoord");
		user.setRole("student");
		user.setEmail("gebruiker@chinatown.ch");
		
		//Check if variable not equal to null
		assertNotNull(user.getUsername());
		assertNotNull(user.getPassword());
		assertNotNull(user.getRole());
		assertNotNull(user.getEmail());
		
		//Check if the variable is same as expected
		assertEquals("Gebruiker", "Gebruiker", user.getUsername());
		assertEquals("Wachtwoord", "Wachtwoord", user.getPassword());
		assertEquals("student", "student", user.getRole());
		assertEquals("gebruiker@chinatown.ch", "gebruiker@chinatown.ch", user.getEmail());
		
		//Negative test
		assertNotSame("Must be gebruiker@chinatown.ch", "admin@chinatown.ch", user.getEmail());
	}
		
	@Test
	public void categoryTest() {
		Category category = new Category();
		
		category.setCategory("Categorie 1");
		
		assertNotNull(category.getCategory());
		
		assertEquals("Pass - should be the same", "Categorie 1", category.getCategory());
		
		assertNotSame("Pass - shouldnt be the same (capitals)", "categorie 1", category.getCategory());
	}
	
	public void randomExamTest() {
		RandomExam exam = new RandomExam();
		
		exam.addQuestion(new Question());
		exam.addQuestion(new Question());
		exam.addQuestion(new Question());
		
		assertNotNull(exam.getQuestions());
		
		assertEquals("Pass - should be the same", 3, exam.getQuestions().size());
		
		assertNotSame("Pass - shouldnt be the same", 2, exam.getQuestions().size());
	}

}
