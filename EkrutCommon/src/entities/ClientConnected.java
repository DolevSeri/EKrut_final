package entities;

public class ClientConnected {
	private String IP,host,status;

	public ClientConnected(String IP, String host, String status) {
		this.IP = IP;
		this.host = host;
		this.status = status;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "[IP=" + IP + ", host=" + host + ", status=" + status + "]";
	}
	
}
