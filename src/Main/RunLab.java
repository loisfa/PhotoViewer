package Main;

import java.awt.Color;

import javax.swing.UIManager;


public class RunLab{

	public static void main(String [ ] args) {
		
		UIManager.put("nimbusBase", new Color(50,50,200));		
		UIManager.put("nimbusBlueGrey", new Color(150,150,190));		
		UIManager.put("control", new Color(240,255,240));
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {

		}

		Window window = new Window();
		window.setVisible(true);

	}
}
