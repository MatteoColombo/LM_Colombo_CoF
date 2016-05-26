package game.board.nobility;

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
import game.exceptions.TrackXMLFileException;
import game.reward.Reward;
import game.reward.Bonus;

/**
 * This class is the one which loads the information from the xml file and
 * generates a list of rewards each element in the list can be either null or a
 * reward. Null is used when a step of the nobility track doesn't contain any
 * bonus
 * 
 * @author Matteo Colombo
 */
public class NobilityLoader {
	private final String xmlPath;
	private List<Reward> trackRewards;

	public NobilityLoader(String xmlPath) throws TrackXMLFileException {
		this.xmlPath = xmlPath;
		this.trackRewards = new ArrayList<>();
		loadXML();
	}

	/**
	 * Reads the XML file and for each step of the nobility track it calls the
	 * methods that return the corresponding reward
	 * 
	 * @throws TrackXMLFileException
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
	 * Returns a Reward which will be the list of bonuses for a step of the
	 * nobility track
	 * 
	 * @param bonusList
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
	 * Returns a single bonus of a single step, one or more bonuses composed
	 * create a reward
	 * 
	 * @param bonusAttr
	 *            receives the attributes of a bonus
	 * @return a Bonus
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
	 * It creates the object using from a string which is the bonus type and an
	 * integer which is the amount
	 * 
	 * @param bonusType
	 *            a string, it's the name that identifies the type of bonus
	 * @param value
	 *            an integer, it's the amount of the bonus (e.g. how many
	 *            victory points)
	 * @return the bonus object
	 */
	private Bonus instantiateBonus(String bonusType, int value) {
		Bonus[] allTypeBonus= Bonus.getAllBonus();
		for(int i=0; i<allTypeBonus.length;i++)
			if(bonusType.equals(allTypeBonus[i].getTagName()))
				return allTypeBonus[i].newCopy(value);
		return null;
	}

	/**
	 * Returns a list of reward. Each element of the list represent a step of
	 * the nobility track. if an element is null, the step doesn't have a reward
	 * 
	 * @return
	 */
	public List<Reward> getNobilityTrack() {
		return trackRewards;
	}
}
