package server.control.instruction.update;

import java.util.List;

import client.model.ModelInterface;
import server.model.board.city.CityConnection;

public class UpdateSetConnections implements Update {

	private static final long serialVersionUID = 4850985240489417896L;
	private List<CityConnection> connections;

	public UpdateSetConnections(List<CityConnection> connections) {
		this.connections = connections;
	}

	@Override
	public void execute(ModelInterface model) {
		model.setConnections(connections);
	}

}
