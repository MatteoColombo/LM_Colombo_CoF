package lm_20;

import static org.junit.Assert.*;

import org.junit.Test;

import game.player.Coins;

public class TestCoins {

	@Test
	public void test() {
		Coins coin= new Coins(10);
		coin.decrease(5);
		coin.increment(4);
		assertEquals(coin.getAmount(), 9);
	}

}
