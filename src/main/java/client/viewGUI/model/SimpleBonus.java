package client.viewGUI.model;

import java.util.HashMap;
import java.util.Map;

import server.model.reward.Bonus;

public class SimpleBonus {
	
	private static Map<String, String> bonusImages;
	static {
		bonusImages = new HashMap<>();
		bonusImages.put("assistants", "/simboli/assistants.png"); 
		bonusImages.put("coins", "/simboli/yellowCoin.png");
		bonusImages.put("extra", "/simboli/mainAction.png");
		bonusImages.put("nobility", "/simboli/nobility.png");
		bonusImages.put("victory", "/simboli/victory.png"); 
		bonusImages.put("politic", "/simboli/politic.png"); 
		bonusImages.put("city", "/simboli/extraCityBonus.png"); 
		bonusImages.put("fromPermit", "/simboli/extraPermissionBonus.png"); 
		bonusImages.put("takePermit", "/simboli/extraPermission.png"); 
	
	}
	
	private String name;
	private int amount;
	
	public SimpleBonus(Bonus b) {
		this.name = b.getTagName();
		this.amount = b.getAmount();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getImagePath() {
		return bonusImages.get(name);
	}
}
