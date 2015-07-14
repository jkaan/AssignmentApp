package nl.hz.bict.sor21314.team1.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Answer")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "answer_id", unique = true, nullable = false)	
	private long answerId;
	
	@Column(name="answer")
	private String answer;
	
	@Column(name="correctAnswer")
	private boolean correctAnswer;

	@ManyToOne 
	@JoinColumn(name="question_id")
	private Question question;     

    public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer(){
		
	}
	
	public boolean getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Answer(int question_Id, String answer){
		//setQuestion_Id(question_Id);
		setAnswer(answer);
	}

	public long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
