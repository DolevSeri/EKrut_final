package entities;

import java.io.Serializable;

import enums.Role;

/**
 * Class using for Messages for managers from system
 * @author Ron
 *
 */
public class MessageInSystem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5325003775890494869L;
	private int msgID;
	private Role role;
	private String description;

	public MessageInSystem(int msgID, Role role, String description) {
		this.msgID = msgID;
		this.role = role;
		this.description = description;
	}

	public int getMsgID() {
		return msgID;
	}

	public Role getRole() {
		return role;
	}

	public String getDescription() {
		return description;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
