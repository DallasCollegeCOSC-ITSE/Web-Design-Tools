import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

public class MoveBall3 
{
	public static void main(String[] args) 
	{
		MoveBallFrame3 frame = new MoveBallFrame3();
		frame.setSize(600,600);
		frame.setVisible(true);
	}
}
class MoveBallFrame3 extends JFrame implements KeyListener, MouseListener, MouseMotionListener
{
	private Point2D location;
	public final boolean SHOULD_DISPLAY_INFO=true;
	public final int MOVE_AMOUNT = 25;
	
	public boolean UP, DOWN, RIGHT, LEFT, CLICK;
	
	public MoveBallFrame3()
	{
		location = new Point(100,100);
		//create the keyListener (give it my location so that it can change it)
		
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		if (UP)
		{
			location.setLocation(location.getX(),location.getY()-MOVE_AMOUNT);   	
		}
		if (DOWN)
		{
			location.setLocation(location.getX(),location.getY()+MOVE_AMOUNT);   	
		}
		if (RIGHT)
		{
			location.setLocation(location.getX()+MOVE_AMOUNT,location.getY());   	
		}
		if (LEFT)
		{
			location.setLocation(location.getX()-MOVE_AMOUNT,location.getY());   	
		}
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.blue);
		g.fillOval((int)location.getX(), (int)location.getY(), 50, 50);	
		g.drawString("Me", (int)location.getX()+17, (int)location.getY());
	}
	
	//Listener methods that must be defined
	//KeyListener
	public void keyTyped(KeyEvent e) { /*do nothing*/ }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) 
    { 
    	if (e.getKeyCode() == KeyEvent.VK_UP)
    	{
    		UP=true;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_DOWN)
    	{
    		DOWN=true;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    	{
    		RIGHT=true;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_LEFT)
    	{
    		LEFT=true;
    	}
    	repaint();
    }


    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) 
    {
    	if (e.getKeyCode() == KeyEvent.VK_UP)
    	{
    		UP=false;
     	}
    	if (e.getKeyCode() == KeyEvent.VK_DOWN)
    	{
    		DOWN=false;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    	{
    		RIGHT=false;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_LEFT)
    	{
    		LEFT=false;
    	}
        repaint();        
    }
    
    //MouseMotionListener
    public void mouseDragged(MouseEvent e)
    {
    	if (CLICK)
    	{
	    	location.setLocation(e.getX(), e.getY());
	    	repaint();
    	}
    }
    public void mouseMoved(MouseEvent e)
    {    	
    }
   
    //MouseListener
    public void mouseClicked(MouseEvent e)
    {    	
    }
    public void mouseEntered(MouseEvent e)
    {    	
    }
    public void mouseExited(MouseEvent e)
    {    	
    }
    public void mousePressed(MouseEvent e)
    {
    	//System.out.println("Click "+e.getX() +" < "+ location.getX()+25);
    	if (e.getX() < location.getX()+35 && e.getX() > location.getX()-15 && 
    		e.getY() < location.getY()+35 && e.getY() > location.getY()-15)
    	{
    		System.out.println("Click");
    		CLICK=true;
    	}
    }
    public void mouseReleased(MouseEvent e)
    {
    	CLICK=false;
    }
}
