import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

public class MoveBall2 {
public static void main(String[] args) {
	MoveBallFrame2 frame = new MoveBallFrame2();
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	frame.setSize(600,600);
	frame.setVisible(true);
}
}
class MoveBallFrame2 extends JFrame implements KeyListener
{
	private Point2D location;
	public final boolean SHOULD_DISPLAY_INFO=true;
	public final int MOVE_AMOUNT = 25;
	public boolean UP,DOWN,LEFT,RIGHT;
	
	public MoveBallFrame2()
	{
		location = new Point(100,100);
		this.addKeyListener(this);
	}
	public void paint(Graphics g)//not "atomic" - instant
	{
		if(UP)
		{
			location.setLocation(location.getX(),location.getY()-MOVE_AMOUNT);
		}
		if(DOWN)
		{
			location.setLocation(location.getX(),location.getY()+MOVE_AMOUNT);
		}
		if(RIGHT)
		{
			location.setLocation(location.getX()+MOVE_AMOUNT,location.getY());
		}
		if(LEFT)
		{
			location.setLocation(location.getX()-MOVE_AMOUNT,location.getY());
		}
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.blue);
		g.fillOval((int)location.getX(), (int)location.getY(),50,50);
		g.drawString("BALL", (int)location.getX(), (int)location.getY()-15);
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {/* do nothing*/}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			UP=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			DOWN=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			RIGHT=true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			LEFT=true;
		}
		
		
		//paint(this.getGraphics());	//Doesn't work well //bad
		repaint();//suggest to the os that our frame needs to be repainted //good
		
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			UP=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			DOWN=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			RIGHT=false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			LEFT=false;
		}
		
		
		//paint(this.getGraphics());	//Doesn't work well //bad
		repaint();//suggest to the os that our frame needs to be repainted //good
		
	}
}
