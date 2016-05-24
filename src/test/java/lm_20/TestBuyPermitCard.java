package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.ABuyPermissionCard;
import game.board.council.Council;
import game.board.council.CouncilorPool;
import game.board.map.MapLoader;
import game.player.PermissionCard;
import game.player.Player;
import game.player.PoliticCard;

public class TestBuyPermitCard {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private Council council;
	private PermissionCard pcard;

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
		this.player = new Player(10, 3, 6, 10, colorList, 0, 0);
		this.pool = new CouncilorPool(4, 4, colorList);
		this.council = pool.getCouncil();
		MapLoader ml = new MapLoader("src/main/resources/map.xml", pool);
		this.pcard = new PermissionCard(ml.getRegions().get(0).getCities());
	}

	/**
	 * Buys a permit card with a 100% satisfied council
	 */
	@Test
	public void testBuyPermitCardWorking() {
		List<Color> colorOfCouncil = council.getCouncilorsColor();
		List<PoliticCard> cards = new ArrayList<>();
		for (Color c : colorOfCouncil) {
			PoliticCard p;
			do {
				p = new PoliticCard(colorList);
			} while (p.getCardColor() != c);
			cards.add(p);
		}
		try {
			ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards);
			action.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1, player.getPermissionCard().size());

	}
	
	/**
	 * Tries to buy a not satisfied council but has no money
	 */
	@Test
	public void testBuyPermitCardNotenoughMoney() {
		List<Color> colorOfCouncil = council.getCouncilorsColor();
		List<PoliticCard> cards = new ArrayList<>();
		PoliticCard p;
		do {
			p = new PoliticCard(colorList);
		} while (!p.isMultipleColor());
		cards.add(p);

		try {
			ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards);
			action.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(10, player.getCoins().getAmount());
		assertEquals(0, player.getPermissionCard().size());

	}
	
	
	/**
	 * Tries to buy a not satisfied council, has to pay
	 */
	@Test
	public void testBuyPermitCardWithMoney() {
		this.player.getCoins().increaseAmount(2);
		List<PoliticCard> cards = new ArrayList<>();
		PoliticCard p;
		do {
			p = new PoliticCard(colorList);
		} while (!p.isMultipleColor());
		cards.add(p);

		try {
			ABuyPermissionCard action = new ABuyPermissionCard(player, pcard, council, cards);
			action.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertEquals(1, player.getPermissionCard().size());

	}

}