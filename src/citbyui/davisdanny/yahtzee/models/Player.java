package citbyui.davisdanny.yahtzee.models;

import java.util.HashMap;

public abstract class Player {

	String name;
	ScoreCard score;

	public Player(String name) {
		setName(name);
		score = new ScoreCard(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract boolean keepRolling(int[] dice);

	public abstract int[] chooseDiceToKeep(int[] dice);
	
	public abstract String chooseScore(HashMap<String,Integer> choices,int[] dice);
	
	public ScoreCard getScore(){
		return score;
	}

}
