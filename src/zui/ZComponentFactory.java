package zui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class ZComponentFactory {

	private static Font headerFont = new Font(Font.SERIF,Font.BOLD,24);
	private static Font bigFont = new Font(Font.SERIF,Font.BOLD,38);
	private static Font basicFont = new Font(Font.SERIF,Font.BOLD,18);
	private static Font weakFont = new Font(Font.SERIF,Font.PLAIN,18);
	
	public static JLabel createHeaderLabel(String text, Color foreground){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(foreground);
		label.setFont(headerFont);
		return label;
	}
	
	public static JLabel createHeaderLabel(String text){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(headerFont);
		return label;
	}
	
	
	
	
	
	
	
	public static JLabel createBigHeaderLabel(String text, Color foreground){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(foreground);
		label.setFont(bigFont);
		return label;
	}
	
	public static JLabel createBigHeaderLabel(String text){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(bigFont);
		return label;
	}
	
	
	
	
	
	
	public static JLabel createBasicLabel(String text, Color foreground){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(foreground);
		label.setFont(basicFont);
		return label;
	}
	
	public static JLabel createBasicLabel(String text){
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(basicFont);
		return label;
	}
	
	
	
	
	
	
	
	
	
	public static JScrollPane createScrollPane(int width, int height, Component client, Color foreground, Color background){
		JScrollPane pane = new JScrollPane(client);
		pane.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.setMinimumSize(new Dimension(width, height));
		pane.setPreferredSize(new Dimension(width, height));
		pane.setForeground(foreground);
		pane.setFont(basicFont);
		pane.setBackground(background);
		pane.setBorder(new LineBorder(foreground));
		JPanel corner = new JPanel();
		corner.setBackground(background);
		corner.setBorder(new LineBorder(foreground));
		pane.getHorizontalScrollBar().setUI(new ZScrollBarUI(foreground,background));
		pane.getVerticalScrollBar().setUI(new ZScrollBarUI(foreground,background));
		pane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, corner);
		return pane;
	}
	
	public static JScrollPane createScrollPane(int width, int height, Component client){
		JScrollPane pane = new JScrollPane(client);
		pane.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.setMinimumSize(new Dimension(width, height));
		pane.setPreferredSize(new Dimension(width + 200, height));
		pane.setForeground(Color.BLACK);
		pane.setFont(basicFont);
		pane.setBackground(Color.WHITE);
		pane.setOpaque(true);
		pane.setBorder(new LineBorder(Color.LIGHT_GRAY));
		JPanel corner = new JPanel();
		corner.setBackground(Color.LIGHT_GRAY);
		pane.getHorizontalScrollBar().setUI(new ZScrollBarUI());
		pane.getVerticalScrollBar().setUI(new ZScrollBarUI());
		pane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, corner);
		return pane;
	}
	
	
	
	
	
	
	
	
	
	public static JButton createJButton(String text, int width,  Color foreground, Color background, Color highlight){
		JButton button = new JButton(text);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, 30));
		button.setPreferredSize(new Dimension(width,30));
		button.setVerticalAlignment(SwingConstants.BOTTOM);
		button.setBorder(new LineBorder(foreground));
		button.setFocusable(false);
		button.setOpaque(true);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		button.setFont(basicFont);
		button.setBackground(background);
		button.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				//AudioManager.setSound(5);
				button.setBorder(new LineBorder(highlight));
				button.setBackground(background);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBorder(new LineBorder(foreground));
				
			}
		
			@Override
			public void mousePressed(MouseEvent e) {
				button.setBorder(new BevelBorder(BevelBorder.LOWERED, foreground, foreground));
				button.setBackground(background);
				button.setForeground(foreground);
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				button.setBorder(new LineBorder(highlight));
				button.setForeground(foreground);
				
			}});
			
		return button;
	}
	
	public static JButton createJButton(String text, int width){
		JButton button = new JButton(text);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, 30));
		button.setPreferredSize(new Dimension(width,30));
		button.setVerticalAlignment(SwingConstants.BOTTOM);
		button.setBorder(new LineBorder(Color.LIGHT_GRAY));
		button.setFocusable(false);
		button.setOpaque(true);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		button.setFont(basicFont);
		//button.setBackground(Color.BLACK);
		button.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				//Play Sound Here
				button.setBorder(new LineBorder(Color.GRAY));
				//button.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBorder(new LineBorder(Color.LIGHT_GRAY));
				
			}
		
			@Override
			public void mousePressed(MouseEvent e) {
				button.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY));
				//button.setBackground(Color.LIGHT_GRAY);
				//button.setForeground(Color.BLACK);
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				button.setBorder(new LineBorder(Color.LIGHT_GRAY));
				button.setForeground(Color.BLACK);
				
			}});
			
		return button;
	}
	
	
	
	
	
	
	
	
	public static JTextField createTextField(String s, int width, Color border, Color foreground, Color background){
		JTextField field = new JTextField(s,25);
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.setMaximumSize(new Dimension(width, 30));
		field.setPreferredSize(new Dimension(width,30));
		field.setBorder(new LineBorder(border));
		field.setForeground(foreground);
		field.setFont(weakFont);
		field.setBackground(background);
		field.setCaretColor(foreground);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		return field;
	}
	
	public static JTextField createTextField(String s, int width){
		JTextField field = new JTextField(s,25);
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.setMaximumSize(new Dimension(width, 30));
		field.setPreferredSize(new Dimension(width,30));
		field.setBorder(new LineBorder(Color.LIGHT_GRAY));
		field.setForeground(Color.BLACK);
		field.setFont(weakFont);
		field.setBackground(Color.WHITE);
		field.setCaretColor(Color.BLACK);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		return field;
	}
	
	
	
	
	
	
	
	public static JTextArea createTextArea(String s, int width, int height, Color border, Color foreground, Color background){
		JTextArea field = new JTextArea(s);
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.setMaximumSize(new Dimension(width, height));
		field.setPreferredSize(new Dimension(width,height));
		field.setBorder(new LineBorder(border));
		field.setForeground(foreground);
		field.setFont(weakFont);
		field.setBackground(background);
		field.setCaretColor(foreground);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		return field;
	}
	
	
	
	public static JTextArea createTextArea(String s, int width, int height){
		JTextArea field = new JTextArea(s);
		field.setAlignmentX(Component.CENTER_ALIGNMENT);
		field.setMaximumSize(new Dimension(width, height));
		field.setPreferredSize(new Dimension(width, height));
		field.setBorder(new LineBorder(Color.LIGHT_GRAY));
		field.setForeground(Color.BLACK);
		field.setFont(weakFont);
		//field.setBackground(Color.WHITE);
		field.setCaretColor(Color.BLACK);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		return field;
	}
	
	
	public static JScrollPane createScrollPaneTextArea(String s, int width, int height, Color border, Color foreground, Color background){
		JTextArea field = new JTextArea(s);
		field.setForeground(foreground);
		field.setFont(weakFont);
		field.setBackground(background);
		field.setCaretColor(foreground);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		
		field.setLineWrap(true);
		field.setWrapStyleWord(true);
		JScrollPane pane = createScrollPane(height, width, field, background, background);
		
		return pane;
	}
	
	
	
	public static JScrollPane createScrollPaneTextArea(String s, int width, int height){
		JTextArea field = new JTextArea(s);
		field.setForeground(Color.BLACK);
		field.setFont(weakFont);
		field.setBackground(Color.WHITE);
		field.setCaretColor(Color.BLACK);
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(s)){
					field.setText("");
					field.setFont(basicFont);
					field.setCaretPosition(0);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setFont(weakFont);
					field.setText(s);
				}
				
			}
			
		});
		
		field.setLineWrap(true);
		field.setWrapStyleWord(true);
		JScrollPane pane = createScrollPane(height, width, field);
		
		return pane;
	}
	
	
	public static JButton createIconButton(int width, int height, ImageIcon img, Color foreground, Color border, Color background){
		JButton button = new JButton();
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, height));
		button.setPreferredSize(new Dimension(width,height));
		button.setIcon(img);
		button.setBorder(new LineBorder(border));
		button.setFocusable(false);
		button.setOpaque(true);
		button.setForeground(foreground);
		button.setFocusPainted(false);
		button.setBackground(background);
		button.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				//Play Sound Here
				button.setBorder(new LineBorder(foreground));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBorder(new LineBorder(border));
			}
		
			@Override
			public void mousePressed(MouseEvent e) {
		
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}});
			
		return button;

	}
	
	public static JButton createIconButton(int width, int height, ImageIcon img){
		JButton button = new JButton();
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, height));
		button.setPreferredSize(new Dimension(width,height));
		button.setIcon(img);
		button.setBorder(new LineBorder(Color.LIGHT_GRAY));
		button.setFocusable(false);
		button.setOpaque(true);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		//button.setBackground(Color.WHITE);
		button.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				//Play Sound Here
				button.setBorder(new LineBorder(Color.GRAY));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBorder(new LineBorder(Color.LIGHT_GRAY));
			}
		
			@Override
			public void mousePressed(MouseEvent e) {
		
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}});
			
		return button;

	}
	
}

