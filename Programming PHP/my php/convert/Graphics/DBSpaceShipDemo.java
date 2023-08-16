import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DBSpaceShipDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Basic Example");
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,800);
		BasicsPanel panel = new BasicsPanel(1200,800);
		frame.add(panel);
		frame.setVisible(true);
		panel.loop();
	}
}
class BasicsPanel extends JPanel
{
	Image Buffer;
	int width,height;
	
	public BasicsPanel(int w, int h)
	{
		width = w;
	   height = h;
	   
	}
	
	public void loop()
	{
		//setup the graphics
		Buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) Buffer.getGraphics();
		
		SpaceShip ss = new SpaceShip();
		JFrame frame = (JFrame) this.getTopLevelAncestor();
		
		frame.addKeyListener(ss);//cannot add keyListener(heavy weight) to the panel(light Weight)
		
		ArrayList<Asteroid>aster = new ArrayList<>();
		
		
		aster.add(new Asteroid(width,height));
		
		while(true)
		{
			g.setColor(Color.white);
			g.fillRect(0,0,Buffer.getWidth(null),Buffer.getHeight(null));
			
			ss.drawShip(g);
			ss.Move();
			
			for(Asteroid a : aster)
			{
				a.move();
				a.draw(g);
			
			}
			
			//if we add an if statement stay if it has not been 20milisecs don't draw.
				//this would make the loop run as fast as possible 
			//this can be seprated from the game loop
			this.getGraphics().drawImage(Buffer, 0, 0, null);
			try{Thread.sleep(20);}catch(Exception e){}
		}
	}
}
//in the real implemtation a interface/abstract should parent any colidable child   
class Asteroid
{
	Vector2D location,velocity;
	int radius = 100,width,height;
	
	
	public Asteroid(int width, int height)
	{
		this.width = width;
		this.height = height;
		//randomly genertaion the location and the velocity
		location = new Vector2D((float)Math.random()*(width-radius),(float)Math.random()*(height-radius));
		//move from -1 to 1 
		velocity = new Vector2D((float)Math.random()*2-1,(float)Math.random()*2-1);
		velocity = velocity.multiply(10);
	}
	
	final int PADDING = 10;
	public void move()
	{
		//location = location.add(velocity);
		
		//if it moves off screen set it back to the other end for the screen
		if(location.getX() < -PADDING)
			location.setX(width);
		
		if(location.getY() < -PADDING)
			location.setY(height);
		
		if(location.getX() > width+PADDING)
			location.setX(0);
		
		if(location.getY() > height + PADDING)
			location.setY(0);
	}

	/*
	 * public boolean checkCollision(Shape s) { if(new
	 * Ellipse2D.Float(location.getX(), location.getY(), radius, radius))) {
	 * 
	 * } }
	 */
	public void draw(Graphics2D g)
	{
		g.setColor(new Color(100,100,30));
		g.fillOval((int)location.getX(), (int)location.getY(), radius, radius);
	}
	
}

class SpaceShip implements KeyListener
{
	Image shipImage;
	//TODO move this class into a separate file, currently in MyDrawingFrameMultiballWithCollision.java
	Vector2D location, velocity;
	int rotation; //0-360
	
	public boolean UP,DOWN,LEFT,RIGHT;
	public SpaceShip()
	{
		location = new Vector2D(500,400);
		velocity = new Vector2D(0,0);
		shipImage = new ImageIcon("F:\\eclipse-jee-2021-12-R-win32-x86_64\\"
		+ "eclipse\\Workspace\\Graphics\\src\\NicePng_spaceship-png_138961.png")
				.getImage();//no casting needed with the .getImage method
							//more resource intensive but less particular with image types
		
	}
	//for max preformance, we might want to store the collision rectangle for the spaceship
	public Shape getCollision()
	{
		Rectangle collision = new Rectangle(0,0,50, 45);//awt likes int's because pixels don't tend to be fractions
		
		double rad = Math.toRadians(rotation);
		AffineTransform at = new AffineTransform();
		at.translate((int)location.getX(),(int)location.getY());
		at.rotate(rad,25,22);//rotating starting from the offest
		
		return at.createTransformedShape(collision);
	}
	
	public void drawShip(Graphics2D g)
	{
		
		g.setColor(Color.magenta);
		g.draw(getCollision());
		double rad = Math.toRadians(rotation+90);
		AffineTransform at = new AffineTransform();
		at.translate(location.getX(),location.getY());
		at.scale(.5f, .5f);
		at.rotate(rad,50,45);//rotating starting from the offest
		g.setTransform(at);
		g.drawImage(shipImage,0, 0,null);
		g.setTransform(new AffineTransform());
		
	}
	public void Move()
	{
		double rad = Math.toRadians(rotation);
		AffineTransform at = new AffineTransform();
		if (UP)
		{
			velocity = velocity.add(new Vector2D((float)Math.cos(rad),(float)Math.sin(rad)));
			
		}
		if (DOWN)
		{
			velocity = velocity.add(new Vector2D((float)Math.cos(rad),(float)Math.sin(rad)).multiply(-1));
		}
		if (RIGHT)
		{
			rotation+=3;
		}
		if (LEFT)
		{
			rotation -= 3; 
		}
		location = location.add(velocity);
		velocity = velocity.multiply(.90f);
	}

	@Override
	public void keyTyped(KeyEvent e) {/*do nothing*/}

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
             
    }
	
}

/*
 * import java.awt.Color; import java.awt.Graphics2D; import java.awt.Image;
 * import java.awt.image.BufferedImage;
 * 
 * import javax.swing.JFrame; import javax.swing.JPanel;
 * 
 * public class DBBasics { public static void main(String[] args) { JFrame frame
 * = new JFrame("Basic Example");
 * 
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * frame.setSize(1200,800); BasicsPanel panel = new BasicsPanel(1200,800);
 * frame.add(panel); frame.setVisible(true); panel.loop(); } } class BasicsPanel
 * extends JPanel { Image Buffer; int width,height;
 * 
 * public BasicsPanel(int w, int h) { width = w; height = h;
 * 
 * }
 * 
 * public void loop() { //setup the graphics Buffer = new
 * BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB); Graphics2D g =
 * (Graphics2D) Buffer.getGraphics();
 * 
 * while(true) { g.setColor(Color.gray);
 * g.fillRect(0,0,Buffer.getWidth(null),Buffer.getHeight(null));
 * g.setColor(Color.green); g.fillRect(100, 100, 100, 100);
 * this.getGraphics().drawImage(Buffer, 0, 0, null);
 * try{Thread.sleep(20);}catch(Exception e){} } } }
 */

