package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User extends BaseModel {

	@Id
	private long id;

	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
	private String email;

	@OneToMany(mappedBy = "user")
	private List<FriendConnection> friends;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FriendConnection> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendConnection> friends) {
		this.friends = friends;
	}
}
