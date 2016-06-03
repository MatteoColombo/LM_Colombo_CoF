package lm_20;

import static org.junit.Assert.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import control.CliParser;

public class TestCliParser {

	@Test
	public void test() throws ParseException {
		CliParser parser = new CliParser();
		String request = "permission -region 2 -permission 1 -cards 1 2 3";
		CommandLine cmd = parser.computeRequest(request.split(" "));
		assertEquals("2", cmd.getOptionValue("region"));
		assertEquals("1", cmd.getOptionValues("cards")[0]);
		assertEquals("2", cmd.getOptionValues("cards")[1]);
		assertEquals("3", cmd.getOptionValues("cards")[2]);
		assertEquals("1", cmd.getOptionValue("permission"));
	}

}
