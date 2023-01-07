package entityControllers;

import java.util.ArrayList;

import entities.SystemMessage;

/**
 * class that will manage messages from system and holds all of the messages
 * 
 * @author peleg
 *
 */
public class MessageInSystemController {
	private ArrayList<SystemMessage> msgList = new ArrayList<>();

	public void setMsgList(ArrayList<SystemMessage> msgList) {
		this.msgList = msgList;
	}

	public ArrayList<SystemMessage> getMsgList() {
		return this.msgList;
	}
}
