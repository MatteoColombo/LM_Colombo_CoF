package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.board.MapExplorer;
import game.board.MapLoader;
import game.board.Region;
import game.board.council.CouncilorPool;
import game.player.Player;
import game.reward.Reward;

public class TestMapExplorer {
	ArrayList<Color> colors;
	MapLoader ml;
	Player p = new Player(0, 0, 0, 10, null, 0, 0);
	Player p2 = new Player(0, 0, 0, 10, null, 0, 0);
	Region plains;
	Region mountain;

	@Before
	public void setUp() throws Exception {
		colors = new ArrayList<Color>();
		colors.add(new Color(20, 30, 40));
		colors.add(new Color(100, 30, 50));
		colors.add(new Color(200, 130, 140));
		colors.add(new Color(2, 3, 40));
		colors.add(new Color(2, 3, 4));
		colors.add(new Color(255, 255, 255));
		ml = new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
		plains = ml.getRegions().get(0);
		mountain = ml.getRegions().get(0);
		plains.getCity("Hellar").addEmporium(p.getEmporium().remove(0));
		plains.getCity("Juvelar").addEmporium(p.getEmporium().remove(0));
		plains.getCity("Hellar").addEmporium(p2.getEmporium().remove(0));
		plains.getCity("Indur").addEmporium(p2.getEmporium().remove(0));
		mountain.getCity("Merkatim").addEmporium(p2.getEmporium().remove(0));
		mountain.getCity("Merkatim").addEmporium(p.getEmporium().remove(0));
		mountain.getCity("Lyram").addEmporium(p.getEmporium().remove(0));
		mountain.getCity("Osium").addEmporium(p.getEmporium().remove(0));
		mountain.getCity("Naris").addEmporium(p.getEmporium().remove(0));
	}

	@Test
	public void testExploration() {
	MapExplorer explorer = new MapExplorer();
	List<Reward> expected = new ArrayList<Reward>();
	expected.add(plains.getCity("Hellar").getReward());
	expected.add(plains.getCity("Juvelar").getReward());
	expected.add(plains.getCity("Merkatim").getReward());
	expected.add(plains.getCity("Lyram").getReward());
	expected.add(plains.getCity("Osium").getReward());
	expected.add(plains.getCity("Naris").getReward());
	List<Reward> connectedRewards = new ArrayList<Reward>(explorer.getAdiacentRewards(plains.getCity("Hellar"), p));
	
	assertEquals(expected.containsAll(connectedRewards), connectedRewards.containsAll(expected));
	}
	
}