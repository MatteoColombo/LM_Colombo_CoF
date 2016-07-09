package server.control.instruction.update;

import java.util.List;
import client.model.ModelInterface;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;

/**
 * Updates the BoardRewards when changes occures
 */
public class UpdateBoardRewards implements Update {
	private static final long serialVersionUID = -7193830133821996753L;
	private List<BVictoryPoints> kingReward;
	private List<BoardColorReward> colorReward;
	private List<BoardRegionReward> regionReward;

	/**
	 * Create a new UpdateBoardRewards
	 * @param kingReward the updated list of BVictoryPoints represeinting the kingRewards
	 * @param colorReward the updated list of BoardColorReward
	 * @param regionReward the updated list of BoardRegionReward
	 */
	public UpdateBoardRewards(List<BVictoryPoints> kingReward, List<BoardColorReward> colorReward,
			List<BoardRegionReward> regionReward) {
		this.kingReward = kingReward;
		this.colorReward = colorReward;
		this.regionReward = regionReward;
	}

	@Override
	public void execute(ModelInterface model) {
		model.updateBoardReward(kingReward, colorReward, regionReward);
	}

}
