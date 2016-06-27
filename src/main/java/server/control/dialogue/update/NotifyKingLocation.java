package server.control.dialogue.update;

import client.model.ModelInterface;

public class NotifyKingLocation implements Update {

	private String location;
	
	public NotifyKingLocation(String location) {
		this.location=location;
	}
	@Override
	public void execute(ModelInterface model) {
		model.setKingLocation(location);
	}

}
