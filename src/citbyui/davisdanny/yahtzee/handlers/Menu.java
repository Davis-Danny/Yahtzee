package citbyui.davisdanny.yahtzee.handlers;

import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.util.InvalidCommandException;

public class Menu implements Handler {
	SessionBean session;

	public Menu(SessionBean session) {
		this.session = session;
	}

	@Override
	public void handle(String[] args) {
		displayMenu(args[0]);
	}

	public void displayMenu(String[] args) {
		View view = session.getView();
		String[] commands = new String[args.length];
		int i = 0;
		for (String option : args) {
			String[] list = option.split(":");
			view.display((i + 1) + ": " + list[0]);
			commands[i] = list[1];
			i++;
		}
		while (true) {
			int command = view.getInputInt();
			if (command > args.length) {
				view.display(command + " is too high. Please enter a number that corrosponds to a command.");
			} else if (command <= 0) {
				view.display(
						"Numbers lower than 1 are not supported. Please enter a number that corrosponds to a command.");
			} else {
				try{
				session.getController().handleRequest(commands[command - 1]);
				break;
				}catch(InvalidCommandException e){
					System.out.println(e.getAttemptedCommand()+" is not a valid command.");
				}
			}
		}
	}

	public void displayMenu(String menuName) {
		switch (menuName.toLowerCase()) {
		case "main":
			displayMenu(new String[] { "Close:close", "start new game:game start new","continue saved game:game start saved","join game:join" });
			break;
		case "test":
			displayMenu(new String[] { "menu Main" });
			break;
		default:
			System.out.println("menu \""+menuName+"\" not found.");
		}
	}
	
}
