package view.p2pdialogue.request;

import java.util.List;

import client.view.ViewInterface;
import view.p2pdialogue.combinedrequest.CombinedDialogue;

public class RequestWichMapToUse implements Request {

	private static final long serialVersionUID = 3660956964273763957L;

	private List<String> maps;
	
	public RequestWichMapToUse(List<String> maps) {
		this.maps=maps;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printAskWhichMapToUse(maps);
	}

}
