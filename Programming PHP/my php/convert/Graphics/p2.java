
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//No more random imports

public class p2 {

	public static void main(String[] args) {

		
		drawingPane frame = new drawingPane();
		frame.setSize(800,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setup();
		frame.Game();
	}

}
class drawingPane extends JFrame
{
	Image raster;
	Graphics2D rasterGraphics;
	public static Ship myShip;
	public ArrayList<Vector2D> stars = new ArrayList<>();
	public void setup()
	{
		raster = this.createImage(600, 600);
		rasterGraphics = (Graphics2D) raster.getGraphics();
		this.createBackground();
	}
		
	public void Game()
	{
		int FrameNumber=0;
		
		//player ship
		myShip = new Ship();	
		this.addKeyListener(myShip);
		
		int speed = 10;
		
		while (true)
		{
			FrameNumber++;
				
			if (FrameNumber % 50 == 0)
				new Obstical();
			
			Obstical.checkCollision(myShip);
			
			//background stuffs
			rasterGraphics.setColor(Color.darkGray);
			rasterGraphics.fillRect(0,0,this.getWidth(),this.getHeight());
			rasterGraphics.setColor(Color.white);
			for (Vector2D vect : stars)
			{
				rasterGraphics.drawOval((int)vect.getX(),(int)vect.getY(),3,3);
				vect.setY(vect.getY()+speed);
				if (vect.getY() > 600)
				{
					vect.setY((int)(Math.random()*-100));
					vect.setX((int)(Math.random()*this.getWidth()));
				}
				
			}
			for (int i=0;i<ScreenObj.ScreenObjs.size();i++)
			{
				ScreenObj so = ScreenObj.ScreenObjs.get(i);
				//Act on the various objects
				so.Act(FrameNumber);		
				
				//Draw various screen objects							
				so.Draw(rasterGraphics);
				
			}

			//Draw final Image
			Graphics frameG = this.getGraphics();
			frameG.drawImage(raster,0,0,getWidth(),getHeight(),null);

			try{
				Thread.sleep(10);
			}catch(Exception e){}
		}
	}

	public void createBackground()
	{
		int H = this.getHeight();
		int W = this.getWidth();
				
		for (int i=0;i<100;i++)
		{
			stars.add(new Vector2D((int)(Math.random()*W),(int)(Math.random()*H)));
		}		
	}
}
abstract class ScreenObj
{
	public static ArrayList<ScreenObj> ScreenObjs = new ArrayList<ScreenObj>();
	public static final AffineTransform identity = new AffineTransform();
	public Vector2D Velocity; //x,y  (velocityVector)
	public Vector2D Location; //x y  (pointVector)
	public Vector2D Direction; //x,y (dirVector)
	public Vector2D Size;
	public java.awt.Image image;
	public boolean isAlive;
	
	public abstract void Act(int frame);
	public abstract void Draw(Graphics g);
	public ScreenObj()
	{
		ScreenObjs.add(this);
		isAlive=true;
	}
	public java.awt.Shape getCollisionShape()
	{
		Rectangle collision = new Rectangle((int)(Location.getX()),
				(int)(Location.getY()),
				(int)Size.getX(),
				(int)Size.getY());
		return collision;
	}
	public void Die()
	{
		isAlive = false;
		ScreenObjs.remove(this);
	}
	/**
	 * @returns the distance between this ScreenObj and the one passed in
	 */
	public float Distance(ScreenObj so)
	{
		double XDelta = Location.getX() - so.Location.getX();
		double YDelta = Location.getY() - so.Location.getY();
		return (float)Math.sqrt(XDelta * XDelta + YDelta*YDelta);
	}
}
class Ship extends ScreenObj implements KeyListener
{
	public final float MaxVelocity = 5.0f, MoveDeltaScale = 0.1f, DirectionDelta = 3;
	public boolean UP, DOWN, RIGHT, LEFT, RL, RR, SPACE;
	public int lives = 5;
	public float invensible = 100;
	public int Score=0;
	
