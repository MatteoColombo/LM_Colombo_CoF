package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
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
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
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
