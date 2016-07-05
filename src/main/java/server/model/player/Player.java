package server.model.player;

import java.util.List;

import java.awt.Color;

import server.control.connection.ClientInt;
import server.model.action.Action;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that represents the Game status of the Player.
 * <p>
 * Each of the {@link #getClientCopy() Players} that are playing in this game
 * has several parameters that are:
 * <ul>
 * <li>Its {@link #getName() name}, that can be freely {@link #setName(String)
 * set};</li>
 * <li>Its {@link #getCoins() Coins};</li>
 * <li>Its {@link #getAssistants() Assistants};</li>
 * <li>Its {@link #getNobilityPoints() NoblePoints};</li>
 * <li>Its {@link #getVictoryPoints() VictoryPoints};</li>
 * <li>Its {@link #getPoliticCard() PoliticCard}, that are individually
 * {@link #drawAPoliticCard() drawn};</li>
 * <li>Its {@link #getPermissionCard() PermissionCard};</li>
 * <li>Its {@link #getEmporium() Emporiums};</li>
 * <li>Its Actions, which are the {@link #getMainActionsLeft() Main ones}, that
 * can be {@link #increaseMainAction() increased} or {@link #doMainAction()
 * decreased}, and the {@link #getIfExtraActionDone() Extra one}, that can only
 * be {@link #doExtraAction() set as used} or not and both can be
 * {@link #actionsReset() reseted} to their default state;</li>
 * <li>Its {@link #getClient() Client};</li>
 * <li>Its {@link #getSuspended() status}, that can be
 * {@link #setSuspension(boolean) suspended} or not.</li>
 * </ul>
 * 
 * @see Action
 * @see Assistants
 * @see Coins
 * @see Emporium
 * @see NobilityTrack
 * @see NoblePoints
 * @see PermissionCard
 * @see PoliticCard
 * @see VictoryPoints
 */
public class Player implements Serializable {
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
	 * Initializes a new Player cloning it for an existing one.
	 * 
	 * @param p
	 *            the Player used to clone
	 * @see Player
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
	 * Initializes a regular Player for a Client.
	 * 
	 * @param config
	 *            the Configuration object that is needed for all the parameters
	 * @param numberOfPlayers
	 *            the number of Players already in the game before this one
	 * @param client
	 *            the ClientInt of the client which will represent this Player
	 * @param track
	 *            the NobilityTrack of the Game
	 * @see Player
	 */
	public Player(Configuration config, int numberOfPlayers, ClientInt client, NobilityTrack track) {
		this.coins = new Coins(config.getInitialPlayerMoney() + numberOfPlayers);
		this.assistants = new Assistants(config.getInitialPlayerHelpers() + numberOfPlayers);
		this.victoryPoints = new VictoryPoints(config.getInitialVictoryPoints());
		this.noblePoints = new NoblePoints(config.getInitialNobilityPoints(), this, track);
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		if (client != null)
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
	 * Creates a fake Player which is used when the Game has only two Players
	 * and so some {@link Emporium Emporiums} need to be placed in the Map.
	 * 
	 * @param config
	 *            the Configuration object that is needed for all the parameters
	 * @param track
	 *            the NobilityTrack of the Game
	 * @see Player
	 */
	public Player(Configuration config, NobilityTrack track) {
		this.coins = new Coins(0);
		this.assistants = new Assistants(0);
		this.victoryPoints = new VictoryPoints(-1);
		this.noblePoints = new NoblePoints(0, this, track);
		this.politicCard = new ArrayList<>();
		this.emporium = new ArrayList<>();
		for (int i = 0; i < config.getInitialPoliticCards(); i++)
			politicCard.add(new PoliticCard(config.getColorsList()));
		this.permissionCard = new ArrayList<>();
		for (int i = 0; i < config.getInitialEmporiums(); i++)
			emporium.add(new Emporium(this));
		this.isSuspended = true;
		this.pickedColours = config.getColorsList();
		this.mainActions = DEFAULTMAINACTION;
	}

	/**
	 * Returns a simplified copy of this Player.
	 * 
	 * @return a new Player which is a simplified version of this one
	 * @see Player
	 */
	public Player getClientCopy() {
		return new Player(this);
	}

	/**
	 * Returns the Player {@link Coins}.
	 * 
	 * @return the Player Coins
	 * @see Player
	 */
	public Coins getCoins() {
		return this.coins;
	}

	/**
	 * Returns the Player {@link Assistants}.
	 * 
	 * @return the Player Assistants
	 * @see Player
	 */
	public Assistants getAssistants() {
		return this.assistants;
	}

	/**
	 * Returns the Player {@link VictoryPoints}.
	 * 
	 * @return the Player VictoryPoints
	 * @see Player
	 */
	public VictoryPoints getVictoryPoints() {
		return this.victoryPoints;
	}

	/**
	 * Returns the Player {@link NoblePoints}.
	 * 
	 * @return the Player NoblePoints
	 * @see Player
	 */
	public NoblePoints getNobilityPoints() {
		return this.noblePoints;
	}

	/**
	 * Returns the Player {@link PoliticCard PoliticCards}.
	 * 
	 * @return the Player PoliticCards
	 * @see Player
	 */
	public List<PoliticCard> getPoliticCard() {
		return this.politicCard;
	}

	/**
	 * Returns the Player {@link PermissionCard PermissionCards}.
	 * 
	 * @return the Player PermissionCard
	 * @see Player
	 */
	public List<PermissionCard> getPermissionCard() {
		return this.permissionCard;
	}

	/**
	 * Returns the Player not yet built {@link Emporium Emporiums}.
	 * 
	 * @return the Player not yet built Emporiums
	 * @see Player
	 */
	public List<Emporium> getEmporium() {
		return this.emporium;
	}

	/**
	 * Returns the Player remaining {@link Action Main Actions}.
	 * 
	 * @return the Player remaining Main Actions
	 * @see Player
	 */
	public int getMainActionsLeft() {
		return this.mainActions;
	}

	/**
	 * Increases by one the number of the Player remaining {@link Action Main
	 * Actions}.
	 * 
	 * @see Player
	 */
	public void increaseMainAction() {
		this.mainActions++;
	}

	/**
	 * Returns if this Player still has his {@link Action Extra Action}.
	 * 
	 * @return <code>true</code> if the Extra Action has been already done;
	 *         <code>false</code> otherwise
	 * @see Player
	 */
	public boolean getIfExtraActionDone() {
		return this.extraAction;
	}

	/**
	 * Resets the Player {@link Action Extra and Main Actions}.
	 * 
	 * @see Player
	 */
	public void actionsReset() {
		this.mainActions = DEFAULTMAINACTION;
		this.extraAction = false;
	}

	/**
	 * Decreases by one the number of the Player remaining {@link Action Main
	 * Actions}.
	 * 
	 * @see Player
	 */
	public void doMainAction() {
		this.mainActions--;
	}

	/**
	 * Sets the Player {@link Action Extra Action} as done.
	 * 
	 * @see Player
	 */
	public void doExtraAction() {
		this.extraAction = true;
	}

	/**
	 * Draws a new {@link PoliticCard} and adds it to the Player hand.
	 * 
	 * @see Player
	 */
	public void drawAPoliticCard() {
		this.politicCard.add(new PoliticCard(this.pickedColours));
	}

	/**
	 * Returns the Player Client Interface.
	 * 
	 * @return the Player Client Interface
	 * @see Player
	 */
	public ClientInt getClient() {
		return this.client;
	}

	/**
	 * Sets if this Player is suspended or not.
	 * 
	 * @param suspendedStatus
	 *            <code>true</code> if this Player is suspended;
	 *            <code>false</code> otherwise
	 * @see Player
	 */
	public void setSuspension(boolean suspendedStatus) {
		this.isSuspended = suspendedStatus;
	}

	/**
	 * Return if this Player is suspended or not.
	 * 
	 * @return <code>true</code> if this Player is suspended; <code>false</code>
	 *         otherwise
	 * @see Player
	 */
	public boolean getSuspended() {
		return this.isSuspended;
	}

	/**
	 * Returns the Player name.
	 * 
	 * @return the Player name
	 * @see Player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the Player name.
	 * 
	 * @param name
	 *            the Player name
	 * @see Player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets to null the ClientInt reference
	 */
	public void removeClient(){
		this.client=null;
	}
}