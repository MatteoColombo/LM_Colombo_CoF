package view.p2pdialogue;

import java.util.List;

import view.client.ViewInterface;

public class DialogueAskWichMapToUse implements Dialogue {

	private static final long serialVersionUID = 3660956964273763957L;

	private List<String> maps;
	
	public DialogueAskWichMapToUse(List<String> maps) {
		this.maps=maps;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printAskWhichMapToUse(maps);
	}

}
