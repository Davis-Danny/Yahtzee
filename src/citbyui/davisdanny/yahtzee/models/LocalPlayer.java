package citbyui.davisdanny.yahtzee.models;

import java.util.HashMap;

import citbyui.davisdanny.yahtzee.main.View;

public class LocalPlayer extends Player {

	View view;

	public LocalPlayer(String name) {
		super(name);
		view = View.getView();
	}

	@Override
	public synchronized boolean keepRolling(int[] dice) {
		view.display("Here are your current dice:");
		view.displayDice(dice);
		int input = view.getInputInt("Would you like to score these dice(1), or reroll some(2)?", 1, 2);
		if (input == 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public synchronized int[] chooseDiceToKeep(int[] dice) {
		int input;
		while (true) {
			view.display("Here are your current dice:");
			view.displayDice(dice);
			input = view.getInputInt("If you want to reroll a die, enter its value. If you're done, enter 0", 0, 6);
			if (input == 0) {
				return dice;
			} else {
				for (int i = 0; i < dice.length; i++) {
					if (dice[i] == input) {
						dice[i] = 0;
						break;
					}
				}
			}
		}
	}

	@Override
	public synchronized String chooseScore(HashMap<String, Integer> choices,int[] dice) {
		view.display("Here are your current dice:");
		view.displayDice(dice);
		view.display(score, choices);
		String choice;
		while (true) {
			choice = view.prompt("Which option would you like to keep?");
			if (score.getScores().containsKey(choice)) {
				view.display("Please enter a box you haven't already filled.");
			} else if (choices.containsKey(choice)) {
				return choice;
			} else{
				view.display("Please enter a box name");
			}
		}
	}

	@Override
	public void notify(String message) {
		view.display(message);
	}
	
	

}
