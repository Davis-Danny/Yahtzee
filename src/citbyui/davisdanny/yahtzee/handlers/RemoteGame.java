package citbyui.davisdanny.yahtzee.handlers;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;
import org.quickconnectfamily.json.ParseException;

import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.models.LocalPlayer;
import citbyui.davisdanny.yahtzee.util.BeanBuildException;
import citbyui.davisdanny.yahtzee.util.InvalidCommandException;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;

public class RemoteGame  implements Handler{
	
	SessionBean session;
	JSONInputStream inFromServer;
	JSONOutputStream outToServer;
	LocalPlayer local;

	public RemoteGame(SessionBean session){
		this.session = session;
	}

	@Override
	public void handle(String[] parameters) {
		String dest;
		if(parameters.length>0){
			dest = parameters[0];
		}else{
			dest = "localhost";
		}
		join(dest);
	}
	
	private MessageBean exchangeBeans(MessageBean bean){
		try {
			outToServer.writeObject(bean);
			HashMap inMap = (HashMap) inFromServer.readObject();
			return new MessageBean(inMap);
		} catch (JSONException | IOException | ParseException | BeanBuildException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void join(String dest){
		try{
			Socket socket = new Socket(dest,9292);
			inFromServer = new JSONInputStream(socket.getInputStream());
			outToServer = new JSONOutputStream(socket.getOutputStream());
			
			View view = View.getView();
			String name = view.prompt("What is your name?");
			local = new LocalPlayer(name);
			MessageBean bean = new MessageBean(Message.JOINREQUEST,name);
			MessageBean inBean = exchangeBeans(bean);
			if(inBean.getMessage()==Message.JOINCONFIRM){
				view.display("Successfully joined game: "+inBean.getData());
				LocalPlayer player = new LocalPlayer(name);
				ready();
			}else{
				view.display("Unable to join game.");
				session.getController().handleRequest("menu main");
			}
			
			}catch(Exception e){
				View.getView().display("Unable to join game.");
				try {
					session.getController().handleRequest("menu main");
				} catch (InvalidCommandException e1) {
					e1.printStackTrace();
				}
			}
	}
	
	private void ready(){
		MessageBean inBean = exchangeBeans( new MessageBean(Message.READY));
		switch(inBean.getMessage()){
			case ROLLPROMPT:
				String data = inBean.getData();
				Boolean response = View.getView().confirm(data);
				exchangeBeans(new MessageBean(Message.ROLLRESPONSE,response.toString()));
				break;
			default:
				break;
		}
	}

}
