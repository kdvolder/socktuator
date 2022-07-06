package socktuator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("socktuator.rsocket.server")
public class RSocktuatorServerProps {
	
	private int port = 7000;
	private String host = "localhost";
	private boolean enabled;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}