package game;

import java.util.ArrayList;
import java.util.Random;

public class Reward {
	
	private static final Bonus[] allBonusType = {new BCoins(0),
												 new BHelpers(0),
												 new BNobilityPoints(0),
												 new BVictoryPoints(0),
												 new BExtraMainAction(0)};
	
	private static final int MAXTRESHOLD = 6;
	

	
	private ArrayList<Bonus> bonusList;

	public Reward(RewardType rewardType) {
		Random r = new Random();
		
		int treshold = r.nextInt(MAXTRESHOLD);
		
		while(treshold > 0) {
			
			Bonus buffer = Reward.getRandomBonus();
			
			if(buffer.isInstantiableFor(rewardType)) {
				treshold -= buffer.getValue();
				bonusList.add(buffer);
			}
		}

	}
	
	private static Bonus getRandomBonus() {
		Random r = new Random();
		int index = r.nextInt(allBonusType.length);
		return allBonusType[index];
	}
}
