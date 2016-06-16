package client.view;

import java.io.IOException;

@FunctionalInterface
public interface ServerManager {
	public void publishMessage(String message) throws IOException;
}	
