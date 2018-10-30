package marmaris.common.res;

import java.awt.Image;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import marmaris.common.log.MGLogger;

public class MGImages {
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private MGImages() {
		// do not instantiate
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Image access
	//
	////////////////////////////////////////////////////////////////////////////
	public static Image getImage(String name) {
		try {
			return new ImageIcon(ImageIO.read(MGImages.class.getResource("/images/"+name))).getImage();
		} catch (Exception e) {
			MGLogger.appendLog(MGImages.class.toString(), Level.WARNING, "Image \""+name +"\" not found");
			MGLogger.appendLog(MGImages.class.toString(), Level.WARNING, e);
			return null;
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Images
	//
	////////////////////////////////////////////////////////////////////////////
	public static final String IC_ACTION_SYNC = "ic_action_sync.png";

}

