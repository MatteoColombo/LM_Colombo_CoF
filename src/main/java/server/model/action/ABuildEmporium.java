package server.model.action;

import java.util.List;

import java.awt.Color;

import server.model.board.BoardRewardsManager;
import server.model.board.city.City;
import server.model.board.map.MapExplorer;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardReward;
import server.model.reward.Reward;

/**
 * An Action that is used by this Player to {@link #execute() build a new
 * Emporium} in a City of his choice for a valid PermissionCard; one extra
 * Assistant have to be payed for every other Emporium of the others Players
 * already in that City. The Player is also awarded with that City Rewards and
 * the ones of all the adjacent Cities where the Player has already one Emporium
 * and if he has placed an Emporium in each City of a Region or in each City of
 * the same Color, he will be also awarded with an extra BoardReward and a
 * BoardKingRewards, if they are still available.
 * <p>
 * This is an main Action.
 * 
 * @see Action
 * @see Assistants
 * @see BoardKingRewards
 * @see BoardReward
 * @see City
 * @see Color
 * @see Emporium
 * @see PermissionCard
 * @see Player
 * @see Region
 * @see Reward
 */
public class ABuildEmporium extends Action {
	private Player player;
	private PermissionCard permissionCard;
	private City chosenCity;
	private BoardRewardsManager bRewardsManager;
	private List<City> allMapCities;

	/**
	 * Checks if the {@link Player} has a valid {@link PermissionCard} to build
	 * an {@link Emporium} in the {@link City chosen City} and if he has the
	 * necessary amount of {@link Assistants} to pay if Emporiums of others
	 * Player are already present in that City; it will throw an exception if
	 * the {@link Action} conditions are not satisfied.
	 *
	 * @param p
	 *            the Player who wants to build an Emporium
	 * @param permissionCard
	 *            the PermissionCard the Player wants to use
	 * @param chosenCity
	 *            the City where the Player wants to build an Emporium
	 * @param allMapCities
	 *            all the Cities of the Game
	 * @param bRewardsManager
	 *            the manager of the BoardRewards
	 * @throws IllegalActionException
	 * @see ABuildEmporium
	 */
	public ABuildEmporium(Player p, PermissionCard permissionCard, City chosenCity, List<City> allMapCities,
			BoardRewardsManager bRewardsManager) throws IllegalActionException {
		super(true, p);
		this.player = p;
		this.permissionCard = permissionCard;
		this.chosenCity = chosenCity;
		this.allMapCities = allMapCities;
		this.bRewardsManager = bRewardsManager;
		if (!permissionCard.getCardCity().contains(chosenCity))
			throw new IllegalActionException("the chosen city is invalid");
		if (chosenCity.hasEmporiumOfPlayer(player))
			throw new IllegalActionException("you already have an emporium there");
		if ((chosenCity.getNumberOfEmporium() > 0)
				&& (player.getAssistants().getAmount() < chosenCity.getNumberOfEmporium()))
			throw new IllegalActionException("you can not afford it!");
		if (this.player.getEmporium().isEmpty())
			throw new IllegalActionException("you have no emporiums left!");
		if (permissionCard.getIfCardUsed())
			throw new IllegalActionException("this card was already used!");
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see ABuildEmporium
	 */
	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(chosenCity.getNumberOfEmporium());
		permissionCard.setCardUsed();
		assignEmporium();
		assignRewards();
		this.player.doMainAction();
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

	}

	/**
	 * Assigns an {@link Emporium} of the {@link Player} to the {@link City
	 * chosen City}.
	 * 
	 * @see ABuildEmporium
	 */
	private void assignEmporium() {
		this.chosenCity.addEmporium(player.getEmporium().remove(0));
	}

	/**
	 * Assigns to the {@link Player} the {@link City chosen City} {@link Reward
	 * Rewards} and the ones of all the adjacent Cities where the Player has
	 * already one {@link Emporium}.
	 * 
	 * @see ABuildEmporium
	 */
	private void assignRewards() {
		MapExplorer explorer = new MapExplorer();
		List<Reward> rewards = explorer.getAdiacentRewards(this.chosenCity, this.player);
		for (Reward rew : rewards) {
			rew.assignBonusTo(this.player);
		}
	}

}
