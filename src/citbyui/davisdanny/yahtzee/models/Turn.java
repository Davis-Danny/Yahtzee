package citbyui.davisdanny.yahtzee.models;

import java.util.HashMap;
import java.util.Random;

import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.util.Util;

public class Turn {

	Player player;
	int[] dice;
	Random rng;

	public Turn(Player player) {
		this.player = player;
		dice = new int[]{0,0,0,0,0};
		rng = new Random();
	}
	
	public void take(){
		View.getView().display(player.getName()+"'s turn is beginning");
		
		int rolls = 1;
		while(roll(rolls)){
			rolls++;
		}
		score();
	}
	
	public boolean roll(int rollCount){
		dice = roll(dice);
		
		if(rollCount==3){
			return false;
		}else if(player.keepRolling(dice)){
			dice = player.chooseDiceToKeep(dice);
			return true;
		}else{
			return false;
		}
	}
	
	public void score(){
		HashMap<String,Integer> scores = ScoreCard.scoreRoll(dice);
		String choice = player.chooseScore(scores,dice);
		player.getScore().addScore(choice, scores.get(choice));
	}
	
	public int[] roll(int[] dice){
		for(int i=0;i<dice.length;i++){
			if(dice[i]==0){
				dice[i] = rng.nextInt(5)+1;
			}
		}
		return dice;
	}
	
}
