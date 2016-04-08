package citbyui.davisdanny.yahtzee.models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class RemotePlayer extends Player {
	
	Socket socket;

	public RemotePlayer(String name,Socket socket){
		super(name);
		this.socket = socket;
	}

	@Override
	public boolean keepRolling(int[] dice) {
		return false;
	}

	@Override
	public int[] chooseDiceToKeep(int[] dice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String chooseScore(HashMap<String, Integer> choices, int[] dice) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
