
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

public class Pong {
	public static void main(String[] args)
	{
		Window window = new Window();
		Thread t1 = new Thread(window);	//window need tobe on a seprate thread for sleeping and etc
		
		t1.start();
	}
}

class Window extends JFrame implements Runnable  {
	
	
	public Graphics2D g2;
	public KL keyListener = new KL();
	public Rect playerOne, ai, ballRect;
	public PlayerController playerController;
	public AiController aiController;
	public Ball ball;
	
	final static int WIDTH = 800;
	
	final static int HEIGHT = 600;
	
	final double PADDLE_WIDTH = 10;
	final double PADDLE_HEIGHT = 100;
	final Color PADDLE_COLOR = Color.WHITE;
	final double BALL_WIDTH = 10;
	final double HZ_PADDING = 30;
	static double toolBarHeight;
	static double insetsBottom;
	
	static final int  PADDLE_SPEED = 250;
	
	public Window()
	{
		
		String TITLE = "Mini Game Pong";
		
		this.setSize(WIDTH,HEIGHT);
		this.setTitle(TITLE);
		
		this.setResizable(false);//can we resize the window
		this.setVisible(true);	//window is visble 
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO change when their is a winner
		this.addKeyListener(keyListener);
		
		toolBarHeight = this.getInsets().top;
		insetsBottom = this.getInsets().bottom;
		
		g2 = (Graphics2D)this.getGraphics();//cast to 2d
		
		playerOne = new Rect(HZ_PADDING,40,PADDLE_WIDTH,PADDLE_HEIGHT,PADDLE_COLOR);
		playerController = new PlayerController(playerOne, keyListener);
		
		ai = new Rect((WIDTH-PADDLE_WIDTH-HZ_PADDING),40,PADDLE_WIDTH,PADDLE_HEIGHT,PADDLE_COLOR);
		
		ballRect = new Rect(WIDTH/2,HEIGHT/2,BALL_WIDTH,BALL_WIDTH,PADDLE_COLOR);
		ball = new Ball(ballRect,playerOne, ai);
		
		aiController = new AiController(new PlayerController(ai),ballRect);
	}
	
	public void update(double deltaTime)
	{
		Image dbImage = createImage(WIDTH,HEIGHT);//double buffer
		Graphics dbg = dbImage.getGraphics();
		this.draw(dbg);
		
		g2.drawImage(dbImage,0,0,this);
		
		
		System.out.println("" + deltaTime + " Seconds passed since the last frame");
		System.out.println(1/deltaTime + "fps");
		
			
		playerController.update(deltaTime);	
		aiController.update(deltaTime);
		ball.update(deltaTime);
		

	}
	public void draw(Graphics g)	//double buffer
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		playerOne.draw(g2);
		ai.draw(g2);
		
		ballRect.draw(g2);
	}
	
	//GameLoop
	public void run()
	{
		double lastFrameTime = 0.0;
		while(true)
		{
			double time = Time.getTime();
			double deltaTime = time-lastFrameTime;
			
			lastFrameTime = time;
			
			update(deltaTime);
			
			
		}
	}
}

class Time
{
	public static double timeStarted = System.nanoTime();
	
	public static double getTime() 
	{
		return (System.nanoTime() - timeStarted)* 1E-9;	//converts nanosec to sec
	}
}

//this is super cool
	class KL implements KeyListener
	{
		private boolean keyPressed[] = new boolean[128];
		
		public void keyTyped(KeyEvent keyEvent)
		{
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			keyPressed[e.getKeyCode()] = true;
		}

		
		public void keyReleased(KeyEvent keyEvent)
		{
			keyPressed[keyEvent.getKeyCode()] = false;
		}

		public boolean isKeyPressed(int keyCode)
		{
			return keyPressed[keyCode];
		} 	
	}
class Rect
{
	public double x,y,width,height;
	public Color color;

	Rect(double x, double y, double width, double height, Color color)
	{
		this.height = height;
		this.width = width;
		this.x = x;
		this.y =y;
		this.color = color;
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setColor(color);
		g2.fill(new Rectangle2D.Double(x,y,width,height));
		
	}
}

class PlayerController
{
	public Rect rect;
	public KL keyListener;
	
	public PlayerController(Rect rect, KL keyListener)
	{
		this.rect=rect;
		this.keyListener=keyListener;
	}
	
	public PlayerController(Rect rect)
	{
		this.rect = rect;
		
	}
	
