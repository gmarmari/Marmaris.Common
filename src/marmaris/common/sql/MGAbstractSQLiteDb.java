package marmaris.common.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import marmaris.common.log.MGLogger;

/**
 * My basic class for an sql lite db 
 */
public abstract class MGAbstractSQLiteDb {

	// SQLite data types
	protected static final String INTEGER_PRIMATY_KEY = "INTEGER PRIMARY KEY"; 
	protected static final String INTEGER = "INTEGER"; 
	protected static final String TEXT = "TEXT"; 
	protected static final String REAL = "REAL"; 
	protected static final String BLOB = "BLOB";
	

	public MGAbstractSQLiteDb() {
		createTables();
	}
	
	private String mURL;
	
	/** Get the URL of the Database */
	protected String getURL() {
		if(mURL == null) 
			mURL = createURL();
		return mURL;
	}
	
	private SimpleDateFormat mDateFormater;
	protected SimpleDateFormat getDateFormater() {
		if (mDateFormater == null)
			mDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.getDefault());
		return mDateFormater;
	}
	
	protected String stringFromDate(Date date) {
		if(date == null)
			return null;
		
		try {
			return getDateFormater().format(date);
		} catch (Exception e) {
			MGLogger.appendLog(getClass().toString(), Level.SEVERE, e);
			return null;
		}
	}
	
	protected Date dateFromString(String value) {
		if(value == null || value.isEmpty())
			return null;
		
		try {
			return getDateFormater().parse(value);
		} catch (Exception e) {
			MGLogger.appendLog(getClass().toString(), Level.SEVERE, e);
			return null;
		}
	}
	
	
	/** Create the URL of the Database */
	protected abstract String createURL();
	
	/** Create the tables of the database */
	protected abstract void createTables();
	
	/**
	 * Gets the table count
	 * @param tableName : the table name
	 * @return : the count of the table, or -1 if error
	 */
	public int getTableCount(String tableName) {
		if(tableName == null || tableName.isEmpty())
			return -1;
		
		String sql = "SELECT COUNT(*) AS total FROM "+tableName;
		
		try {
    		Connection conn = DriverManager.getConnection(getURL());
    		Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count= rs.getInt("total");
            conn.close();
            return count;
        } catch (Exception e) {
        	MGLogger.appendLog(getClass().toString(), Level.SEVERE, e);
        	return -1;
        }
	}
	
}
