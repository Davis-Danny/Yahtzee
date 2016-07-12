package citbyui.davisdanny.yahtzee.models;

import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONUtilities;
import citbyui.davisdanny.yahtzee.util.BeanHandler;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;
import citbyui.davisdanny.yahtzee.util.Util;

public class RemotePlayer extends Player {
	
	Boolean ready;
	BeanHandler handler;
	

	public RemotePlayer(String name,BeanHandler handler){
		super(name);
		this.handler = handler;
		ready = true;
	}

	@Override
	public boolean keepRolling(int[] dice) {
		//create a messageBean to ask if the remote player wants to roll and send it
		MessageBean bean = new MessageBean(Message.ROLLPROMPT,Util.diceToString(dice));
		MessageBean response = handler.exchangeBeans(bean);
		
		// return the response
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
		
		//create and send a bean asking which dice the player will keep
		MessageBean bean = new MessageBean(Message.KEEPPROMPT,Util.diceToString(dice));
		MessageBean response = handler.exchangeBeans(bean);
		if(response.getMessage()==Message.KEEPRESPONSE){
			return Util.diceFromString(response.getData());
		}else{
			Util.error(Message.KEEPRESPONSE.toString(), response.getMessage().toString());
			return null;
		}
	}

	@Override
	public String chooseScore(HashMap<String, Integer> choices, int[] dice) {

		HashMap map = new HashMap();
		try {
			
			//convert the choices and the dice to strings
			/*String JSONChoices = JSONUtilities.stringify(choices);
			String JSONDice = JSONUtilities.stringify(dice);
			
			//place those strings in a HashMap and convert it to a JSON string
			map.put("choices", JSONChoices);
			map.put("dice", JSONDice);*/
			map.put("choices", choices);
			map.put("dice", dice);
			String JSONMap = JSONUtilities.stringify(map);
			
			//create a MessageBean with the ChoosePrompte message and the stringified map
			MessageBean bean = new MessageBean(Message.CHOOSEPROMPT, JSONMap);
			
			//send the MessageBean and get the response
			MessageBean response = handler.exchangeBeans(bean);
			
			if(response.getMessage()==Message.CHOOSERESPONSE){
				return response.getData();
			}else{
				Util.error(Message.KEEPRESPONSE.toString(), response.getMessage().toString());
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Util.nyi("RemotePlayer chooseScore logic");
		return null;
	}
	
	

}
