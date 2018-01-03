package org.pepstock.charba.showcase.client.samples;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.showcase.client.resources.Styles;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * An android-style Toast message. a popup message with auto-hiding capability.<br>
 * It has been used to show to the user all messages (info, warning, error) on UI.
 * 
 * @author Andrea Stock Stocchero
 */
public class Toast extends PopupPanel {
	
	// inject the CSS style for this component
	static {
		Styles.INSTANCE.toast().ensureInjected();
	}

	/**
	 * Enumeration with all message level, related to the {@link Level}.<br>
	 * It contains the {@link Level} and the CSS style to apply to the toast.
	 * 
	 * @author Andrea "Stock" Stocchero
	 *
	 */
	public enum Level {
		INFO(0, Styles.INSTANCE.toast().lightGreen()),
		WARNING(4, Styles.INSTANCE.toast().yellow()),
		ERROR(12, Styles.INSTANCE.toast().red());
		
		private final String style;
		
		private final int level;
		
		private Level(int level, String style) {
			this.level = level;
			this.style = style;
		}
		
		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * 
		 * @return the CSS style class
		 */
		public String getStyle() {
			return style;
		}
		
	}
	
	// default space between active toasts
	private static final int SEPARATOR = 10;
	
	// list of current active toast
	private static final List<Toast> ACTIVE_TOASTS = new LinkedList<Toast>();
	
	private String message;
	
	private String title;
	
	private Level level;
	
	private boolean timerStopped = false;

	/**
	 * Builds the popup getting a title (based on the context when the message has been created) and the message
	 * @param title short descritpion of the message
	 * @param message message
	 */
	public Toast(String title, String message) {
		this(title, Level.INFO, message);
	}
	
	/**
	 * Builds the popup getting a title (based on the context when the message has been created) and the a message.<br>
	 * This constructor is used directly ONLY when a need to show a result from a FORM submit.
	 * @param title short description of the message
	 * @param level message level
	 * @param message message string
	 */
	public Toast(String title, Level level, String message) {
		this.level = level;
		this.message = message;
		this.title = title;
		// build the panel
		build();
		// set the style
		addStyleName(this.level.getStyle());
	}

	/**
	 * Builds the widget
	 */
	private void build() {
		setStyleName(Styles.INSTANCE.toast().main());
		final VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(2);
		// check is there is the title
		if (title != null) {
			final Label t = new Label(title);
			t.addStyleName(Styles.INSTANCE.toast().title());
			panel.add(t);
		}
		// checks if there is the message
		if (message != null) {
			final HTML m = new HTML(message);
			m.addStyleName(Styles.INSTANCE.toast().message());
			panel.add(m);
		}
		// sets the maxwidth as 75% of window
		int maxWidth = Window.getClientWidth() * 75 / 100;
		getElement().getStyle().setPropertyPx("maxWidth", maxWidth);
		// adds the panel
 		setWidget(panel);
	}
	
	/**
	 * Calculate the position where the toast must be showed, based on the already active toasts.
	 * @param t toast to be showed
	 * @return the position of this new toast
	 */
	private static synchronized int getAvailableTop(Toast t) {
		int lastTop = 0;
		// checks if there is any other active toast
		if (ACTIVE_TOASTS.isEmpty()) {
			// the position is the 1 /10 of the total height of window
			lastTop = Window.getClientHeight() / 10;
		} else {
			// gets the last active toast
			Toast last = ((LinkedList<Toast>) ACTIVE_TOASTS).getLast();
			// adds the separator space from the other toast
			lastTop = last.getAbsoluteTop() + last.getOffsetHeight() + SEPARATOR;
		}
		ACTIVE_TOASTS.add(t);
		return lastTop;
	}
	
	/**
	 * When the toast is hidden, removes it from the active list
	 * @param t toast to be removed
	 */
	private static synchronized void onToastHide(Toast t) {
		ACTIVE_TOASTS.remove(t);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#show()
	 */
	@Override
	public void show() {
		// test if a toast (level, message, title) is already showing. In this case, ignore the new one
		if (ACTIVE_TOASTS.contains(this)) {
			return;
		}
		setVisible(false);
		super.show();
		// needs deferred task 
		// otherwise browser don't apply styles
	    Scheduler scheduler = Scheduler.get();
	    scheduler.scheduleDeferred(new ToastShower());
	}
	
	/**
	 * Class to show the popup panel.
	 * 
	 * @author Andrea "Stock" Stocchero
	 *
	 */
	private class ToastShower implements ScheduledCommand {
		@Override
        public void execute() {
			// position calculation
			int left = (Window.getClientWidth() - getOffsetWidth())/2;
			int top = getAvailableTop(Toast.this); 
			// set position
			setPopupPosition(left, top);
			setVisible(true);
			// starts the timer.
			Timer t = new Timer() {
				@Override
				public void run() {
					// if the timer is still running
					// hide the toast. If not running, means
					// the toast is already hidden 
					if (!timerStopped){
						Toast.this.hide();
					}
				}
			};
			// Schedule the timer to close the popup.
			t.schedule(1500);
        }
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#hide()
	 */
    @Override
    public void hide() {
	    super.hide();
	    onToastHide(Toast.this);
    }

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#onPreviewNativeEvent(com.google.gwt.user.client.Event.NativePreviewEvent)
	 */
	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		super.onPreviewNativeEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONKEYDOWN:
			onKeyDown(event);
			break;
		case Event.ONMOUSEOVER:
			onMouseOver(event);
			break;
		case Event.ONMOUSEOUT:
			onMouseOut(event);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Catches the key down event. If ESCAPE, hides the toast
	 * @param event HTMl native event
	 */
	private void onKeyDown(NativePreviewEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE){
			hide();
		}
	}

	/**
	 * Catches the mouse out event. If the mouse is out of the toast, the toast is hidden.
	 * @param event HTMl native event
	 */
	private void onMouseOut(NativePreviewEvent event) {
		if (isShowing() && timerStopped) {
			boolean insidePopup = UITools.isEventInsideWidget(event.getNativeEvent(), this);
			// if inside of popup, ignore the event (generates by label of message and title)
			if (!insidePopup){
				hide();
			}
		}
	}
	/**
	 * Catches the mouse over event. if the mouse is over of the toast, the timer to hide the toast is stopped.
	 * The toast doesn't hide when the cursor of mouse is over it.
	 * @param event HTMl native event
	 */
	private void onMouseOver(NativePreviewEvent event) {
		if (isShowing()) {
			boolean insidePopup = UITools.isEventInsideWidget(event.getNativeEvent(), this);
			if (insidePopup){
				timerStopped = true;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Toast) {
			Toast other = (Toast)obj;
			return  level.equals(other.level) && 
					message.equals(other.message) &&
					title.equals(other.title);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Toast [message=" + message + ", title=" + title + ", level="
				+ level + "]";
	}
}