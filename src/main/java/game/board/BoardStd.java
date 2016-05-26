package game.board;

import game.exceptions.MapXMLFileException;

public class BoardStd extends Board{
	public BoardStd() throws MapXMLFileException{
		super("resources/msp.xml", 20, 6, 4, ColorConstants.getCardsColors());
	}
}
