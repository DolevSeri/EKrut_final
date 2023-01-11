package server;

import ocsf.server.ConnectionToClient;

public class ClientConnected {
	private String IP,host,status,username;
    private ConnectionToClient client;

    public ClientConnected(String IP, String host, String status, ConnectionToClient client) {
        this.IP = IP;
        this.host = host;
        this.status = status;
        this.client = client;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ConnectionToClient getClient() {
		return client;
	}

	public void setClient(ConnectionToClient client) {
		this.client = client;
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
