package citbyui.davisdanny.yahtzee.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Turn {

	Player player;
	int[] dice;
	Random rng;
	Player[] otherPlayers;

	@SuppressWarnings("unchecked")
	public Turn(Player player, Game game) {
		this.player = player;
		ArrayList<Player> otherPlayersList = (ArrayList<Player>) game.getPlayers().clone();
		otherPlayersList.remove(player);
		otherPlayers = (Player[]) otherPlayersList.toArray();
		dice = new int[]{0,0,0,0,0};
		rng = new Random();
	}
	
	public void take(){
		player.notify("Your turn is beginning");
		notifyOthers(player.getName()+"'s turn is beginning.");
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
		notifyOthers(player.getName()+" has scored "+choice+" for "+scores.get(choice)+" points,");
		notifyOthers("leaving them with "+player.getScore().getTotalScore()+" points total.");
		player.notify("You now have "+player.getScore().getTotalScore()+" points.");
	}
	
	public int[] roll(int[] dice){
		for(int i=0;i<dice.length;i++){
			if(dice[i]==0){
				dice[i] = rng.nextInt(5)+1;
			}
		}
		return dice;
	}
	
	private void notifyOthers(String message){
		for(Player target:otherPlayers){
			target.notify(message);
		}
	}
	
}
