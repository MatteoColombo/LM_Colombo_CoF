package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import server.model.board.Board;
import server.model.board.BoardRewardsManager;
import server.model.board.King;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Councilor;
import server.model.board.council.CouncilorPool;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.XMLFileException;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;

public class TestBoard {
	private Configuration config;
	private Board gameBoard;
	private List<Councilor> councilor;

	@Before
	public void setUp() throws ConfigurationErrorException {
		this.config = new Configuration();
		this.councilor = new ArrayList<>();
		councilor.add(new Councilor((Color.decode("#000000"))));
		councilor.add(new Councilor((Color.decode("#0066ff"))));
		councilor.add(new Councilor((Color.decode("#ff99cc"))));
		councilor.add(new Councilor((Color.decode("#cc33ff"))));
	}

	@Test
	public void testBoardInitialization() throws XMLFileException {
		this.gameBoard = new Board(config, 1,false );
	}

	@Test
	public void testBoardRewardsInitialization() throws XMLFileException {
		this.gameBoard = new Board(config, 1, false);
		BoardRewardsManager bRManager = this.gameBoard.getBoardRewardsManager();
		List<BoardColorReward> bColorRew = bRManager.getRemainingBoardColorRewards();
		List<BoardRegionReward> bRegionRew = bRManager.getRemainingBoardRegionRewards();
		List<BVictoryPoints> bKingRew = bRManager.getRemainingBoardKingRewards();
		assertEquals(4, bColorRew.size());
		assertEquals(3, bRegionRew.size());
		assertEquals(5, bKingRew.size());
		assertEquals(8, bColorRew.get(1).getBRBonus().getAmount());
		assertEquals(5, bRegionRew.get(0).getBRBonus().getAmount());
		assertEquals(12, bKingRew.get(2).getAmount());
		bRManager.getRemainingBoardKingRewards().clear();
		bRManager.assingBoardColorReward(Color.decode("#000000"));
		assertEquals(0,bRManager.assingBoardRegionReward(null).getAmount());
		assertEquals(5,bRManager.assingBoardRegionReward(gameBoard.getRegion(0)).getAmount());
		
	}

	@Test
	public void testBoardCouncilorPool() throws XMLFileException {
		this.gameBoard = new Board(config, 1,false);
		CouncilorPool councilorPool = this.gameBoard.getCouncilorPool();
		assertEquals(4, councilorPool.getCouncPerColor());
		assertEquals(6, councilorPool.getListColor().size());

	}

	@Test
	public void testBoardNobilityTrack() throws XMLFileException {
		this.gameBoard = new Board(config, 1, false);
		NobilityTrack nobilityTrack = this.gameBoard.getNobleTrack();
		assertEquals(1, nobilityTrack.getReward(6).getGeneratedRewards().get(0).getAmount());
		assertEquals(20, nobilityTrack.getMaxPoint());

	}

	@Test
	public void testBoardRegions() throws XMLFileException {
		this.gameBoard = new Board(config, 1, false);
		List<Region> regions = this.gameBoard.getRegions();
		assertEquals(this.gameBoard.getRegion(0), regions.get(0));
		assertEquals("Arkon", regions.get(0).getCity("Arkon").getName());
		for (Councilor councilor : this.councilor)
			regions.get(0).getCouncil().insertCouncilor(councilor);
		assertEquals(Color.decode("#000000"), this.gameBoard.getRegionCouncil(0).getHeadColor());

	}

	@Test
	public void testBoardKing() throws XMLFileException {
		this.gameBoard = new Board(config, 1, false);
		for (Councilor councilor : this.councilor)
			this.gameBoard.getKingCouncil().insertCouncilor(councilor);
		assertEquals(Color.decode("#000000"), this.gameBoard.getKingCouncil().getHeadColor());
		assertEquals("Juvelar", this.gameBoard.getKingLocation().getName());
		City city = this.gameBoard.getKingLocation().getConnectedCities().get(0);
		this.gameBoard.moveKing(city);
		assertEquals("Indur", this.gameBoard.getKingLocation().getName());
		King king = this.gameBoard.getKing();
		assertEquals(king.getKingLocation(), this.gameBoard.getKingLocation());

	}
}
