package model;
import java.util.TimerTask;


public class ImageTimerTask extends TimerTask {

	/**
	 * Référence modèle
	 */
	private Console console = Console.getInstance();
	private String view;
	
	/**
	 * Constructor
	 * @param console
	 */
	public ImageTimerTask(String view) {
		this.view = view;
	}

	/**
	 * Run time function
	 */
	@Override
	public void run() {
		console.time(view);		
	}

}
