package marmaris.common.res;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import marmaris.common.log.MGLogger;

public class MGStrings {
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private MGStrings() {
		// do not instantiate
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "strings/marmaris_common_strings"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();

	private static ResourceBundle loadBundle() {
		try {
			return ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (MissingResourceException e) {
			MGLogger.appendLog(MGStrings.class.toString(), Level.SEVERE, e);
			return null;
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			MGLogger.appendLog(MGStrings.class.toString(), Level.WARNING, e);
			return "!" + key + "!";
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings
	//
	////////////////////////////////////////////////////////////////////////////
	
	// General
	public static final String ADD = "Add"; //$NON-NLS-1$
	public static final String OK = "OK"; //$NON-NLS-1$
	public static final String CANCEL = "Cancel"; //$NON-NLS-1$
	public static final String DELETE = "Delete"; //$NON-NLS-1$
	public static final String WARNING = "Warning"; //$NON-NLS-1$
	public static final String ERROR = "Error"; //$NON-NLS-1$
	public static final String SUCCESS = "Success"; //$NON-NLS-1$
	public static final String TEXT = "Text"; //$NON-NLS-1$
	public static final String NAME = "Name"; //$NON-NLS-1$
	public static final String DATE = "Date"; //$NON-NLS-1$
	
	//SwProgressBar
	public static final String MG_PROGRESS_BAR_LABEL_LOADING = "MGProgressBar.mLabel.Loading"; //$NON-NLS-1$
	
}
