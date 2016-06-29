package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import server.model.board.Board;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.configuration.XMLFileException;
import server.model.market.Market;
import server.model.market.OnSaleItem;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

public class TestMarket {
	private List<Player> allPlayers;
	private List<Color> colorList;
	private Market market;

	@Before
	public void setUp() throws TrackXMLFileException, ConfigurationErrorException {
		colorList = new ArrayList<>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		allPlayers = new ArrayList<>();
		NobilityTrack track = new NobilityTrack(
				new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		allPlayers.add(new Player(10, 1, 6, 10, colorList, 0, 0, track, null));
		allPlayers.add(new Player(11, 2, 6, 10, colorList, 0, 0, track, null));
		allPlayers.add(new Player(12, 3, 6, 10, colorList, 0, 0, track, null));
		allPlayers.add(new Player(13, 4, 6, 10, colorList, 0, 0, track, null));
		this.market = new Market(allPlayers);
	}

	@Test
	public void testMarketBuyPolitic() {
		PoliticCard card = allPlayers.get(0).getPoliticCard().remove(0);
		OnSaleItem item= new OnSaleItem(card, allPlayers.get(0), 3);
		market.addItemOnSale(card, 3, allPlayers.get(0));
		market.buyItem(item, allPlayers.get(1));
		assertEquals(8, allPlayers.get(1).getCoins().getAmount());
		assertEquals(13, allPlayers.get(0).getCoins().getAmount());
		assertEquals(7, allPlayers.get(1).getPoliticCard().size());
	}
	
	@Test
	public void testMarketBuyAssistants(){
		allPlayers.get(0).getAssistants().decreaseAmount(1);
		Assistants assist = new Assistants(1);
		OnSaleItem item= new OnSaleItem(assist, allPlayers.get(0), 3);
		market.addItemOnSale(assist, 3, allPlayers.get(0));
		market.buyItem(item, allPlayers.get(1));
		assertEquals(8, allPlayers.get(1).getCoins().getAmount());
		assertEquals(13, allPlayers.get(0).getCoins().getAmount());
		assertEquals(3, allPlayers.get(1).getAssistants().getAmount());
		assertEquals(0, allPlayers.get(0).getAssistants().getAmount());
	}
	
	@Test
	public void testMarketBuyPermit() throws XMLFileException, ConfigurationErrorException {
		Board b= new Board(new Configuration(), 1);
		PermissionCard card = new PermissionCard(b.getRegion(0).getCities());
		OnSaleItem item= new OnSaleItem(card, allPlayers.get(0), 3);
		market.addItemOnSale(card, 3, allPlayers.get(0));
		market.buyItem(item, allPlayers.get(1));
		assertEquals(8, allPlayers.get(1).getCoins().getAmount());
		assertEquals(13, allPlayers.get(0).getCoins().getAmount());
		assertEquals(1, allPlayers.get(1).getPermissionCard().size());
	}
	
}
