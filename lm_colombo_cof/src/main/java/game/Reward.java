package game;

import java.util.ArrayList;
import java.util.Random;

public class Reward {
	
	public static final Bonus[] allBonusType = { new BCoins(1),
												 new BAssistants(1),
												 new BNobilityPoints(1),
												 new BVictoryPoints(1),
												 new BPoliticCards(1),
												 new BExtraMainAction(1)};	

	private static FlagTable flagTable = new FlagTable(allBonusType.length);
	private ArrayList<Bonus> bonusList = new ArrayList<Bonus>();
	
	public Reward(Bonus singleBonus)  {
		this.bonusList.add(singleBonus);
	}
	
	public Reward(int differentBonus, int treshold) {
		Random r = new Random();
		
		// TODO differenciate rewardType more (if needed)
		
		int bonusToInsert = differentBonus;
		
		while(bonusToInsert > 0) {
			
			int indexBonus = r.nextInt(allBonusType.length);
			while(flagTable.isFlagged(indexBonus)) {
				indexBonus = r.nextInt(allBonusType.length);
			}
			
			Bonus buffer = getBonusFromTable(indexBonus);
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
	
	private static Bonus getBonusFromTable(int index) {
		return allBonusType[index].deepCopy();
	}
	
	private int getTotalValue() {
		int sum = 0;
		for(Bonus bonus: bonusList) {
			sum+= bonus.getValue();
		}
		return sum;
	}
}
