package lm_20;

import static org.junit.Assert.*;

import org.junit.Test;

import model.player.Coins;

public class TestCoins {

	@Test
	public void test() {
		Coins coin= new Coins(10);
		coin.decreaseAmount(5);
		coin.increaseAmount(4);
		assertEquals(coin.getAmount(), 9);
	}

}
