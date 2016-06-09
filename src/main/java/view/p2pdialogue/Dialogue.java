package view.p2pdialogue;

import java.io.Serializable;

import view.client.ViewInterface;

public interface Dialogue extends Serializable{
	public void execute(ViewInterface view);
}
