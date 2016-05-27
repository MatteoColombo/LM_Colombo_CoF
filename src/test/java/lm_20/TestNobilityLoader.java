package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.board.nobility.NobilityLoader;
import game.player.Player;
import game.reward.Reward;

public class TestNobilityLoader {

	@Test
	public void testNobilityLoader() throws Exception{
		List<Color> colors = new ArrayList<Color>();
		colors.add(new Color(20, 30, 40));
		colors.add(new Color(100, 30, 50));
		colors.add(new Color(200, 130, 140));
		colors.add(new Color(2, 3, 40));
		colors.add(new Color(2, 3, 4));
		colors.add(new Color(255, 255, 255));
		Player p= new Player(10, 1, 6, 10, colors, 0, 0);
		NobilityLoader nl = new NobilityLoader("src/main/resources/nobtrack.xml");
		List<Reward> rew= nl.getNobilityTrack();
		rew.get(2).assignBonusTo(p);
		assertEquals(2, p.getVictoryPoints().getAmount());
		assertEquals(12, p.getCoins().getAmount());
	}

}
