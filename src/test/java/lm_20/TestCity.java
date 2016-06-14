package lm_20;

import static org.junit.Assert.*;
import org.junit.Test;

import javafx.scene.paint.Color;
import model.board.city.City;
import model.player.Player;
import model.reward.Reward;
import model.reward.RewardCity;

public class TestCity {
	
	@Test
	public void testGetter() {
		Reward reward = new RewardCity();
		City castrum = new City(Color.SILVER, "Castrum", reward);
		assertEquals(castrum.getName(), "Castrum");
		assertEquals(castrum.getColor(), Color.SILVER);
		assertEquals(castrum.isCapital(), false);
		assertEquals(castrum.getNumberOfEmporium(), 0);
		assertEquals(castrum.getReward(), reward);
	}
	
	@Test
	public void testConnections() {
		City castrum = new City(Color.SILVER, "Castrum", new RewardCity());
		City milan = new City(Color.GOLD, "Milan", new RewardCity());
		City rome = new City(Color.AQUA, "Milan", new RewardCity());
		
		castrum.addConnection(milan);
		assertEquals(castrum.isConnectedTo(milan), true);
		assertEquals(castrum.isConnectedTo(rome), false);
		assertEquals(castrum.getConnectedCities().get(0), milan);
	}
	
	@Test
	public void testEmporium() {
		City castrum = new City(Color.SILVER, "Castrum", new RewardCity());
		Player p = new Player(0, 0, 0, 10, null, 0, 0);
		castrum.addEmporium(p.getEmporium().remove(0));
		assertEquals(castrum.hasEmporiumOfPlayer(p), true);
		assertEquals(castrum.getNumberOfEmporium(), 1);
	}

}
