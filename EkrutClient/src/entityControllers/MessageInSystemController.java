package entityControllers;

import java.util.ArrayList;

import entities.MessageInSystem;

/**
 * class that will manage messages from system and holds all of the messages
 * 
 * @author peleg
 *
 */
public class MessageInSystemController {
	private ArrayList<MessageInSystem> msgList = new ArrayList<>();

	public void setMsgList(ArrayList<MessageInSystem> msgList) {
		this.msgList = msgList;
	}

	public ArrayList<MessageInSystem> getMsgList() {
		return this.msgList;
	}
}
