package models;

import com.avaje.ebean.annotation.EnumValue;

public enum SubscriptionStatus {
	@EnumValue("1") SUBSCRIBED, @EnumValue("2") BLOCKED;
}
