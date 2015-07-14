package nl.hz.bict.sor21314.team1.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import nl.hz.bict.sor21314.team1.entities.Answer;
import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.persistency.PersistentStorage;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * This class handles all requests to the database related to questions
 * @author Kevin
 * @version 23-04-2014
 */
public class QuestionService extends Observable {
	private static QuestionService instance;
	
	public QuestionService(){
		// Nothing to do
	}
	
	public static QuestionService getInstance() {
		if(instance == null)
			instance = new QuestionService();
		
		return instance;
	}
	
	/**
	 * Insert a question with it's answers into the database
	 * @param question Question object
	 * @param answers Answer objects
	 */
	public void insertQuestion(Question question, List<Answer> answers) {
		PersistentStorage storage = PersistentStorage.getInstance();
		
		storage.saveSingle(question);
		storage.save(answers);
		
		// Observer
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Update an existing question
	 * @param question Question to update
	 * @param answers Answers to update
	 */
	public void updateQuestion(Question question, List<Answer> answers) {
		PersistentStorage storage = PersistentStorage.getInstance();	
		storage.saveOrUpdate(answers);
		storage.saveOrUpdateSingle(question);
		
		
		// Observer
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return Returns a list with all questions from the database
	 */
	public List<Question> getAllQuestions() {
		PersistentStorage storage = PersistentStorage.getInstance();
		ArrayList<Question> questions = new ArrayList<Question>();
		
		// Cast to Question
		for(Object o : storage.getAll(Question.class)) {
			Question question = (Question) o;
			questions.add(question);
		}
		
		return questions;
	}
	
	/**
	 * Returns a specific question with a given id
	 * @param id Id of the question
	 * @return Question with the given id
	 */
	public Question getQuestion(long id) {
		PersistentStorage storage = PersistentStorage.getInstance();
		return (Question) storage.getSingle(Question.class, id);
	}
	
	/**
	 * 
	 * @param question
	 * @param answers
	 * @param correctAnswerIndex
	 */
	public void insertQuestion(String question, List<String> answers, int correctAnswerIndex) {
		PersistentStorage storage = PersistentStorage.getInstance();
		
		Question objQuestion = new Question();
		objQuestion.setQuestion(question);
		storage.saveSingle(objQuestion);
		
		List<Object> objsAnswers = new LinkedList<Object>();
		System.out.println(answers.toString());
		for(int index = 0; index < answers.size(); index++){
			if(answers.get(index) != null){
				Answer answer = new Answer();
				answer.setAnswer(answers.get(index));
				answer.setQuestion(objQuestion);
				answer.setCorrectAnswer(index == correctAnswerIndex ? true : false);
				objsAnswers.add(answer);
			}
		}
		
		System.out.println("final3");
		storage.saveOrUpdate(objsAnswers);
		
		System.out.println("final");
	}
	
	public List<Question> getQuestions() {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		storage.acquireSessionLock();
		Query query = session.createQuery("from Question");
		
		@SuppressWarnings("unchecked")
		List<Question> results = query.list();
		
		storage.releaseSessionLock();
		
		return results;
	}
}
