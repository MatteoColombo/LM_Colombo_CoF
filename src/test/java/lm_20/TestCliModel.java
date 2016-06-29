package lm_20;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import client.cli.model.CliBonus;
import client.cli.model.CliCity;
import client.cli.model.CliNobility;
import client.cli.model.CliPermission;
import client.cli.model.CliPlayer;
import client.cli.model.Game;
import client.cli.view.Cli;
import server.model.board.Board;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.board.council.Councilor;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.XMLFileException;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.reward.Reward;

public class TestCliModel {
	private Game model;
	
	/**
	 * Tests the client CliGame and the CliRegion, not all tests are possible due to the printwriters
	 * @throws ConfigurationErrorException
	 * @throws XMLFileException
	 */
	@Test
	public void testCliModel() throws ConfigurationErrorException, XMLFileException{
		Configuration config= new Configuration();
		Cli cli= new Cli(config);
		model= new Game(cli);
		model.setConfiguration(config);
		model.initMap(1);
		List<Councilor> councMembers= new ArrayList<>();
		councMembers.add(new Councilor(config.getColorsList().get(0)));
		councMembers.add(new Councilor(config.getColorsList().get(2)));
		councMembers.add(new Councilor(config.getColorsList().get(0)));
		councMembers.add(new Councilor(config.getColorsList().get(1)));
		Council council= new Council(councMembers);
		model.setCouncil(council, -1);
		model.setCouncil(council, 1);
		model.setCouncil(council,0);
		model.setCouncil(council, 2);
		assertEquals(config.getColorsTranslationReverse().get(config.getColorsList().get(0)), model.getKingCouncil().get(0));
		assertEquals(config.getColorsTranslationReverse().get(config.getColorsList().get(0)), model.getRegions().get(1).getCouncil().get(0));
		assertEquals(1,model.getRegions().get(1).getRegionIndex());
		assertEquals(5, model.getRegions().get(0).getCities().size());
		assertEquals(config,model.getConfiguration());
		assertEquals(0,model.getMyIndex());
		Board b= new Board(config, 1);
		PermissionCard pc= new PermissionCard(b.getRegion(1).getCities());
		model.setPermission(pc, 1, 0);
		CliPermission[] perm=model.getRegions().get(1).getPermission();
		assertEquals(null, perm[1]);
		assertEquals(pc.getCardCity().size(), perm[0].getCities().size());
		List<Reward> reward= new ArrayList<>();
		for(Region r: b.getRegions()){
			for(City c: r.getCities())
				reward.add(c.getReward());
		}
		model.setBonus(reward);
		assertNotEquals(null, model.getRegions().get(2).getCities().get(4).getBonus());
		model.buildEmporium("Juvelar", "matteo");
		assertEquals(1, model.getRegions().get(1).getCities().get(2).getEmporiums().size());
		model.setKingLocation("Framek");
		for(int i=0;i<model.getRegions().size();i++)
			for(int j=0;j<config.getNumberDisclosedCards();j++){
				pc= new PermissionCard(b.getRegion(i).getCities());
				model.setPermission(pc, i, j);
			}
				
		
		model.yourTurnEnded();
		model.isYourTurn();
		Player p1= new Player(config);
		p1.setName("test1");
		Player p2= new Player(config);
		p2.setName("test2");
		List<Player> players= new ArrayList<>();
		players.add(p1);
		players.add(p2);
		model.setAllPlayers(players);
		p2.getCoins().increaseAmount(3);
		model.updatePlayer(p2, 1);
		assertEquals(1, model.getMyIndex());
		model.getRegions().get(0).setBonus(25);
		assertEquals(25, model.getRegions().get(0).getBonus());
	}
	
	
	/**
	 * This tests the CliCity and the ClyBonus
	 */
	@Test
	public void testCity(){
		List<String> connection= new ArrayList<>();
		connection.add("torino");
		connection.add("venezia");
		CliCity city= new CliCity("milano", connection, false);
		assertEquals("milano", city.getName());
		assertEquals(2,city.getConnections().size());
		assertEquals(false, city.isHasKing());
		city.setHasKing(true);
		assertEquals(true, city.isHasKing());
		city.addEmporium("matteo");
		assertEquals("matteo", city.getEmporiums().get(0));
		CliBonus bonus= new CliBonus(2, "testBonus");
		List<CliBonus> reward= new ArrayList<>();
		reward.add(bonus);
		city.setBonus(reward);
		assertEquals(1, city.getBonus().size());
		assertEquals("testBonus", city.getBonus().get(0).getName());
		assertEquals(2, city.getBonus().get(0).getValue());
	}
	
	/**
	 * This tests the CliPlayer, the CliPermission 
	 * @throws ConfigurationErrorException
	 * @throws XMLFileException
	 */
	@Test
	public void testPlayer() throws ConfigurationErrorException, XMLFileException{
		Configuration config= new Configuration();
		Player p= new Player(config);
		p.getPoliticCard().add(new PoliticCard(config.getColorsList()));
		PoliticCard c;
		do{
			c= new PoliticCard(config.getColorsList());
		}while(!c.isMultipleColor());
		p.getPoliticCard().add(c);
		p.getAssistants().increaseAmount(1);
		p.getCoins().increaseAmount(1);
		p.setName("Test");
		p.getVictoryPoints().increaseAmount(3);
		Board b= new Board(config,0);
		PermissionCard card= new PermissionCard(b.getRegion(0).getCities());
		card.setCardUsed();
		p.getPermissionCard().add(card);
		CliPlayer player= new CliPlayer(p, config);
		assertEquals("Test", player.getName());
		assertEquals(1,player.getCoins());
		assertEquals(1,player.getAssistants());
		assertEquals(0,player.getNobility());
		assertEquals(2,player.getPolitic().size());
		assertEquals("multi",player.getPolitic().get(1));
		assertEquals(3,player.getVictory());
		assertEquals(true, player.getPermission().get(0).isUsed());
		assertEquals(card.getCardCity().size(), player.getPermission().get(0).getCities().size());
		assertEquals(card.getCardReward().getGeneratedRewards().size(), player.getPermission().get(0).getReward().size());
		
	}
	
	/**
	 * Test the CliNobility
	 */
	@Test
	public void testNobility(){
		CliBonus b1= new CliBonus(1, "b1");
		CliBonus b2= new CliBonus(2, "b2");
		List<CliBonus> bonus= new ArrayList<>();
		bonus.add(b1);
		bonus.add(b2);
		CliNobility nobility= new CliNobility(bonus);
		assertEquals(1,nobility.getReward().get(0).getValue());
	}
}
