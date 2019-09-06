package rendering;

import java.awt.Component;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import utils.Config;
import utils.SharedMemory;

public class Window {

	private Component component;
	private JFrame win;
	private String title;
	
	public Window(String title, Component component){
		this.title = title;
		win = new JFrame(title);
		this.component = component;
	}
	
	public void start(){
		setDefaultState();
		win.setVisible(true);
	}
	
	private void setDefaultState(){
		win.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.exit(0);
		    }
		});
		win.add(component);
		win.setResizable(Config.RESIZABLE);
		win.pack();
		win.setLocationRelativeTo(null);
	}
	
	public void setTitle(String newTitle){
		StringBuffer sbuf = SharedMemory.getClearedStringBuffer();
		sbuf.append(title);
		sbuf.append(" | ");
		sbuf.append(newTitle);
		win.setTitle(sbuf.toString());
	}
	
	public void setIconImage(String path1, String path2, String path3){
		ImageIcon ii1 = new ImageIcon(path1);
		ImageIcon ii2 = new ImageIcon(path2);
		ImageIcon ii3 = new ImageIcon(path3);
		setIconImage(ii1.getImage(), ii2.getImage(), ii3.getImage());
	}
	
	/** This method sets the icon image of the frame according to the best imageIcon size requirements for the system's appearance settings.
	 *  This method should only be called after pack() or show() has been called for the Frame.
	 * @param frame The Frame to set the image icon for.
	 * @param image20x20 An image, 20 pixels wide by 20 pixels high
	 * @param image26x26 An image, 26 pixels wide by 26 pixels high
	 * @param image28x28 An image, 28 pixels wide by 28 pixels high
	 */
	private void setIconImage(Image image20x20, Image image26x26, Image image28x28) {
	    Insets insets = win.getInsets();
	    int titleBarHeight = insets.top;
	    if (titleBarHeight == 32) {
	        // It's 'Windows Classic Style with Large Fonts', so use a 26 x 26 image
	        if (image26x26 != null) win.setIconImage(image26x26);
	    }
	    else {
	    	/**
	    	 * Use the default 20 x 20 image - Looks fine on all other Windows Styles & Font Sizes
	    	 * (except 'Windows Classic Style with Extra Large Fonts' where image is slightly distorted.
	    	 * Have to live with that as cannot differentiate between 'Windows XP Style with
	    	 * Normal Fonts' appearance and 'Windows Classic Style with Extra Large Fonts'
	    	 * appearance as they both have the same insets values) 
	    	 */	
	        if (image20x20 != null) win.setIconImage(image20x20);
	    }
	}
	
	public Component getComponent(){
		return component;
	}
	
}