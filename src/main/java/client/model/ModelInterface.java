package client.model;

import java.util.List;

import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Reward;

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

	public void updateBoardReward(List<BVictoryPoints> kingReward, List<BoardColorReward> colorReward,
			List<BoardRegionReward> regionReward);
}
