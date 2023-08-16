import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

class Sprite
{
	public BufferedImage spriteSheet; 	//the entire sheet
	public BufferedImage currentFrame;	//what to draw
	public int animationNumber = 9; 	//vertical
	public int frameNumber = 0;   		//horizontal
	public int tileSize = 64; //if tile size if different width and height you will need another variable for this
	public static final int FRAME_NUMBER = 8; //if the number of frames is different this might need to not be final
	
//	public Sprite(String fileName)
//	{
//		this(fileName,64);
//	}
	public Sprite(String fileName)
	{	
		this.tileSize = tileSize;
		
			Image image = new ImageIcon (fileName).getImage();
			spriteSheet = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
			spriteSheet.getGraphics().drawImage(image,0,0,null);
		 	
		//init currentFrame
		currentFrame = spriteSheet.getSubimage(0,0, 284, 284);
	}
	//update is called when you want to progress to the next frame
	//often times though you want to limit it to a certain time per frame
	public void Update()
	{
		frameNumber = (frameNumber + 1) % FRAME_NUMBER;
		currentFrame = spriteSheet.getSubimage(frameNumber*tileSize, animationNumber*tileSize, tileSize, tileSize);
	}	
	
	
}
