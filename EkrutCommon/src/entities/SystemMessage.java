package entities;

import java.io.Serializable;

import enums.MessageStatus;
import enums.Role;

/**
 * Class using for Messages for managers from system
 * 
 * @author Ron
 *
 */
public class SystemMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5325003775890494869L;
	private static int msgID;
	private String username;
	private String description;
	private MessageStatus status;

	public SystemMessage(int msgID, String username, String description, MessageStatus status) {
		this.msgID = msgID;
		this.username = username;
		this.description = description;
		this.status = status;
	}

	public int getMsgID() {
		return msgID;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public String getDescription() {
		return description;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setRole(String username) {
		this.username = username;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return description;
	}
}
