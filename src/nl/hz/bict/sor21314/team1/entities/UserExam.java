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
@Table(name = "UserExam")
public class UserExam {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userexamID",columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT", unique = true, nullable = false)	
	private Integer userexamID;
	
	@Column(name="questionID")
	private Long questionID;
	
	@Column(name="answer")
	private String answer;
	
	
	//mapping for exam_id
	@ManyToOne
    @JoinColumn(name="exam_id")
    private Exams exams;
     

//	@ManyToMany(mappedBy="userexam")
//	private Set<Exams> exam = new HashSet<Exams>();
	//end
	
	//mapping for user_id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User users;
	

//	@ManyToMany(mappedBy="userexam")
//	private Set<Users> user = new HashSet<Users>();
	//end


	public Exams getExams() {
		return exams;
	}

	public void setExams(Exams exams) {
		this.exams = exams;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Integer getUserexamID() {
		return userexamID;
	}

	public void setUserexamID(Integer userexamID) {
		this.userexamID = userexamID;
	}

	public Long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Long questionID2) {
		this.questionID = questionID2;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
