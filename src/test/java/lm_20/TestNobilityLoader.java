package lm_20;

import static org.junit.Assert.*;
import org.junit.Test;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.TrackXMLFileException;

public class TestNobilityLoader {
	private NobilityLoader nl;
	@Test
	public void testNobilityLoader() throws Exception{
		nl = new NobilityLoader("src/main/resources/nobtrack.xml");
		assertEquals(21, nl.getNobilityTrack().size());
	}

	@Test(expected=TrackXMLFileException.class)
	public void testExceptionTrackXML() throws Exception{
		nl= new NobilityLoader("src/file.xml");
	}
}
