package Components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutFrame extends JFrame {
	
	//Member Declaration
	JLabel Explanation;
	JPanel ExplanationPanel;
	
	public AboutFrame() {
		this.setSize(500, 300);
		this.setTitle("About this program...");
		
		//Create Label that contains the explanation about this program.
		
		Explanation = new JLabel("<html><br><br><br><br>This Program is for studying."
							   + "<br>and made for the class named 'Project Design'.<html>");
				
		//Create panel where Explanation will be located.
		ExplanationPanel = new JPanel();
		ExplanationPanel.setSize(300, 100);
		ExplanationPanel.add(Explanation);
		
		this.add(ExplanationPanel);
		
		//this.pack();//remove the empty spaces		
		this.setVisible(true);
	}

}
