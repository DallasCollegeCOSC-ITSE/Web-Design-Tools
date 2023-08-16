import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public abstract class Screenobj {

public static ArrayList<Screenobj> Screenobjs = new ArrayList<Screenobj>();
    
    Vector2D location;
    Vector2D direction;
    Vector2D size;
    Vector2D velocity;
    public boolean isAlive;
    
    Image image;
    
    public Screenobj() {
        Screenobjs.add(this);
        isAlive=true;
    }
    
    public Shape getCollisionShape()
    {
        Rectangle collision = new Rectangle((int)location.getX(), ((int) location.getY()));
        
        
        return collision;
    }
    
    public void draw(Graphics2D g)
    {
    	g.setColor(Color.green);
    	g.drawRect((int)location.getX(), (int)location.getY(), (int)size.getX(), (int)size.getY());
        g.drawImage(image,((int)location.getX()), ((int)location.getY()),null);
    }
    public Screenobj returnScreenObj()
    {
        return this;
    }
    
    public void act() {}
    
    public void Die()
    {
        isAlive = false;
        Screenobjs.remove(this);
    }
    
    
}
