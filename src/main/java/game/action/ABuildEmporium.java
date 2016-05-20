package game.action;

import game.board.city.City;
import game.exceptions.IllegalActionException;
import game.player.Emporium;
import game.player.PermissionCard;
import game.player.Player;

public class ABuildEmporium extends Action{
	private Player player;
	private Emporium emporium;
	private PermissionCard permissionCard;
	private City city;
	
	public ABuildEmporium(Player p, PermissionCard permissionCard, City city) {
		super(true);
		this.player = p;
		this.permissionCard = permissionCard;
		this.city = city;
	}
	
	@Override
	public void execute() throws IllegalActionException {
		
		if(!permissionCard.getCardCity().contains(city)) {
			throw new IllegalActionException("the chosen city is invalid");
		}
		
		if(city.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
		}
		
		if(city.getNumberOfEmporium() > 0) {
			if(player.getAssistants().getAmount() < city.getNumberOfEmporium()) {
				throw new IllegalActionException("you can not afford it!");
			}
			player.getAssistants().decrease(city.getNumberOfEmporium());
		}
		
		this.emporium.setCity(city);
		
		// TODO get rewards from map exploration
		
	}
	
}
