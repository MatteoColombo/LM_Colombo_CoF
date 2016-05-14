package game.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reward {	

	private ArrayList<Bonus> bonusList = new ArrayList<>();

	public Reward(List<Bonus> bonusList) {
		this.bonusList = (ArrayList<Bonus>) bonusList;
	}
	
	public Reward(Bonus singleBonus)  {
		this.bonusList.add(singleBonus);
	}
	
	public Reward(Bonus[] availableBonus, int differentBonus, int value) {
		
		if(differentBonus > availableBonus.length) {
			throw new IllegalArgumentException();
		}
		FlagTable flagTable = new FlagTable(availableBonus.length);
		Random r = new Random();		
		int bonusToInsert = differentBonus;
		
		while(bonusToInsert > 0) {
			
			int indexBonus = r.nextInt(availableBonus.length);
			while(flagTable.isFlagged(indexBonus)) {
				indexBonus = r.nextInt(availableBonus.length);
			}
			// the following statement generate a balanced amount for the choosen bonus
			// TODO balancement not tested yet
			int amount = (r.nextInt(value) / (availableBonus[indexBonus].getValue() * differentBonus)) +1;
			Bonus buffer = availableBonus[indexBonus].newCopy(amount);
			bonusList.add(buffer);
			flagTable.flag(indexBonus);
			bonusToInsert--;
		}
	}
}
