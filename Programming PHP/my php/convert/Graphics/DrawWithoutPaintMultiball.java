	import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
 * Note that these balls will collide with the walls but not themselves. Two round objects 
 * colliding is quite a difficult physics question. I will cover that in another example
 * 
 * @author Jay Laughlin
 *
 */
public class DrawWithoutPaintMultiball {
	public static void main(String[] args) {
		MyDrawingFrameMultiball f = new MyDrawingFrameMultiball();
		
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(500,500);
		f.setVisible(true);
		f.setup();
		f.draw();
		
	}
}

class MyDrawingFrameMultiball extends JFrame
{
	//buffer for drawing off screen
	private Image raster;
	//graphics for buffer
	private Graphics rasterGraphics;
	
	//this is the current X and Y of the ball
	
	private Image background;
	
	
	public void setup() {
		//setup buffered graphics
		raster = this.createImage(500,500);
		rasterGraphics = raster.getGraphics();
		
		//Easy way to read in an image
		
		background = new ImageIcon("F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\Graphics\\src\\boss-Ai upscale.jpg").getImage();
		
	}
	/**
	 * This is the workhorse of the program. This is where the main loop for graphics is done
	 */

	public void draw() {
		//create and add the balls to an array to use later
		
		ArrayList<Multiball> multiballs = new ArrayList<Multiball>();
		multiballs.add(new Multiball(100,300,1.1f,1.2f,Color.red));
		multiballs.add(new Multiball(100,100,-1.5f,1.2f,Color.green));
		multiballs.add(new Multiball(100,300,1.5f,-1.2f,Color.blue));
		
		while(true)
		{
			//get the start time of the loop to use later
			long time = System.currentTimeMillis();
			
			//draw and move background and balls
			DrawBackground(rasterGraphics);
			for (Multiball b : multiballs) {
				b.MoveBall();
				b.DrawBall(rasterGraphics);
			}
			
			//rasterGraphics.setColor(Color.orange);
			//rasterGraphics.drawLine((int)multiballs.get(0).X)
			//(int)multiballs.get(0).Y, (int)multiballs.get(1).X, (int)multiballs.get(1).Y);
			//rasterGraphics.drawLine((int)multiballs.get(2).X, 
			//(int)multiballs.get(2).Y, (int)multiballs.get(1).X, (int)multiballs.get(1).Y);
			
			//draw the scene from the buffer raster
			getGraphics().drawImage(raster, 0, 0, 500, 500, null);
			
			//use the start time minus the current time to get the delta time
			//-this will vary as your program runs to make your program run smoothly we are going
			//to use delta time when we sleep
			
				long deltaTime = System.currentTimeMillis()-time;//time it takes per loop
				try {Thread.sleep(Math.max(10-deltaTime,0)/**/);}catch(Exception e){}
			
			
		}
		
		
	}
	private void DrawBackground(Graphics g)
	{
		g.drawImage(background,0,0,500,500,null);
	}
}

class Multiball 
{
	public Color C;
	
	public float X,Y;
	//these variables are how much we are going to change the current X and Y per loop
	
	public float XVelocity,YVelocity,speed;
	
	public Multiball()
	{
		speed = 1.0f;
		XVelocity = 2.5f;
		YVelocity = 4.15f;
		X=200;
		Y=200;
	}
	
	public Multiball(int x, int y, float xv, float yv, Color c)
	{
		speed = 1.0f;
		XVelocity = xv;
		YVelocity = yv;
		X=x;
		Y=y;
		C=c;
	}
	
	public void MoveBall()
	{
		//Check boundaries for the ball
		
		if(X > 475)
			XVelocity *= -1;
		else if(X < 20)
			XVelocity *= -1;
		if (Y > 475)
			YVelocity *= -1;
		else if (Y < 40)
			YVelocity *= -1;
		
		//update the ball's current location
		
		X+= XVelocity * speed;
		Y+= YVelocity * speed;
	}
	
	public void DrawBall(Graphics g)
	{
		for (int i = 0; i <800 ; i+=5) {
			g.setColor(new Color(255-i/5,i/4,i%255));
			g.drawLine(185,328,i,600);
			
		}
		g.setColor(C);
		drawCircle(g,(int)X,(int)Y,20);
	}
	
	/**
	 * @param cg = graphics that you are using
	 * @param xCenter = X Position
	 * @param yCenter = Y Position
	 * @param r = radius
	 * Author:  Siddhartha Bhattacharya
	 */
	
	public void drawCircle(Graphics cg, int xCenter,int yCenter, int r)
	{
		cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
	}
}