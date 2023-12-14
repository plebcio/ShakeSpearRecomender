package com.shakespeare;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ShakespeareFrame extends JFrame implements ActionListener {
	
	class DroolsThread extends Thread {
		KieSession kSession;
		public DroolsThread(KieSession kSession) {
			this.kSession = kSession;
		}
		public void run() {
			kSession.fireAllRules();
		}
	}
	
	private static KieServices ks;
	private static KieContainer kContainer;
	private static KieSession kSession;
	private int optionNum = -1;
	
	DroolsThread thread;
	
	private JPanel startPanel = new JPanel();
	private JButton startButton = new JButton("Begin");
	private JLabel startLabel = new JLabel("Which Shakespeare Play Should I See Next?");
	
	
	private JRadioButton[] radioList = new JRadioButton[7];
	
	private JPanel mainPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel nextPanel = new JPanel();
	
	private JLabel questionText = new JLabel();
	private JLabel buttonText = new JLabel("Choose one option:");	
	private ButtonGroup group = new ButtonGroup();
	private JButton close = new JButton("Close");
	private JButton next = new JButton("Next");
	private Font fieldFont = new Font("Times New Roman", Font.BOLD, 25);
	private Font radioFont = new Font("Times New Roman", Font.PLAIN, 15);
	
	private JLabel choice = new JLabel();
	
	public ShakespeareFrame() {
		super("Shakespeare Play Chooser");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		 try {
	            // load up the knowledge base
		        ks = KieServices.Factory.get();
	    	    kContainer = ks.getKieClasspathContainer();
	        	kSession = kContainer.newKieSession("ksession-rules");
	        	
	        	ShakespeareInterface inter =  new ShakespeareInterface();
	        	kSession.setGlobal("inter", inter);
	        	thread = new DroolsThread(kSession);
	        } 
		 catch (Throwable t) {
	            t.printStackTrace();
	     }
		
		startPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);
		startLabel.setFont(fieldFont);
		startLabel.setHorizontalAlignment(JTextField.CENTER);
		startPanel.add(startLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(50,10,50,10);
		startPanel.add(new JPanel(),c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,30,10,10);
		c.weightx = 1;
		c.weighty = 1;
		startButton.addActionListener(this);
		startPanel.add(startButton,c);
		
		add(startPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public void start() {
		remove(startButton);
		remove(startLabel);
		remove(startPanel);

		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setLayout(new GridLayout(8,1,50,20));
		nextPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		questionText.setFont(fieldFont);
		questionText.setHorizontalAlignment(JTextField.CENTER);
		choice.setFont(fieldFont);
		choice.setHorizontalAlignment(JTextField.CENTER);
		choice.setForeground(new Color(0,150,150));
		textPanel.add(questionText);
		buttonPanel.add(buttonText);
		for(int i = 0; i<7; i++) {
			radioList[i] = new JRadioButton("Przykładowa odpowiedz "+(i+1));
			radioList[i].setFont(radioFont);
			group.add(radioList[i]);
			buttonPanel.add(radioList[i]);
		}
		close.addActionListener(this);
		next.addActionListener(this);
		nextPanel.add(next);
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);
		mainPanel.add(textPanel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,30,10,10);
		c.weightx = 1;
		c.weighty = 2;
		mainPanel.add(buttonPanel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);
		mainPanel.add(nextPanel,c);
		add(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(startButton)) {
			start();
			thread.start();
		}
		if(e.getSource().equals(next)) {
			int i;
			for(i = 0; i<7; i++) {
				if(radioList[i].isSelected()) {
					break;
				}
			}
			group.clearSelection();
			if(i<7) {
				optionNum = i;
			}
			
		}
		if(e.getSource().equals(close)) {
			System.exit(0);
		}
	}
	
	public void setQuestion(String question) {
		questionText.setText(question);
		pack();
		setLocationRelativeTo(null);
	}
	
	public int getOptionNum() {
		return optionNum;
	}
	
	public void resetOpt() {
		optionNum = -1;
	}
	
	public void setOptions(String[] opts) {
		int i = 0;
		for(String o : opts) {
			radioList[i].setText(o);
			radioList[i].setVisible(true);
			i++;
		}
		while(i<7) {
			radioList[i].setVisible(false);
			i++;
		}
		pack();
		setLocationRelativeTo(null);
	}
	
	public void setFinal(String nazwa) {
		questionText.setText("You should watch ");
		for(int i = 0; i<7; i++) {
			buttonPanel.remove(radioList[i]);
		}
		buttonPanel.remove(buttonText);
		remove(buttonPanel);
		next.setVisible(false);
		nextPanel.remove(next);
		nextPanel.add(close);
		close.setVisible(true);
		choice.setText("\'"+nazwa+"\'");
		textPanel.add(choice);
		choice.setVisible(true);
		pack();
		setLocationRelativeTo(null);
		
	}
	
	
}
