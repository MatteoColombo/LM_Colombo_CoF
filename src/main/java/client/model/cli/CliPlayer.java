package client.model.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Configuration;
import model.player.PermissionCard;
import model.player.Player;
import model.player.PoliticCard;

public class CliPlayer {
	private String name;
	private int coins;
	private int assistants;
	private int victory;
	private int nobility;
	private List<CliPermission> permissions;
	private List<String> politic;

	public CliPlayer(Player p, Configuration config) {
		this.name = p.getName();
		this.coins = p.getCoins().getAmount();
		this.assistants = p.getAssistants().getAmount();
		this.victory = p.getVictoryPoints().getAmount();
		this.nobility = p.getNoblePoints().getAmount();
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

	public String getName() {
		return name;
	}

	public int getCoins() {
		return coins;
	}

	public int getAssistants() {
		return assistants;
	}

	public int getVictory() {
		return victory;
	}

	public int getNobility() {
		return nobility;
	}

	public List<String> getPolitic() {
		return politic;
	}

	public List<CliPermission> getPermission(){
		return this.permissions;
	}
}
