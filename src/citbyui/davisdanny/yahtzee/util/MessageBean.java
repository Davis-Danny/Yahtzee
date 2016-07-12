package citbyui.davisdanny.yahtzee.util;

import java.io.Serializable;
import java.util.HashMap;

public class MessageBean implements Serializable{

	private static final long serialVersionUID = 1730424696116832267L;
	
	private Message message;
	private String data;

	public MessageBean(Message message) {
		setMessage(message);
	}
	
	public MessageBean(Message message,String data){
		this(message);
		setData(data);
	}
	
	public MessageBean(HashMap<String, Object> map)throws BeanBuildException{
			setMessage(parseMessage(map));
			if(map.containsKey("data")){
				setData((String) map.get("data"));
			}
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public Message parseMessage(HashMap<String, Object> map)throws BeanBuildException{
		for(Message message: Message.values()){
			if( message.value.equals(((HashMap<String, String>) map.get("message")).get("value"))){
				return message;
			}
		}
		throw new BeanBuildException();
	}

	public enum Message{
		JOINREQUEST("Request to join"),JOINCONFIRM("Confirmed"),INVALIDMESSAGE("Message not valid"),READY("Ready for messages")
		,ROLLPROMPT("Will you roll?"),ROLLRESPONSE("Will I Roll?"),KEEPPROMPT("Which dice will you keep?")
		,KEEPRESPONSE("I will keep these dice:"),CHOOSEPROMPT("Which score will you choose?"),CHOOSERESPONSE("This score.")
		,NOTIFICATION("For your information:"),DONE("The game is over.");
		private String value;
		
		private Message(String value){
			this.value = value;
		}
	}

}
