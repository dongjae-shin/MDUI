package Components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*; //to make textfile.
import java.util.*;//to use Scanner
import Components.TimeFolder;

//The condition panel which will be attached to a tab.
class ConditionPanel extends JPanel implements ActionListener {
	
	//Member Declaration
	JLabel label;
	SpinnerModel spinnerModelN_equil, spinnerModelR_cut, spinnerModelTimeInterval, 
				 spinnerModelTotalSteps, spinnerModelDensity, spinnerModelTargetTemperature, 
				 spinnerModelParticleNumber;
	JSpinner spinnerN_equil, spinnerR_cut, spinnerTimeInterval, spinnerTotalSteps, 
			 spinnerDensity, spinnerTargetTemperature, spinnerParticleNumber;
	JButton makeConditionFile;
	JPanel LabelPanel, SpinnerPanel;
	TimeFolder timeFolder;
	
	//Constructor
	public ConditionPanel() {
		
		//Initialize each component
		//Label:
		label = new JLabel("<html>Equilibration Number(n_equil):"
				+ "<br><br>Potential Truncation:(r_cut)"
				+ "<br><br>Time Interval:<html>"
				+ "<br><br>Total Steps:<html>"
				+ "<br><br>Density:<html>"
				+ "<br><br>Target Temperature:<html>"
				+ "<br><br>Number of Particles(= 4*(NC^3)):"
				+ "<br>(NC = positive integer; the no. of unitcells in the simulation box)<html>");
		
		
		//SpinnerModel:
		spinnerModelN_equil = new SpinnerNumberModel(0, 0, 100000000, 1000);
		spinnerModelR_cut = new SpinnerNumberModel(0, 0, 100000000, 0.1);
		spinnerModelTimeInterval = new SpinnerNumberModel(0, 0, 100000000, 0.001);
		spinnerModelTotalSteps = new SpinnerNumberModel(0, 0, 100000000, 1000);
		spinnerModelDensity = new SpinnerNumberModel(0, 0, 100000000, 0.01);
		spinnerModelTargetTemperature = new SpinnerNumberModel(0, 0, 100000000, 0.01);
		spinnerModelParticleNumber = new SpinnerNumberModel(0, 0, 5324, 1);
		
		//Spinner:
		spinnerN_equil = new JSpinner(spinnerModelN_equil);
		spinnerR_cut = new JSpinner(spinnerModelR_cut);
		spinnerTimeInterval = new JSpinner(spinnerModelTimeInterval);
		spinnerTotalSteps = new JSpinner(spinnerModelTotalSteps);
		spinnerDensity = new JSpinner(spinnerModelDensity);
		spinnerTargetTemperature = new JSpinner(spinnerModelTargetTemperature);
		spinnerParticleNumber = new JSpinner(spinnerModelParticleNumber);
		
		//Button:
		makeConditionFile = new JButton("Make Condition File");
		makeConditionFile.addActionListener(this);
		
		//Internal Panel:
		LabelPanel = new JPanel();
		SpinnerPanel = new JPanel();
		GridLayout grid = new GridLayout(1,1);
		grid.setVgap(0);
		LabelPanel.setLayout(grid);
		grid = new GridLayout(7,1);
		grid.setVgap(15);
		SpinnerPanel.setLayout(grid);
		
		
		//Panel Setting
		this.setSize(400, 500);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10 , 30));//to use absolute position	
		
		//add components on  each panel
		LabelPanel.add(label);
		
		SpinnerPanel.add(spinnerN_equil);
		SpinnerPanel.add(spinnerR_cut);
		SpinnerPanel.add(spinnerTimeInterval);
		SpinnerPanel.add(spinnerTotalSteps);
		SpinnerPanel.add(spinnerDensity);
		SpinnerPanel.add(spinnerTargetTemperature);
		SpinnerPanel.add(spinnerParticleNumber);
		
		//Add internal panels and Button 
		this.add(LabelPanel);
		this.add(SpinnerPanel);
		this.add(makeConditionFile);
		
	}
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == makeConditionFile) {
			//Initialize Time folder
			timeFolder = new TimeFolder();
			
			try{
				BufferedWriter outputStream = new BufferedWriter(
						new FileWriter("data_folder/" + timeFolder.getFolderName() +"/input_data_condition.txt"));
				
				//Generate folderName.txt to share the directory with other tabs.
				BufferedWriter folderNameStream = new BufferedWriter(
						new FileWriter("folderName.txt"));
				folderNameStream.write(timeFolder.getFolderName());
				folderNameStream.close();
				
				//Write condition contents
				outputStream.write("n_equil     = " + spinnerN_equil.getValue()); 
				outputStream.newLine();
				outputStream.write("r_cut       = " + spinnerR_cut.getValue());
				outputStream.newLine();
				outputStream.write("delta_t     = " + spinnerTimeInterval.getValue());
				outputStream.newLine();
				outputStream.write("n_step      = " + spinnerTotalSteps.getValue());
				outputStream.newLine();
				outputStream.write("density     = " + spinnerDensity.getValue());
				outputStream.newLine();
				outputStream.write("temperature = " + spinnerTargetTemperature.getValue());
				outputStream.newLine();
				outputStream.write("NMOL        = " + spinnerParticleNumber.getValue());
				outputStream.close();
				
			} catch(IOException ioe) {
				System.err.println(ioe);
				System.exit(1);
			}
		}
	}
}

