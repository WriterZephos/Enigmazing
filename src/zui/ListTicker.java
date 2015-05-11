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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ListTicker extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField field;
	int count;
	int upper;
	int lower;
	int width;
	int starting;
	ArrayList<String> options;
	private Font tinyFont = new Font(Font.SERIF, Font.BOLD,12);
	private Font bigFont = new Font(Font.SERIF, Font.BOLD,20);
	
	public ListTicker(int start, String...list){

		lower = 0;
		upper = 0;
		count = start;
		starting = start;
		width = 0;
		options = new ArrayList<String>();
		
		FontMetrics fm = getFontMetrics(bigFont);
		
		for(int i = 0 ; i < list.length; i++){
			
			upper = i;
			options.add(list[i]);
			String tester = list[i] + "   ";
			
			if(fm.stringWidth(tester) > width){
				width = fm.stringWidth(tester);
			}
		}
				
		setMaximumSize(new Dimension(40 + width, 30));
		setMinimumSize(new Dimension(40 + width, 30));
		setPreferredSize(new Dimension(40 + width,30));
		setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		
		field = ZComponentFactory.createTextField(" ",20 + width);
		field.setFont(bigFont);
		field.setText("" + options.get(count) +" ");
		field.setMaximumSize(new Dimension(width, 30));
		field.setMinimumSize(new Dimension(width, 30));
		field.setPreferredSize(new Dimension(width, 30));
		field.setAlignmentY(Component.CENTER_ALIGNMENT);
		field.setHorizontalAlignment(JTextField.RIGHT);
		
		field.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				boolean match = false;
				for (int i = 0 ; i < upper+1 ; i++){
					if (field.getText().replaceAll("\\s","").equalsIgnoreCase(options.get(i))){
						match = true;
						count = i;
						field.setText(options.get(count).toUpperCase() + " ");
					}
				}
				if(!match){
					field.setText(options.get(count) + " ");
				}
				
			}});
		
		JButton up = ZComponentFactory.createJButton("+",20);
		up.setMaximumSize(new Dimension(40, 15));
		up.setMinimumSize(new Dimension(40, 15));
		up.setPreferredSize(new Dimension(40,15));
		up.setFont(tinyFont);
		
		up.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count < upper) {
					count++;
					field.setText(options.get(count) + " ");
					field.revalidate();
					field.repaint();
				}
				
			}});
		
		JButton down = ZComponentFactory.createJButton("-",20);
		down.setMaximumSize(new Dimension(40, 15));
		down.setMinimumSize(new Dimension(40, 15));
		down.setPreferredSize(new Dimension(40,15));
		down.setFont(tinyFont);
		
		down.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (count > lower) {
					count--;
					field.setText(options.get(count) +" ");
					field.revalidate();
					field.repaint();
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
	}
	
	
	
	public int getValue(){		
		return count;

	}
	public String getOption(){		
		return options.get(count);
	}
	
	public void setCount(int i){
		if( i <= upper && i >= lower){
			count = i;
			field.setText(options.get(count) +" ");
			field.revalidate();
			field.repaint();
		}
	}
	
	
	public void reset(){
		count = starting;
		field.setText(options.get(count) +" ");
	}
	
}