	public Ship()
	{
		System.out.println("Locating Ship Image");
		image = new ImageIcon("ship.png").getImage();
		if (image.getHeight(null) < 8)
			System.out.println("Could not find ship");
		
		Direction = new Vector2D(0,1);
		Velocity = new Vector2D(0,0);
		Location = new Vector2D(300,300);
		Size = new Vector2D(32,32);
	}
	public void Die()
	{
		if (invensible > 0)
			return;
		if (lives-- > 0)
		{
			Location = new Vector2D(300,300);
			invensible=100;
		}
		else
		{
			//isAlive = false;
			super.Die();
		}
	}
	public void Draw(Graphics g) 
	{			
		AffineTransform at = new AffineTransform();
		at.setTransform(identity);
		at.translate(Location.getX()-32,600-Location.getY()-32);
		at.rotate(Math.atan2(Direction.getX(),Direction.getY()),32,32);
		
		Graphics2D g2d = (Graphics2D)g;
		//System.out.println("Alpha: "+((0+(100f-invensible))/100));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (0+(100f-invensible))/100 ));
		if (isAlive)
			g2d.drawImage(image,at,null);	
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		//draw lives
		for (int i=0;i<lives;i++)
		{
			g2d.setColor(Color.green);
			g2d.fillOval(20+i*15, 50, 10, 10);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(20+i*15, 50, 10, 10);
		}
		g2d.setColor(Color.GREEN);
		g2d.drawString("Score: "+Score, 20, 75);
	}
	public void Act(int frame)
	{
		if (!isAlive)
			return;
		if (invensible > 0)
		{
			invensible-=.3f;
			invensible = invensible < 0 ? 0:invensible;
		}
		if (SPACE)
		{
			Shoot();
			SPACE = false;
		}
		
		//boundaries
		if (Location.getX() > 600)
			Location.set(0, Location.getY());
		else if (Location.getX() < -image.getWidth(null))
			Location.set(600,Location.getY());
		if (Location.getY() > 600)
			Location.set(Location.getX(),600);
		else if (Location.getY() < 0)
			Location.set(Location.getX(),0);
		
		//cheat
		//Velocity = Velocity.multiply(.98f);
		
		Location = Location.add( Velocity );		
		if (UP)
		{
			Velocity = Velocity.add(Direction.multiply(MoveDeltaScale));
		}
		if (DOWN)
		{
			Velocity = Velocity.add(Direction.multiply(-MoveDeltaScale));
		}
		if (RR || RIGHT)
		{
			Velocity = Velocity.add(Direction.rotate(90).multiply(MoveDeltaScale));
		}
		if (RL || LEFT)
		{
			Velocity = Velocity.add(Direction.rotate(-90).multiply(MoveDeltaScale));
		}
		if (Velocity.getLength() > MaxVelocity)
			Velocity = Velocity.normalize().multiply(MaxVelocity);
		
		
	}
	public void Shoot()
	{
		
		new Shot(this,Location,Direction,Velocity);
			
	}
	//KeyListener
	public void keyTyped(KeyEvent e) { /*do nothing*/ }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) 
    { 
    	if (e.getKeyCode() == KeyEvent.VK_SPACE)
    	{
    		SPACE = true;
    	}
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
    	if (e.getKeyCode() == KeyEvent.VK_E)
    	{
    		RR=true;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_Q)
    	{
    		RL=true;
    	}
    }
    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) 
    {
    	if (e.getKeyCode() == KeyEvent.VK_SPACE)
    	{
    		SPACE = false;
    	}
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
    	if (e.getKeyCode() == KeyEvent.VK_E)
    	{
    		RR=false;
    	}
    	if (e.getKeyCode() == KeyEvent.VK_Q)
    	{
    		RL=false;
    	}              
    }
}
class Shot extends ScreenObj
{
	ScreenObj Creator;
	int TimeToLive = 50;
	public final int SPEED = 5;
	
	public Shot(ScreenObj creator, Vector2D location, Vector2D direction, Vector2D initialVelocity)
	{
		this.Location = new Vector2D(location);
		Direction = new Vector2D(direction); //the new is important here
		Size = new Vector2D(4,4);
		Creator = creator;
		Velocity = initialVelocity;
	}
	
	@Override
	public void Act(int frame) 
	{		
		if (Location.getX() > 600)
			Location.set(0, Location.getY());
		else if (Location.getX() < 0)
			Location.set(600,Location.getY());
		if (Location.getY() > 600)
			this.Die();
		else if (Location.getY() < -100)
			this.Die();
		
		this.Location = Location.add(Velocity);
		this.Location = Location.add(Direction.multiply(SPEED));
		
		
		for (int i=0;i<ScreenObjs.size();i++)
		{
			ScreenObj s = ScreenObjs.get(i);
			if (s instanceof Shot || s == Creator)
				continue;
			
			java.awt.Shape collision = s.getCollisionShape();
			//System.out.println("Collision: "+collision.getBounds());
			//System.out.println("Shot: "+Location.getX() +" "+(600-Location.getY()));
			if (collision.contains(Location.getX(), (Location.getY())))
			{
				//System.out.println("HIT");
				this.Die();
				s.Die();		
			}
		}		
		 
		TimeToLive--;
		if (TimeToLive < 0)
			this.Die();
	}

	@Override
	public void Draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int)this.Location.getX(), 600-(int)this.Location.getY(), 4, 4);
	}	
}
class Obstical extends ScreenObj
{
	private static ArrayList<Obstical> obs = new ArrayList<>();
	static Image asteroidImage = new ImageIcon("Asteroid.png").getImage();
	public Obstical()
	{
		Location=new Vector2D((int)(Math.random()*600),700);
		Size = new Vector2D(50,50);
		Velocity = new Vector2D((float)(Math.random()*3)-1.5f,-5);
		obs.add(this);
		image = asteroidImage;
	}
	public static void checkCollision(Ship myShip) {
		Area shipArea = new Area(myShip.getCollisionShape());
		for (Obstical o : obs)
		{
			Area oArea = new Area(o.getCollisionShape());
			oArea.intersect(shipArea);
			if (!oArea.isEmpty())
				myShip.Die();
		}		
	}
	@Override
	public void Act(int frame) {
		Location = Location.add(Velocity);
		
		if (Location.getX() > 600)
			Location.setX(0);
		else if (Location.getX() < 0)
			Location.setX(600);
		if (Location.getY() < 0)
		{
			Location=new Vector2D((int)(Math.random()*600),700);
		}
	}

	@Override
	public void Draw(Graphics g) {
		
		g.drawImage(image,(int)Location.getX(), 600 - (int)Location.getY(), (int)Size.getX(), (int)Size.getY(),null);
	}
	public java.awt.Shape getCollisionShape()
	{
		Rectangle collision = new Rectangle((int)(Location.getX()),
				(int)(Location.getY()),
				(int)Size.getX(),
				(int)Size.getY());
		return collision;
	}
}