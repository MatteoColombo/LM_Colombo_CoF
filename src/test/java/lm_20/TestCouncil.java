package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.board.council.Council;
import model.board.council.Councilor;
import model.exceptions.IllegalActionException;
import model.player.PoliticCard;

public class TestCouncil {
	List<Councilor> members;
	
	@Before
	public void setUp() throws Exception {
		members = new ArrayList<Councilor>();
		members.add(new Councilor(Color.BLACK));
		members.add(new Councilor(Color.WHITE));
		members.add(new Councilor(Color.ORANGE));
		members.add(new Councilor(Color.PINK));
	}

	@Test
	public void testGetter() {
		Council council = new Council(members);
		assertEquals(council.getHeadColor(), Color.BLACK);
		assertEquals(council.getCouncilorsColor().get(3), Color.PINK);
	}
	
	@Test
	public void testInsert() {
		Council council = new Council(members);
		Councilor member = new Councilor(Color.ORANGE);
		council.insertCouncilor(member);
		assertEquals(council.getHeadColor(), Color.WHITE);
		assertEquals(council.getCouncilorsColor().get(3), Color.ORANGE);
	}
	
	@Test
	public void testSatisfaction() throws IllegalActionException{
		Council council = new Council(members);
		List<PoliticCard> card = new ArrayList<>();
		card.add(new PoliticCard(Color.ORANGE));
		card.add(new PoliticCard(Color.PINK));
		assertEquals(2, council.compareCardCouncil(card));
	}
	
	@Test
	public void testNoCard() throws IllegalActionException{
		Council council = new Council(members);
		List<PoliticCard> empty = new ArrayList<>();
		council.compareCardCouncil(empty);
		assertEquals(4, council.compareCardCouncil(empty));

	}
	
	@Test(expected = IllegalActionException.class)
	public void testTooManyCards() throws IllegalActionException {
		Council council = new Council(members);
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.PINK);
		colors.add(Color.ORANGE);
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(colors));
		cards.add(new PoliticCard(colors));
		cards.add(new PoliticCard(colors));
		cards.add(new PoliticCard(colors));
		cards.add(new PoliticCard(colors));
		cards.add(new PoliticCard(colors));
		council.compareCardCouncil(cards);
	}
}
