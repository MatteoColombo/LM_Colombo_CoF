package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.action.ABuyPermissionCard;
import server.model.action.IllegalActionException;
import server.model.board.council.Council;
import server.model.board.council.CouncilorPool;
import server.model.board.map.MapLoader;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

public class TestBuyPermitCard {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private Council council;
	private PermissionCard pcard;
	private MapLoader ml;
	/**
	 * Creates a list with the colors and it initializes the player
	 */
	@Before
	public void setUp() throws Exception {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		NobilityTrack track = new NobilityTrack(
				new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		this.player = new Player(10, 3, 6, 10, colorList, 0, 0, track, null);
		this.pool = new CouncilorPool(4, 4, colorList);
		this.council = pool.getCouncil();
		ml = new MapLoader("src/main/resources/map.xml", pool);
		this.pcard = ml.getRegions().get(0).getPermissionCard(0);
	}

	/**
	 * Buys a permit card with a 100% satisfied council
	 */
	@Test
	public void testBuyPermitCardWorking() throws Exception {
		List<Color> colorOfCouncil = council.getCouncilorsColor();
		List<PoliticCard> cards = new ArrayList<>();
		for (Color c : colorOfCouncil) {
			PoliticCard p;
			do {
				p = new PoliticCard(colorList);
			} while (p.getCardColor() != c);
			cards.add(p);
		}

		ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards,ml.getRegions().get(0),0);
		action.execute();

		assertEquals(1, player.getPermissionCard().size());

	}

	/**
	 * Tries to buy a not satisfied council but has no money
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuyPermitCardNotenoughMoney() throws Exception {
		List<PoliticCard> cards = new ArrayList<>();
		PoliticCard p;
		do {
			p = new PoliticCard(colorList);
		} while (!p.isMultipleColor());
		cards.add(p);

		ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards,ml.getRegions().get(0),0 );
		action.execute();

		assertEquals(10, player.getCoins().getAmount());
		assertEquals(0, player.getPermissionCard().size());

	}

	/**
	 * Tries to buy a not satisfied council, has to pay
	 */
	@Test
	public void testBuyPermitCardWithMoney() throws Exception {
		this.player.getPoliticCard().clear();
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(this.council.getHeadColor()));
		this.player.getPoliticCard().addAll(cards);
		ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards,ml.getRegions().get(0),0);
		action.execute();
		assertEquals(1, player.getPermissionCard().size());

	}

}
