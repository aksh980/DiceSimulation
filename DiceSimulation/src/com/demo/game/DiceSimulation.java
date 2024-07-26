package com.demo.game;
import java.util.*;

public class DiceSimulation {
	final static int NUM_SIMULATIONS = 10000;
    final static int NO_OF_DICES = 5;
    public static void main(String[] args) {
        
        Map<Integer, Integer> scoreCounts = new HashMap<>();

        for (int i = 0; i < NUM_SIMULATIONS; i++) {
            int totalScore = simulateGame();
            scoreCounts.put(totalScore, scoreCounts.getOrDefault(totalScore, 0) + 1);
        }

        System.out.println("Number of simulations was " + NUM_SIMULATIONS + " using "+NO_OF_DICES+" dice.");
        for (Map.Entry<Integer, Integer> entry : scoreCounts.entrySet()) {
            int totalScore = entry.getKey();
            int countOfScore = entry.getValue();
            double frequency = (double) countOfScore / NUM_SIMULATIONS;
            System.out.printf("Total %d occurs %.2f occurred %d.0 times.%n", totalScore, frequency, countOfScore);
        }
    }

    public static int simulateGame() {
        List<Integer> diceList = rollDices(NO_OF_DICES);
        int totalScore = 0;

        while (!diceList.isEmpty()) {
            if (diceList.contains(3)) {
            	diceList.removeIf(die -> die == 3);
            } else {
                int min = Collections.min(diceList);
                totalScore += min;
                diceList.remove(Integer.valueOf(min));
            }

            if (!diceList.isEmpty()) {
            	diceList = rollDices(diceList.size());
            }
        }

        return totalScore;
    }

    public static List<Integer> rollDices(int numOfDices) {
        Random rand = new Random();
        List<Integer> diceList = new ArrayList<>();
        for (int i = 0; i < numOfDices; i++) {
            diceList.add(rand.nextInt(6) + 1);
        }
        return diceList;
    }
}

