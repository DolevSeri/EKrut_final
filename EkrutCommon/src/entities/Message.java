package entities;

import java.io.Serializable;

import enums.Request;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Request request;
	private Object object;
	
	  public Message(Request request, Object object) {
		    this.request = request;
		    this.object = object;
		  }
		  
		  public Request getRequest() {
		    return this.request;
		  }
		  
		  public void setRequest(Request request) {
		    this.request = request;
		  }
		  
		  public Object getObject() {
		    return this.object;
		  }
		  
		  public void setObject(Object object) {
		    this.object = object;
		  }
	
}
