package client.model;

import java.util.List;

import model.Configuration;
import model.board.council.Council;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.Reward;

public interface ModelInterface {
	public void initMap(int choosen);

	public int getMyIndex();

	public void isYourTurn();

	public void yourTurnEnded();
	
	public void setCouncil(Council council, int index);
	
	public void setBonus(List<Reward> bonus);

	public void setAllPlayers(List<Player> players);

	public void updatePlayer(Player p, int index);

	public void playerJoined(Player p);
	
	public void setConfiguration(Configuration config);
	
	public Configuration getConfiguration();
	
	public void setPermission(PermissionCard pc, int region, int slot);

	public void buildEmporium(String city, String name);
	
	public void setKingLocation(String location);
}
