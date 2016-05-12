package game;

import java.util.ArrayList;
import java.util.Random;

public class Reward {
	
	private static final Bonus[] allBonusType = {new BCoins(1),
												 new BAssistants(1),
												 new BNobilityPoints(1),
												 new BVictoryPoints(1),
												 new BPoliticCards(1),
												 new BExtraMainAction(1),
												 new BExtraRewardFromCity(1)};
	
	private static final int MAXDIFFERENTBONUS = 3;
	private static final int MULTIPLIER = 30;

	private static FlagTable flagTable = new FlagTable(allBonusType.length);
	private ArrayList<Bonus> bonusList;

	public Reward(RewardType rewardType) {
		Random r = new Random();
		
		// TODO differenciate rewardType more (if needed)
		
		int differentBonus = r.nextInt(MAXDIFFERENTBONUS) +1;
		int bonusToInsert = differentBonus;
		int treshold = r.nextInt(differentBonus*MULTIPLIER);
		
		while(bonusToInsert > 0) {
			
			int indexBonus = r.nextInt(allBonusType.length);
			while(flagTable.isFlagged(indexBonus)) {
				indexBonus = r.nextInt(allBonusType.length);
			}
			
			Bonus buffer = getBonusFromTable(indexBonus);
			if(!(buffer.mustBeAlone && differentBonus > 1) && buffer.isInstantiableFor(rewardType)) {
				bonusList.add(buffer);
				flagTable.flag(indexBonus);
				bonusToInsert--;
			}
		}
		
		int actualValue = this.getTotalValue();
		while(actualValue < treshold) {
			int bonusToIncrement = r.nextInt(differentBonus);
			bonusList.get(bonusToIncrement).increment(1);
			actualValue += bonusList.get(bonusToIncrement).getValue();
		}
		
		flagTable.unflagAll();
	}
	
	
	// constructor for the king's bonuses that contains only victory points
	public Reward(int vp) {
		this.bonusList.add(new BVictoryPoints(vp));
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
