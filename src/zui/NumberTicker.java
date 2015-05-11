package zui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberTicker extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field;
	int count;
	int upper;
	int lower;
	int minChar;
	int increment;
	private Font tinyFont = new Font(Font.SERIF,Font.BOLD,12);
	private Font bigFont = new Font(Font.SERIF,Font.BOLD,20);
	private boolean cycle;
	private boolean inclusiveLow;
	private boolean inclusiveHigh;
	
	public NumberTicker(int upp, int low,boolean cy, boolean incLow, boolean incHigh){
		count = low;
		if (!incLow) count ++;
		upper = upp;
		lower = low;
		minChar = 1;
		increment = 1;
		cycle = cy;
		inclusiveLow = incLow;
		inclusiveHigh = incHigh;
		initialize();
	
	}
	
	public NumberTicker(int upp, int low, int mC,boolean cy,boolean incLow, boolean incHigh){
		count = low;
		if (!incLow) count ++;
		upper = upp;
		lower = low;
		minChar = mC;
		increment = 1;
		cycle = cy;
		inclusiveLow = incLow;
		inclusiveHigh = incHigh;
		initialize();
	
	}
	
	public NumberTicker(int upp, int low, int mC, int iC, boolean cy,boolean incLow, boolean incHigh){
		count = low;
		if (!incLow) count ++;
		upper = upp;
		lower = low;
		minChar = mC;
		increment = iC;
		cycle = cy;
		inclusiveLow = incLow;
		inclusiveHigh = incHigh;
		initialize();
	
	}
		
	private void initialize(){
		FontMetrics fm = getFontMetrics(bigFont);
		int width = fm.stringWidth(String.valueOf(upper) + "   ");
		
		setMaximumSize(new Dimension(40 + width, 30));
		setMinimumSize(new Dimension(40 + width, 15));
		setPreferredSize(new Dimension(40 + width,30));
		setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		
		field = ZComponentFactory.createTextField(" ",30);
		field.setFont(bigFont);
		field.setMaximumSize(new Dimension(width, 30));
		field.setMinimumSize(new Dimension(width, 15));
		field.setPreferredSize(new Dimension(width,30));
		field.setHorizontalAlignment(JTextField.RIGHT);
		field.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				String s = field.getText().replaceAll("\\s", "");
				if (!s.matches("[0-9]+")){
					updateText();
					return;
				} else if (Integer.parseInt(s) < upper && Integer.parseInt(s) > lower){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) <= upper && inclusiveHigh){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) >= lower && inclusiveLow){
					count = Integer.parseInt(s);
				}
				
				updateText();
				
				
			}});
		
		JButton up = ZComponentFactory.createJButton("+",20);
		up.setMaximumSize(new Dimension(20, 15));
		up.setMinimumSize(new Dimension(40, 15));
		up.setPreferredSize(new Dimension(20,15));
		up.setFont(tinyFont);
		up.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = field.getText().replaceAll("\\s", "");
				if (!s.matches("[0-9]+")){
					updateText();
					return;
				} else if (Integer.parseInt(s) < upper && Integer.parseInt(s) > lower){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) <= upper && inclusiveHigh){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) >= lower && inclusiveLow){
					count = Integer.parseInt(s);
				}
				
				if ((count + increment) < upper) {
					count += increment;
					updateText();
				}  else if ((count + increment) == upper && inclusiveHigh){
					count += increment;
					updateText();
				} else {
					count = (cycle ? lower + (increment - (upper - count)) : upper);
					if(count == lower && !inclusiveLow) count++;
					updateText();
				}
				
			}});
		
		JButton down = ZComponentFactory.createJButton("-",20);
		down.setMaximumSize(new Dimension(20, 15));
		down.setMinimumSize(new Dimension(40, 15));
		down.setPreferredSize(new Dimension(20,15));
		down.setFont(tinyFont);
		down.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = field.getText().replaceAll("\\s", "");
				if (!s.matches("[0-9]+")){
					updateText();
					return;
				} else if (Integer.parseInt(s) < upper && Integer.parseInt(s) > lower){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) <= upper && inclusiveHigh){
					count = Integer.parseInt(s);
				} else if (Integer.parseInt(s) >= lower && inclusiveLow){
					count = Integer.parseInt(s);
				}
				
				if ((count - increment) > lower) {
					count -= increment;
					updateText();
				} else if ((count - increment) == lower && inclusiveLow){
					count -= increment;
					updateText();
				} else{
					count = (cycle ? upper - (increment - (count - lower)) : lower);
					if(count == upper && !inclusiveHigh) count--;
					updateText();
				}
				
			}});
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,1,0,0));
		panel2.setMaximumSize(new Dimension(40, 30));
		panel2.setMinimumSize(new Dimension(40, 30));
		panel2.setPreferredSize(new Dimension(40,30));
		panel2.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel2.add(up);
		panel2.add(down);
		
		add(field);
		add(panel2);
		
		updateText();
	}
	
	public int getValue(){
		
		return count;
	}
	
	private void updateText(){
		
		int zeroes = minChar - String.valueOf(count).length();
		
		StringBuilder sb = new StringBuilder();
		if (zeroes > 0){
			for (int i = 0 ; i < zeroes ; i++){
				sb.append("0");
			}	
		}
		
		sb.append(count);
		sb.append(" ");

		field.setText(sb.toString());
		field.revalidate();
		field.repaint();
	}
	
	public void setCount(int newCount){
		if (newCount >= lower && newCount <= upper){
			count = newCount;
			updateText();
		}
		
	}
	
	public void reset(){
		count = lower;
		if(!inclusiveLow) count++;
		updateText();
	}
	
	
}
