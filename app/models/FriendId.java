package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FriendId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	public long userId;

	@Column(name = "friend_id")
	public long friendId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (friendId ^ (friendId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendId other = (FriendId) obj;
		if (friendId != other.friendId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

}
