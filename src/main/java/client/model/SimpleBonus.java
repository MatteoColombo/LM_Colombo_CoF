package client.model;

import java.util.HashMap;
import java.util.Map;

import model.reward.Bonus;

public class SimpleBonus {
	
	public static Map<String, String> bonusImages;
	static {
		bonusImages = new HashMap<>();
		bonusImages.put("assistants", "/simboli/Aiutanti.png"); 
		bonusImages.put("coins", "/simboli/RicchezzaGialla.png");
		bonusImages.put("extra", "/simboli/AzionePrincipale.png");
		bonusImages.put("nobility", "/simboli/Nobilta.png");
		bonusImages.put("victory", "/simboli/Vittoria.png"); 
		bonusImages.put("politic", "/simboli/PescaPolitica.png"); 
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
}
