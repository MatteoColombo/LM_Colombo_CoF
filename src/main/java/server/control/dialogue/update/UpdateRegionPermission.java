package server.control.dialogue.update;

import client.model.ModelInterface;
import server.model.player.PermissionCard;

public class UpdateRegionPermission implements Update {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286361150212043651L;
	private int region;
	private int slot;
	private PermissionCard card;
	
	public UpdateRegionPermission(PermissionCard card, int region, int slot) {
		this.card = card;
		this.region = region;
		this.slot = slot;
	}

	@Override
	public void execute(ModelInterface model) {
		model.setPermission(card, region, slot);
	}

}
