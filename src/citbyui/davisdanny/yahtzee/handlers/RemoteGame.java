package citbyui.davisdanny.yahtzee.handlers;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;
import org.quickconnectfamily.json.JSONUtilities;
import org.quickconnectfamily.json.ParseException;

import citbyui.davisdanny.yahtzee.main.SessionBean;
import citbyui.davisdanny.yahtzee.main.View;
import citbyui.davisdanny.yahtzee.models.LocalPlayer;
import citbyui.davisdanny.yahtzee.util.BeanHandler;
import citbyui.davisdanny.yahtzee.util.MessageBean;
import citbyui.davisdanny.yahtzee.util.MessageBean.Message;
import citbyui.davisdanny.yahtzee.util.Util;

public class RemoteGame implements Handler {

	SessionBean session;
	JSONInputStream inFromServer;
	JSONOutputStream outToServer;
	BeanHandler handler;
	LocalPlayer local;

	public RemoteGame(SessionBean session) {
		this.session = session;
	}

	@Override
	public void handle(String[] parameters) {
		String dest;
		if (parameters.length > 0) {
			dest = parameters[0];
		} else {
			dest = "localhost";
		}
		join(dest);
	}

	private void join(String dest) {
		try {
			Socket socket = new Socket(dest, 9292);
			handler = new BeanHandler(socket);

			View view = View.getView();
			String name = view.prompt("What is your name?");
			local = new LocalPlayer(name);
			MessageBean bean = new MessageBean(Message.JOINREQUEST, name);
			MessageBean inBean = handler.exchangeBeans(bean);
			if (inBean.getMessage() == Message.JOINCONFIRM) {
				view.display("Successfully joined game: " + inBean.getData());
				ready();
			} else {
				view.display("Unable to join game.");
				session.getController().handleRequest("menu main");
			}

		} catch (Exception e) {
			// View.getView().display("Unable to join game.");
			// try {
			// session.getController().handleRequest("menu main");
			// } catch (InvalidCommandException e1) {
			e.printStackTrace();
			// }
		}
	}

	@SuppressWarnings("unchecked")
	private void ready() {
		while (true) {
			// Util.debug("Ready loop");
			// MessageBean inBean = exchangeBeans( new
			// MessageBean(Message.READY));
			MessageBean inBean = handler.waitForBean();
			int dice[];
			switch (inBean.getMessage()) {
			case ROLLPROMPT:
				dice = Util.diceFromString(inBean.getData());
				Boolean response = local.keepRolling(dice);
				handler.sendBean(new MessageBean(Message.ROLLRESPONSE, response.toString()));
				break;
			case KEEPPROMPT:
				// Util.debug("got to KEEPPROMT");
				dice = Util.diceFromString(inBean.getData());
				int[] keepResponse = local.chooseDiceToKeep(dice);
				handler.sendBean(new MessageBean(Message.KEEPRESPONSE, Util.diceToString(keepResponse)));
				break;
			case CHOOSEPROMPT:
				try {
					HashMap<String, Object> map = (HashMap<String, Object>) JSONUtilities.parse(inBean.getData());
					ArrayList<Long> diceList = (ArrayList<Long>) map.get("dice");
					HashMap<String, Integer> choices = (HashMap<String, Integer>) map.get("choices");
					String choice = local.chooseScore(choices, toIntArray(diceList));
					handler.sendBean(new MessageBean(Message.CHOOSERESPONSE, choice));
					local.getScore().addScore(choice, choices.get(choice));
				} catch (JSONException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case NOTIFICATION:
				local.notify(inBean.getData());
				break;
			default:
				break;
			}
		}
	}

	private int[] toIntArray(ArrayList<Long> list) {
		int[] out = new int[list.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = (int)Math.toIntExact(list.get(i));
		}
		return out;
	}

}
