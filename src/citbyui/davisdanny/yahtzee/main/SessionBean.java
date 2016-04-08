package citbyui.davisdanny.yahtzee.main;

public class SessionBean {
	
	private ApplicationController controller;
	private View view;
	
	public SessionBean(ApplicationController controller,View view){
		setController(controller);
		setView(view);
	}
	
	public ApplicationController getController() {
		return controller;
	}
	public void setController(ApplicationController controller) {
		this.controller = controller;
	}
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	
	
}
