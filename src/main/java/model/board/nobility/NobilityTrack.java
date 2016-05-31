package model.board.nobility;

import java.util.List;

import model.reward.Reward;

public class NobilityTrack {
	private List<Reward> track;

	public NobilityTrack(List<Reward> track) {
		this.track = track;
	}

	public Reward getReward(int index) {
		return track.get(index);
	}

	public int getMaxPoint() {
		return track.size() + 1;
	}
}
