package citbyui.davisdanny.yahtzee.util;

import java.net.ServerSocket;
import java.net.Socket;
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
			BeanHandler handler = new BeanHandler(playerSocket);
			while (true) {
					MessageBean bean = handler.waitForBean();
					System.out.println(bean.getData() + " has joined the game.");
					handler.sendBean(new MessageBean(Message.JOINCONFIRM,game.getName()));
					return new RemotePlayer(bean.getData(), handler);
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

}
