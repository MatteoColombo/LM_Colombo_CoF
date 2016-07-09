package server.control.instruction.update;

import client.model.ModelInterface;
/**
 * Update the map when an empoirum is built in
 */
public class UpdateEmporiumBuilt implements Update {

	private int player;
	private String city;
	private static final long serialVersionUID = 1329724702238079691L;

	/**
	 * Create a new UpdateEmporiumBuilt
	 * @param player the index of player who built the emporium
	 * @param city  the city name where the emporium is built in
	 */
	public UpdateEmporiumBuilt(int player, String city){
		this.city=city;
		this.player=player;
	}
	@Override
	public void execute(ModelInterface model) {
		model.buildEmporium(city, player);
	}

}
