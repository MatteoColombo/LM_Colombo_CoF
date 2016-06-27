package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.market.Market;
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
	public void testMarket() {
		PoliticCard card = allPlayers.get(0).getPoliticCard().remove(0);
		market.addItemOnSale(card, 3, allPlayers.get(0));
		market.buyItem(market.getItemsOnSale().get(0), allPlayers.get(1));
		assertEquals(13, allPlayers.get(0).getCoins().getAmount());
		assertEquals(8, allPlayers.get(1).getCoins().getAmount());
		assertEquals(7, allPlayers.get(1).getPoliticCard().size());
	}

	/*
	 * @Test public void testGetPlayerBundle() throws NegativeException { for
	 * (int index = 0; index < this.allPlayers.size(); index++) { Player player
	 * = this.allPlayers.get(index); GoodsBundle goodsBundle =
	 * this.market.getPlayerBundle(player); assertEquals(player,
	 * goodsBundle.getPlayerOwner()); } }
	 * 
	 * @Test public void testAddPermissionCardsToBundle() throws
	 * NegativeException, IllegalActionException { Player player =
	 * this.allPlayers.get(0); List<PermissionCard> permissionCard = new
	 * ArrayList<>(); PermissionCard permissionCard1 = new PermissionCard(null,
	 * null); PermissionCard permissionCard2 = new PermissionCard(null, null);
	 * PermissionCard permissionCard3 = new PermissionCard(null, null);
	 * permissionCard.add(permissionCard1); permissionCard.add(permissionCard2);
	 * permissionCard.add(permissionCard3);
	 * player.getPermissionCard().addAll(permissionCard); GoodsBundle
	 * goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignPermissionCardsToBundle(player, permissionCard, 10);
	 * assertEquals(10, goodsBundle.getPermissionCardsPrice().getAmount());
	 * assertEquals(permissionCard2,
	 * goodsBundle.getSellingPermissionCards().get(1)); }
	 * 
	 * @Test(expected = IllegalActionException.class) public void
	 * testAlreadyUsedAddPermissionCardsToBundle() throws NegativeException,
	 * IllegalActionException { Player player = this.allPlayers.get(0);
	 * List<PermissionCard> permissionCard = new ArrayList<>(); PermissionCard
	 * permissionCard1 = new PermissionCard(null, null); PermissionCard
	 * permissionCard2 = new PermissionCard(null, null);
	 * permissionCard2.setCardUsed(); PermissionCard permissionCard3 = new
	 * PermissionCard(null, null); permissionCard.add(permissionCard1);
	 * permissionCard.add(permissionCard2); permissionCard.add(permissionCard3);
	 * player.getPermissionCard().addAll(permissionCard); GoodsBundle
	 * goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignPermissionCardsToBundle(player, permissionCard, 10);
	 * assertEquals(10, goodsBundle.getPermissionCardsPrice().getAmount());
	 * assertEquals(permissionCard2,
	 * goodsBundle.getSellingPermissionCards().get(0)); assertEquals(true,
	 * player.getPermissionCard().isEmpty()); }
	 * 
	 * @Test public void testRemoveOnePermissionCardFromBundle() throws
	 * NegativeException, IllegalActionException { Player player =
	 * this.allPlayers.get(0); List<PermissionCard> permissionCard = new
	 * ArrayList<>(); PermissionCard permissionCard1 = new PermissionCard(null,
	 * null); PermissionCard permissionCard2 = new PermissionCard(null, null);
	 * PermissionCard permissionCard3 = new PermissionCard(null, null);
	 * permissionCard.add(permissionCard1); permissionCard.add(permissionCard2);
	 * permissionCard.add(permissionCard3);
	 * player.getPermissionCard().addAll(permissionCard); GoodsBundle
	 * goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignPermissionCardsToBundle(player, permissionCard, 10);
	 * this.market.removeOnePermissionCardFromBundle(player, permissionCard2);
	 * assertEquals(10, goodsBundle.getPermissionCardsPrice().getAmount());
	 * assertEquals(permissionCard3,
	 * goodsBundle.getSellingPermissionCards().get(1));
	 * assertEquals(permissionCard2, player.getPermissionCard().get(0)); }
	 * 
	 * @Test public void testRemoveAllPermissionCardsFromBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); List<PermissionCard> permissionCard = new
	 * ArrayList<>(); PermissionCard permissionCard1 = new PermissionCard(null,
	 * null); PermissionCard permissionCard2 = new PermissionCard(null, null);
	 * PermissionCard permissionCard3 = new PermissionCard(null, null);
	 * permissionCard.add(permissionCard1); permissionCard.add(permissionCard2);
	 * permissionCard.add(permissionCard3);
	 * player.getPermissionCard().addAll(permissionCard); GoodsBundle
	 * goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignPermissionCardsToBundle(player, permissionCard, 10);
	 * this.market.removePermissionCardsFromBundle(player); assertEquals(0,
	 * goodsBundle.getPermissionCardsPrice().getAmount()); assertEquals(true,
	 * goodsBundle.getSellingPermissionCards().isEmpty());
	 * assertEquals(permissionCard2, player.getPermissionCard().get(1)); }
	 * 
	 * @Test public void testAddPoliticCardsToBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); List<PoliticCard> politicCard = new
	 * ArrayList<>(); PoliticCard politicCard1 = new PoliticCard(colorList);
	 * PoliticCard politicCard2 = new PoliticCard(colorList); PoliticCard
	 * politicCard3 = new PoliticCard(colorList); politicCard.add(politicCard1);
	 * politicCard.add(politicCard2); politicCard.add(politicCard3);
	 * player.getPoliticCard().addAll(politicCard); GoodsBundle goodsBundle =
	 * this.market.getPlayerBundle(player);
	 * this.market.assignPoliticCardsToBundle(player, politicCard, 10);
	 * assertEquals(10, goodsBundle.getPoliticCardsPrice().getAmount());
	 * assertEquals(politicCard2, goodsBundle.getSellingPoliticCards().get(1));
	 * }
	 * 
	 * @Test public void testRemoveOnePoliticCardFromBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); List<PoliticCard> politicCard = new
	 * ArrayList<>(); PoliticCard politicCard1 = new PoliticCard(colorList);
	 * PoliticCard politicCard2 = new PoliticCard(colorList); PoliticCard
	 * politicCard3 = new PoliticCard(colorList); politicCard.add(politicCard1);
	 * politicCard.add(politicCard2); politicCard.add(politicCard3);
	 * player.getPoliticCard().addAll(politicCard); GoodsBundle goodsBundle =
	 * this.market.getPlayerBundle(player);
	 * this.market.assignPoliticCardsToBundle(player, politicCard, 10);
	 * this.market.removeOnePoliticCardFromBundle(player, politicCard2);
	 * assertEquals(10, goodsBundle.getPoliticCardsPrice().getAmount());
	 * assertEquals(politicCard3, goodsBundle.getSellingPoliticCards().get(1));
	 * assertEquals(politicCard2, player.getPoliticCard().get(6)); }
	 * 
	 * @Test public void testRemoveAllPoliticCardsFromBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); List<PoliticCard> politicCard = new
	 * ArrayList<>(); PoliticCard politicCard1 = new PoliticCard(colorList);
	 * PoliticCard politicCard2 = new PoliticCard(colorList); PoliticCard
	 * politicCard3 = new PoliticCard(colorList); politicCard.add(politicCard1);
	 * politicCard.add(politicCard2); politicCard.add(politicCard3);
	 * player.getPoliticCard().addAll(politicCard); GoodsBundle goodsBundle =
	 * this.market.getPlayerBundle(player);
	 * this.market.assignPoliticCardsToBundle(player, politicCard, 10);
	 * this.market.removePoliticCardsFromBundle(player); assertEquals(0,
	 * goodsBundle.getPoliticCardsPrice().getAmount()); assertEquals(true,
	 * goodsBundle.getSellingPoliticCards().isEmpty());
	 * assertEquals(politicCard2, player.getPoliticCard().get(7)); }
	 * 
	 * @Test public void testAddAssistantsToBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); player.getAssistants().increaseAmount(7);
	 * GoodsBundle goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignAssistantsToBundle(player, 7, 10); assertEquals(10,
	 * goodsBundle.getAssistantsPrice().getAmount()); assertEquals(7,
	 * goodsBundle.getSellingAssistants().getAmount()); assertEquals(3,
	 * player.getAssistants().getAmount()); }
	 * 
	 * @Test public void testRemoveAssistantsFromBundle() throws
	 * IllegalActionException, NegativeException { Player player =
	 * this.allPlayers.get(0); player.getAssistants().increaseAmount(7);
	 * GoodsBundle goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignAssistantsToBundle(player, 7, 10);
	 * this.market.removeAssistantsFromBundle(player); assertEquals(0,
	 * goodsBundle.getAssistantsPrice().getAmount()); assertEquals(0,
	 * goodsBundle.getSellingAssistants().getAmount()); assertEquals(10,
	 * player.getAssistants().getAmount()); }
	 * 
	 * @Test public void testClearAllBundles() throws NegativeException,
	 * IllegalActionException { Player player = this.allPlayers.get(0);
	 * List<PoliticCard> politicCard = new ArrayList<>(); PoliticCard
	 * politicCard1 = new PoliticCard(colorList); PoliticCard politicCard2 = new
	 * PoliticCard(colorList); PoliticCard politicCard3 = new
	 * PoliticCard(colorList); politicCard.add(politicCard1);
	 * politicCard.add(politicCard2); politicCard.add(politicCard3);
	 * player.getPoliticCard().addAll(politicCard);
	 * player.getAssistants().increaseAmount(7); List<PermissionCard>
	 * permissionCard = new ArrayList<>(); PermissionCard permissionCard1 = new
	 * PermissionCard(null, null); PermissionCard permissionCard2 = new
	 * PermissionCard(null, null); PermissionCard permissionCard3 = new
	 * PermissionCard(null, null); permissionCard.add(permissionCard1);
	 * permissionCard.add(permissionCard2); permissionCard.add(permissionCard3);
	 * player.getPermissionCard().addAll(permissionCard); GoodsBundle
	 * goodsBundle = this.market.getPlayerBundle(player);
	 * this.market.assignAssistantsToBundle(player, 7, 10);
	 * this.market.assignPoliticCardsToBundle(player, politicCard, 10);
	 * this.market.assignPermissionCardsToBundle(player, permissionCard, 10);
	 * this.market.giveBackUnsoldGoods(); assertEquals(0,
	 * goodsBundle.getAssistantsPrice().getAmount()); assertEquals(10,
	 * player.getAssistants().getAmount()); assertEquals(0,
	 * goodsBundle.getPoliticCardsPrice().getAmount()); assertEquals(true,
	 * goodsBundle.getSellingPoliticCards().isEmpty());
	 * assertEquals(politicCard2, player.getPoliticCard().get(7));
	 * assertEquals(0, goodsBundle.getPermissionCardsPrice().getAmount());
	 * assertEquals(true, goodsBundle.getSellingPermissionCards().isEmpty());
	 * assertEquals(permissionCard2, player.getPermissionCard().get(1)); }
	 */
}
