package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.board.Region;
import model.board.council.CouncilorPool;
import model.board.map.MapExplorer;
import model.board.map.MapLoader;
import model.player.Player;
import model.reward.Reward;

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
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
		ml = new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
		plains = ml.getRegions().get(1);
		mountain = ml.getRegions().get(2);
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
	//expected.add(plains.getCity("Juvelar").getReward());
	expected.add(mountain.getCity("Merkatim").getReward());
	expected.add(mountain.getCity("Lyram").getReward());
	expected.add(mountain.getCity("Osium").getReward());
	expected.add(mountain.getCity("Naris").getReward());
	List<Reward> connectedRewards = new ArrayList<Reward>(explorer.getAdiacentRewards(plains.getCity("Hellar"), p));
	
	assertEquals(expected.containsAll(connectedRewards), connectedRewards.containsAll(expected));
	}
	
}
