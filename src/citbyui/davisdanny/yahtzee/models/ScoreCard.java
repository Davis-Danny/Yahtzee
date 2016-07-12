package citbyui.davisdanny.yahtzee.models;

import java.util.HashMap;

public class ScoreCard {
	
	Player owner;
	HashMap<String,Integer> scores;
	
	public ScoreCard(Player owner){
		this.owner = owner;
		scores = new HashMap<String,Integer>();
	}
	
	public static HashMap<String,Integer> scoreRoll(int[] dice){
		HashMap<String,Integer> scores = new HashMap<String,Integer>();
		scoreUpper(dice,scores);
		scoreLower(dice,scores);
		return scores;
	}
	
	public static HashMap<String,Integer> scoreUpper(int[] dice,HashMap<String,Integer> scores){
		for(int i = 1;i<=6;i++){
			int score = 0;
			for(int die:dice){
				if(die==i){
					score += die;
				}
			}
			scores.put(Integer.toString(i), score);
		}
		return scores;
	}
	
	public static HashMap<String,Integer> scoreLower(int[] dice,HashMap<String,Integer> scores){
		
		//3 and 4 of a kind
		scores.put("3 of a kind", 0);
		scores.put("4 of a kind", 0);
		for(int i = 1;i<=6;i++){
			int count = count(i,dice);
			if(count >=3){
				scores.put("3 of a kind", total(dice));
			}
			if(count >=4){
				scores.put("4 of a kind", total(dice));
			}
		}
		
		
		// full house
		scores.put("Full House", 0);
		for(int i = 1;i<=6;i++){
			if(count(i,dice)==3){
				for(int j = 1;j<=6;j++){
					if(count(j,dice)==2){
						scores.put("Full House", 25);
						
					}
				}
			}
		}
		
		//straights
		scores.put("Small Straight", 0);
		scores.put("Large Straight", 0);
		for(int i=1;i<=3;i++){
			if(contains(dice,i)){
				if(contains(dice,i+1)){
					if(contains(dice,i+2)){
						if(contains(dice,i+3)){
							scores.put("Small Straight", 30);
							if(contains(dice,i+4)){
								scores.put("Large Straight",40);
							}
						}
					}
				}
			}
		}
		
		//yahtzee
		scores.put("YAHTZEE", 0);
		for(int i=1;i<=6;i++){
			if(count(i,dice)==5){
				scores.put("YAHTZEE", 50);
			}
		}
		
		//chance
		scores.put("Chance", total(dice));
		
		return scores;
	}
	
	public static int count(int i,int[] dice){
		int count = 0;
		for(int die:dice){
			if(die==i){
				count++;
			}
		}
		return count;
	}
	
	public static int total(int[] dice){
		int total = 0;
		for(int die:dice){
			total += die;
		}
		return total;
	}
	
	public static boolean contains(int[] dice,int i){
		for(int die:dice){
			if(die==i){
				return true;
			}
		}
		return false;
	}

	public int getTotalScore(){
		int total = 0;
		for(int score:scores.values()){
			total += score;
		}
		return total;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public HashMap<String, Integer> getScores() {
		return scores;
	}

	public void setScores(HashMap<String, Integer> scores) {
		this.scores = scores;
	}
	
	public void addScore(String key,int value){
		scores.put(key, value);
	}
	
}
