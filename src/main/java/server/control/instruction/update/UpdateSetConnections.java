package server.control.instruction.update;

import java.util.List;

import client.model.ModelInterface;
import server.model.board.city.CityConnection;

/**
 * Update sent during the gaming setup, setting all the generated connections on the map
 */
public class UpdateSetConnections implements Update {

	private static final long serialVersionUID = 4850985240489417896L;
	private List<CityConnection> connections;

	/**
	 * Create a new UpdateSetConnection
	 * @param connections the list of connections for the cities
	 */
	public UpdateSetConnections(List<CityConnection> connections) {
		this.connections = connections;
	}

	@Override
	public void execute(ModelInterface model) {
		model.setConnections(connections);
	}

}
