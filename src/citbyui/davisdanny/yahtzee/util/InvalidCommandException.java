package citbyui.davisdanny.yahtzee.util;

public class InvalidCommandException extends Exception {
	
	private static final long serialVersionUID = -3694699490609765143L;
	String attemptedCommand;
	
	public InvalidCommandException(String attemptedCommand){
		this.attemptedCommand = attemptedCommand;
	}
	
	public String getAttemptedCommand(){
		return attemptedCommand;
	}
}
