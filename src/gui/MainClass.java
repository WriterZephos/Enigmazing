package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import encryption.LightDecryptor;
import encryption.LightEncryptor;
import zui.ZComponentFactory;

public class MainClass {
	
	JFrame frame;
	JPanel editorPanel, filePanel, sidePanel;
	Dimension size;
	JScrollPane scrollPane;
	JTextArea textArea;
	JButton encryptButton, decipherButton, chooseFile, chooseOutputFile, saveText, loadText, encryptFileButton, decipherFileButton;
	JTextField editorKey1Field, editorKey2Field, fileKey1Field, fileKey2Field;
	JLabel editorMessageLabel,inputPathLabel, outputPathLabel, fileMessageLabel1, fileMessageLabel2;
	JTabbedPane tabbedPane;
	JFileChooser fileChooser;
	JCheckBox overWriteCheckbox, editorEncodeKeyCheckbox, fileEncodeKeyCheckbox;

	String inputFile,outputFile;
	
	boolean editorWarned = false;
	boolean fileWarned = false;

	LightEncryptor encryptor;
	LightDecryptor decipher;
	
	
	public MainClass(){
		size  = new Dimension(650,600);
		frame = new JFrame("Enigmazing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//tabs
		tabbedPane = new JTabbedPane();
		
		//file chooser
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new MyFilter());
		
		initializeEditorPanel();
		initializeFilePanel();
		
		//add tabs
		tabbedPane.addTab("Encrypt Text", editorPanel);
		tabbedPane.addTab("Encrypt File", filePanel);
		
		// everything to frame
		frame.getContentPane().add(tabbedPane);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private void initializeEditorPanel(){
		
				//panel
				editorPanel = new JPanel();
				editorPanel.setPreferredSize(size);
				editorPanel.setMaximumSize(size);
				editorPanel.setMinimumSize(size);
				editorPanel.setLayout(new BorderLayout());
				
				//textArea
				Dimension textSize = new Dimension(375,550);
				textArea = new JTextArea();
				textArea.setLineWrap(true);
				//text.setEditable(false);
				textArea.setPreferredSize(textSize);
				textArea.setMaximumSize(textSize);
				textArea.setMinimumSize(textSize);
				
				//scrollPane
				scrollPane = ZComponentFactory.createScrollPane(375, 550,textArea);
				
				//message panel
				editorMessageLabel = ZComponentFactory.createBasicLabel("");
				editorMessageLabel.setForeground(Color.RED);
				
				Dimension topSize = new Dimension(650,30);
				JPanel topPanel = new JPanel();
				topPanel.setPreferredSize(topSize);
				topPanel.setMaximumSize(topSize);
				topPanel.setMinimumSize(topSize);
				topPanel.setLayout(new BorderLayout());
				topPanel.add(editorMessageLabel,BorderLayout.CENTER);
				
				//suideUI
				Dimension sideSize = new Dimension(175,550);
				sidePanel = new JPanel();
				sidePanel.setPreferredSize(sideSize);
				sidePanel.setMaximumSize(sideSize);
				sidePanel.setMinimumSize(sideSize);
				sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
				
				editorKey1Field = ZComponentFactory.createTextField("", 100);
				editorKey2Field = ZComponentFactory.createTextField("", 100);

				initializeEditorButtons();
				
				sidePanel.add(Box.createRigidArea(new Dimension(100,40)));
				sidePanel.add(encryptButton);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(decipherButton);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(ZComponentFactory.createBasicLabel("Use encoded key"));
				sidePanel.add(editorEncodeKeyCheckbox);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(ZComponentFactory.createBasicLabel("Key 1"));
				sidePanel.add(editorKey1Field);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(ZComponentFactory.createBasicLabel("Key 2"));
				sidePanel.add(editorKey2Field);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(loadText);
				sidePanel.add(Box.createRigidArea(new Dimension(100,20)));
				sidePanel.add(saveText);
				sidePanel.setVisible(true);
				
				// add stuff to panel
				editorPanel.add(scrollPane, BorderLayout.CENTER);
				editorPanel.add(sidePanel, BorderLayout.EAST);
				editorPanel.add(Box.createRigidArea(new Dimension(30,30)), BorderLayout.WEST);
				editorPanel.add(topPanel, BorderLayout.NORTH);
				editorPanel.add(Box.createRigidArea(new Dimension(30,30)), BorderLayout.SOUTH);
		
	}
	
	private void initializeEditorButtons(){
		
		
		Object[] options = {"Okay, got it","Okay, don't show this again","Don't encode the key!"};
			
		editorEncodeKeyCheckbox = new JCheckBox();
		editorEncodeKeyCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		editorEncodeKeyCheckbox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(editorEncodeKeyCheckbox.isSelected()){
					int result = JOptionPane.OK_OPTION;
					if(!editorWarned){
						result = JOptionPane.showOptionDialog(frame, 
								"You have chosen to encode the key."
								+ " Doing so means that anyone with this program will"
								+ " be able to decipher it. \nDo you still want to encode the key?",
								"Encode Key",
							    JOptionPane.YES_NO_CANCEL_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null,
							    options,
							    options[2]);
						}
					if(result == JOptionPane.OK_OPTION || result == JOptionPane.NO_OPTION){
						if(result == JOptionPane.NO_OPTION) editorWarned = true;
					} else {
						editorEncodeKeyCheckbox.setSelected(false);
					}
				}
				
			}});
		
		encryptButton = ZComponentFactory.createJButton("Encrypt", 100);
		encryptButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				int key1 = 0;
				int key2 = 0;
				String s = editorKey1Field.getText();
				try{
					key1 = Integer.parseInt(s);
					for(int i = 2; i < key1+1 ; i++){
						if(key1 % i == 0 && 94 % i == 0){
							editorKey1Field.setText("");
							editorMessageLabel.setText("        Key 1 must not have any common factors with 94.");
							valid = false;
						}
					}
				} catch(NumberFormatException e2){
					editorKey1Field.setText("");
					editorMessageLabel.setText("        Invalid key1 input.");
					valid = false;
				}
				s = editorKey2Field.getText();
				try{
					key2 = Integer.parseInt(s);
				} catch(NumberFormatException e2){
					editorKey2Field.setText("");
					editorMessageLabel.setText("        Invalid key2 input.");
					valid = false;
				}
				if(valid){
					editorMessageLabel.setText("");
					encryptor = new LightEncryptor(key1,key2);
					String plainText = textArea.getText();
					String crypto = encryptor.encrypt(plainText,editorEncodeKeyCheckbox.isSelected());
					textArea.setText(crypto);
				}
			}});
		
		decipherButton = ZComponentFactory.createJButton("Decipher", 100);
		decipherButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				int key1 = 0;
				int key2 = 0;
				String s = editorKey1Field.getText();
				String cypherText = textArea.getText();
				if(!editorEncodeKeyCheckbox.isSelected()){
					try{
						key1 = Integer.parseInt(s);
						for(int i = 2; i < key1+1 ; i++){
						if(key1 % i == 0 && 94 % i == 0){
							editorKey1Field.setText("");
							editorMessageLabel.setText("        Key 1 must not have any common factors with 94.");
							valid = false;
						}
					}
					} catch(NumberFormatException e2){
						editorKey1Field.setText("");
						editorMessageLabel.setText("        Invalid key1 input.");
						valid = false;
					}
					s = editorKey2Field.getText();
					try{
						key2 = Integer.parseInt(s);
					} catch(NumberFormatException e2){
						editorKey2Field.setText("");
						editorMessageLabel.setText("        Invalid key2 input.");
						valid = false;
					}
				} else {
					if(!cypherText.startsWith("l#g((c'n'*F!#gEg9")) {
						valid = false;
						editorMessageLabel.setText("        An encoded key could not be found within the cypher text.");
					}
				}
				if(valid){
					editorMessageLabel.setText("");
					decipher = new LightDecryptor(key1,key2);
					String plainText = decipher.decipher(cypherText,editorEncodeKeyCheckbox.isSelected());
					textArea.setText(plainText);
				}
				
			}});
		
		loadText = ZComponentFactory.createJButton("Load Text", 100);
		loadText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(frame);
				StringBuilder sb = new StringBuilder();
				String loadedText = null;
				
				if(result == JFileChooser.APPROVE_OPTION){
					File f = fileChooser.getSelectedFile();
					if(!f.exists()){
						editorMessageLabel.setText("        File does not exist.");
					}
					
					try(Scanner reader = new Scanner(new BufferedReader(new FileReader(f)))){
							
						while(reader.hasNextLine()){
							sb.append(reader.nextLine());
							if(reader.hasNextLine()) sb.append("\n");
						}
						
						loadedText = sb.toString();
						textArea.setText(loadedText);
						
					}catch (FileNotFoundException e1){
						editorMessageLabel.setText("        File not found.");
					}
				}
				
			}});
		
		saveText = ZComponentFactory.createJButton("Save Text", 100);
		saveText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showSaveDialog(frame);
				String savePath = null;
				String textToSave = textArea.getText();
				if(result == JFileChooser.APPROVE_OPTION){
					File f = fileChooser.getSelectedFile();
					savePath = f.getPath();
				    Pattern pattern = Pattern.compile("[\\\\:*?<>|+\"/]+");
				    Matcher matcher = pattern.matcher(f.getName());
					if(matcher.find()){
						editorMessageLabel.setText("        Invalid file name.");
						savePath = null;
					} else {
						if(!savePath.endsWith(".txt")) {
							savePath = savePath + ".txt";
							f = new File(savePath);
						}
						if(!f.exists()){
							try {
								Formatter format = new Formatter(savePath);
								format.flush();
								format.close();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						try(FileWriter writer = new FileWriter(f)){
								writer.write(textToSave);
								writer.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				
			}});
		
	}
	
	private void initializeFilePanel(){
		//file panel
		filePanel = new JPanel();
		filePanel.setPreferredSize(size);
		filePanel.setMaximumSize(size);
		filePanel.setMinimumSize(size);
		filePanel.setLayout(new BoxLayout(filePanel,BoxLayout.PAGE_AXIS));
		
		inputPathLabel = ZComponentFactory.createBasicLabel("");
		outputPathLabel = ZComponentFactory.createBasicLabel("");
		fileMessageLabel1 = ZComponentFactory.createBasicLabel("");
		fileMessageLabel1.setForeground(Color.RED);
		fileMessageLabel2 = ZComponentFactory.createBasicLabel("");
		fileMessageLabel2.setForeground(Color.GREEN.darker());
		overWriteCheckbox = new JCheckBox();
		overWriteCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		initializeFileButtons();
		
		//add stuff to file panel
		filePanel.add(Box.createRigidArea(new Dimension(100,40)));
		filePanel.add(fileMessageLabel1);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(chooseFile);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(inputPathLabel);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(ZComponentFactory.createBasicLabel("Check to overwrite the existing file"));
		filePanel.add(overWriteCheckbox);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(ZComponentFactory.createBasicLabel("...or choose a destination for the new file"));
		filePanel.add(chooseOutputFile);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(outputPathLabel);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(ZComponentFactory.createBasicLabel("Check to use or include an encoded key"));
		filePanel.add(fileEncodeKeyCheckbox);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(fileKey1Field);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(fileKey2Field);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(encryptFileButton);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(decipherFileButton);
		filePanel.add(Box.createRigidArea(new Dimension(100,20)));
		filePanel.add(fileMessageLabel2);
	}
	
	private void initializeFileButtons(){
		
		Object[] options = {"Okay, got it","Okay, don't show this again","Don't encode the key!"};
		
		
		fileEncodeKeyCheckbox = new JCheckBox();
		fileEncodeKeyCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
		fileEncodeKeyCheckbox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(fileEncodeKeyCheckbox.isSelected()){
					int result = JOptionPane.OK_OPTION;
					if(!fileWarned){
						result = JOptionPane.showOptionDialog(frame, 
								"You have chosen to encode the key."
								+ " Doing so means that anyone with this program will"
								+ " be able to decipher it. \nDo you still want to encode the key?",
								"Encode Key",
							    JOptionPane.YES_NO_CANCEL_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null,
							    options,
							    options[2]);
						}
					if(result == JOptionPane.OK_OPTION || result == JOptionPane.NO_OPTION){
						if(result == JOptionPane.NO_OPTION) fileWarned = true;
					} else {
						fileEncodeKeyCheckbox.setSelected(false);
					}
				}
				
			}});
		
		chooseFile = ZComponentFactory.createJButton("Choose File", 150);
		chooseFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(frame);
				
				if(result == JFileChooser.APPROVE_OPTION){
					File f = fileChooser.getSelectedFile();
					inputFile = f.getPath();
					inputPathLabel.setText(f.getName());
					fileMessageLabel1.setText("");
					fileMessageLabel2.setText("");
				}
				
			}});
		
		chooseOutputFile = ZComponentFactory.createJButton("Choose File", 150);
		chooseOutputFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showSaveDialog(frame);
				
				if(result == JFileChooser.APPROVE_OPTION){
					File f = fileChooser.getSelectedFile();
					outputFile = f.getPath();
				    Pattern pattern = Pattern.compile("[\\\\:*?<>|+\"/]+");
				    Matcher matcher = pattern.matcher(f.getName());
					if(matcher.find()){
						fileMessageLabel1.setText("Invalid file name.");
						outputFile = null;
					} else {
						if(!outputFile.endsWith(".txt")) {
							outputFile = outputFile + ".txt";
							f = new File(outputFile);
						}
						outputPathLabel.setText(f.getName());
						fileMessageLabel1.setText("");
						fileMessageLabel2.setText("");
					}
				}
				
			}});
		
		fileKey1Field = ZComponentFactory.createTextField("", 100);
		fileKey2Field = ZComponentFactory.createTextField("", 100);
		
		encryptFileButton = ZComponentFactory.createJButton("Encrypt", 100);
		encryptFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				int key1 = 0;
				int key2 = 0;
				
				String s = fileKey1Field.getText();
				try{
					key1 = Integer.parseInt(s);
					for(int i = 2; i < key1+1 ; i++){
					if(key1 % i == 0 && 94 % i == 0){
						fileKey1Field.setText("");
						fileMessageLabel1.setText("Key 1 must not have any common factors with 94.");
						fileMessageLabel2.setText("");
						valid = false;
					}
				}
				} catch(NumberFormatException e2){
					fileKey1Field.setText("");
					fileMessageLabel1.setText("Invalid key input.");
					fileMessageLabel2.setText("");
					valid = false;
				}
				s = fileKey2Field.getText();
				try{
					key2 = Integer.parseInt(s);
				} catch(NumberFormatException e2){
					fileKey2Field.setText("");
					fileMessageLabel1.setText("Invalid key input.");
					fileMessageLabel2.setText("");
					valid = false;
				}
				
				if(inputFile == null){
					valid = false;
					fileMessageLabel1.setText("Must select a source.");
					fileMessageLabel2.setText("");
				}
				
				if(overWriteCheckbox.isSelected()){
					outputFile = inputFile;
				}
				
				if(outputFile == null){
					valid = false;
					fileMessageLabel1.setText("Must select a destination.");
					fileMessageLabel2.setText("");
				}
				
				if(valid){
					fileMessageLabel1.setText("");
					encryptor = new LightEncryptor(key1,key2);
					try {
						encryptor.encryptFile(inputFile,outputFile,fileEncodeKeyCheckbox.isSelected());
						fileMessageLabel2.setForeground(Color.GREEN);
						fileMessageLabel2.setText("Encryption successful.");
					} catch (IOException e1) {
						fileMessageLabel2.setForeground(Color.RED);
						fileMessageLabel2.setText("Encryption failed.");
						e1.printStackTrace();
					}
					outputFile = null;
					outputPathLabel.setText("");
				}
			}});
		
		decipherFileButton = ZComponentFactory.createJButton("Decipher", 100);
		decipherFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				int key1 = 0;
				int key2 = 0;
				if(!fileEncodeKeyCheckbox.isSelected()){
					String s = fileKey1Field.getText();
					try{
						key1 = Integer.parseInt(s);
						for(int i = 2; i < key1+1 ; i++){
							if(key1 % i == 0 && 94 % i == 0){
								fileKey1Field.setText("");
								fileMessageLabel1.setText("Key 1 must not have any common factors with 94.");
								fileMessageLabel2.setText("");
								valid = false;
							}
						}
					} catch(NumberFormatException e2){
						fileKey1Field.setText("");
						valid = false;
					}
					s = fileKey2Field.getText();
					try{
						key2 = Integer.parseInt(s);
					} catch(NumberFormatException e2){
						fileKey2Field.setText("");
						fileMessageLabel1.setText("Invalid key input.");
						fileMessageLabel2.setText("");
						valid = false;
					}
				} else {
					key1 = 0;
					key2 = 0;
				}
				
				if(inputFile == null){
					valid = false;
					fileMessageLabel1.setText("Must select a source.");
					fileMessageLabel2.setText("");
				}
				
				if(overWriteCheckbox.isSelected()){
					outputFile = inputFile;
				}
				
				if(outputFile == null){
					valid = false;
					fileMessageLabel1.setText("Must select a destination.");
					fileMessageLabel2.setText("");
				}
				
				if(valid){
					fileMessageLabel1.setText("");
					decipher = new LightDecryptor(key1,key2);
					try {
						if(decipher.decipherFile(inputFile,outputFile,fileEncodeKeyCheckbox.isSelected())){
							fileMessageLabel2.setForeground(Color.BLUE);
							fileMessageLabel2.setText("Decryption successful.");
						} else {
							fileMessageLabel2.setForeground(Color.RED);
							fileMessageLabel2.setText("An encoded key was not found within the cypher text.");
						}
						
					} catch (IOException e1) {
						fileMessageLabel2.setForeground(Color.RED);
						fileMessageLabel2.setText("Decryption failed.");
						e1.printStackTrace();
					}
					outputFile = null;
					outputPathLabel.setText("");
				}
			}});
		
	}
	
	private class MyFilter extends FileFilter{

		@Override
		public boolean accept(File f) {
			if(f.isDirectory()) return true;
			String name = f.getName();
			if(name.endsWith(".txt")) return true;
			return false;
		}

		@Override
		public String getDescription() {
			
			return ".txt files";
		}
		
	}

	public static void main(String[] args) {
		MainClass m = new MainClass();
	}

}
