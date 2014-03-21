package Model;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;


public class Console {
	private  static Console singletonConsole = null;
	public static String PROPERTY_NAME = "image";
	public static String PROPERTY_NAME2 = "image2";
	private PropertyChangeSupport pcs;
	private Timer timer;
	private Timer timer2;
	private ImageTimerTask itt;
	private ImageTimerTask itt2;
	private Integer timerInterval;
	private Integer switchImage;
	private Integer switchImage2;

	/**
	 * Constructor
	 */
	private Console() {
		pcs = new PropertyChangeSupport(this);
		this.timerInterval = 0;
		this.switchImage = 0;
		this.switchImage2 = 1;
	}
	
	/**
	 * Get the Console instance 
	 * @return
	 */
	public static Console getInstance() {
		if (null == singletonConsole) { 
			singletonConsole = new Console();
        }
        return singletonConsole;
	}
	
	/**
	 * Add a listener to the source
	 * @param pcl
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Notifie ImageView
	 */
	public void time(String view) {
		if (view.equals("view1")) {
			pcs.firePropertyChange(PROPERTY_NAME, null, getImage());
		} else if (view.equals("view2")) {
			pcs.firePropertyChange(PROPERTY_NAME2, null, getImage2());
		}
	}
	

	/**
	 * Start timer (on pressing start button)
	 */
	public void startTimer() {
		if (timer == null) {
			this.timer = new Timer();
			this.itt = new ImageTimerTask(this, "view1");
			this.timer.schedule(this.itt, 0, Long.parseLong(timerInterval.toString()));
		}
		if (timer2 == null) {
			this.timer2 = new Timer();
			this.itt2 = new ImageTimerTask(this, "view2");
			this.timer2.schedule(this.itt2, 0, Long.parseLong(timerInterval.toString()));
		}
	}
	
	/**
	 * Stop timer
	 */
	public void stopTimer() {
		this.timer.cancel();
		this.timer.purge();
		this.timer = null;
		this.timer2.cancel();
		this.timer2.purge();
		this.timer2 = null;
	}
	
	/**
	 * Set timer value
	 * @param i
	 */
	public void setInterval(Integer i) {
		timerInterval = i;
	}
	
	/**
	 * Get new image for imageview 1
	 * @return
	 */
	public String getImage() {
		String s = "images/nf28td1_images/image" + String.valueOf(switchImage) + ".jpg";
		System.out.println(s);
		if (switchImage == 8) {
			switchImage = 0;
		} else {
			switchImage++;
		}
		return s;
	}
	
	/**
	 * Get new image for imageview 2
	 * @return
	 */
	public String getImage2() {
		String s = "images/" + String.valueOf(switchImage2) + ".png";
		if (switchImage2 == 3) {
			switchImage2 = 1;
		} else {
			switchImage2++;
		}
		return s;
	}
}
