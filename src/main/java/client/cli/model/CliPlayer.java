package client.cli.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * This is the simplified model of the player, it is used in the CLI
 */
public class CliPlayer {
	private String name;
	private int coins;
	private int assistants;
	private int victory;
	private int nobility;
	private List<CliPermission> permissions;
	private List<String> politic;

	/**
	 * Instantiates the simplified player
	 * @param p the player which need to be copied
	 * @param config the configuration objext, it is used for the colors
	 */
	public CliPlayer(Player p, Configuration config) {
		this.name = p.getName();
		this.coins = p.getCoins().getAmount();
		this.assistants = p.getAssistants().getAmount();
		this.victory = p.getVictoryPoints().getAmount();
		this.nobility = p.getNobility().getAmount();
		this.politic = new ArrayList<>();
		this.permissions = new ArrayList<>();
		for (PoliticCard card : p.getPoliticCard()) {
			if (card.isMultipleColor())
				this.politic.add("multi");
			else
				this.politic.add(config.getColorsTranslationReverse().get(card.getCardColor()));
		}
		for (PermissionCard perm : p.getPermissionCard()) {
			List<String> cities = perm.getCardCity().stream().map(city -> city.getName()).collect(Collectors.toList());
			List<CliBonus> reward = perm.getCardReward().getGeneratedRewards().stream()
					.map(bonus -> new CliBonus(bonus.getAmount(), bonus.getTagName())).collect(Collectors.toList());
			permissions.add(new CliPermission(cities, reward, perm.getIfCardUsed()));
		}

	}

	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the coins of the player
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * 
	 * @return returns the number of assistants
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * 
	 * @return the amount of victory points
	 */
	public int getVictory() {
		return victory;
	}

	/**
	 * 
	 * @return the amount of nobility points
	 */
	public int getNobility() {
		return nobility;
	}

	/**
	 * Each String is the english name of the colors of the cards
	 * @return a list of colors
	 */
	public List<String> getPolitic() {
		return politic;
	}

	/**
	 * A list of CliPermission cards owned by the player
	 * @return the permission cards owned by the player
	 */
	public List<CliPermission> getPermission(){
		return this.permissions;
	}
}