class MethodPanel extends JPanel implements ActionListener {
	
	//Member Declaration
	JLabel label3, label4, label5;
	ButtonGroup NVEGroup = new ButtonGroup();
	ButtonGroup NVTGroup = new ButtonGroup();
	ButtonGroup SimulationGroup = new ButtonGroup();
	JRadioButton NVESimulation = new JRadioButton("NVE Simulation");
	JRadioButton NVTSimulation = new JRadioButton("NVT Simulation");
	JRadioButton leapfrog = new JRadioButton("Leapfrog Integrator");
	JRadioButton velocityVerlet = new JRadioButton("velocity Verlet Integrator");
	JRadioButton leapfrogBerendsen = new JRadioButton(
							   "Leapfrog Integrator(with Berendsen thermostat)");
	JRadioButton velocityVerletBerendsen = new JRadioButton(
						 "velocity Verlet Integrator(with Berendsen thermostat)");
	JRadioButton noseHoover = new JRadioButton(
									   "Integrator with Nose-Hoover thermostat");
	JButton makeMethodFile = new JButton("Make Method File");	
	
	SpinnerModel spinnerModelCoupling, spinnerModelTau, spinnerModelKsi;
	JSpinner spinnerCoupling, spinnerTau, spinnerKsi;
	
	JPanel NVEPanel, NVTPanelRadioButtonLabel, NVTPanelSpinner;
	String[][] outputContent = {
	{"BERENDSEN      = 0","NOSEHOOVER     = 0","VELOCITYVERLET = 0", "LEAPFROG       = 1"},
	{"BERENDSEN      = 0","NOSEHOOVER     = 0","VELOCITYVERLET = 1", "LEAPFROG       = 0"},
	{"BERENDSEN      = 1","NOSEHOOVER     = 0","VELOCITYVERLET = 0", "LEAPFROG       = 1"},
	{"BERENDSEN      = 1","NOSEHOOVER     = 0","VELOCITYVERLET = 1", "LEAPFROG       = 0"},
	{"BERENDSEN      = 0","NOSEHOOVER     = 1","VELOCITYVERLET = 0", "LEAPFROG       = 0"}};
	public int contentIndex = 0;//to choose the outputContent
	
	
	//Constructor
	public MethodPanel() {
		
		//Radio Button:
		//add actionListener on each radio button
		NVESimulation.addActionListener(this);
		NVTSimulation.addActionListener(this);
		leapfrog.addActionListener(this);
		velocityVerlet.addActionListener(this);
		leapfrogBerendsen.addActionListener(this);
		velocityVerletBerendsen.addActionListener(this);
		noseHoover.addActionListener(this);
		//Grouping radio buttons
		SimulationGroup.add(NVESimulation);
		SimulationGroup.add(NVTSimulation);
		
		NVEGroup.add(leapfrog);
		NVEGroup.add(velocityVerlet);
		
		NVTGroup.add(leapfrogBerendsen);
		NVTGroup.add(velocityVerletBerendsen);
		NVTGroup.add(noseHoover);
		
		//Spinner:
		//SpinnerModel
		spinnerModelCoupling = new SpinnerNumberModel(0,0,1000,0.0001);
		spinnerModelTau = new SpinnerNumberModel(0,0,1000,0.01);
		spinnerModelKsi = new SpinnerNumberModel(0,0,1000,0.001);
		
		spinnerCoupling = new JSpinner(spinnerModelCoupling);
		spinnerTau = new JSpinner(spinnerModelTau);
		spinnerKsi = new JSpinner(spinnerModelKsi);
		
		//Label:
		label3 = new JLabel("        coupling constant(for both methods above): ");
		label4 = new JLabel("        tau value: ");
		label5 = new JLabel("        ksi value: ");
		
		//Button:
		makeMethodFile.addActionListener(this);
		
		//Initialize internal panels
		GridLayout grid = new GridLayout(3,1);
		grid.setHgap(10);
		grid.setVgap(2);
		
		NVEPanel = new JPanel();
		NVEPanel.setLayout(grid);
		NVEPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		NVTPanelRadioButtonLabel = new JPanel();
		grid = new GridLayout(7,1);
		grid.setVgap(2);
		NVTPanelRadioButtonLabel.setLayout(grid);
		
		NVTPanelSpinner = new JPanel();
		NVTPanelSpinner.setLayout(grid);
		
		//Add components on internal panels
		NVEPanel.add(NVESimulation);
		NVEPanel.add(leapfrog);//RadioButton
		NVEPanel.add(velocityVerlet);//RadioButton
		NVTPanelRadioButtonLabel.add(NVTSimulation);
		NVTPanelRadioButtonLabel.add(leapfrogBerendsen);//RadioButton
		NVTPanelRadioButtonLabel.add(velocityVerletBerendsen);//RadioButton
		NVTPanelRadioButtonLabel.add(label3);
		NVTPanelRadioButtonLabel.add(noseHoover);//RadioButton
		NVTPanelRadioButtonLabel.add(label4);
		NVTPanelRadioButtonLabel.add(label5);
		NVTPanelSpinner.add(new JLabel(""));
		NVTPanelSpinner.add(new JLabel(""));
		NVTPanelSpinner.add(new JLabel(""));
		NVTPanelSpinner.add(spinnerCoupling);
		NVTPanelSpinner.add(new JLabel(""));
		NVTPanelSpinner.add(spinnerTau);
		NVTPanelSpinner.add(spinnerKsi);
		
		//Set the Layout manager of MethodPanel
		this.setLayout(new FlowLayout());
		
		//Panel configuration
		JPanel panelUpper = new JPanel();
		JPanel panelLower = new JPanel();
		JPanel panelButton = new JPanel();
		grid = new GridLayout(1,3);
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		FlowLayout flowButton = new FlowLayout(FlowLayout.CENTER);
		
		panelUpper.setLayout(grid);
		panelLower.setLayout(flow);
		panelButton.setLayout(flowButton);
		panelUpper.add(NVEPanel);
		panelUpper.add(new JLabel(""));
		panelUpper.add(new JLabel(""));
		panelLower.add(NVTPanelRadioButtonLabel);
		panelLower.add(NVTPanelSpinner);
		panelLower.add(new JLabel("                             "));
		panelButton.add(makeMethodFile);
				
		//Initially, let all the radio buttons disabled.
		leapfrog.setEnabled(false);
		velocityVerlet.setEnabled(false);
		leapfrogBerendsen.setEnabled(false);
		velocityVerletBerendsen.setEnabled(false);
		noseHoover.setEnabled(false);
		spinnerCoupling.setEnabled(false);
		spinnerTau.setEnabled(false);
		spinnerKsi.setEnabled(false);
		
		//Add components on MethodPanel
		this.add(panelUpper);
		this.add(panelLower);
		this.add(panelButton);
		
		this.setSize(400, 500);
		
	}
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == NVESimulation) {
			leapfrog.setEnabled(true);
			velocityVerlet.setEnabled(true);
			leapfrogBerendsen.setEnabled(false);
			velocityVerletBerendsen.setEnabled(false);
			noseHoover.setEnabled(false);
			spinnerCoupling.setEnabled(false);
			spinnerTau.setEnabled(false);
			spinnerKsi.setEnabled(false);
		}
		if (e.getSource() == NVTSimulation) {
			leapfrog.setEnabled(false);
			velocityVerlet.setEnabled(false);
			leapfrogBerendsen.setEnabled(true);
			velocityVerletBerendsen.setEnabled(true);
			noseHoover.setEnabled(true);
			spinnerCoupling.setEnabled(true);
			spinnerTau.setEnabled(true);
			spinnerKsi.setEnabled(true);
		}
		if (e.getSource() == leapfrog) {
			contentIndex = 0;
		}
			
		if (e.getSource() == velocityVerlet) {
			contentIndex = 1;
			
		}
		if (e.getSource() == leapfrogBerendsen) {
			contentIndex = 2;
			
		}
		if (e.getSource() == velocityVerletBerendsen) {
			contentIndex = 3;
			
		}
		if (e.getSource() == noseHoover) {
			contentIndex = 4;
			
		}
		if (e.getSource() == makeMethodFile) {
			
				Scanner folderNameScanner = null;
			
			try {
				//read the foldername.txt which was shared by ConditionPanel
				folderNameScanner = new Scanner(new BufferedReader(new FileReader("folderName.txt")));
				String folderName = folderNameScanner.nextLine();				
				
				BufferedWriter outputStream = new BufferedWriter(
						new FileWriter("data_folder/" + folderName + "/input_data_method.txt"));
				
				folderNameScanner.close();
								
				outputStream.write(outputContent[contentIndex][0]); 
				outputStream.newLine();
				outputStream.write("couplingconst  = " + spinnerCoupling.getValue());
				outputStream.newLine();
				outputStream.write(outputContent[contentIndex][1]); 
				outputStream.newLine();
				outputStream.write("ksi            = " + spinnerKsi.getValue());	
				outputStream.newLine();
				outputStream.write("tau            = " + spinnerTau.getValue());
				outputStream.newLine();
				outputStream.write(outputContent[contentIndex][2]); 
				outputStream.newLine();
				outputStream.write(outputContent[contentIndex][3]); 
				outputStream.close();
				
			} catch(IOException ioe) {
				System.err.println(ioe);
				System.exit(1);
			}
		}
	}
}


