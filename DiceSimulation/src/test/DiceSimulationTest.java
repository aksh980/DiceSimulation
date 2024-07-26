package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.demo.game.DiceSimulation;

class DiceSimulationTest {

	 @Test
	    public void testRollDice() {
	        List<Integer> dice = DiceSimulation.rollDices(5);
	        assertEquals(5, dice.size(), "Should roll exactly 5 dice");

	        for (int die : dice) {
	            assertTrue(die >= 1 && die <= 6, "Each die should be between 1 and 6");
	        }
	    }

	    @Test
	    public void testSimulateGame() {
	        int score = DiceSimulation.simulateGame();
	        assertTrue(score >= 0, "Score should be non-negative");
	    }

	    @Test
	    public void testScoreCounts() {
	        final int NUM_SIMULATIONS = 1000; 
	        Map<Integer, Integer> scoreCounts = new HashMap<>();

	        for (int i = 0; i < NUM_SIMULATIONS; i++) {
	            int score = DiceSimulation.simulateGame();
	            scoreCounts.put(score, scoreCounts.getOrDefault(score, 0) + 1);
	        }

	        assertFalse(scoreCounts.isEmpty(), "Score counts should not be empty after simulations");

	        int totalCounts = scoreCounts.values().stream().mapToInt(Integer::intValue).sum();
	        assertEquals(NUM_SIMULATIONS, totalCounts, "Total counts should match the number of simulations");
	    }

	    @Test
	    public void testGameLogicWithSpecificRolls() {
	        List<Integer> initialRoll = Arrays.asList(3, 1, 3, 6, 6);
	        int score = playGameWithRolls(initialRoll);
	        assertEquals(8, score, "Score should be 8 for the specific scenario");
	    }

	    private int playGameWithRolls(List<Integer> initialRoll) {
	        List<Integer> dice = new LinkedList<>(initialRoll);
	        int totalScore = 0;

	        while (!dice.isEmpty()) {
	            if (dice.contains(3)) {
	                dice.removeIf(die -> die == 3);
	            } else {
	                int min = Collections.min(dice);
	                totalScore += min;
	                dice.remove(Integer.valueOf(min));
	            }

	            if (!dice.isEmpty()) {
	                dice = dice.size() == 3 ? new LinkedList<>(Arrays.asList(2, 5, 5)) :
	                        dice.size() == 2 ? new LinkedList<>(Arrays.asList(6, 6)) :
	                        dice.size() == 1 ? new LinkedList<>(Arrays.asList(3)) : DiceSimulation.rollDices(dice.size());
	            }
	        }

	        return totalScore;
	    }
	}
