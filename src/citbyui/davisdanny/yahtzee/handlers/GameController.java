package citbyui.davisdanny.yahtzee.handlers;

import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.models.Game;
import citbyui.davisdanny.yahtzee.util.Util;

public class GameController implements Handler {

	SessionBean session;

	public GameController(SessionBean session) {
		this.session = session;
	}

	@Override
	public void handle(String[] parameters) {
		switch (parameters[0]) {
		case "start":
			start(parameters[1]);
			break;
		}
	}

	public void start(String type) {
		Game game = null;
		switch (type) {
		case "new":
			game = new Game(session);
			break;
		case "saved":
			// TODO saved game logic
			Util.nyi("saved game logic");
			break;
		}

		game.begin();
	}

	public enum Message{
	};
	

}
