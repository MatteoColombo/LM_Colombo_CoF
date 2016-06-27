package server.control.dialogue.update;

import client.model.ModelInterface;

public class UpdateEmporiumBuilt implements Update {

	private String name;
	private String city;
	private static final long serialVersionUID = 1329724702238079691L;

	public UpdateEmporiumBuilt(String name, String city){
		this.city=city;
		this.name=name;
	}
	@Override
	public void execute(ModelInterface model) {
		model.buildEmporium(city, name);
	}

}
