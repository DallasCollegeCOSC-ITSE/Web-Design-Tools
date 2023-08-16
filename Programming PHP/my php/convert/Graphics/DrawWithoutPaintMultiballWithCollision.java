import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DrawWithoutPaintMultiballWithCollision {
	public static void main(String[] args) {
		MyDrawingFrameMultiballWithCollision b = new MyDrawingFrameMultiballWithCollision();
		
		String Name = "Waiting For FaceIt";
		
		b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		b.setSize(500,500);
		b.setVisible(true);
		b.setup(Name);
		b.draw();
	}
}
class MyDrawingFrameMultiballWithCollision extends JFrame
{
	//buffer for drawing off screen
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image raster;
	
	//graphics for the buffer
	private Graphics rasterGraphics;
	//this is the current x and y of the abll
	
	public void setup(String Name)
	{
		raster  = this.createImage(500,500);
		rasterGraphics = raster.getGraphics();
		this.setName(Name);
		
	}
	/**
	 * This is the workhorse of the program. This is where the main loop for graphics is done
	 */
	
	public void draw()
	{
		//create and add the balls to an array to use later
		ArrayList<MultiballWithCollision> multiballs = new ArrayList<MultiballWithCollision>();
		
		multiballs.add(new MultiballWithCollision(170,100,2.0f,1.6f,Color.red));
		multiballs.add(new MultiballWithCollision(250,150,-1.5f,1.7f,Color.green));
		multiballs.add(new MultiballWithCollision(300,300,1.9f,-2.2f,Color.blue));
		multiballs.add(new MultiballWithCollision(20,60,1.5f,-1.8f,Color.magenta));
		multiballs.add(new MultiballWithCollision(150,400,1.5f,-2.2f,Color.yellow));
		multiballs.add(new MultiballWithCollision(350,150,1.5f,-2.2f,Color.orange));
		
		multiballs.add(new MultiballWithCollision(111,100,2.0f,1.6f,Color.white));
		multiballs.add(new MultiballWithCollision(213,150,-1.5f,1.7f,Color.GREEN));
		
		multiballs.add(new MultiballWithCollision(185,354,2.0f,1.6f,Color.gray));
		multiballs.add(new MultiballWithCollision(223,345,-1.5f,1.7f,Color.CYAN));
		while (true)
		{
		//get tha start time of the loop to use later
			long time = System.currentTimeMillis();
			
		//draw and move the background and balls
			
			DrawBackground(rasterGraphics);
			for (MultiballWithCollision b : multiballs) {
				b.MoveBall(multiballs);
				b.DrawBall(rasterGraphics);
			}
			
		//draw the scene from the buffer raster
			getGraphics().drawImage(raster,0,0,500,500,null);
			
			//use the start time minus the current time to get the delta time - this will
			//vary as your program runs to make your program run smoothly we are going
			//to use delta time when we sleep
			
			long deltaTime = System.currentTimeMillis()-time;
			try{Thread.sleep(10-deltaTime);}catch(Exception e){}
		}
	}
	
	private void DrawBackground(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
	}
}

class MultiballWithCollision
{
	public Color C;
	
	public int radius;
	public Vector2D position, Velocity;
	
	//These Variables are how much we are going to change the current X and Y per loop
	
	public float speed;
	
	public MultiballWithCollision(int x, int y, float xv, float yv, Color c)
	{
		speed = 1.0f;
		position = new Vector2D();
		Velocity = new Vector2D();
		position.set(x,y);
		Velocity.set(xv,yv);
		C=c;
		radius = 20;
	}
	
	public void MoveBall(ArrayList<MultiballWithCollision> balls)
	{
	//check for collision with another ball
		
		for (MultiballWithCollision b : balls) {
			if(b!= this && this.colliding(b))
				this.resolveCollision(b);
		}
		
		
	//Check boundaries for the ball
		if(position.getX() > 475)
			Velocity.setX(Velocity.getX() * -1);
		else if(position.getX() < 20)
			Velocity.setX(Velocity.getX() * -1);
		if(position.getY() > 475)
			Velocity.setY(Velocity.getY() * -1);
		else if(position.getY() < 40)
			Velocity.setY(Velocity.getY() * -1);
		
		//update the ball's current location
		position = position.add(Velocity);
	}
	public void DrawBall(Graphics g)
	{
		g.setColor(C);
		drawCircle(g,(int)position.getX(),(int)position.getY(),radius);
	}
	
	//Original code author Simucal, http://stackoverflow.com/questions/345838/ball-to-ball-collision-detection-and-handling
		//This just checks collision by getting the distance between each ball
	public boolean colliding(MultiballWithCollision ball)
	{
		float xd = position.getX() - ball.position.getX();
		float yd = position.getY() - ball.position.getY();
		
		float sumRadius = radius + ball.radius;
		float sqrRadius = sumRadius * sumRadius;
		
		float distSqr = (xd * xd) + (yd * yd);
		if (distSqr <= sqrRadius)
			return true;
		
		return false;
	}
	
	//what happens if they collide
	//liner algebra (scary maths)
	public void resolveCollision(MultiballWithCollision ball)
	{
		//get the mtd
		Vector2D delta = position.subtract(ball.position);
		float d = delta.getLength();
		//minimum translation distance to push balls apart after intersecting
		Vector2D mtd = delta.multiply(((radius + ball.radius)-d)/d);
		
		//resolve intersection --
		//inverse mass quantities
		
		float im1 = 1 / 1; //If the balls have different masses you can use this
		float im2 = 1 / 1; //my balls however all have the same mass
		
		//push-pull them apart based off their mass
		position = position.add(mtd.multiply(im1/(im1 + im2)));
		ball.position = ball.position.subtract(mtd.multiply(im1 / (im1 + im2)));
		
		//impact speed
		Vector2D v = (this.Velocity.subtract(ball.Velocity));
		float vn = v.dot(mtd.normalize());
		
		//sphere intersecting but moving away from each other already
		if(vn > 0.0f) return;
		
		//collision impulse
		float i = (-(1.0f+0.99f)*vn) / (im1 + im2);
		Vector2D impulse = mtd.multiply(i);
		
		//change in momentum
		this.Velocity = this.Velocity.add(impulse.multiply(im1));
		ball.Velocity = ball.Velocity.subtract(impulse.multiply(im2));
		
	}
	
	/**
	 * @param cg = graphics that you are using
	 * @param xCenter = X Position
	 * @param yCenter = Y Position
	 * @param r = radius
	 * Author:  Siddhartha Bhattacharya
	 */
	
	public void drawCircle(Graphics cg, int xCenter, int yCenter, int r)
	{
		cg.fillOval(xCenter-r,  yCenter-r, 2*r, 2*r);
	}
}

