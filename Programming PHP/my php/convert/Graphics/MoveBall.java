import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
//AWT - Abstract Window toolkit _ system dependent (Heavy weight)
//vs
//Swing - system independent (lightweight) (MVC - model-view-controller)
//components
	//buttons,Lable,menues,menuitems,textboxes

//object
//component
//Container(aggregation with composition)


//Window (heavy weight AWT)
//Frame
//JFrame (heavyweight JDialog/JPanel???)


public class MoveBall {
public static void main(String[] args) {
	//Creating the JFrame
	MoveBallFrame frame = new MoveBallFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(600,600);
	frame.setVisible(true);

}

}
class MoveBallFrame extends JFrame implements KeyListener
{
	
	private Point2D location;	//x and y
	public final boolean SHOULD_DISPLAY_INFO=false;
	public final int MOVE_AMOUNT = 25;

	public MoveBallFrame()
	{
		location = new Point(100,100);
		
		//create the KeyListener (give the location so that it can change it)
		this.addKeyListener(this);
	}
	
	//paint is automagically called by the OS when your window needs to be redrawn
	//Graphics = paintBrush
	public void paint(Graphics g)//not "atomic" - instant
	{
		g.setColor(Color.yellow);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		g.setColor(Color.blue);
		g.fillOval((int)location.getX()-25,(int)location.getY()-25,50,50);
	}
	//Listener methods that must be defined
	
	@Override
	public void keyTyped(KeyEvent e) {/*do nothing  */}

	/** Handle the key-pressed event from the text feild. */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			location.setLocation(location.getX(),location.getY()-MOVE_AMOUNT);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			location.setLocation(location.getX(),location.getY()+MOVE_AMOUNT);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			location.setLocation(location.getX()+MOVE_AMOUNT,location.getY());
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			location.setLocation(location.getX()-MOVE_AMOUNT,location.getY());
		}
		//paint(this.getGraphics());	//Doesn't work well //bad
		repaint();//suggest to the os that our frame needs to be repainted //good
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}