class MonitoringPanel extends JPanel implements ItemListener, ActionListener {
	
	//Members Declaration
	JLabel labelOutFreq;
	SpinnerModel spinnerModelOutFreq = new SpinnerNumberModel(0,0,100000,100);
	JSpinner spinnerOutFreq;
	JCheckBox PBCCheckBox, makeViewFileCheckBox;
	JButton makeViewFile = new JButton("Make Monitoring File");
	String[] checkBoxContent = {"PBC_CONDITION = 0", "VIEWFILE      = 0"};
	
	//Constructor
	public MonitoringPanel() {
		//Initialize each components
		//Label:
		labelOutFreq = new JLabel("   Output Frequency:              ");
		
		//Spinner:
		spinnerOutFreq = new JSpinner(spinnerModelOutFreq);
		
		//RadioButton:
		PBCCheckBox = new JCheckBox("Periodic Boundary Condition");
		PBCCheckBox.addItemListener(this);
		makeViewFileCheckBox = new JCheckBox("Make Viewfile");
		makeViewFileCheckBox.addItemListener(this);
		
		//Button:
		makeViewFile.addActionListener(this);
		
		//Internal panels
		JPanel internalPanel1 = new JPanel();
		JPanel internalPanel2 = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		internalPanel1.setLayout(flow);
		internalPanel2.setLayout(flow);
		flow = new FlowLayout(FlowLayout.LEFT);
		buttonPanel.setLayout(flow);
		
		
		//add components on internal panels
		internalPanel1.add(labelOutFreq);
		internalPanel1.add(spinnerOutFreq);
		internalPanel2.add(PBCCheckBox);
		internalPanel2.add(makeViewFileCheckBox);
		buttonPanel.add(new JLabel("                                                     "));//for Windows
		//buttonPanel.add(new JLabel("                           "));//for Linux
		buttonPanel.add(makeViewFile);
		
		//Panel Settting
		this.setSize(400,500);
		flow = new FlowLayout(FlowLayout.LEFT,20,50);
		this.setLayout(flow);
		this.add(internalPanel1);
		this.add(internalPanel2);
		this.add(buttonPanel);
		
		
	}
	
