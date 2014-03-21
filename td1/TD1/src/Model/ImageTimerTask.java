package Model;
import java.util.TimerTask;


public class ImageTimerTask extends TimerTask {

	private Console console;
	private String view;
	
	/**
	 * Constructor
	 * @param console
	 */
	public ImageTimerTask(Console console, String view) {
		this.console = console;
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
