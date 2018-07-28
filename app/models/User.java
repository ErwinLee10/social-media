package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.annotation.Where;

@Entity
public class User extends BaseModel {

	@Id
	private long id;

	@Column(name = "email", columnDefinition = "varchar(255)", nullable = false)
	private String email;

	@OneToMany(mappedBy = "user")
	private List<FriendConnection> friendsConnection;

	@OneToMany(mappedBy = "target")
	@Where(clause = "subscriptionStatus=1")
	private List<UserUpdateSubscription> myFans;

	@OneToMany(mappedBy = "target")
	@Where(clause = "subscriptionStatus=2")
	private List<UserUpdateSubscription> myHaters;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FriendConnection> getFriendsConnection() {
		return friendsConnection;
	}

	public void setFriendsConnection(List<FriendConnection> friends) {
		this.friendsConnection = friends;
	}

	public List<UserUpdateSubscription> getMyFans() {
		return myFans;
	}

	public void setMyFans(List<UserUpdateSubscription> myFans) {
		this.myFans = myFans;
	}

	public List<UserUpdateSubscription> getMyHaters() {
		return myHaters;
	}

	public void setMyHaters(List<UserUpdateSubscription> myHaters) {
		this.myHaters = myHaters;
	}

}
