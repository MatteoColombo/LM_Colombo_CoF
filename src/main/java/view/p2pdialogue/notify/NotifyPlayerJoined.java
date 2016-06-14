package view.p2pdialogue.notify;

import client.view.ViewInterface;
import model.player.Player;

public class NotifyPlayerJoined implements Notify{

	private static final long serialVersionUID = 2415811892430599147L;
	private Player player;
	
	public NotifyPlayerJoined(Player p){
		this.player=player;
	}
	
	@Override
	public void execute(ViewInterface view) {
		// TODO Auto-generated method stub
		
	}

}
