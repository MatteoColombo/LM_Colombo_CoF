package game;

import java.util.ArrayList;
import java.util.Random;

public class Reward {	

	private ArrayList<Bonus> bonusList = new ArrayList<Bonus>();

	public Reward(ArrayList<Bonus> bonusList) {
		this.bonusList = bonusList;
	}
	
	public Reward(Bonus singleBonus)  {
		this.bonusList.add(singleBonus);
	}
	
	public Reward(Bonus[] availableBonus, int differentBonus, int treshold) throws IllegalArgumentException{
		
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
			
			Bonus buffer = availableBonus[indexBonus].deepCopy();
			bonusList.add(buffer);
			flagTable.flag(indexBonus);
			bonusToInsert--;
		}
		
		int actualValue = this.getTotalValue();
		while(actualValue < treshold) {
			int bonusToIncrement = r.nextInt(differentBonus);
			try {
				bonusList.get(bonusToIncrement).increment(1);
				actualValue += bonusList.get(bonusToIncrement).getValue();
			} catch(UnsupportedOperationException e) {}
		}
		
		flagTable.unflagAll();
	}
	
	private int getTotalValue() {
		int sum = 0;
		for(Bonus bonus: bonusList) {
			sum+= bonus.getValue();
		}
		return sum;
	}
}
