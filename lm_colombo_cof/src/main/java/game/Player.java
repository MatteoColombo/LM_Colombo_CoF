package game;

import java.util.ArrayList;

public class Player {
	private Coins coins;
	private Assistants assistants;
	private VictoryPoints victoryPoints;
	private NoblePoints noblePoints;
	private ArrayList<PoliticCard> politicCard;
	private ArrayList<PermissionCard> permissionCard;
	private ArrayList<Emporium> emporium;

	public Player(int money, int helper, int draw, int maxEmp) {
		this.coins = new Coins(money);
		this.assistants = new Assistants(helper);
		this.victoryPoints = new VictoryPoints(0);
		this.noblePoints = new NoblePoints(0);
		this.politicCard = new ArrayList<PoliticCard>();
		this.permissionCard = new ArrayList<PermissionCard>();
		this.emporium = new ArrayList<Emporium>();
		for (int i = 0; i < draw; i++)
			politicCard.add(new PoliticCard());
		for (int i = 0; i < maxEmp; i++)
			emporium.add(new Emporium(this));
	}

	public int getCoins() {
		return this.coins.getAmount();
	}

	public int getAssistants() {
		return this.assistants.getAmount();
	}

	public int getVictoryPoints() {
		return this.victoryPoints.getAmount();
	}

	public int getNoblePoints() {
		return this.noblePoints.getAmount();
	}
}