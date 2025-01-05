package com.example.coinapiservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coinapiservice.model.CoinRequest;

@CrossOrigin()
@RestController
@RequestMapping("/api/coins")
public class CoinController {
	private List<Double> validDenominations = Arrays.asList(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);
	private String errorMessage;
	@PostMapping("/minimize")
    public String getMinimumCoins(@RequestBody CoinRequest request) {
        validateRequest(request);
        if(errorMessage!=null) {
        	return errorMessage;
        }
        return calculateMinimumCoins(request.getTargetAmount(), request.getCoinDenominations());
    }

    private void validateRequest(CoinRequest request) {
    	errorMessage=null;
        if (request.getTargetAmount() < 0 || request.getTargetAmount() > 10000) {
        	errorMessage="Target amount must be between 0 and 10,000.00.";
        }
        
        if(request.getCoinDenominations().size()==0) {
        	errorMessage="Coin denominations list cannot be null or empty.";
        }

        List<Double> errorDenomination=new ArrayList<Double>();
        for (Double denomination : request.getCoinDenominations()) {
            if (!validDenominations.contains(denomination)) {
            	errorDenomination.add(denomination);             
            }
        }
        if(errorDenomination.size()>0) {
        	errorMessage="Invalid coin denominations: " + errorDenomination.toString();
        }
    }

    private String calculateMinimumCoins(double targetAmount, List<Double> denominations) {
        List<Double> result = new ArrayList<>();
        List<Double> suggestedDenominations=new ArrayList<>();
        denominations = denominations.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        targetAmount = Math.round(targetAmount * 100.0) / 100.0; // Round to two decimal places

        for (double coin : denominations) {
            while (targetAmount >= coin) {
                result.add(coin);
                targetAmount = Math.round((targetAmount - coin) * 100.0) / 100.0; // Avoid floating-point issues
            }
        }
        
        if (targetAmount > 0) {
        	for(double coin:validDenominations) {
        		if(targetAmount>=coin) {
        			suggestedDenominations.add(coin);
        		}
        		else {
        			break;
        		}
        	}
            return String.format("Target amount cannot be represented with the given denominations. Suggested denominations = %s",suggestedDenominations.toString());
        }

        result = result.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        return String.format("A list of minimum number of coins = %s",result.toString());
    }
}
