package com.example.coinapiservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.coinapiservice.controller.CoinController;
import com.example.coinapiservice.model.CoinRequest;

@SpringBootTest
class CoinApiServiceApplicationTests {

	private CoinController coinController = new CoinController();

    @Test
    public void testValidInputExample1() {
        CoinRequest request = new CoinRequest(7.03, Arrays.asList(0.01, 0.5, 1.0, 5.0, 10.0));
        String response = coinController.getMinimumCoins(request);
        assertEquals("A list of minimum number of coins = [0.01, 0.01, 0.01, 1.0, 1.0, 5.0]", response);
    }

    @Test
    public void testValidInputExample2() {
        CoinRequest request = new CoinRequest(103.0, Arrays.asList(1.0, 2.0, 50.0));
        String response = coinController.getMinimumCoins(request);
        assertEquals("A list of minimum number of coins = [1.0, 2.0, 50.0, 50.0]", response);
    }

    @Test
    public void testInvalidDenomination() {
        CoinRequest request = new CoinRequest(7.03, Arrays.asList(0.03, 0.5, 1.0, 5.0, 10.0));
        String response = coinController.getMinimumCoins(request);
        assertEquals("Invalid coin denominations: [0.03]", response);
    }

    @Test
    public void testTargetAmountOutOfRange() {
        CoinRequest request = new CoinRequest(10001.0, Arrays.asList(0.01, 0.5, 1.0));
        String response = coinController.getMinimumCoins(request);
        assertEquals("Target amount must be between 0 and 10,000.00.", response);
    }

    @Test
    public void testEmptyDenominations() {
        CoinRequest request = new CoinRequest(50.0, Arrays.asList());
        String response = coinController.getMinimumCoins(request);
        assertEquals("Coin denominations list cannot be null or empty.", response);
    }

}
