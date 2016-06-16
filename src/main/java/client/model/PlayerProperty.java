package client.model;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.player.*;
import util.ColorConverter;
public class PlayerProperty {
	
	private StringProperty name;
	private IntegerProperty coins;
	private IntegerProperty assistants;
	private IntegerProperty nobility;
	private IntegerProperty victory;
	private BooleanProperty canNotDoMainAction;
	private BooleanProperty canNotDoSideAction;
	/**
	 * web color repreentation of each politic card color
	 */
	private ObservableList<StringProperty> politicCards;
	private ObservableList<ObjectProperty<PermissionCard>> permissions;
	
	public PlayerProperty() {
		canNotDoMainAction = new SimpleBooleanProperty(true);
		canNotDoSideAction = new SimpleBooleanProperty(true);
		name = new SimpleStringProperty("");
		coins = new SimpleIntegerProperty(0);
		assistants = new SimpleIntegerProperty(0);
		nobility = new SimpleIntegerProperty(0);
		victory = new SimpleIntegerProperty(0);
		politicCards = FXCollections.observableArrayList();
		permissions = FXCollections.observableArrayList();	
	}
	
	/**
	 * since permission cards changes not so often as the others,
	 * this distinction imprve performance
	 * @param player
	 * @return
	 */
	public PlayerProperty setAllButPermissions(Player player) {
		if(player.getMainActionsLeft()>0) {
			canNotDoMainAction.set(false);
		}
		canNotDoSideAction.set(player.getIfExtraActionDone());
		name.set(player.getName());
		assistants.set(player.getAssistants().getAmount());
		victory.set(player.getVictoryPoints().getAmount());
		coins.set(player.getCoins().getAmount());
		nobility.set(player.getNoblePoints().getAmount());
		
		politicCards.clear();
		for(PoliticCard card: player.getPoliticCard()) {
			if(!card.isMultipleColor()) {
				politicCards.add(new SimpleStringProperty(ColorConverter.awtToWeb(card.getCardColor())));
			} else {
				politicCards.add(new SimpleStringProperty("multi"));
			}
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

	public BooleanProperty canNotDoMainAction() {
		return this.canNotDoMainAction;
	}
	
	public BooleanProperty canNotDoSideAction() {
		return this.canNotDoSideAction;
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
	
	public ObservableList<StringProperty> getPoliticCards() {
		return this.politicCards;
	}
	
	public ObservableList<ObjectProperty<PermissionCard>> getPermissions() {
		return this.permissions;
	}
	
	public void setPermissions(List<PermissionCard> cards) {
		permissions.clear();
		for(PermissionCard card: cards) {
			permissions.add(new SimpleObjectProperty<PermissionCard>(card));
		}
	}
}
