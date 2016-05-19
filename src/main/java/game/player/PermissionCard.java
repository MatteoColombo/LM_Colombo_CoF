package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.reward.*;

import game.board.exceptions.NegativeException;

public class PermissionCard {

	private List<String> cities;
	private Reward rewards;
	private boolean used;

	public PermissionCard(List<String> citiesOfRegions) { // Bonus missing
		boolean empty;
		cities = new ArrayList<>();
		do {
			for (String x : citiesOfRegions) {
				boolean i = new Random().nextBoolean();
				if (i)
					this.cities.add(x);
			}
			empty = this.cities.isEmpty();
		} while (!empty);
	}

	public List<String> getCardCity() {
		return this.cities;
	}

	public Reward getCardBonus() {
		return this.rewards;
	}

	public boolean getIfCardUsed() {
		return this.used;
	}

	public void setCardUsed() throws NegativeException {
		if (!this.used)
			this.used = true;
		else
			throw new NegativeException();
	}

}