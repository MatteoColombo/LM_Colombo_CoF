package client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javafx.scene.paint.Color;
import model.player.*;
import util.ColorConverter;
public class PlayerProperty {
	
	private static Map<String, String> cardsImage;
	static {
		cardsImage = new HashMap<>();
		cardsImage.put("#ffffff", "/politicCards/politicWhite.png"); //white http://i.imgur.com/3IpU6RZ.png
		cardsImage.put("#000000", "/politicCards/politicBlack.png"); //black http://i.imgur.com/fx111fM.png
		cardsImage.put("#ff9900", "/politicCards/politicOrange.png"); //orange http://i.imgur.com/RkE0x6s.png
		cardsImage.put("#0066ff", "/politicCards/politicBlue.png"); //blue http://i.imgur.com/8OjyOO1.pnq
		cardsImage.put("#ff99cc", "/politicCards/politicPink.png"); //pink http://i.imgur.com/QMetxhE.png
		cardsImage.put("#cc33ff", "/politicCards/politicPurple.png"); //purple http://i.imgur.com/jcAOwVA.png
		cardsImage.put("multi", "/politicCards/politicMulti.png"); //multi http://i.imgur.com/ySc0dOQ.png
	}
	
	private StringProperty name;
	private Color color;
	
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
	private ObservableList<PermissionProperty> permissions;
	
	public PlayerProperty() {
		canNotDoMainAction = new SimpleBooleanProperty(true);
		canNotDoSideAction = new SimpleBooleanProperty(true );
		name = new SimpleStringProperty("");
		coins = new SimpleIntegerProperty(0);
		assistants = new SimpleIntegerProperty(0);
		nobility = new SimpleIntegerProperty(0);
		victory = new SimpleIntegerProperty(0);
		politicCards = FXCollections.observableArrayList();
		permissions = FXCollections.observableArrayList();	
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	/**
	 * since permission cards changes not so often as the others,
	 * this distinction improve performance
	 * @param player
	 * @return
	 */
	public PlayerProperty setAllButPermissions(Player player) {
		canNotDoMainAction.set(player.getMainActionsLeft()>0? false:true);
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

	public void setAll(Player player) {
		this.setAllButPermissions(player);
		this.setAllPermissions(player);
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
	
	public static Map<String, String> getPoliticCardsImages() {
		return cardsImage;
	}
	
	public ObservableList<PermissionProperty> getPermissions() {
		return this.permissions;
	}
	
	public void setAllPermissions(Player player) {
		for(PermissionCard pc: player.getPermissionCard()) {
			addPermission(pc);
		}
	}
	
	public void addPermission(PermissionCard p) {
		PermissionProperty card = new PermissionProperty();
		card.set(p);
		permissions.add(card);
	}
}
