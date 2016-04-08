package citbyui.davisdanny.yahtzee.handlers;

import citbyui.davisdanny.yahtzee.main.ApplicationController;
import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.util.InvalidCommandException;

public class Startup implements Handler {
	SessionBean session;

	public Startup(SessionBean session) {
		this.session = session;
	}

	public Startup() {
		session = null;
	}

	public void handle(String[] parameters) {
		if (session == null) {
			session = newSession();
		}

		// add handlers to controller
		ApplicationController controller = session.getController();
		controller.addHandler("Menu", new Menu(session));
		controller.addHandler("close", new Close());
		controller.addHandler("game", new GameController(session));
		controller.addHandler("join", new RemoteGame());

		// Open main menu
		try {
			controller.handleRequest("menu main");
		} catch (InvalidCommandException e) {
			System.out.println("ERROR: Invalid command int Startup.handle(): " + e.getAttemptedCommand());
			e.printStackTrace();
		}

	}

	public static SessionBean newSession() {
		return new SessionBean(new ApplicationController(), new View());
	}

	public SessionBean getSessionBean() {
		return session;
	}

}
