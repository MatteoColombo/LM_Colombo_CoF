package model.player;

import java.awt.Color;
import java.util.List;

import model.Configuration;
import view.server.ClientInt;

import java.util.ArrayList;

/**
 * 
 * @author Davide Cavallini
 *
 */
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
	private ClientInt client;
	private boolean isSuspended;

	/**
	 * 
	 * @param money
	 * @param helper
	 * @param draw
	 * @param maxEmp
	 * @param pickedColours
	 * @param initalVictory
	 * @param initialNoble
	 */
	public Player(int money, int helper, int draw, int maxEmp, List<Color> pickedColours, int initialVictory,
			int initialNoble, ClientInt client) {
		this(money, helper, draw, maxEmp, pickedColours, initialVictory, initialNoble);
		this.client = client;
	}

	public Player(Configuration config, int numberOfPlayers, ClientInt client) {
		this.coins = new Coins(config.getInitialPlayerMoney() + numberOfPlayers);
		this.assistants = new Assistants(config.getInitialPlayerHelpers() + numberOfPlayers);
		this.victoryPoints = new VictoryPoints(config.getInitialVictoryPoints());
		this.noblePoints = new NoblePoints(config.getInitialNobilityPoints());
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		this.pickedColours = config.getColorsList();
		for (int i = 0; i < config.getInitialPoliticCards(); i++)
			politicCard.add(new PoliticCard(pickedColours));
		for (int i = 0; i < config.getInitialEmporiums(); i++)
			emporium.add(new Emporium(this));
		this.DEFAULTMAINACTION = 1;
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
		this.isSuspended = false;
		this.client=client;
	}

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
		this.extraAction = false;
		this.pickedColours = pickedColours;
		this.isSuspended = false;
	}

	/**
	 * 
	 * @return
	 */
	public Coins getCoins() {
		return this.coins;
	}

	/**
	 * 
	 * @return
	 */
	public Assistants getAssistants() {
		return this.assistants;
	}

	/**
	 * 
	 * @return
	 */
	public VictoryPoints getVictoryPoints() {
		return this.victoryPoints;
	}

	/**
	 * 
	 * @return
	 */
	public NoblePoints getNoblePoints() {
		return this.noblePoints;
	}

	/**
	 * 
	 * @return
	 */
	public List<PoliticCard> getPoliticCard() {
		return this.politicCard;
	}

	/**
	 * 
	 * @return
	 */
	public List<PermissionCard> getPermissionCard() {
		return this.permissionCard;
	}

	/**
	 * 
	 * @return
	 */
	public List<Emporium> getEmporium() {
		return this.emporium;
	}

	/**
	 * 
	 * @return
	 */
	public int getMainActionsLeft() {
		return this.mainActions;
	}

	/**
	 * 
	 */
	public void increaseMainAction() {
		this.mainActions++;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getIfExtraActionDone() {
		return this.extraAction;
	}

	/**
	 * 
	 */
	public void actionsReset() {
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
	}

	/**
	 * 
	 */
	public void doMainAction() {
		this.mainActions -= 1;
	}

	/**
	 * 
	 */
	public void doExtraAction() {
		this.extraAction = true;
	}

	/**
	 * 	
	 */
	public void drawAPoliticCard() {
		this.politicCard.add(new PoliticCard(this.pickedColours));
	}

	/**
	 * Returns the Client interface which is used to dialogue with the view
	 * 
	 * @return the Client Interface
	 */
	public ClientInt getClient() {
		return this.client;
	}

	public void setSuspension(boolean suspendedStatus) {
		this.isSuspended = suspendedStatus;
	}

	public boolean getSuspended() {
		return this.isSuspended;
	}

}