	public void actionPerformed (ActionEvent e) {
			if (e.getSource() == makeViewFile) {
				
				Scanner folderNameScanner = null;
			
			try {
				//read the foldername.txt which was shared by ConditionPanel
				folderNameScanner = new Scanner(new BufferedReader(new FileReader("folderName.txt")));
				String folderName = folderNameScanner.nextLine();				
				
				BufferedWriter outputStream = new BufferedWriter(
						new FileWriter("data_folder/" + folderName + "/input_data_monitoring.txt"));
				
				folderNameScanner.close();
								
				outputStream.write(checkBoxContent[0]); 
				outputStream.newLine();
				outputStream.write(checkBoxContent[1]); 
				outputStream.newLine();
				outputStream.write("outFreq       = " + spinnerOutFreq.getValue());	
				outputStream.close();
				
			} catch(IOException ioe) {
				System.err.println(ioe);
				System.exit(1);
			}
		}
	}
	
	public void itemStateChanged (ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source == PBCCheckBox) {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				checkBoxContent[0] = "PBC_CONDITION = 0";
			else
				checkBoxContent[0] = "PBC_CONDITION = 1";
		}
		if(source == makeViewFileCheckBox) {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				checkBoxContent[1] = "VIEWFILE      = 0";
			else
				checkBoxContent[1] = "VIEWFILE      = 1";
		}
		
	}
}

public class SettingOptionFrame extends JFrame implements ActionListener {
	//It uses TabbedPane	
	
	//Member Declaration
	ConditionPanel conditionPanel = new ConditionPanel();
	MethodPanel methodPanel = new MethodPanel();
	MonitoringPanel monitoringPanel = new MonitoringPanel();
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public SettingOptionFrame() {
		//Frame Settings
		this.setSize(550, 450);//for Windows
		//this.setSize(650, 400);//for Linux
		this.setResizable(false);
		this.setTitle("Setting Options");
		
		tabbedPane.add("Condtions", conditionPanel);
		tabbedPane.add("Methods", methodPanel);
		tabbedPane.add("Monitoring", monitoringPanel);
		
		this.add(tabbedPane);
		this.setVisible(true);
	}
	
	public void actionPerformed (ActionEvent e) {
		
	}	
}
