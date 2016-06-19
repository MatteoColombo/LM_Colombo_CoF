package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.board.nobility.NobilityLoader;
import model.exceptions.TrackXMLFileException;
import model.player.Player;
import model.reward.Reward;

public class TestNobilityLoader {

	@Test
	public void testNobilityLoader() throws Exception{
		NobilityLoader nl = new NobilityLoader("src/main/resources/nobtrack.xml");
	}

	@Test(expected=TrackXMLFileException.class)
	public void testExceptionTrackXML() throws Exception{
		NobilityLoader nl= new NobilityLoader("src/file.xml");
	}
}
