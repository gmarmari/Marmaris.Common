package marmaris.common.log;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import marmaris.common.tools.MGAppShared;


public class MGLogger {
	
	private static Logger LOGGER;
	static private FileHandler file;
    static private MGLogHtmlFormatter htmlFormatter;
	
	/**
	 * Appends a message to the log. If LOGGER == null, then create a new Logger.
	 * @param tag : the class info
	 * @param level : Log Level
	 * @param msg : the message to be logged
	 */
	public static void appendLog(String tag, Level level, String msg) {
		if (msg != null && msg.length() > 0) {
			if (LOGGER == null) {
				LOGGER = Logger.getLogger(MGLogger.class.toString());
				// mLogger.setLevel(Level.WARNING);

				if (file == null) {
					try {
						file = new FileHandler("SwLog.html");
						htmlFormatter = new MGLogHtmlFormatter();
						file.setFormatter(htmlFormatter);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				if (file != null)
					LOGGER.addHandler(file);
			}

			String log = "";
			if (tag != null && tag.length() > 0) 
				log += "TAG:" +MGAppShared.NEW_LINE_HTML+MGAppShared.NEW_LINE + tag; 
			if (log.length() > 0)
				log += MGAppShared.NEW_LINE_HTML+MGAppShared.NEW_LINE;
			log += "MESSAGE:" + MGAppShared.NEW_LINE_HTML+MGAppShared.NEW_LINE + msg;
			LOGGER.log(level, log);
		}
	}
	
	/**
	 * Appends a message to the log
	 * @param tag : the class info
	 * @param level : Log Level
	 * @param e : the Exception to be logged
	 */
	public static void appendLog(String tag, Level level, Exception e) {
		appendLog(tag, level, getExceptionToString(e));
	}
	
	private static String getExceptionToString(Exception e) {
		if (e == null) 
			return null;
		
		String msg = "Exception: "+ e.toString();
		if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
			msg+= MGAppShared.NEW_LINE_HTML+MGAppShared.NEW_LINE + " Stacktrace: ";
			for (StackTraceElement stacktrace : e.getStackTrace()) {
				if (stacktrace != null) {
					msg+= MGAppShared.NEW_LINE_HTML+MGAppShared.NEW_LINE;
					msg += 	"Filename: " +stacktrace.getFileName() + ", "
							+"ClassName: " +stacktrace.getClassName() + ", "
							+"MethodName: " +stacktrace.getMethodName()  + ", "
							+"LineNumber: " +stacktrace.getLineNumber();
				}
			}
		}	
		
		return msg;
	}
	
}
