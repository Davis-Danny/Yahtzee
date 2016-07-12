package citbyui.davisdanny.yahtzee.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import citbyui.davisdanny.yahtzee.models.ScoreCard;
import citbyui.davisdanny.yahtzee.util.Util;

public class View {

	Scanner scanner;
	static View view;

	public View() {
		scanner = new Scanner(System.in);
		view = this;
	}

	public int getInputInt() {

		while (true) {
			String command = scanner.nextLine();

			try {
				int intCommand = Integer.parseInt(command);
				return intCommand;
			} catch (NumberFormatException e) {
				display("Please enter a number");
			}
		}
	}

	public int getInputInt(String prompt) {
		display(prompt);
		return getInputInt();
	}

	public int getInputInt(String prompt, int min, int max) {
		int input = getInputInt(prompt);
		while (true) {
			if (input < min) {
				input = getInputInt(input + " is too low. Please enter a number between " + min + " and " + max);
			} else if (input > max) {
				input = getInputInt(input + " is too high. Please enter a number between " + min + " and " + max);
			} else {
				return input;
			}
		}
	}

	public String prompt(String message) {
		display(message);
		return scanner.nextLine();
	}

	public void display(String message) {
		System.out.println(message);
	}

	public static View getView() {
		return view;
	}

	public void displayDice(int[] dice) {
		// if (display) {
		String[] lines = { "", "", "", "", "" };
		for (int die : dice) {
			if(die == 0){
				continue;
			}
			lines[0] += " +-----+";
			if (die == 1) {
				lines[1] += " |     |";
			} else if (die < 4) {
				lines[1] += " |0    |";
			} else {
				lines[1] += " |0   0|";
			}
			if (die % 2 == 1) {
				lines[2] += " |  0  |";
			} else if (die == 6) {
				lines[2] += " |0   0|";
			} else {
				lines[2] += " |     |";
			}
			if (die == 1) {
				lines[3] += " |     |";
			} else if (die < 4) {
				lines[3] += " |    0|";
			} else {
				lines[3] += " |0   0|";
			}
			lines[4] += " +-----+";
		}
		for (String line : lines) {
			display(line);
		}
		// }
	}
	
	public void display(ScoreCard card){
		display(card,new HashMap<String,Integer>());
	}
	
	public void display(ScoreCard card,HashMap<String,Integer> scorables){
		ScoreBean bean = new ScoreBean(card.getScores(),scorables);
		display("Upper Section:");
		display("(1)Aces: "+bean.getScore("1"));
		display("(2)Twos: "+bean.getScore("2"));
		display("(3)Threes: "+bean.getScore("3"));
		display("(4)Fours"+bean.getScore("4"));
		display("(5)Fives: "+bean.getScore("5"));
		display("(6)Sixes: "+bean.getScore("6"));
		
		display("Lower Section:");
		display("3 of a kind: "+bean.getScore("3 of a kind"));
		display("4 of a kind: "+bean.getScore("4 of a kind"));
		display("Full House: "+bean.getScore("Full House"));
		display("Small Straight: "+bean.getScore("Small Straight"));
		display("Large Straight: "+bean.getScore("Large Straight"));
		display("YAHTZEE: "+bean.getScore("YAHTZEE"));
		display("Chance: "+bean.getScore("Chance"));
		
	}
	
	public boolean confirm(String message) {
		System.out.println(message);
		String command = scanner.next();
		ArrayList<String> yes = new ArrayList<String>();
		ArrayList<String> no = new ArrayList<String>();
		yes.add("Yes");
		yes.add("yes");
		yes.add("y");
		yes.add("1");
		no.add("No");
		no.add("no");
		no.add("n");
		no.add("0");
		if (yes.contains(command)) {
			return true;
		} else if (no.contains(command)) {
			return false;
		} else {
			display("input not recognized, try again.");
			//UI.debug("Input: " + command);
			return confirm(message);
		}
	}
	
	private class ScoreBean{
		
		private HashMap<String,Integer> scores;
		private HashMap<String,Integer> scorables;
		
		public ScoreBean(HashMap<String,Integer> scores,HashMap<String,Integer> scorables){
			this.scores = scores;
			this.scorables = scorables;
		}
		
		public String getScore(String key){
			if(scores.containsKey(key)){
				return "" + scores.get(key);
			}else if(scorables.containsKey(key)){
				return "(" + scorables.get(key) + ")";
			}else{
				return ":(";
			}
		}
	}

}
