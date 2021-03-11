package hufs.cse.house.factory;
/**
 * Created on 2015. 3. 16.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */


import java.awt.EventQueue;

/**
 * @author cskim
 *
 */
public class HouseDrawMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HouseFrame frame = new HouseFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
