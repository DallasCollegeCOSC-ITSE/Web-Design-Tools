import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	// init FINALS

	static final int SCREEN_WIDTH = Constraints.SCREEN_WIDTH_X;

	static final int SCREEN_HEIGHT = Constraints.SCREEN_HEIGHT_Y;
	
	static final String NAME = Constraints.PROGRAM_NAME; 

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		initFrame(NAME,SCREEN_HEIGHT,SCREEN_WIDTH);
		
	}

	public static void initFrame(String Name, int width, int height) throws InterruptedException, FileNotFoundException {
		JFrame frame = new JFrame(Name);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		frame.setResizable(false);
		
		//init and add to the JFrame 
		
		MyShippingNetworkPanel panel = new MyShippingNetworkPanel(width, height);
		
		frame.add(panel);
		frame.setVisible(true);
		panel.preSimulationLoop();
	}

}

class MyShippingNetworkPanel extends JPanel implements MouseListener	{
	private static final long serialVersionUID = 1L;

	// graphics for the buffer
	Image raster;
	Graphics2D g;
	
	int width,height;

	Point2D clickLocation = null;
    Point2D mouseLocation = null;

	// setting up the Buffer
	public MyShippingNetworkPanel(int w, int h) {
		 width = w;
	     height = h;
	       
		
		//Buffer
		raster = new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB);
		//Buffer Graphics
		g = (Graphics2D)raster.getGraphics();
		
		this.addMouseListener(this);

	}

	public void preSimulationLoop() throws InterruptedException, FileNotFoundException {
		Button start = new Button(500,400,150,80,Button.Job.Start,new ImageIcon("Start.png").getImage());
    	Button quit = new Button(500,600,150,80,Button.Job.Quit,new ImageIcon("Quit.png").getImage());
		Button about = new Button(500,700,150,80,Button.Job.About,new ImageIcon("About.png").getImage());
		Button back = new Button(500,500,150,80,Button.Job.Back,new ImageIcon("Back.png").getImage());
		
		//Loop
		while(true)
    	{
			
			g.setColor(Color.black);
    		g.fillRect(0,0,this.getWidth(),this.getHeight());
    		g.setColor(Color.WHITE);
    		g.setFont(new Font ("Arial", Font.PLAIN,72));
    		g.drawString("Shipping Network", 250,250);
    		//draw
    		start.draw(g, mouseLocation);
    		quit.draw(g, mouseLocation);
    		about.draw(g, mouseLocation);
    		
    		this.getGraphics().drawImage(raster, 0, 0, null);
    		
    		//check for actions
    		if(clickLocation != null)
    		{
    			//start Simulation
    			if(start.check(clickLocation))
    				break;
    			if(quit.check(clickLocation))
    				System.exit(0);
    			
    			while(about.check(clickLocation))
    			{
    				g.setColor(Color.black);
    				g.fillRect(0,0,this.getWidth(),this.getHeight());
    	    		g.setColor(Color.white);
    	    		
    	    		//TODO Write a function for printing text on screen
    	    		g.drawString("A Simulation Created By Luka Bostick", 200,250);
    	    		g.drawString("For The Dallas College Course", 200,300);
    	    		g.drawString("Fundementals of Programming 2 (COSC-1437)", 200, 350);
    	    		g.drawString("Spring 2022", 200, 400);
    	    		
    	    		back.draw(g, mouseLocation);
    	    		
    	    		quit.isEmpty();
    				
    	    		this.getGraphics().drawImage(raster, 0, 0, null);
        			
    	    		if(back.check(getLocation()))
    	    			break;
    			}  			
    				clickLocation = null;
    		}
    		try {
    			Thread.sleep(100);
    			
    		}catch(InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
		loop();
	}
	public void loop() throws FileNotFoundException
	{
		int FrameNumber=0;
		
		JFrame frame = (JFrame) this.getTopLevelAncestor();
		
		Shipping s = new Shipping();
		
		
		while(true)
		{
			FrameNumber++;
			
			g.setColor(Color.white);
			g.fillRect(0, 0, raster.getWidth(null), raster.getHeight(null));
			
			//g.drawImage(new ImageIcon("Simulation Template.png").getImage(), 150, 50, null);
			
			for(int i=0;i<Screenobj.Screenobjs.size();i++)
			{
				Screenobj so = Screenobj.Screenobjs.get(i);
				
				//Act on the various objects
                so.act();        
                
                //Draw various screen objects                            
                so.draw(g);
			}
			this.getGraphics().drawImage(raster, 0, 0, null);
			try{Thread.sleep(20);}catch(Exception e){}
		}
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		clickLocation = e.getLocationOnScreen();
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}	
}