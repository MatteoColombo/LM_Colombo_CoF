package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.MapXMLFileException;
import model.exceptions.NegativeException;
import model.market.GoodsBundle;
import model.market.Market;
import model.player.*;

public class TestMarket {
	private List<Color> colorList;
	private List<Player> allPlayers;
	private Market market;

	@Before
	public void setUp() throws MapXMLFileException {
		colorList = new ArrayList<>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		allPlayers = new ArrayList<>();
		allPlayers.add(new Player(10, 3, 6, 10, colorList, 0, 0));
		allPlayers.add(new Player(11, 4, 6, 10, colorList, 1, 0));
		allPlayers.add(new Player(12, 5, 6, 10, colorList, 2, 0));
		allPlayers.add(new Player(13, 6, 6, 10, colorList, 3, 0));
		this.market = new Market(allPlayers);
	}

	@Test
	public void testGetPlayerBundle() throws NegativeException {
		Player player1 = this.allPlayers.get(0);
		GoodsBundle goodsBundle = this.market.getPlayerBundle(player1);
		assertEquals(player1, goodsBundle.getPlayerOwner());
	}

}
