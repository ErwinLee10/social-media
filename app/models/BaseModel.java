package models;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.SoftDelete;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;


@MappedSuperclass
public class BaseModel extends Model {

	@Version
	private Long version;

	@WhenCreated
	private LocalDateTime whenCreated;

	@WhenModified
	private LocalDateTime whenModified;
	
	@SoftDelete
	private boolean isDeleted;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public LocalDateTime getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(LocalDateTime whenCreated) {
		this.whenCreated = whenCreated;
	}

	public LocalDateTime getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(LocalDateTime whenModified) {
		this.whenModified = whenModified;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
}