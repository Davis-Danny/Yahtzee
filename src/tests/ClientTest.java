package tests;

import citbyui.davisdanny.yahtzee.handlers.RemoteGame;
import citbyui.davisdanny.yahtzee.main.View;

public class ClientTest {

	public ClientTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		View view = new View();
		RemoteGame game = new RemoteGame(null);
		game.handle(args);
	}

}