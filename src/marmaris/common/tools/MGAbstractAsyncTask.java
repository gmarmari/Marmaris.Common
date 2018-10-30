package marmaris.common.tools;


import java.awt.Component;
import java.util.logging.Level;

import javax.swing.SwingWorker;

import marmaris.common.log.MGLogger;

/**
 *My basic class for an background task with input I and output O.
 */
public abstract class MGAbstractAsyncTask<I, O>{
	
	public interface Listener{
		void onWorkerDone();
	}
	private Listener mListener;
	
	private SwingWorker<O, Void> worker = new SwingWorker<O, Void>(){

		@Override
		protected O doInBackground() throws Exception {
			try {
				output = swExecute();
			}catch (InterruptedException e) {
				MGLogger.appendLog(getClass().toString(), Level.INFO, "SwingWorker canceled.");
			}catch (Exception e) {
				MGLogger.appendLog(getClass().toString(), Level.SEVERE, e);
				appendError(e.toString());
				output = null;
			}
			return output;
		}
		
		protected void done() {
			if(!isCancelled()) {
				onSwPostExecute();
				if(mListener != null)
					mListener.onWorkerDone();
			}
		};
		
	};
	
	private Component c;
	
	protected I input;
	private O output;
	public O getOutput(){
		return output;
	}
	
	protected MGProgressBar mProgressBar;
	
	private String error;
	/**
	 * An Error to string if any went wrong
	 * @return
	 */
	public String getError() {
		return error;
	}
	protected void appendError(String msg) {
		if (msg == null || msg.length() == 0)
			return;
		if(error == null)
			error = "";
		if (error.length() > 0)
			error += MGAppShared.NEW_LINE;
		error += msg;
	}
	
	/**
	 * sets the input, calls onSwPreExecute and starts the SwingWorker. When the work is done, calls onSwPostExecute and onWorkerDone of the given listener.
	 * On onWorkerDone get the output by calling the getOutput() method.
	 * @param input : the input
	 * @param mListener : our listener in order to take the output when the work is done.
	 */
	public void start(Component c, I input, Listener mListener) {
		this.c = c;
		this.input = input;
		this.mListener = mListener;
		onSwPreExecute();
		worker.execute();
	}
	
	/**
	 * Starts a progress bar before the worker starts work on a different thread.
	 * Override to change this behavior
	 */
	protected void onSwPreExecute(){
		mProgressBar = MGProgressBar.newInstance(c);
		mProgressBar.setListener(new MGProgressBar.Listener() {
			
			@Override
			public void onClosed() {
				if (!worker.isDone())
					worker.cancel(true);
			}

			@Override
			public void onCancel() {
				if (!worker.isDone())
					worker.cancel(true);
			}
		});
		mProgressBar.start();
	}
	
	/**
	 * Is executed on doInBackground of the SwingWorker
	 * @return the output
	 * @throws Exception : if any exception is caught and its message is added to error
	 */
	protected abstract O swExecute() throws Exception;
	
	/**
	 * Closes the progress bar when the workers work is finished.
	 * Override to change this behavior
	 */
	protected void onSwPostExecute() {
		if (mProgressBar != null)
			mProgressBar.stop();
	}
	

}
