package client.view;

public interface ViewInterface {

	public void printIllegalAction(Exception e);

	public void printAskPlayerName();

	public void showGame();

	public void showRoom();
	
	public void printMessage(String message);

	public void printAskPlayersNumber(int max);

	public void printAskWhichMapToUse();

	public void printAskWhatActionToDo();

}
