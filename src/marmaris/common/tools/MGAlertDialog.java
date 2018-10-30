package marmaris.common.tools;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MGAlertDialog {

	/**
	 * Show a JOptionPane
	 */
	public static void showMessageDialog(Component parent, String msg, String title, int messageType) {
		JOptionPane.showMessageDialog(parent, msg, title, messageType);
	}
	
}
