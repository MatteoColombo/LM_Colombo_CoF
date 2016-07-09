package server.model.action;

import java.util.List;

import java.awt.Color;

import server.model.board.BoardRewardsManager;
import server.model.board.King;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.board.map.MapExplorer;
import server.model.player.Assistants;
import server.model.player.Coins;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardReward;
import server.model.reward.Reward;

/**
 * An Action that is used by a Player to build a new Emporium in a City of his
 * choice using the King for an amount of Coins based on the number of
 * PoliticCards used to satisfy the King Council and for an other amount of
 * Coins calculated from the number of adjacent Cities the King has to move
 * through to arrive at the chosen City; if multicolor PoliticCards are used,
 * extra Coins have to be payed and one extra Assistant have to be payed for
 * every other Emporium of the others Players already in that City. The Player
 * is also awarded with that City Rewards and the ones of all the adjacent
 * Cities where the Player has already one Emporium and if he has placed an
 * Emporium in each City of a Region or in each City of the same Color, he will
 * be also awarded with an extra BoardReward and a BoardKingRewards, if they are
 * still available.
 * <p>
 * This is a MainAction.
 * 
 * @see Action
 * @see Assistants
 * @see BoardKingRewards
 * @see BoardReward
 * @see City
 * @see Coins
 * @see Color
 * @see Council
 * @see Emporium
 * @see King
 * @see PermissionCard
 * @see Player
 * @see PoliticCards
 * @see Region
 * @see Reward
 */
public class ABuildEmporiumWithKing extends Action {
	private Player player;
	private City chosenCity;
	private King king;
	private MapExplorer mx;
	private int priceForMovement;
	private static final int PRICEPERROUTE = 2;
	private int priceForTheKingCouncil;
	private BoardRewardsManager bRewardsManager;
	private List<City> allMapCities;
	private List<PoliticCard> politicCards;

	/**
	 * Checks if the {@link Player} has enough {@link PoliticCard PoliticCards}
	 * to satisfy the {@link Council King Council} and the consequent amount of
	 * {@link Coins} together with an other amount of Coins calculated from the
	 * number of adjacent {@link City Cities} the {@link King} has to move
	 * through to arrive at the chosen City to be able to build here an
	 * {@link Emporium} and also if he has the necessary amount of
	 * {@link Assistants} to pay if Emporiums of others Player are already
	 * present in that City; it will throw an exception if the {@link Action}
	 * conditions are not satisfied.
	 * 
	 * @param p
	 *            the Player who wants to build an Emporium
	 * @param king
	 *            the King of the Game with his own Council
	 * @param chosenCity
	 *            the City where the Player wants to build an Emporium
	 * @param allMapCities
	 *            all the Cities of the Game
	 * @param politic
	 *            the PoliticCards this Player want to use to satisfy the King
	 *            Council
	 * @param bRewardsManager
	 *            the manager of the Board Rewards
	 * @throws IllegalActionException
	 * @see ABuildEmporiumWithKing
	 */
	public ABuildEmporiumWithKing(Player p, King king, City chosenCity, List<City> allMapCities,
			List<PoliticCard> politic, BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true, p);
		this.king = king;
		this.player = p;
		this.chosenCity = chosenCity;
		this.allMapCities = allMapCities;
		this.politicCards = politic;
		this.mx = new MapExplorer();
		this.bRewardsManager = bRewardsManager;

		int difference = king.getKingCouncil().compareCardCouncil(politicCards);
		priceForTheKingCouncil = calculatePrice(difference);
		priceForTheKingCouncil += calculatePriceMultipleColoredCards();

		if (chosenCity.hasEmporiumOfPlayer(player) || this.player.getEmporium().isEmpty()) {
			throw new IllegalActionException("you can't build an emporium here!");
		}
		if (player.getCoins().getAmount() < priceForTheKingCouncil) {
			throw new IllegalActionException("you can not afford the king's council price!");
		}

		if ((chosenCity.getNumberOfEmporium() > 0)
				&& (player.getAssistants().getAmount() < (chosenCity.getNumberOfEmporium()))) {
			throw new IllegalActionException("you can not afford the building, you need more assistants!");
		}
		this.priceForMovement = PRICEPERROUTE * mx.getDistance(king.getKingLocation(), chosenCity);
		if ((priceForMovement > 0) && (player.getCoins().getAmount() < priceForMovement + priceForTheKingCouncil)) {
			throw new IllegalActionException("you can not pay the king's travel!");
		}

	}

	@Override
	public void execute() {
		king.moveKing(chosenCity);
		player.getAssistants().decreaseAmount(chosenCity.getNumberOfEmporium());
		player.getCoins().decreaseAmount(priceForMovement + priceForTheKingCouncil);
		this.player.doMainAction();
		assignEmporium();
		assignRewards();
		MapExplorer mp = new MapExplorer();
		if (mp.isColorComplete(this.player, this.chosenCity.getColor(), this.allMapCities)
				&& !this.chosenCity.isCapital()) {
			BVictoryPoints playerBReward = this.bRewardsManager.assingBoardColorReward(this.chosenCity.getColor());
			playerBReward.assignBonusTo(this.player);
		}
		if (this.chosenCity.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.assingBoardRegionReward(this.chosenCity.getRegion());
			playerBReward.assignBonusTo(this.player);
		}
		// remove used cards
		List<PoliticCard> playerHand = player.getPoliticCard();
		for (PoliticCard used : politicCards) {
			playerHand.remove(used);
		}
	}

	/**
	 * Assigns an {@link Emporium} of the {@link Player} to the {@link City
	 * chosen City}.
	 * 
	 * @see ABuildEmporiumWithKing
	 */
	private void assignEmporium() {
		this.chosenCity.addEmporium(player.getEmporium().remove(0));
	}

	/**
	 * Assigns to the {@link Player} the {@link City chosen City} {@link Reward
	 * Rewards} and the ones of all the adjacent Cities where the Player has
	 * already one {@link Emporium}.
	 * 
	 * @see ABuildEmporiumWithKing
	 */
	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.chosenCity, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

	// I KNOW SONAR THIS IS DUPLICATE BUT STFU WE'LL FIX LATER
	/**
	 * Calculates the {@link Coins} that can be paid instead of
	 * {@link PoliticCard PoliticCards}.
	 * 
	 * @param difference
	 *            the number of missing PoliticCards
	 * @return the extra Coins to be paid
	 * @see ABuildEmporiumWithKing
	 */
	private int calculatePrice(int difference) {
		if (difference == 0) {
			return 0;
		}
		return difference * 3 + 1;
	}

	// ALSO THIS
	/**
	 * Calculate the extra {@link Coins} that have to be paid because of
	 * multicolored {@link PoliticCard PoliticCards}.
	 * 
	 * @return the extra Coins to be paid
	 * @see ABuildEmporiumWithKing
	 */
	private int calculatePriceMultipleColoredCards() {
		int multipCards = 0;
		for (PoliticCard card : politicCards)
			if (card.isMultipleColor())
				multipCards++;
		return multipCards;
	}
}
