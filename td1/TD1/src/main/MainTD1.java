package main;
import view.ConsoleView;
import view.ImageView;


public class MainTD1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		td1();
	}
	
	public static void td1() {
		new ImageView("ImageView1");
		new ImageView("ImageView2");
		new ConsoleView("ConsoleView");
	}

}
