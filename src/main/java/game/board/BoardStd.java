package game.board;

import game.exceptions.XMLFileException;

public class BoardStd extends Board{
	public BoardStd() throws XMLFileException{
		super("src/main/resources/map.xml", "src/main/resources/nob.xml", 6, 4, ColorConstants.getCardsColors());
	}
}
