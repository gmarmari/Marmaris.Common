package marmaris.common.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import marmaris.common.log.MGLogger;
import marmaris.common.res.MGImages;
import marmaris.common.res.MGStrings;

import javax.swing.JProgressBar;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * My progress bar 
 */
public class MGProgressBar extends JDialog {
	
	public interface Listener{
		void onClosed();
		void onCancel();
	}
	private Listener mListener;
	public void setListener(Listener mListener) {
		this.mListener = mListener;
	}

	private static final long serialVersionUID = 1L;
	
	private final JPanel mContentPanel = new JPanel();
	
	private JLabel mLabel;
	
	private JProgressBar mProgressBar;
	private int maximum = 0;

	/**
	 * Creates a new instance of SwProgressBar
	 * @return the SwProgressBar
	 */
	public static MGProgressBar newInstance(Component parent) {
		try {
			MGProgressBar dialog = new MGProgressBar();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			if(parent != null)
				dialog.setLocationRelativeTo(parent);
			return dialog;
		} catch (Exception e) {
			MGLogger.appendLog(MGProgressBar.class.toString(), Level.SEVERE, e);
			return null;
		}
	}
	
	/**
	 * Set the maximum value of the progress bar.
	 * If the maximum <= 0, then the progress bar will be indeterminate
	 * @param maximum an integer value
	 */
	public void setMaximum(int maximum) {
		this.maximum = maximum > 0 ? maximum : 0;
		if(mProgressBar != null) {
			if (this.maximum == 0)
				mProgressBar.setIndeterminate(true);
			else
				mProgressBar.setIndeterminate(false);
		}
	}
	
	/**
	 * Sets the text of the JLabel mLabel
	 * @param label a string
	 */
	public void setLabel(String label) {
		if (mLabel != null)
			mLabel.setText(label);
	}
	
	/** shows the progress bar: setVisible(true); */
	public void start() {
		setVisible(true);
	}

	/**  Hides the progress bar: setVisible(false); */
	public void stop() {
		setVisible(false);
	}
	
	/**
	 * Updates the progress bar value
	 * @param progress : an integer value
	 */
	public void updateProgress(int progress) {
		if(mProgressBar != null && progress >= 0 && progress <= maximum) {
			mProgressBar.setValue(progress);
		}
	}
	
	/**  Create the dialog. */
	private MGProgressBar() {
		setIconImage(MGImages.getImage(MGImages.IC_ACTION_SYNC));
		setBounds(100, 100, 250, 150);
		setPreferredSize(new Dimension(500, 200));
		getContentPane().setLayout(new BorderLayout());
		mContentPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		getContentPane().add(mContentPanel, BorderLayout.CENTER);
		mContentPanel.setLayout(new BoxLayout(mContentPanel, BoxLayout.Y_AXIS));
		{
			mProgressBar = new JProgressBar();
			mProgressBar.setMinimum(0);
			mProgressBar.setIndeterminate(true);
			mProgressBar.setBorder(new EmptyBorder(2, 2, 2, 2));
			mProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
			mContentPanel.add(mProgressBar);
		}
		{
			mLabel = new JLabel(MGStrings.getString(MGStrings.MG_PROGRESS_BAR_LABEL_LOADING));
			mLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
			mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mContentPanel.add(mLabel);
		}

		{
			JPanel mButtonPane = new JPanel();
			mButtonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(mButtonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton(MGStrings.getString(MGStrings.CANCEL));
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						stop();
						if(mListener != null)
							mListener.onCancel();
					}
				});
				mButtonPane.add(cancelButton);
			}
		}
		
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				if(mListener != null)
					mListener.onClosed();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
