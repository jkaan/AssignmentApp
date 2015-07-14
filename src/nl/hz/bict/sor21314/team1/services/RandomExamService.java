package nl.hz.bict.sor21314.team1.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.hz.bict.sor21314.team1.entities.Question;
import nl.hz.bict.sor21314.team1.entities.RandomExam;
import nl.hz.bict.sor21314.team1.entities.User;
import nl.hz.persistency.PersistentStorage;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;

public class RandomExamService {

	public RandomExamService() {

	}

	public RandomExam getExam(Long randomExamId) {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		storage.acquireSessionLock();
		
		Query query = session.createQuery("from RandomExam WHERE random_exam_id = :random_exam_id");
		query.setParameter("random_exam_id", randomExamId);
		
		List<RandomExam> randomExam = query.list();
		
		storage.releaseSessionLock();
		
		return randomExam.get(0);
	}

	public List<RandomExam> getExams() {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();

		storage.acquireSessionLock();

		Query query = session.createQuery("from RandomExam");

		List<RandomExam> randomExams = query.list();

		storage.releaseSessionLock();

		return randomExams;
	}
	
	public List<RandomExam> getExamsForUser() {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		User userId = (User) VaadinSession.getCurrent().getAttribute("user");

		storage.acquireSessionLock();

		Query query = session.createQuery("from RandomExam WHERE user_id = :user_id");
		query.setParameter("user_id", userId.getUserID());

		List<RandomExam> randomExams = query.list();

		storage.releaseSessionLock();

		return randomExams;
	}

	public List<Question> getQuestionsForExam(Long randomExamId) {
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();

		storage.acquireSessionLock();

		Query query = session.createQuery("from Question WHERE random_exam_id = :random_exam_id");
		query.setParameter("random_exam_id", randomExamId);

		List<Question> questions = query.list();

		storage.releaseSessionLock();

		return questions;
	}

	public void generateRandomExam(User user, List<Question> questions, ArrayList<CheckBox> boxes ) {
		PersistentStorage storage = PersistentStorage.getInstance();

		Random random = new Random();
		int index;

		RandomExam randomExam = new RandomExam();
		randomExam.setUser(user);
		
		int count;
		for(count = 0; count < 25; count++){
			index = random.nextInt(questions.size());
			if (questions.get(index) != null) {
				for(CheckBox box : boxes){
					System.out.println(random.nextInt());
					if(questions.get(index).getCategory().toString().equals(box.getDescription().toString())){
						randomExam.addQuestion(questions.get(index));
						questions.remove(index);
					}
				}
			}
		}
		storage.saveSingle(randomExam);
	}
}
