import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class open {
	public static void main(String[] args) throws IOException
	{
		String url = "C:\\Users\\Luka Big Money\\Dropbox\\PC (2)\\Desktop\\Tetris 2-playerRefactor code by meth meth method\\index.html";
		File htmlFile = new File(url);
		Desktop.getDesktop().browse(htmlFile.toURI());
	}
}
