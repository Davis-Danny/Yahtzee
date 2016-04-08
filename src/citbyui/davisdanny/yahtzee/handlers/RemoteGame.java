package citbyui.davisdanny.yahtzee.handlers;

import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.models.LocalPlayer;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;

public class RemoteGame  implements Handler{

	public RemoteGame(){
		
	}

	@Override
	public void handle(String[] parameters) {
		try{
		String dest;
		if(parameters.length>0){
			dest = parameters[0];
		}else{
			dest = "localhost";
		}
		Socket socket = new Socket(dest,9292);
		JSONInputStream inFromServer = new JSONInputStream(socket.getInputStream());
		JSONOutputStream outToServer = new JSONOutputStream(socket.getOutputStream());
		
		View view = View.getView();
		String name = view.prompt("What is your name?");
		MessageBean bean = new MessageBean(Message.JOINREQUEST,name);
		outToServer.writeObject(bean);
		HashMap inMap = (HashMap) inFromServer.readObject();
		MessageBean inBean = new MessageBean(inMap);
		if(inBean.getMessage()==Message.JOINCONFIRM){
			view.display("Successfully joined game: "+inBean.getData());
			LocalPlayer player = new LocalPlayer(name);
			
		}else{
			view.display("Unable to join game.");
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
