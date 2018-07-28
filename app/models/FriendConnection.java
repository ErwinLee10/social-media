package models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class FriendConnection extends BaseModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FriendConnectionId id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friend;

	public FriendConnectionId getId() {
		return id;
	}

	public void setId(FriendConnectionId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

}
