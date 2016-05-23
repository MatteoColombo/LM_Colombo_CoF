package game.reward;

public class FlagTable {
	private boolean[] flags;
	
	public FlagTable(int length) {
		flags = new boolean[length];
		this.unflagAll();
	}
	
	public void unflagAll() {
		for(int i = flags.length -1; i >= 0; i--) {
			unflag(i);
		}
	}
	
	public void flagAll() {
		for(int i = flags.length -1; i >= 0; i--) {
			flag(i);
		}
	}
	
	public void flag(int index) {
		flags[index] = true;
	}	
	
	public void unflag(int index) {
		flags[index] = false;
	}
	
	public boolean isFlagged(int index) {
		return flags[index];
	}
}
