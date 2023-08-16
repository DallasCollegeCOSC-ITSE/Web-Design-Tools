import java.awt.Color;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class Button extends Rectangle implements MouseListener
{
	//Job is the functionality of the button and doubles as the text for the button
	Job job;
	Point clickLocation;
	
	Image image;
	
	enum Job
	{
		Start,
		Quit,
		About,
		Back
	}
	
	
	public Button(int x , int y, int width, int height, Job job,Image image)
	{
		super(x,y,width,height);
		this.job=job;
		
		this.image=image;
	}
	
	public void draw(Graphics2D g, Point2D mouselocation)
	{
		if(mouselocation != null && check(mouselocation))
			g.setColor(Color.red);
		g.setColor(Color.CYAN);
		g.fill(this);
		g.setColor(Color.black);
		g.setFont(new Font ("Arial", Font.PLAIN,32));
		g.drawString(job.toString(), x+45, y+40);
		g.drawImage(image,x, y,null);
		
	}

	public boolean check(Point2D point)
	{
		return this.contains(point);
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
