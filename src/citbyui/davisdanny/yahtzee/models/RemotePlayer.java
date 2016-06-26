package citbyui.davisdanny.yahtzee.models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;
import org.quickconnectfamily.json.ParseException;

import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.util.BeanBuildException;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;
import citbyui.davisdanny.yahtzee.util.Util;

public class RemotePlayer extends Player {
	
	Socket socket;
	Boolean ready;
	JSONInputStream inFromClient;
	JSONOutputStream outToClient;
	

	public RemotePlayer(String name,Socket socket){
		super(name);
		this.socket = socket;
		try {
			inFromClient = new JSONInputStream(socket.getInputStream());
			outToClient = new JSONOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ready = true;
	}

	@Override
	public boolean keepRolling(int[] dice) {
		MessageBean bean = new MessageBean(Message.ROLLPROMPT,dice.toString());
		MessageBean response = exchangeBeans(bean);
		View view = View.getView();
		if(response.getMessage()==Message.ROLLRESPONSE){
			if(response.getData().equals("true")){
				return true;
			}else if(response.getData().equals("false")){
				return false;
			}else{
				Util.error("true or false", response.getData());
			}
		}else{
			Util.error(Message.ROLLRESPONSE.toString(), response.getMessage().toString());
			return false;
		}
		return false;
	}

	@Override
	public int[] chooseDiceToKeep(int[] dice) {
		Util.nyi("RemotePlayer chooseDiceToKeep logic");
		return null;
	}

	@Override
	public String chooseScore(HashMap<String, Integer> choices, int[] dice) {
		Util.nyi("RemotePlayer chooseScore logic");
		return null;
	}
	
	private MessageBean exchangeBeans(MessageBean bean){
		try {
			outToClient.writeObject(bean);
			HashMap inMap = (HashMap) inFromClient.readObject();
			return new MessageBean(inMap);
		} catch (JSONException | IOException | ParseException | BeanBuildException e) {
			e.printStackTrace();
			return null;
		}
	}

}
