package nl.hz.bict.sor21314.team1.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Exams")
public class Exams {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "exam_id", unique = true, nullable = false)	
	private long exam_Id;
	
	@Column(name="exam")
	private String exam;
	
	@Column(name="exam_open")
	private Integer exam_open;
    
	public Exams(){
		
	}
	
	public Exams(String exam){
		setExam(exam);
	}
	
	public long getExamID(){
		return this.exam_Id;
	}
	
	public void setExamID(long examID){
		this.exam_Id = examID; 
	}
	
	public long getExamOpen(){
		return this.exam_open;
	}
	
	public void setExamOpen(int exam_open){
		this.exam_open = exam_open; 
	}

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}
	
	
}
