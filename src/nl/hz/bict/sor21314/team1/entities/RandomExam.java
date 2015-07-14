package nl.hz.bict.sor21314.team1.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RandomExam")
public class RandomExam {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "random_exam_id", unique = true, nullable = false)	
	private long randomExamId;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "exam_has_question", 
			joinColumns = { @JoinColumn(name = "random_exam_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "question_id") })
	private List<Question> questions;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public RandomExam() {
		this.questions = new ArrayList<Question>();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getRandomExamId() {
		return randomExamId;
	}

	public void setRandomExamId(long randomExamId) {
		this.randomExamId = randomExamId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
	}
}
