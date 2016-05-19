package game.player;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Player {
	private Coins coins;
	private Assistants assistants;
	private VictoryPoints victoryPoints;
	private NoblePoints noblePoints;
	private List<PoliticCard> politicCard;
	private List<PermissionCard> permissionCard;
	private List<Emporium> emporium;
	private List<Color> pickedColours;
	private int mainActions;
	private boolean extraAction;
	private final int DEFAULTMAINACTION;

	public Player(int money, int helper, int draw, int maxEmp, List<Color> pickedColours, int initalVictory,
			int initialNoble) {
		this.coins = new Coins(money);
		this.assistants = new Assistants(helper);
		this.victoryPoints = new VictoryPoints(initalVictory);
		this.noblePoints = new NoblePoints(initialNoble);
		this.politicCard = new ArrayList<>();
		this.permissionCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		for (int i = 0; i < draw; i++)
			politicCard.add(new PoliticCard(pickedColours));
		for (int i = 0; i < maxEmp; i++)
			emporium.add(new Emporium(this));
		this.DEFAULTMAINACTION = 1;
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = true;
		this.pickedColours = pickedColours;

	}

	public Coins getCoins() {
		return this.coins;
	}

	public Assistants getAssistants() {
		return this.assistants;
	}

	public VictoryPoints getVictoryPoints() {
		return this.victoryPoints;
	}

	public NoblePoints getNoblePoints() {
		return this.noblePoints;
	}

	public List<PoliticCard> getPoliticCard() {
		return this.politicCard;
	}

	public List<PermissionCard> getPermissionCard() {
		return this.permissionCard;
	}

	public List<Emporium> getEmporium() {
		return this.emporium;
	}

	public int getMainActionsLeft() {
		return this.mainActions;
	}

	public void increseMainAction() {
		this.mainActions++;
	}

	public boolean getIfExtraActionDone() {
		return this.extraAction;
	}

	public void actionsReset() {
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
	}

	public void doMainAction(){
		this.mainActions-=1;
	}
	
	public void doExtraAction(){
		this.extraAction=true;
	}
	
	public void drawAPoliticCard() {
		this.politicCard.add(new PoliticCard(this.pickedColours));
	}

}