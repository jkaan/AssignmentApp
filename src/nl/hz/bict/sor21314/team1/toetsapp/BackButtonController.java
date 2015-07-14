package nl.hz.bict.sor21314.team1.toetsapp;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

/**
 * This handles the back button in the application. It's not a complete implementation. 
 * Closing the window and edit the url to another website isn't seen by this controller. 
 * This controller only sees when a view changes via the navigator.
 * This class uses a popupview for a confirmation
 * @author Kevin
 * @version 15-05-2014
 */
public class BackButtonController implements ViewChangeListener, Button.ClickListener {
	private static final long serialVersionUID = 2000089605665940139L;
	
	// Result posibilities
	private static final int RESULT_UNDEFINED = 2;
	private static final int RESULT_TRUE = 1;
	private static final int RESULT_FALSE = 0;

	// Confirm by the user
	private BackButtonPopupView popup;
	
	// Application is in edit mode
	private static boolean editing = false;
	
	// Result from the popup
	private int result;
	private Thread thread;

	public BackButtonController() {
		popup = new BackButtonPopupView(this);
		result = RESULT_UNDEFINED;
	}
	
	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		if(!editing)
			return true;
		else {
			return getResult();
		}
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		// Nothing
	}
	
	private boolean getResult() {
		UI.getCurrent().addWindow(popup);

		Runnable run = new Run();
		thread = new Thread(run);
		thread.start();
		
		if(result == RESULT_FALSE) {
			result = RESULT_UNDEFINED;
			return false;
		} else {
			result = RESULT_UNDEFINED;
			editing = false;
			return true;
		}
	}
	
	public static void setEditing() {
		editing = false;
	}
	
	public static void setNotEditing() {
		editing = false;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getId().equals("YES"))
			result = RESULT_TRUE;
		if(event.getButton().getId().equals("NO"))
			result = RESULT_FALSE;
		
		popup.close();
	}
	
	private class Run implements Runnable {
		@Override
		public void run() {
			while(result == RESULT_UNDEFINED) {
				synchronized(thread) {
					try {
						thread.wait();
					} catch(InterruptedException e) {
						
					}
				}
			}
			
			thread.notify();
		}
		
	}

}
