package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model.Find;


@Entity
public class User extends BaseModel{

	@Id
	private long id;
	
	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
