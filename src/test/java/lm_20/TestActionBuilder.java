package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import model.board.nobility.NobilityLoader;
import model.board.nobility.NobilityTrack;
import model.exceptions.IllegalActionException;
import model.player.Player;
import model.player.PoliticCard;

import org.junit.Before;
import org.junit.Test;

import control.ActionBuilder;
import control.CliParser;
import model.Configuration;
import model.action.Action;
import model.board.Board;
/**
 * 
 * @author gianpaolobranca
 *
 */
public class TestActionBuilder {
	Board board;
	Player player;
	CliParser parser = new CliParser();
	Configuration config;
	
	@Before
	public void setUp() throws Exception {
		config= new Configuration();
		board = new Board(config, 0);
		NobilityTrack track= new NobilityTrack(new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.PINK);
		colors.add(Color.ORANGE);
		player = new Player(10, 4, 0, 10, colors, 25, 3,track,null);
	}

	@Test
	public void testMakeABuildEmporium() throws Exception {
		ActionBuilder builder = new ActionBuilder(board,config);
		player.getPermissionCard().add(board.getRegion(0).givePermissionCard(0));
		String cityname = player.getPermissionCard().get(0).getCardCity().get(0).getName();
		String input = "emporium -city " + cityname + " -permission 1";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporium(player, parsed);
		a.execute();
		// an emporium has been created
		assertEquals(board.getMap().getCity(cityname).getNumberOfEmporium(), 1);
		// the card has been used
		assertEquals(true, player.getPermissionCard().get(0).getIfCardUsed());
	}
	
	@Test
	// this sometimes fail if blue is not available in the pool
	public void testMakeASlideCouncil() throws Exception {
		ActionBuilder builder = new ActionBuilder(board,config);
		
		try{
		String input = "slide -council k -color blue";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeASlideCouncil(player, parsed);
		a.execute();
		assertEquals(board.getKingCouncil().getCouncilorsColor().get(3), config.getColorsTranslation().get("blue"));
		assertEquals(14,player.getCoins().getAmount());
		}catch(IllegalActionException e){
			assertEquals("there are no more councilor available of the choosen color", e.getMessage());
			assertEquals(10,player.getCoins().getAmount());
			
		}	
		
	}
	
	@Test
	public void testMakeABuildEmporiumWithKing() throws Exception {
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(0)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(1)));
		player.getPoliticCard().add(new PoliticCard(board.getKingCouncil().getCouncilorsColor().get(2)));
		ActionBuilder builder = new ActionBuilder(board,config);
		String input = "king -city Juvelar -cards 1 2 3";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeABuildEmporiumWithKing(player, parsed);
		a.execute();
		assertEquals(player.getCoins().getAmount(), 6);
		assertEquals(board.getMap().getCity("Juvelar").getNumberOfEmporium(), 1);
	}
	
	@Test
	public void testMakeAShufflePermissionCard() throws Exception {
		ActionBuilder builder = new ActionBuilder(board,config);
		String input = "shuffle -region 2";
		CommandLine parsed = parser.computeRequest(input.split(" "));
		Action a = builder.makeAShufflePermissionCards(player, parsed);
		a.execute();
		// the execution is already working and tested
	}

}
