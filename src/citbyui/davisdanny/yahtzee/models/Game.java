package citbyui.davisdanny.yahtzee.models;

import java.util.ArrayList;

import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.util.InvalidCommandException;
import citbyui.davisdanny.yahtzee.util.Util;
import citbyui.davisdanny.yahtzee.util.WaitForPlayer;

public class Game {

	private SessionBean session;
	private String name;
	private ArrayList<Player> players;
	private int roundNum;
	private int nextPlayer;

	public Game(SessionBean session) {
		this.session = session;
		View view = session.getView();
		name = view.prompt("game name:");
		setPlayers(setupPlayers());
		setRoundNum(0);
	}

	private ArrayList<Player> setupPlayers() {
		View view = session.getView();
		ArrayList<Player> players = new ArrayList<Player>();
		do {
			int type;
			boolean repeat;
			do {
				repeat = false;
				type = view.getInputInt("Where is the next player playing? This computer(1),or another computer(2)", 1,
						2);
				switch (type) {
				case 1:
					String name = view.prompt("What is their name?");
					players.add(new LocalPlayer(name));
					break;
				case 2:
					players.add(WaitForPlayer.getRemoteConnection(this));
				default:
					view.display("Please enter a valid number");
					repeat = true;
				}
			} while (repeat);
		} while (view.confirm("Would you like to add another player?"));
		return players;
	}

	public void nextTurn() {
		Player player = players.get(nextPlayer);
		Turn turn = new Turn(player, this);
		turn.take();
		nextPlayer++;
		if (nextPlayer == players.size()) {
			nextPlayer = 0;
			roundNum++;
		}
	}

	public void begin() {
		while (roundNum < 13) {
			nextTurn();
		}
		end();
		View.getView().display("Game is completed.");
		Util.nyi("Game end logic");
	}

	private void end() {
		notifyAll("All players have completed their turns!");
		notifyAll("The final scores are:");
		Player winner = players.get(0);
		for (Player player : players) {
			notifyAll(player.getName() + ": " + player.getScore().getTotalScore() + " points");
			if (player.getScore().getTotalScore() > winner.getScore().getTotalScore()) {
				winner = player;
			}
		}
		notifyAll(winner.getName() + " has won, with a score of " + winner.getScore().getTotalScore() + " points.");
		for(Player player:players){
			player.done();
		}
		try {
			session.getController().handleRequest("menu main");
		} catch (InvalidCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void notifyAll(String message) {
		for (Player player : players) {
			player.notify(message);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public void incRoundNum() {
		roundNum++;
	}

	public int getNextPlayer() {
		return nextPlayer;
	}

}
