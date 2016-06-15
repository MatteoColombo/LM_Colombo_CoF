package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.board.nobility.NobilityLoader;
import model.exceptions.TrackXMLFileException;
import model.player.Player;
import model.reward.Reward;

public class TestNobilityLoader {

	@Test
	public void testNobilityLoader() throws Exception{
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.YELLOW);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		Player p= new Player(10, 1, 6, 10, colors, 0, 0);
		NobilityLoader nl = new NobilityLoader("src/main/resources/nobtrack.xml");
		List<Reward> rew= nl.getNobilityTrack();
		rew.get(2).assignBonusTo(p);
		assertEquals(2, p.getVictoryPoints().getAmount());
		assertEquals(12, p.getCoins().getAmount());
	}

	@Test(expected=TrackXMLFileException.class)
	public void testExceptionTrackXML() throws Exception{
		NobilityLoader nl= new NobilityLoader("src/file.xml");
	}
}
