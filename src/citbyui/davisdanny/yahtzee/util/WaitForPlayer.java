package citbyui.davisdanny.yahtzee.util;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.models.Game;
import citbyui.davisdanny.yahtzee.models.RemotePlayer;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;

public class WaitForPlayer {
	
	public static ServerSocket listeningSocket;
	private static boolean setup = true;;


	public static RemotePlayer getRemoteConnection(Game game) {
		if(setup){
			setup();
		}
		try {
			View view = View.getView();
			view.display("Waiting for player to join...");
			Socket playerSocket = listeningSocket.accept();
			JSONInputStream inFromClient = new JSONInputStream(playerSocket.getInputStream());
			JSONOutputStream outToClient = new JSONOutputStream(playerSocket.getOutputStream());
			while (true) {
				HashMap inMap = (HashMap) inFromClient.readObject();
				try{
					MessageBean bean = new MessageBean(inMap);
					System.out.println(bean.getData() + " has joined the game.");
					outToClient.writeObject(new MessageBean(Message.JOINCONFIRM,game.getName()));
					return new RemotePlayer(bean.getData(), playerSocket);
				} catch(BeanBuildException e) {
					System.out.println("Unknown map recieved: " + inMap);
					outToClient.writeObject(new MessageBean(Message.INVALIDMESSAGE));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static void setup(){
		try{
			listeningSocket = new ServerSocket(9292);
		}catch(Exception e){
			e.printStackTrace();
		}
		setup = false;
	}

	/*
	 * private class Listener implements Runnable{
	 * 
	 * 
	 * 
	 * public Listener(ServerSocket listeningSocket){
	 * 
	 * }
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * }
	 */

}
