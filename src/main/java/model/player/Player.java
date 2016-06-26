package model.player;

import java.util.List;


import java.awt.Color;

import model.Configuration;
import model.board.nobility.NobilityTrack;
import view.server.ClientInt;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Davide Cavallini
 *
 */
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2872730191434091631L;
	private Coins coins;
	private Assistants assistants;
	private VictoryPoints victoryPoints;
	private NoblePoints noblePoints;
	private List<PoliticCard> politicCard;
	private String name;
	private List<PermissionCard> permissionCard;
	private transient List<Emporium> emporium;
	private transient List<Color> pickedColours;
	private int mainActions;
	private boolean extraAction;
	private transient int DEFAULTMAINACTION;
	private transient ClientInt client;
	private boolean isSuspended;

	/**
	 * constructor for cloning an existing player
	 * 
	 * @param p
	 */
	public Player(Player p) {
		this.name = p.getName();
		this.coins = new Coins(p.getCoins().getAmount());
		this.assistants = new Assistants(p.getAssistants().getAmount());
		this.noblePoints = new NoblePoints(p.getNoblePoints().getAmount(), null, null);
		this.victoryPoints = new VictoryPoints(p.getVictoryPoints().getAmount());
		this.permissionCard= new ArrayList<>();
		this.mainActions= p.getMainActionsLeft();
		this.extraAction= p.getIfExtraActionDone();
		for(PermissionCard card: p.getPermissionCard())
			this.permissionCard.add(new PermissionCard(card.getCardCity(), card.getCardReward()));
		this.politicCard = new ArrayList<>();
		for (PoliticCard card : p.getPoliticCard()) {
			this.politicCard.add(new PoliticCard(card.getCardColor()));
		}
	}

	

	public Player(Configuration config, int numberOfPlayers, ClientInt client, NobilityTrack track) {
		this.coins = new Coins(config.getInitialPlayerMoney() + numberOfPlayers);
		this.assistants = new Assistants(config.getInitialPlayerHelpers() + numberOfPlayers);
		this.victoryPoints = new VictoryPoints(config.getInitialVictoryPoints());
		this.noblePoints = new NoblePoints(config.getInitialNobilityPoints(), this, track);
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		this.name = client.getName();
		this.permissionCard = new ArrayList<>();
		this.pickedColours = config.getColorsList();
		for (int i = 0; i < config.getInitialPoliticCards(); i++)
			politicCard.add(new PoliticCard(pickedColours));
		for (int i = 0; i < config.getInitialEmporiums(); i++)
			emporium.add(new Emporium(this));
		this.DEFAULTMAINACTION = 1;
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
		this.isSuspended = false;
		this.client = client;
	}

	

	/**
	 * This is @deprecated and used just for tests
	 * 
	 * @param money
	 * @param helper
	 * @param draw
	 * @param maxEmp
	 * @param pickedColours
	 * @param initalVictory
	 * @param initialNoble
	 */
	public Player(int money, int helper, int draw, int maxEmp, List<Color> pickedColours, int initalVictory,
			int initialNoble, NobilityTrack track, ClientInt client) {
		this.coins = new Coins(money);
		this.assistants = new Assistants(helper);
		this.victoryPoints = new VictoryPoints(initalVictory);
		this.noblePoints = new NoblePoints(initialNoble, this, track);
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
		this.client=client;
	}

	public Player getClientCopy() {
		Player newp = new Player(this);
		return newp;
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
		this.mainActions--;
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

	/**
	 * Sets if the player is suspended or not
	 * 
	 * @param suspendedStatus
	 */
	public void setSuspension(boolean suspendedStatus) {
		this.isSuspended = suspendedStatus;
	}

	/**
	 * Return if the player is suspended or not
	 * 
	 * @return return true if the player is suspended, false otherwise
	 */
	public boolean getSuspended() {
		return this.isSuspended;
	}

	/**
	 * Returns the player's name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

}