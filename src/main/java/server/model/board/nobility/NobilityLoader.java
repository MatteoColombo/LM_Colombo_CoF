package server.model.board.nobility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.model.configuration.TrackXMLFileException;
import server.model.reward.Bonus;
import server.model.reward.Reward;

/**
 * A class that loads the information from the XML file and generates a list of
 * rewards of the NobilityTrack.
 * <p>
 * Each element in the list can be either a Reward or <code>null</code> if a
 * step of the {@link #getNobilityTrack() NobilityTrack} doesn't contain any
 * Bonus.
 * 
 * @author Matteo Colombo
 * @see Bonus
 * @see NobilityTrack
 * @see Reward
 */
public class NobilityLoader {
	private final String xmlPath;
	private List<Reward> trackRewards;

	/**
	 * Initializes the NobilityLoader saving this XML file and loading it.
	 * 
	 * @param xmlPath
	 *            the path of the XML file that will be saved and loaded
	 * @throws TrackXMLFileException
	 * @see NobilityLoader
	 */
	public NobilityLoader(String xmlPath) throws TrackXMLFileException {
		this.xmlPath = xmlPath;
		this.trackRewards = new ArrayList<>();
		loadXML();
	}

	/**
	 * Reads the XML file and for each step of the {@link NobilityTrack} it
	 * calls the methods that return the corresponding {@link Reward}.
	 * 
	 * @throws TrackXMLFileException
	 * @see NobilityLoader
	 */
	private void loadXML() throws TrackXMLFileException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File xml = new File(xmlPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xml);
			xmlDoc.getDocumentElement().normalize();
			Element root = xmlDoc.getDocumentElement();
			NodeList cellsList = root.getElementsByTagName("step");
			for (int i = 0; i < cellsList.getLength(); i++) {
				Node cell = cellsList.item(i);
				NodeList bonusList = cell.getChildNodes();
				getReward(bonusList);
			}
		} catch (ParserConfigurationException pec) {
			throw new TrackXMLFileException(pec);
		} catch (IOException ioe) {
			throw new TrackXMLFileException(ioe);
		} catch (SAXException saxe) {
			throw new TrackXMLFileException(saxe);
		}

	}

	/**
	 * Add a {@link Reward} for a step of the {@link NobilityTrack} which will
	 * be a list of {@link Bonus Bonuses} or <code>null</code>.
	 * 
	 * @param bonusList
	 *            the Reward that will be added to the NobilityTrack; can both
	 *            be a list of Bonus or <code>null</code>
	 * @see NobilityLoader
	 */
	private void getReward(NodeList bonusList) {
		List<Bonus> rew = new ArrayList<>();
		boolean isBonus = false;
		for (int i = 0; i < bonusList.getLength(); i++)
			if (bonusList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				isBonus = true;
				rew.add(getBonus(bonusList.item(i).getChildNodes()));
			}

		if (!isBonus)
			trackRewards.add(null);
		else
			trackRewards.add(new Reward(rew));

	}

	/**
	 * Returns a single {@link Bonus} of a single step, one or more Bonuses
	 * composed create a {@link Reward}.
	 * 
	 * @param bonusAttr
	 *            receives the attributes of a Bonus
	 * @return a Bonus
	 * @see NobilityLoader
	 */
	private Bonus getBonus(NodeList bonusAttr) {
		String bonusType = "";
		int value = 0;
		for (int i = 0; i < bonusAttr.getLength(); i++) {
			if (bonusAttr.item(i).getNodeType() == Node.ELEMENT_NODE) {
				switch (bonusAttr.item(i).getNodeName()) {
				case "name":
					bonusType = bonusAttr.item(i).getTextContent();
					break;
				case "value":
					value = Integer.parseInt(bonusAttr.item(i).getTextContent());
					break;
				default:
					break;
				}
			}

		}
		return instantiateBonus(bonusType, value);

	}

	/**
	 * It creates the object using a string which is the {@link Bonus} type and
	 * an integer which is the amount.
	 * 
	 * @param bonusType
	 *            a string that is the name that identifies the type of Bonus
	 * @param value
	 *            an integer that is the amount of the Bonus
	 * @return the Bonus object
	 * @see NobilityLoader
	 */
	private Bonus instantiateBonus(String bonusType, int value) {
		Bonus[] allTypeBonus = Bonus.getAllBonus();
		for (int i = 0; i < allTypeBonus.length; i++)
			if (bonusType.equals(allTypeBonus[i].getTagName()))
				return allTypeBonus[i].newCopy(value);
		return null;
	}

	/**
	 * Returns the list of {@link Reward Rewards}. Each element of the list
	 * represent a step of the NobilityTrack; if an element is <code>null</code>
	 * , the step doesn't have a Reward.
	 * 
	 * @return the NobilityTrack as a list of Rewards
	 * @see NobilityLoader
	 */
	public List<Reward> getNobilityTrack() {
		return trackRewards;
	}
}
