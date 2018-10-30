package marmaris.common.tools;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * An extension of JPanel with an image
 */
public class MGImagePanel extends JPanel{
	
	private static final long serialVersionUID = -5194777201683297269L;

	public MGImagePanel(Image image) {
		if (image != null) 
			add(new JLabel(new ImageIcon(image)));
	}
	

}
