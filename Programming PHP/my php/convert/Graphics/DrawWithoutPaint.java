import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * This is an example of double buffering without using the paint method. Paint (at least in 
 * older version of java) was a terrible resource hog, so this was how we used to do double
 * buffered drawing. Java has improved the performance of paint (at least from what little 
 * testing I've done of it) but this technique for double buffering still provides many 
 * advantages.
 * 
 * Warning: to show why double buffering is necessary this example is moving around a bouncing
 * ball. There is a limited amount of physics calculations here. Do not run away screaming
 * when you see the word Velocity.
 * 
 * @author Jay Laughlin
 *
 */

public class DrawWithoutPaint {
	public static void main(String[] args) {
		MyDrawingFrame f = new MyDrawingFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(800,800);
		f.setVisible(true);//sets this a heavy weight
		f.setup();	//must be called after setVisible(true)
		f.draw();
		
	}
}

class MyDrawingFrame extends JFrame
{
	//buffer for drawing off screen
	private Image raster;//by convention we call this raster
	//graphics for the buffer
	
	private Graphics rasterGraphics;
	//this is the current x and y of the ball
	private float X,Y;
	//these variables are how much we are going to change the current x and y
	private float XVelocity, YVelocity, speed;
	
	public MyDrawingFrame()
	{
		//
	}
	public void setup()
	{
		//the frame must be set visible first
		//this line must be done outside the constructor 
		raster = this.createImage(500,500);
		rasterGraphics = raster.getGraphics();
		
		//Initial Speed
		speed = 5.5f;
		XVelocity = (float)Math.random();
		YVelocity = (float)Math.random();
		X=200;
		Y=200;
	}
	
	public void paint(Graphics g)
	{
		return;
	}
	public void draw()
	{
		//draw the initial backGround
		rasterGraphics.setColor(Color.black);
		rasterGraphics.fillRect(0, 0, 500, 500);
		while(true)
		{
			//checkKeys
			//No user Input
			
			DrawBackground(rasterGraphics);
			MoveBall();
			DrawBall(rasterGraphics);
			
			//done
			//drawImage is atomic
			this.getGraphics().drawImage(raster, 0, 0, getWidth(), getHeight(), null);
			try {Thread.sleep(10);}catch(Exception e) {}
			
		}
	}
	private void DrawBackground(Graphics g)
	{
		
		//g.setColor(Color.black);
		//I same time by only drawing drawing over the place where the ball was last
		g.fillRect((int)X-25,(int)Y-25,50,50);
	}
	public void DrawBall(Graphics g)
	{
		g.setColor(Color.yellow);
		drawCircle(g,(int)X,(int)Y,20);
	}
	
	public void MoveBall()
	{
		//Check boundaries for the ball
		if (X > 475)
			XVelocity *= -1;
		else if (X < 20)
			XVelocity *= -1;
		if (Y > 475)
			YVelocity *= -1;
		else if (Y < 40)
			YVelocity *= -1;
	//update the ball's current location
		X += XVelocity * speed;
		Y += YVelocity * speed;
		
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
		cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
	}
}

