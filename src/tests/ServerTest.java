package tests;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.models.RemotePlayer;
import citbyui.davisdanny.yahtzee.util.BeanHandler;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;

public class ServerTest {

	public static ServerSocket listeningSocket;
	public static RemotePlayer player;
	public static View view;
	
	public static void main(String[] args) throws IOException {
		player = setup();
		view = new View();
		//Turn turn = new Turn(player);
		//turn.take();
	}
	
	private static RemotePlayer setup() throws IOException{
		try{
			listeningSocket = new ServerSocket(9292);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Socket playerSocket = listeningSocket.accept();
		BeanHandler handler = new BeanHandler(playerSocket);
		while (true) {
				MessageBean bean = handler.waitForBean();
				System.out.println(bean.getData() + " has joined the game.");
				handler.sendBean(new MessageBean(Message.JOINCONFIRM,"testGame"));
				return new RemotePlayer(bean.getData(), handler);
		}
	}

}
