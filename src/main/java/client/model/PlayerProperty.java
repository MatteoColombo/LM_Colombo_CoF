package client.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import model.player.*;
public class PlayerProperty {
	
	private StringProperty name;
	private IntegerProperty coins;
	private IntegerProperty assistants;
	private IntegerProperty nobility;
	private IntegerProperty victory;
	private ObservableList<ObjectProperty<PoliticCard>> politicCards;
	private ObservableList<ObjectProperty<PermissionCard>> permissions;
	/**
	 * since permission card change not so often as the others,
	 * this distinction imporve performance
	 * @param player
	 * @return
	 */
	public PlayerProperty setAllButPermissions(Player player) {
		assistants.set(player.getAssistants().getAmount());
		victory.set(player.getVictoryPoints().getAmount());
		coins.set(player.getCoins().getAmount());
		nobility.set(player.getNoblePoints().getAmount());
		
		politicCards.clear();
		for(PoliticCard card: player.getPoliticCard()) {
			politicCards.add(new SimpleObjectProperty<PoliticCard>(card));
		}	
		return this;
	}
	
	public PlayerProperty setAll(Player player) {
		setAllButPermissions(player);
		setPermissions(player.getPermissionCard());
		return this;
	}

	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}

	public IntegerProperty coinsProperty() {
		return coins;
	}
	
	public int getCoins() {
		return coins.get();
	}

	public void setCoins(int coins) {
		this.coins.set(coins);
	}
	
	public IntegerProperty assistantsProperty() {
		return assistants;
	}
	
	public int getAssistants() {
		return assistants.get();
	}

	public void setAssistants(int assistants) {
		this.assistants.set(assistants);
	}
	
	public IntegerProperty victoryProperty() {
		return victory;
	}
	
	public int getVictory() {
		return victory.get();
	}

	public void setVictory(int victory) {
		this.victory.set(victory);
	}
	
	public IntegerProperty nobilityProperty() {
		return nobility;
	}
	
	public int getNobility() {
		return nobility.get();
	}

	public void setNobility(int nobility) {
		this.coins.set(nobility);
	}
	
	public void setPermissions(List<PermissionCard> cards) {
		permissions.clear();
		for(PermissionCard card: cards) {
			permissions.add(new SimpleObjectProperty<PermissionCard>(card));
		}
	}
}
