package server.control.dialogue.update;

import client.model.ModelInterface;

public class NotifyKingLocation implements Update {

	private static final long serialVersionUID = 2653404705987899118L;
	private String location;
	
	public NotifyKingLocation(String location) {
		this.location=location;
	}
	@Override
	public void execute(ModelInterface model) {
		model.setKingLocation(location);
	}

}
