import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main {
	public static void main(String[] args) {

		//My very first program, doesn't work on linux because weird invisible panel relies on a bug
		
		JFrame.setDefaultLookAndFeelDecorated(true);


		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CaptureMaker gtw = new CaptureMaker();
				gtw.setVisible(true);


			}
		});
	}
}
