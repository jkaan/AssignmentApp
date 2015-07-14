package nl.hz.bict.sor21314.team1.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_id", unique = true, nullable = false)	
	private long questionId;
	
	@Column(name="question")
	private String question;
	
	@Column(name="ranking")
	private int ranking;
	
	@Column(name="category")
	private String category;
	
	@Column(name="imgname")
	private String imgname;
	
	@Column(name = "code")
	private String codeFragment;

    @ManyToMany(mappedBy="questions")
    private Set<RandomExam> exams;
    
    @OneToMany(mappedBy="question")
    private List<Answer> answers;

	public Question() {
		this.exams = new HashSet<RandomExam>();
		this.answers = new ArrayList<Answer>();
	}
	
	public Question(String question) {
		setQuestion(question);
	}
	
	public long getQuestionId(){
		return this.questionId;
	}
	
	public void setQuestionId(long questionId){
		this.questionId = questionId; 
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getImageName() {
		return imgname;
	}
	
	public void setImageName(String imgname) {
		this.imgname = imgname;
	}
	
	public String getCodeFragment() {
		return codeFragment;
	}
	
	public void setCodeFragment(String codeFragment) {
		this.codeFragment = codeFragment;
	}
	
	public List<Answer> getAnswers() {
        return this.answers;
    }
	
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public Set<RandomExam> getExams() {
		return exams;
	}

	public void setExams(Set<RandomExam> exams) {
		this.exams = exams;
	}
	
	public void addExam(RandomExam exam) {
		this.exams.add(exam);
	}
	
	public void removeExam(RandomExam exam) {
		this.exams.remove(exam);
	}
	
	
	
}
