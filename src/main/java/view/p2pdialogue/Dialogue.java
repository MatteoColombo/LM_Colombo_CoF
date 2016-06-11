package view.p2pdialogue;

import java.io.Serializable;

import client.view.ViewInterface;

@FunctionalInterface
public interface Dialogue extends Serializable{
	public void execute(ViewInterface view);
}