	public void update(double deltaTime)
	{
		if(keyListener != null)
		{
			
			if(keyListener.isKeyPressed(KeyEvent.VK_DOWN)){
				moveDown(deltaTime);
			}
			
			else if(keyListener.isKeyPressed(KeyEvent.VK_UP))
			{
				moveUp(deltaTime);
			}
		}
		
		
	}
	
	public void moveUp(double deltaTime)
	{
		
			if(rect.y - Window.PADDLE_SPEED*deltaTime > Window.toolBarHeight)	//hitbox
			this.rect.y -= Window.PADDLE_SPEED*deltaTime;
		
	}
	
	public void moveDown(double deltaTime)
	{
			if((rect.y+Window.PADDLE_SPEED*deltaTime) + rect.height < Window.HEIGHT-Window.insetsBottom)
			this.rect.y +=Window.PADDLE_SPEED * deltaTime;
		
	}
}

class AiController
{
	public PlayerController ai;
	
	public Rect ball;
	
	AiController(PlayerController ai, Rect ball)
	{
		this.ai=ai;
		this.ball=ball;
	}
	//recursive
	public void update(double deltaTime)
	{
		this.ai.update(deltaTime);
		
		//if its above up move up
		if(ball.y < this.ai.rect.y)
		{
			this.ai.moveUp(deltaTime);
		}
		//if its below move down
		else if(ball.y + ball.height > this.ai.rect.y + ai.rect.y)
		{
			this.ai.moveDown(deltaTime);
		}
	}
}
class Ball
{
	private static final double MAX_ANGLE = 45;
	public Rect rect;
	public Rect leftPaddle, rightPaddle;
	
	//Ball speed
	private double vy = 200.0;
	private double vx = -200.0;
	
	
	public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle)
	{
		this.rect=rect;
		this.leftPaddle = leftPaddle;
		this.rightPaddle = rightPaddle;
	}

	public double calculateNewVelocityAngle(Rect paddle)
	{
		double relativeIntersectY = (paddle.y+(paddle.height /2.0)) - (this.rect.y + (this.rect.y /2.0));
		
		double normalIntersectY = relativeIntersectY / (paddle.height / 2);
		
		double theta = normalIntersectY*MAX_ANGLE;
		
		return Math.toRadians(theta);
	}
	public void update(double deltaTime) 
	{
		//Paddle Bounce
		if(vx < 0)
		{
			if( this.rect.x <= this.leftPaddle.x + this.leftPaddle.width && this.rect.x + this.rect.width >= this.leftPaddle.x&&
				this.rect.y >= this.leftPaddle.y && 
			    this.rect.y <= this.leftPaddle.y + this.leftPaddle.height)
			{
				double theta = calculateNewVelocityAngle(leftPaddle);
				
				double newVx = (Math.cos(theta)) * vx;	//ballspeed
				double newVY = (-Math.sin(theta)) * vy;     //ballspeed
				
				double oldSign = Math.signum(vx);
				
				this.vx = newVx * (-1.0 * oldSign);
				this.vy = newVY;
			}else if(this.rect.x + this.rect.width < this.leftPaddle.x)
			{
				System.out.println("You have Lost");
				
			
			}
		}
		else if(vx > 0)
		{
			if( this.rect.x + this.rect.width >= this.rightPaddle.x&& this.rect.x <= this.rightPaddle.x + this.rightPaddle.width&&
					this.rect.y >= this.rightPaddle.y && 
				    this.rect.y <= this.rightPaddle.y + this.rightPaddle.height)
				{
				double theta = calculateNewVelocityAngle(leftPaddle);
				
				double newVx = (Math.cos(theta)) * vx;	//ballspeed
				double newVY = (-Math.sin(theta)) * vy;     //ballspeed
				
				double oldSign = Math.signum(vx);
				
				this.vx = newVx * (-1.0 * oldSign);
				this.vy = newVY;
				}
			else if(this.rect.x + this.rect.width > this.rightPaddle.x + this.rightPaddle.width)
			{
				System.out.println("Ai has Lost");
			}
		}
		
		//Screen Bounce 
		if(vy > 0)
		{
			if(this.rect.y + this.rect.height > Window.HEIGHT)
			{
				this.vy *=-1;
			}
		}else if(vy < 0)
		{
			if(this.rect.y < Window.toolBarHeight)
			{
				this.vy *=-1;
			}
		}
		
		//pos = pos + velocity
		//velocity = velocity + acceleration
		
		this.rect.x += vx * deltaTime;
		this.rect.y += vy * deltaTime;
	}
	

}
