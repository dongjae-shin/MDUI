/*This Class builds the outermost frame.*/
package Components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;//to use scanner
import Components.AboutFrame;

public class MainFrame extends JFrame implements ActionListener {
	
	//Members Declaration
	JMenuBar menuBar;
	JMenu menuSetting, menuResult, menuAbout, menuExit; 
	JMenuItem menuItemSettingOption, menuItemAbout, menuItemExit;
	JButton makePathFile, runMD, runMoleculeViewer, clearOutputWindow, clearViewerWindow;
	TextArea outputWindow = new TextArea("Output results are printed here.\n",25,85);//for Windows
	TextArea viewerWindow = new TextArea("Viewer logs are printed here.\n",10,85);//for Windows
	//TextArea outputWindow = new TextArea("Output results are printed here.\n",25,68);//for Linux
	//TextArea viewerWindow = new TextArea("Viewer logs are printed here.\n",10,68);//for Linux
	public MainFrame() {
		this.setSize(650, 750);//for Windows
		//this.setSize(650, 900);//for Linux
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("MDUI ver. 1.0");
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		/*Components Settings*/
		//Button:
		makePathFile = new JButton("Make Path File");
		runMD = new JButton("Run MD");
		runMoleculeViewer = new JButton("Run MoleculeViewer");
		clearOutputWindow = new JButton("Clear Output Window");
		clearViewerWindow = new JButton("Clear Viewer Window");
		makePathFile.addActionListener(this);
		runMD.addActionListener(this);
		runMoleculeViewer.addActionListener(this);
		clearOutputWindow.addActionListener(this);
		clearViewerWindow.addActionListener(this);
		
		JPanel outputPanel = new JPanel();
		JPanel viewerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JLabel outputLabel = new JLabel("<Output Window>");//for Linux
		JLabel viewerLabel = new JLabel("<Viewer Window>");//for Linux
		outputPanel.setLayout(new FlowLayout(FlowLayout.LEFT,200,0));//for Linux
		outputPanel.add(outputLabel);
		/*
		outputPanel.add(new JLabel("<Output Window>                        "
				+ "                                                        "
				+ "                  "));
		*/
		outputPanel.add(clearOutputWindow);
		viewerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,200,0));//for Linux
		viewerPanel.add(viewerLabel);
		/*
		viewerPanel.add(new JLabel("<Viewer Window>                        "
				+ "                                                        "
				+ "                  "));
		*/
		viewerPanel.add(clearViewerWindow);
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
		buttonPanel.add(makePathFile);
		buttonPanel.add(runMD);
		buttonPanel.add(runMoleculeViewer);
		
		
		/*Menu Bar Settings*/
		
		//Create menuBar
		menuBar = new JMenuBar();
		
		
		//Create First menu, 'Setting'
		menuSetting = new JMenu("Setting");
		menuSetting.setMnemonic(KeyEvent.VK_S);//it sets keyboard shortcut on menu.

		//Setting: menuItems
		menuItemSettingOption = new JMenuItem("Set the simulation options", 
															KeyEvent.VK_O);
		menuItemSettingOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
														  ActionEvent.CTRL_MASK));
		menuItemSettingOption.addActionListener(this);
		
		
		//Create Result menu, 'Result'
		menuResult = new JMenu("Result");
		menuResult.setMnemonic(KeyEvent.VK_R);//keyboard shortcut
		
		
		//Result: menuItems
		
		
		
		//Create About menu, 'About'
		menuAbout = new JMenu("About");
		menuAbout.setMnemonic(KeyEvent.VK_A);
		
		//About: menuItems
		menuItemAbout = new JMenuItem("About this program...", KeyEvent.VK_A);
		menuItemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 
													     ActionEvent.CTRL_MASK));
		menuItemAbout.addActionListener(this);
		
		
		//Create Exit menu, 'Exit'
		menuExit = new JMenu("Exit");
		menuExit.setMnemonic(KeyEvent.VK_X);
		
		//Exit: menuItems
		menuItemExit = new JMenuItem("Exit this program", KeyEvent.VK_X);
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
														 ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener(this);
		
				
		//Register each menu items on each menu component.
		menuSetting.add(menuItemSettingOption);
		   //menuResult.add();
		menuAbout.add(menuItemAbout);
		menuExit.add(menuItemExit);
				
		//Register each menu components on the menuBar
		menuBar.add(menuSetting);
		menuBar.add(menuResult);
		menuBar.add(menuAbout);
		menuBar.add(menuExit);
		
		//Register menuBar to MainFrame
		this.setJMenuBar(menuBar);
		
		//Add components on Main Frame
		FlowLayout flow = new FlowLayout();
		flow.setVgap(5);
		this.setLayout(flow);
		
		this.add(outputPanel);
		this.add(outputWindow);
		this.add(viewerPanel);
		this.add(viewerWindow);
		this.add(buttonPanel);
				
		this.setVisible(true);
	}
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == clearOutputWindow) {
			outputWindow.setText("Output results are printed here.\n");
		}
		if (e.getSource() == clearViewerWindow) {
			viewerWindow.setText("Viewer logs are printed here.\n");
		}
		if (e.getSource() == makePathFile) {
			Scanner folderNameScanner = null;
			
			try{
				
				folderNameScanner = new Scanner(new BufferedReader(new FileReader("folderName.txt")));
				String folderName = folderNameScanner.nextLine();
				
				BufferedWriter pathFileStream = new BufferedWriter(new FileWriter("InputPath.txt"));
				
				pathFileStream.write("pathCondition = data_folder\\\\" + folderName + "\\\\input_data_condition.txt");
				pathFileStream.newLine();
				pathFileStream.write("pathMethod = data_folder\\\\" + folderName + "\\\\input_data_method.txt");
				pathFileStream.newLine();
				pathFileStream.write("pathMonitoring = data_folder\\\\" + folderName + "\\\\input_data_monitoring.txt");
				pathFileStream.newLine();
				pathFileStream.write("pathOutput = data_folder\\\\" + folderName + "\\\\output_data.txt");
				pathFileStream.newLine();
				pathFileStream.write("pathVACF = data_folder\\\\" + folderName + "\\\\VACF.txt");
				pathFileStream.newLine();
				pathFileStream.write("pathViewFile = coordinates.txt");
				pathFileStream.close();
	           
				outputWindow.append("\n\n\n");
				outputWindow.append("pathFiles generated.\n");
				folderNameScanner.close();
				
			}catch(IOException ioe) {
				System.err.println(ioe);
				System.exit(1);
			}
			
		}
		if (e.getSource() == runMoleculeViewer) {
			//we use thread to do multitasking
			Thread threadMoleculeViewer = new Thread(new ThreadRunMoleculeViewer());
			threadMoleculeViewer.start();
		}
		if (e.getSource() == runMD) {
			//we use thread to do multitasking
			Thread threadMD = new Thread(new ThreadRunMD());
			threadMD.start();
		}
		if (e.getSource() == menuItemSettingOption) {
			SettingOptionFrame settingOptionFrame = new SettingOptionFrame();
		}
		if (e.getSource() == menuItemExit) {
			System.exit(0);
		}
		if (e.getSource() == menuItemAbout) {
			AboutFrame aboutFrame = new AboutFrame();
		}
	}
	//the method to generate MD thread
	public class ThreadRunMD implements Runnable{
		
		@Override
		public void run(){
			try {
				runMD();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void runMD() throws IOException {
			try {
				//Process p = Runtime.getRuntime().exec("wine md_my_simulation_autocorr.exe");//forLinux
				ProcessBuilder pb = new ProcessBuilder("md_my_simulation_autocorr.exe");//forWindows
	            Process p = pb.start();
	            InputStream is = p.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            char[] buff = new char[1];
	            int len = -1;
	            //print on the output window
	            outputWindow.append("\n\n\n");
	            while ( (len = br.read(buff)) != -1) {
	               outputWindow.append(new String(buff, 0, len));
	            }
	            outputWindow.append("\n");
	            br.close();
	            outputWindow.append("MD simulation Done.\n");
	            p.destroy();
			} catch(IOException ioe2) {
				ioe2.printStackTrace();
			}
		}

	}
	
	//the method to generate Molecule Viewer thread
	public class ThreadRunMoleculeViewer implements Runnable{
		
		@Override
		public void run(){
			try {
				runMoleculeViewer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void runMoleculeViewer() throws IOException {
			try {
				ProcessBuilder pb = new ProcessBuilder("MoleculeViewer.exe");//forWindows
				Process p = pb.start();
				//Process p = Runtime.getRuntime().exec("wine MoleculeViewer.exe");//for Linux
	            InputStream is = p.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            char[] buff = new char[8];
	            int len = -1;
	            //print on the viewerWindow
	            viewerWindow.append("\n\n\n");
	            while ( (len = br.read(buff)) != -1) {
	            	viewerWindow.append(new String(buff, 0, len));
	            }
	            
	            br.close();
	            p.destroy();
			} catch(IOException ioe1) {
				viewerWindow.append("Error! External Program Run Failed" + ioe1.getMessage() + "\n");
			}
		}

	}

	
}

