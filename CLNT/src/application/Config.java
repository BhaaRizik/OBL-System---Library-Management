package application;

public class Config {
	public boolean isDebug= true;
	public String host = null;
	public int port = 0;
	
	private static Config instance = new Config();

	private Config() {
	}

	public static Config getConfig() {
		return instance;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}


}
