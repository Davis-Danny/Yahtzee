package citbyui.davisdanny.yahtzee.util;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

public class BeanHandler {

	JSONInputStream inStream;
	JSONOutputStream outStream;

	public BeanHandler(Socket socket) {
		try {
			inStream = new JSONInputStream(socket.getInputStream());
			outStream = new JSONOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MessageBean exchangeBeans(MessageBean bean) {
		sendBean(bean);
		return waitForBean();
	}

	@SuppressWarnings("unchecked")
	public MessageBean waitForBean() {
		try {
			HashMap<String, Object> inMap = (HashMap<String, Object>) inStream.readObject();
			MessageBean bean = new MessageBean(inMap);
			Util.debug("Recieved: " + bean.getMessage() + ": " + bean.getData());
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void sendBean(MessageBean bean) {
		try {
			Util.debug("Sending: " + bean.getMessage() + ": " + bean.getData());
			outStream.writeObject(bean);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
