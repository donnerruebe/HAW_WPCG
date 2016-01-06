
package computergraphics.applications.blatt6;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Simple viewer for images.
 */
public class ImageViewer extends JFrame implements MouseListener{

  /**
   * Generated ID, required for JFrame classes.
   */
  private static final long serialVersionUID = 8140257581540101857L;

  /**
   * Constructor. Displays the image.
   */
  public ImageViewer(Image image) {
    JLabel label = new JLabel();
    label.setIcon(new ImageIcon(image));
    label.addMouseListener(this);
    getContentPane().add(label);
    
    setLocation(100, 300);
    setSize(image.getWidth(null), image.getHeight(null));
    setVisible(true);
  }

@Override
public void mouseClicked(MouseEvent e)
{
	// TODO Auto-generated method stub
	System.out.println(e.getX() + " :: " + e.getY());
}

@Override
public void mouseEntered(MouseEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e)
{
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e)
{
	// TODO Auto-generated method stub
	
}

}
