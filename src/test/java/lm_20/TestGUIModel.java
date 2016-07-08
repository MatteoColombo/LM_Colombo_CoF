package lm_20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

import client.gui.model.GameProperty;
import client.gui.model.SimpleCity;
import client.gui.model.SimpleMap;
import server.model.board.Board;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.board.council.Councilor;
import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.reward.Reward;
import server.model.reward.RewardCity;
import util.ColorConverter;

public class TestGUIModel {

	GameProperty game;
	Board board;
	Configuration config;
	
	@Before
	public void setUp() throws Exception {
		game = new GameProperty();
		config = new Configuration();
		game.setConfiguration(config);
		game.initMap(3);
		board = new Board(config, 3, false);
	}

	@Test
	public void testMap() {
		SimpleMap map = game.getMap();
		SimpleCity c = map.getRegions().get(1).getCities().get(3);

		assertEquals(map.kingBonus().get(), board.getBoardRewardsManager().getRemainingBoardKingRewards().get(0).getAmount());
		assertEquals(c.getName(),
					board.getRegions().get(1).getCities().get(3).getName());
		
		
		assertEquals(c.getConnections().size(), 
				board.getRegion(1).getCities().get(3).getConnectedCities().size());
		
		Reward r = new RewardCity();
		c.setBonuses(r);
		assertEquals(c.getBonuses().get(0).getName(), r.getGeneratedRewards().get(0).getTagName());
		assertEquals(map.getNobilityTrack().size(), 21);
	}
	
	@Test
	public void testSetCouncil() {
		
		assertEquals(game.getMap().getCouncilorPool().get("#ffffff").get(), 4);

		Council c = new Council(Arrays.asList(new Councilor(Color.BLACK), 
											  new Councilor(Color.RED),
											  new Councilor(Color.WHITE),
											  new Councilor(Color.BLUE)));
		
		game.setCouncil(c, -1);
		game.setCouncil(c, 0);
		assertEquals(game.getMap().getKingCouncil().colors().get(1).get(), ColorConverter.awtToWeb(Color.RED));
		assertEquals(game.getMap().getRegions().get(0).getCouncil().colors().get(2).get(), ColorConverter.awtToWeb(Color.WHITE));
	}
	
	@Test
	public void testSetPermission() {
		PermissionCard pc = new PermissionCard(board.getRegions().get(0).getCities());
		
		game.setPermission(pc, 0, 0);
		assertEquals(game.getMap().getRegions().get(0).getPermissions()[0].getBonuses().get(0).getName(), 
					 pc.getCardReward().getGeneratedRewards().get(0).getTagName());
	
	}
	
	@Test
	public void testPlayerProperty() {
		Player p1 = new Player(config,null);
		p1.setName("ciccio");

		game.setAllPlayers(Arrays.asList(p1));
		
		assertEquals(game.getMyIndex(), 0);
		
		assertEquals(p1.getVictoryPoints().getAmount(), game.getMyPlayerData().victoryProperty().get());
		assertEquals(p1.getCoins().getAmount(), game.getPlayers().get(0).coinsProperty().get());
		assertEquals(p1.getNobilityPoints().getAmount(), game.getMyPlayerData().nobilityProperty().get());
		assertEquals(p1.getAssistants().getAmount(), game.getMyPlayerData().assistantsProperty().get());

		game.isYourTurn();
		assertEquals(game.getMyPlayerData().canNotDoMainAction().get(), false);
		assertEquals(game.getMyPlayerData().canNotDoSideAction().get(), false);
		game.yourTurnEnded();
		assertEquals(game.getMyPlayerData().canNotDoMainAction().get(), true);
		assertEquals(game.getMyPlayerData().canNotDoSideAction().get(), true);
		
		game.buildEmporium("Arkon", 0);
		assertEquals(javafx.scene.paint.Color.ROYALBLUE, game.getMap().getRegions().get(0).getCities().get(0).getEmporiums().get(0));
	
		PermissionCard p = new PermissionCard(board.getRegions().get(0).getCities());
		p1.getPermissionCard().add(p);
		p1.getPoliticCard().add(new PoliticCard(Color.BLACK));
		game.updatePlayer(p1, 0);
		assertEquals(game.getMyPlayerData().getPermissions().get(0).getBonuses().get(0).getName(), 
				p1.getPermissionCard().get(0).getCardReward().getGeneratedRewards().get(0).getTagName());
	
		assertEquals(game.getMyPlayerData().getPoliticCards().size(), 7);
	}
	
	@Test
	public void testSetKingLocation() {
		game.setKingLocation("Arkon");
		assertEquals(game.getMap().getRegions().get(0).getCities().get(0).hasKing().get(), true);
	}
	
	@Test
	public void testBoardReward() {
		game.updateBoardReward(board.getBoardRewardsManager().getRemainingBoardKingRewards(), 
							board.getBoardRewardsManager().getRemainingBoardColorRewards(), 
							board.getBoardRewardsManager().getRemainingBoardRegionRewards());
		
		assertEquals(game.getMap().getColorBonuses().get("#008000").get(), 12);
		
		game.getMap().updateKingBonus(Arrays.asList());
		assertEquals(game.getMap().kingBonus().get(), 0);
	}
	
	@Test
	public void testSetCityReward() {
		List<Reward> rewards = new ArrayList<>();
		for(City c: board.getMap().getCitiesList()) {
			rewards.add(c.getReward());
		}
		
		game.setBonus(rewards);
	}
	
}
