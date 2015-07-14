package nl.hz.bict.sor21314.team1.services;

import java.util.List;

import nl.hz.bict.sor21314.team1.entities.Exams;
import nl.hz.bict.sor21314.team1.entities.UserExam;
import nl.hz.persistency.PersistentStorage;

import org.hibernate.Query;
import org.hibernate.Session;

public class ExamsService {
	public ExamsService(){
		
	}
	
	public List<Exams> getOpenExams(){
		
		PersistentStorage storage = PersistentStorage.getInstance();
		Session session = storage.getSession();
		
		storage.acquireSessionLock();
		Query query = session.createQuery("FROM Exams WHERE exam_open = :examOpen");
		query.setInteger("examOpen", 1);
		
		@SuppressWarnings("unchecked")
		List<Exams> outList = query.list();
		
		storage.releaseSessionLock();
		
		return outList;
	}
	
	public void insertAnswer(Long questionID, String answer,Exams exam, Integer userID) {
		PersistentStorage storage = PersistentStorage.getInstance();
		
		UserExam userexam = new UserExam();
		userexam.setAnswer(answer);
		userexam.setExams(exam);
		userexam.setUsers(null);
		userexam.setQuestionID(questionID);
		storage.saveOrUpdateSingle(userexam);
		
	}
}
