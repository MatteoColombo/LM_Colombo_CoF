package model.action;

import java.util.List;

import model.board.BoardRewardsManager;
import model.board.King;
import model.board.city.City;
import model.board.map.MapExplorer;
import model.exceptions.IllegalActionException;
import model.player.Player;
import model.player.PoliticCard;
import model.reward.BVictoryPoints;
import model.reward.Reward;

public class ABuildEmporiumWithKing extends Action {
	private Player player;
	private City chosenCity;
	private King king;
	private MapExplorer mx;
	private int priceForMovement;
	private final int PRICEPERROUTE = 2;
	private int priceForTheKingCouncil;
	private BoardRewardsManager bRewardsManager;
	private List<City> allMapCities;
	private List<PoliticCard> politicCards;

	public ABuildEmporiumWithKing(Player p, King king, City chosenCity, List<City> allMapCities, List<PoliticCard> politic,
			BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true);
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
		if (player.getCoins().getAmount() < priceForTheKingCouncil) {
			throw new IllegalActionException("you can not afford the king's council price!");
		}

		if (chosenCity.hasEmporiumOfPlayer(player)) {
			throw new IllegalActionException("you already have an emporium there");
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
		if (mp.isColorComplete(this.player, this.chosenCity.getColor(), this.allMapCities)) {
			if (!chosenCity.isCapital()) {
				BVictoryPoints playerBReward = this.bRewardsManager.getBoardColorReward(chosenCity.getColor());
				playerBReward.assignBonusTo(player);
			}
		}
		if (chosenCity.getRegion().isCompleted(this.player)) {
			BVictoryPoints playerBReward = this.bRewardsManager.getBoardRegionReward(chosenCity.getRegion());
			playerBReward.assignBonusTo(player);
		}
		// remove used cards
		List<PoliticCard> playerHand = player.getPoliticCard();
		for (PoliticCard used : politicCards) {
			playerHand.remove(used);
		}
	}

	private void assignEmporium() {
		this.chosenCity.addEmporium(player.getEmporium().remove(0));
	}

	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.chosenCity, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

	// I KNOW SONAR THIS IS DUPLICATE BUT STFU WE'LL FIX LATER
	/**
	 * Calculate the money that can be paid instead of cards
	 * 
	 * @param difference
	 *            the number of missing cards
	 * @return an integer, the price
	 */
	private int calculatePrice(int difference) {
		if (difference == 0) {
			return 0;
		}
		return difference * 3 + 1;
	}

	// ALSO THIS
	/**
	 * Calculate the extra money that have to be paid because of multiple
	 * colored cards
	 * 
	 * @return an integer, the extra price
	 */
	private int calculatePriceMultipleColoredCards() {
		int multipCards = 0;
		for (PoliticCard card : politicCards)
			if (card.isMultipleColor())
				multipCards++;
		return multipCards;
	}
}
