package nl.hz.bict.sor21314.team1.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userID", unique = true, nullable = false)
	private long userID;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	private String role;
    
	@OneToMany(mappedBy="user")
    private Set<RandomExam> exams;
	
	public User() {
		// Nothing to do
	}
	
	public long getUserID(){
		return this.userID;
	}
	
	public void setUserID(long userID){
		this.userID = userID; 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Set<RandomExam> getExams() {
        return this.exams;
    }
	
}
