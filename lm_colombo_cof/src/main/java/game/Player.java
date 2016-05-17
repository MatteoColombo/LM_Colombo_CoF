package game;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private Coins coins;
	private Assistants assistants;
	private VictoryPoints victoryPoints;
	private NoblePoints noblePoints;
	private ArrayList<PoliticCard> politicCard;
	private ArrayList<PermissionCard> permissionCard;
	private ArrayList<Emporium> emporium;

	public Player(int money, int helper, int draw, int maxEmp, ArrayList<Color> pickedColours) {
		this.coins = new Coins(money);
		this.assistants = new Assistants(helper);
		this.victoryPoints = new VictoryPoints(0);
		this.noblePoints = new NoblePoints(0);
		this.politicCard = new ArrayList<PoliticCard>();
		this.permissionCard = new ArrayList<PermissionCard>();
		this.emporium = new ArrayList<Emporium>();
		for (int i = 0; i < draw; i++)
			politicCard.add(new PoliticCard(pickedColours));
		for (int i = 0; i < maxEmp; i++)
			emporium.add(new Emporium(this));
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

	public ArrayList<PoliticCard> getPoliticCard() {
		return this.politicCard;
	}

	public ArrayList<PermissionCard> getPermissionCard() {
		return this.permissionCard;
	}

	public ArrayList<Emporium> getEmporium() {
		return this.emporium;
	}

}