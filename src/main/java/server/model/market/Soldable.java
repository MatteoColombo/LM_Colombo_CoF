package server.model.market;

import java.io.Serializable;
import server.model.configuration.Configuration;

@FunctionalInterface
public interface Soldable extends Serializable {
	
	public String getMarketMessage(Configuration config);
	
}
