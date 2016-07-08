package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.board.Region;
import server.model.board.council.CouncilorPool;
import server.model.board.map.MapExplorer;
import server.model.board.map.MapLoader;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.player.Player;
import server.model.reward.Reward;

public class TestMapExplorer {
	List<Color> colors;
	MapLoader ml;
	Player p;
	Player p2;
	Region plains;
	Region mountain;

	@Before
	public void setUp() throws Exception {
		Configuration config= new Configuration();
		colors = config.getColorsList();
		NobilityTrack track= new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack());
		p= new Player(config,track);
		p2= new Player(config,track);
		ml = new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
		ml.loadConnections();
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
