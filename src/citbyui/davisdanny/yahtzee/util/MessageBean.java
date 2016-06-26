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
	
	public MessageBean(HashMap map)throws BeanBuildException{
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
	
	public Message parseMessage(HashMap map)throws BeanBuildException{
		System.out.println(((HashMap) map.get("message")).get("value"));
		for(Message message: Message.values()){
			if( message.value.equals(((HashMap) map.get("message")).get("value"))){
				return message;
			}
		}
		throw new BeanBuildException();
	}

	public enum Message{
		JOINREQUEST("Request to join"),JOINCONFIRM("Confirmed"),INVALIDMESSAGE("Message not valid"),READY("Ready for messages")
		,ROLLPROMPT("Will you roll?"),ROLLRESPONSE("Will I Roll?");
		private String value;
		
		private Message(String value){
			this.value = value;
		}
	}

}
