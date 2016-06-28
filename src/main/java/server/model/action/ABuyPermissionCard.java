package server.model.action;

import java.util.List;

import server.model.board.Region;
import server.model.board.council.Council;
import server.model.player.Coins;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * An Action that is used by this Player to {@link #execute() buy a PermissionCard}
 * for an amount of Coins based on the number of PoliticCards used to satisfy a
 * specific Council; if multicolor PoliticCards are used, extra Coins have to be
 * payed.
 * <p>
 * This is a main Action.
 * 
 * @see Action
 * @see Coins
 * @see Council
 * @see PermissionCard
 * @see Player
 * @see PoliticCard
 */
public class ABuyPermissionCard extends Action {
	private Player player;
	private PermissionCard permCard;
	private List<PoliticCard> politicCards;
	private Region region;
	private int slot;
	private int price;

	/**
	 * Checks if the {@link Player} has enough {@link PoliticCard PoliticCards}
	 * to satisfy a {@link Council} and the consequent amount of {@link Coins}
	 * to buy a specific {@link PermissionCard}; it will throw an exception if
	 * the {@link Action} conditions are not satisfied.
	 * 
	 * @param p
	 *            the Player who wants to buy the PermissionCard
	 * @param permc
	 *            the PermissionCard this Player want to buy
	 * @param counc
	 *            the Council this Player want to satisfy
	 * @param politic
	 *            the PoliticCards this Player want to use to satisfy a Council
	 * @throws IllegalActionException
	 * @see ABuyPermissionCard
	 */
	public ABuyPermissionCard(Player p, PermissionCard permc, Council counc, List<PoliticCard> politic, Region region, int slot)
			throws IllegalActionException {
		super(true, p);
		this.politicCards = politic;
		this.region=region;
		this.slot=slot;
		this.player = p;
		this.permCard = permc;
		int difference = counc.compareCardCouncil(politicCards);
		price = calculatePrice(difference);
		price += calculatePriceMultipleColoredCards();
		if (player.getCoins().getAmount() < price) {
			throw new IllegalActionException("you can not afford it!");
		}
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see ABuyPermissionCard
	 */
	@Override
	public void execute() {
		region.givePermissionCard(slot);
		player.getCoins().decreaseAmount(price);
		player.getPermissionCard().add(permCard);
		permCard.getCardReward().assignBonusTo(player);
		player.doMainAction();
		// when the action perform, the card given by the used are destroyed
		List<PoliticCard> playerHand = player.getPoliticCard();
		for (PoliticCard used : politicCards) {
			playerHand.remove(used);
		}
	}

	/**
	 * Calculates the extra money that have to be paid because of multiple
	 * colored cards.
	 * 
	 * @return the extra price
	 * @see ABuyPermissionCard
	 */
	private int calculatePriceMultipleColoredCards() {
		int multipCards = 0;
		for (PoliticCard card : politicCards)
			if (card.isMultipleColor())
				multipCards++;
		return multipCards;
	}
	
	/**
	 * Calculates the money that can be paid instead of cards.
	 * 
	 * @param difference
	 *            the number of missing cards
	 * @return the price
	 * @see ABuyPermissionCard
	 */
	private int calculatePrice(int difference) {
		if (difference == 0) {
			return 0;
		}
		return difference * 3 + 1;
	}

	
}
