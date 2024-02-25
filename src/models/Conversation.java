package models;

import java.util.Date;

public class Conversation {
	private long conversationId;
    private long userId1;
    private long userId2;
    private Date dateCreated;
	@Override
	public String toString() {
		return "Conversation [conversationId=" + conversationId + ", userId1=" + userId1 + ", userId2=" + userId2
				+ ", dateCreated=" + dateCreated + "]";
	}
	public long getConversationId() {
		return conversationId;
	}
	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
	public long getUserId1() {
		return userId1;
	}
	public void setUserId1(long userId1) {
		this.userId1 = userId1;
	}
	public long getUserId2() {
		return userId2;
	}
	public void setUserId2(long userId2) {
		this.userId2 = userId2;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
