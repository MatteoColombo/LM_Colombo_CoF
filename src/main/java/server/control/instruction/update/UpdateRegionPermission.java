package server.control.instruction.update;

import client.model.ModelInterface;
import server.model.player.PermissionCard;
/**
 * Update the region permissions when changes occures
 */
public class UpdateRegionPermission implements Update {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286361150212043651L;
	private int region;
	private int slot;
	private PermissionCard card;
	
	/**
	 * Create a new UpdateRegionPermission
	 * @param card the new card for the replacement
	 * @param region the region destination (by index)
	 * @param slot the slot destination (by index)
	 */
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
