package server.model.player;

import java.util.List;

import java.awt.Color;

import server.control.connection.ClientInt;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * This is the class which represents the game status of the player
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
	private static final transient int DEFAULTMAINACTION = 1;
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
		this.noblePoints = new NoblePoints(p.getNobilityPoints().getAmount(), null, null);
		this.victoryPoints = new VictoryPoints(p.getVictoryPoints().getAmount());
		this.permissionCard = new ArrayList<>();
		this.mainActions = p.getMainActionsLeft();
		this.extraAction = p.getIfExtraActionDone();
		for (PermissionCard card : p.getPermissionCard()) {
			PermissionCard copyCard = new PermissionCard(card.getCardCity(), card.getCardReward());
			if (card.getIfCardUsed())
				copyCard.setCardUsed();
			this.permissionCard.add(copyCard);
		}
		this.politicCard = new ArrayList<>();
		for (PoliticCard card : p.getPoliticCard()) {
			this.politicCard.add(new PoliticCard(card.getCardColor()));
		}
	}

	/**
	 * This is the constructor that should be used when creating a regular player for a client
	 * @param config the configuration object, it's needed for the parameters
	 * @param numberOfPlayers the number of players already in the game before this one
	 * @param client the ClientInt of the client which represents this player
	 * @param track the nobility track
	 */
	public Player(Configuration config, int numberOfPlayers, ClientInt client, NobilityTrack track) {
		this.coins = new Coins(config.getInitialPlayerMoney() + numberOfPlayers);
		this.assistants = new Assistants(config.getInitialPlayerHelpers() + numberOfPlayers);
		this.victoryPoints = new VictoryPoints(config.getInitialVictoryPoints());
		this.noblePoints = new NoblePoints(config.getInitialNobilityPoints(), this, track);
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		if(client!=null)
			this.name = client.getName();
		this.permissionCard = new ArrayList<>();
		this.pickedColours = config.getColorsList();
		for (int i = 0; i < config.getInitialPoliticCards(); i++)
			politicCard.add(new PoliticCard(pickedColours));
		for (int i = 0; i < config.getInitialEmporiums(); i++)
			emporium.add(new Emporium(this));
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
		this.isSuspended = false;
		this.client = client;
	}

	/**
	 * This is used to create a fake player which is used when the game has only
	 * 2 players and we need to place some emporiums in the map
	 * 
	 * @param config
	 */
	public Player(Configuration config, NobilityTrack track) {
		this.coins = new Coins(0);
		this.assistants = new Assistants(0);
		this.victoryPoints = new VictoryPoints(0);
		this.noblePoints = new NoblePoints(0, this, track);
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		for(int i=0;i<config.getInitialPoliticCards();i++)
			politicCard.add(new PoliticCard(config.getColorsList()));
		this.permissionCard = new ArrayList<>();
		for (int i = 0; i < config.getInitialEmporiums(); i++)
			emporium.add(new Emporium(this));
		this.isSuspended = true;
		this.pickedColours=config.getColorsList();
		this.mainActions=DEFAULTMAINACTION;
	}

	

	/**
	 * 
	 * @return a new Player which is a simplified copy of this one
	 */
	public Player getClientCopy() {
		return new Player(this);
	}

	/**
	 * 
	 * @return the Coins object
	 */
	public Coins getCoins() {
		return this.coins;
	}

	/**
	 * 
	 * @return the assistants object
	 */
	public Assistants getAssistants() {
		return this.assistants;
	}

	/**
	 * 
	 * @return the victory points object
	 */
	public VictoryPoints getVictoryPoints() {
		return this.victoryPoints;
	}

	/**
	 * 
	 * @return the noble points object
	 */
	public NoblePoints getNobilityPoints() {
		return this.noblePoints;
	}

	/**
	 * 
	 * @return the list of politic cards
	 */
	public List<PoliticCard> getPoliticCard() {
		return this.politicCard;
	}

	/**
	 * 
	 * @return the list of permission cards
	 */
	public List<PermissionCard> getPermissionCard() {
		return this.permissionCard;
	}

	/**
	 * 
	 * @return the list of emporium not yet built
	 */
	public List<Emporium> getEmporium() {
		return this.emporium;
	}

	/**
	 * 
	 * @return returns the number of remaining main actions
	 */
	public int getMainActionsLeft() {
		return this.mainActions;
	}

	/**
	 * increases by one the number of main actions left
	 */
	public void increaseMainAction() {
		this.mainActions++;
	}

	/**
	 * 
	 * @return true if the extra action was done, false otherwise
	 */
	public boolean getIfExtraActionDone() {
		return this.extraAction;
	}

	/**
	 * Resets the extra and main action so that the player can start a new round
	 */
	public void actionsReset() {
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
	}

	/**
	 * Decreases by one the number of main actions left
	 */
	public void doMainAction() {
		this.mainActions--;
	}

	/**
	 * Sets as done the extra action
	 */
	public void doExtraAction() {
		this.extraAction = true;
	}

	/**
	 * 	Draws a new politic cards and adds it to the hand
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

	/**
	 * Sets the name of the player
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
}