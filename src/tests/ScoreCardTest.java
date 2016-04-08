package tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import citbyui.davisdanny.yahtzee.models.ScoreCard;

public class ScoreCardTest {

	@Test
	public void countTest() {
		int[] dice = new int[] { 1, 1, 1, 1, 1 };
		assertEquals("All ones should return 5", 5, ScoreCard.count(1, dice));
		dice = new int[] { 1, 1, 1, 2, 2 };
		assertEquals("3 ones should return 3", 3, ScoreCard.count(1, dice));
	}

	@Test
	public void test1() {
		int[] dice = new int[] { 3, 3, 3, 2, 4 };
		HashMap<String, Integer> scores = ScoreCard.scoreRoll(dice);
		assertEquals("2s should equal 2",2,(int)scores.get("2"));
		assertEquals("3s should equal 9",9,(int)scores.get("3"));
		assertEquals("4s should equal 4",4,(int)scores.get("4"));
		
		assertEquals("3 of a kind should equal 15",15,(int)scores.get("3 of a kind"));
		assertEquals("Chance should equal 15",15,(int)scores.get("Chance"));
	}
	
	@Test
	public void test2() {
		int[] dice = new int[] { 3,3,3,4,6};
		HashMap<String, Integer> scores = ScoreCard.scoreRoll(dice);
		assertEquals("3s should equal 9",9,(int)scores.get("3"));
		
		assertEquals("3 of a kind should equal 19",19,(int)scores.get("3 of a kind"));
		assertEquals("Chance should equal 19",19,(int)scores.get("Chance"));
	}
	
	@Test
	public void straightTest(){
		int[] dice = new int[] {1,2,3,4,5};
		HashMap<String, Integer> scores = ScoreCard.scoreRoll(dice);
		
		assertEquals("Small straight should equal 30",30,(int)scores.get("Small Straight"));
		assertEquals("Large straight should equal 40",40,(int)scores.get("Large Straight"));
	}
	
	@Test
	public void fullHouseTest(){
		int[] dice = new int[] {2,2,2,3,3};
		HashMap<String, Integer> scores = ScoreCard.scoreRoll(dice);
		
		assertEquals("Full House should equal 24",25,(int)scores.get("Full House"));
	}
	
	@Test
	public void checkForEmpty(){
		int[] dice = new int[] {1,1,2,2,3};
		checkForEmpty(dice);
	}
	
	public void checkForEmpty(int[] dice){
		HashMap<String, Integer> scores = ScoreCard.scoreRoll(dice);
		checkForEmpty("1",scores);
		checkForEmpty("2",scores);
		checkForEmpty("3",scores);
		checkForEmpty("4",scores);
		checkForEmpty("5",scores);
		checkForEmpty("6",scores);
		checkForEmpty("3 of a kind",scores);
		checkForEmpty("4 of a kind",scores);
		checkForEmpty("Full House",scores);
		checkForEmpty("Small Straight",scores);
		checkForEmpty("Large Straight",scores);
		checkForEmpty("YAHTZEE",scores);
	}
	
	public void checkForEmpty(String key,HashMap<String,Integer> card){
		assertNotNull(key+" should have a value in it.",card.get(key));
	}

}
