package instruction.update;

import client.model.ModelInterface;

public class UpdateEmporiumBuilt implements Update {

	private int player;
	private String city;
	private static final long serialVersionUID = 1329724702238079691L;

	public UpdateEmporiumBuilt(int player, String city){
		this.city=city;
		this.player=player;
	}
	@Override
	public void execute(ModelInterface model) {
		model.buildEmporium(city, player);
	}

}
