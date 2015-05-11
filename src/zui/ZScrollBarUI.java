package zui;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ZScrollBarUI extends BasicScrollBarUI{

	Color color = Color.GRAY;
	Color color2 = Color.WHITE;
	private JButton decreaseButton;
	private JButton increaseButton;
	private boolean thumbIsPressed;
	private MouseListener thumbPressedListener;
	
	public ZScrollBarUI(Color c, Color c2){
		color = c;
		color2 = c2;
	}
	
	public ZScrollBarUI(){

	}
	 
	private class ScrollBarThumbListener extends MouseAdapter {
		 
		  @Override
		  public void mousePressed(MouseEvent e) {
			  if (getThumbBounds().contains(e.getX(), e.getY())) {
				  thumbIsPressed = true;
				  getScrollBar().repaint();
			  }
		  }
		 
		  @Override
		  public void mouseReleased(MouseEvent e) {
			  thumbIsPressed = false;
			  getScrollBar().repaint();
		  }
	}
	
	@Override
	protected void installDefaults() {
		super.installDefaults();
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}
 
	protected MouseListener createThumbPressedListener() {
		return new ScrollBarThumbListener();
	}
 
	@Override
	protected void installListeners() {
		super.installListeners();
		if ((this.thumbPressedListener = createThumbPressedListener()) != null) {
			this.scrollbar.addMouseListener(this.thumbPressedListener);
		}
	}
 
	@Override
	protected void uninstallListeners() {
		if (this.thumbPressedListener != null) {
			this.scrollbar.removeMouseListener(this.thumbPressedListener);
			this.thumbPressedListener = null;
		}
		super.uninstallListeners();
	}
 
	@Override
	public Dimension getPreferredSize(JComponent c) {
		if (this.scrollbar.getOrientation() == Adjustable.VERTICAL) {
			return new Dimension(20, 53 + 10);
		} else {
			return new Dimension(100, 20);
		}
	}
 
	@Override
	public Dimension getMinimumSize(JComponent c) {
		if (this.scrollbar.getOrientation() == Adjustable.VERTICAL) {
			return new Dimension(18, 40);
		} else {
			return new Dimension(40, 18);
		}	
	}
		  
	@Override
	protected void configureScrollBarColors() {
		super.configureScrollBarColors();
		this.thumbColor = color;
		this.thumbHighlightColor = Color.GRAY;
	}
  
  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    int x = (int) trackBounds.getX();
    int y = (int) trackBounds.getY();
    int w = (int) trackBounds.getWidth();
    int h = (int) trackBounds.getHeight();
   
    if (this.scrollbar.getOrientation() == Adjustable.HORIZONTAL) {
	    g.setColor(color2);
	    g.drawLine(x, 0, x + w - 1, 0);
	    g.setColor(color2);
	    g.drawLine(x, 1, x + w - 1, 1);
	    g.setColor(color2);
	    g.drawLine(x, 2, x + w - 1, 2);
	    g.setColor(color2);
	    g.fillRect(x, 3, w, h - 6);
	    g.setColor(color2);
	    g.drawLine(x, h - 3, x + w - 1, h - 3);
	    g.setColor(color2);
	    g.drawLine(x, h - 2, x + w - 1, h - 2);
	    g.setColor(color2);;
	    g.drawLine(x, h - 1, x + w - 1, h - 1);
	    } else {
	    g.setColor(color2);
	    g.drawLine(0, y, 0, y + h - 1);
	    g.setColor(color2);
	    g.drawLine(1, y, 1, y + h - 1);
	    g.setColor(color2);
	    g.drawLine(2, y, 2, y + h - 1);
	    g.setColor(color2);
	    g.fillRect(3, y, w - 6, h);
	    g.setColor(color2);
	    g.drawLine(w - 3, y, w - 3, h + y - 1);
	    g.setColor(color2);
	    g.drawLine(w - 2, y, w - 2, h + y - 1);
	    g.setColor(color2);
	    g.drawLine(w - 1, y, w - 1, h + y - 1);
    }
    }
   
    @Override
    protected JButton createDecreaseButton(int orientation) {

	    	this.decreaseButton = new JButton();
	    	this.decreaseButton.setBackground(color2);
	    	this.decreaseButton.setSize(0,0);
	    	this.decreaseButton.setPreferredSize(new Dimension(0,0));
			this.decreaseButton.setMaximumSize(new Dimension(0,0));

    return this.decreaseButton;
    }
   
    @Override
    protected JButton createIncreaseButton(int orientation) {
			this.increaseButton = new JButton();
			this.increaseButton.setBackground(color2);
			this.increaseButton.setPreferredSize(new Dimension(0,0));
			this.increaseButton.setMaximumSize(new Dimension(0,0));
	    	this.increaseButton.setSize(0,0);

    	return this.increaseButton;
    }
   
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	    int x = (int)thumbBounds.getX();
	    int y = (int)thumbBounds.getY();
	    int w = (int)thumbBounds.getWidth();
	    int h = (int)thumbBounds.getHeight();
   
	    if (c.isEnabled() && (w > 0) && (h > 0)) {
		    if (this.scrollbar.getOrientation() == Adjustable.HORIZONTAL) {
		    	h -= 2;
		    	y++;
		    	drawHorizThumb(g, x, y, w, h);
		    } else {
		    	w -= 2;
		    	x++;
		    	drawVertThumb(g, x, y, w, h);
		    }
	    }
    }
   
    private void drawHorizThumb(Graphics g, int x, int y, int w, int h) {
    	if (!this.thumbIsPressed) {
    		g.setColor(color);
		    g.drawRect(1 + x, y + 2, w - 2, h - 4);
		    g.drawRect(5 + x, y + 6, w - 10, h - 12);
		    } else {
	    		g.setColor(color);
			    g.fillRect(1 + x, y + 2, w - 2, h - 4);
			    g.setColor(color2);
			    g.fillRect(5 + x, y + 6, w - 10, h - 12);
		}

    }
   
    private void drawVertThumb(Graphics g, int x, int y, int w, int h) {
    	if (!this.thumbIsPressed) {
    		g.setColor(color);
    		g.drawRect(x + 2, y + 1, w - 4, h - 2);
    		g.drawRect(x + 6, y + 5, w - 12, h - 10);
    	} else {
    		g.setColor(color);
    		g.fillRect(x + 2, y + 1, w - 4, h - 2);
    		g.setColor(color2);
    		g.fillRect(x + 6, y + 5, w - 12, h - 10);
    	}
    }
	    
	    
	  private JScrollBar getScrollBar() {
	    	return this.scrollbar;
	  }
// 

}