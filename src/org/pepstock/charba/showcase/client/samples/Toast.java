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

public class Toast extends PopupPanel {
	
	static {
		Styles.INSTANCE.toast().ensureInjected();
	}

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
		
		public int getLevel() {
			return level;
		}

		public String getStyle() {
			return style;
		}
		
	}
	
	private static final int SEPARATOR = 10;
	
	private static final List<Toast> ACTIVE_TOASTS = new LinkedList<Toast>();
	
	private String message;
	
	private String title;
	
	private Level level;
	
	private boolean timerStopped = false;

	public Toast(String title, String message) {
		this(title, Level.INFO, message);
	}

	public Toast(String title, Level level, String message) {
		this.level = level;
		this.message = message;
		this.title = title;
		build();
		addStyleName(this.level.getStyle());
	}

	private void build() {
		setStyleName(Styles.INSTANCE.toast().main());
		final VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(2);
		if (title != null) {
			final Label t = new Label(title);
			t.addStyleName(Styles.INSTANCE.toast().title());
			panel.add(t);
		}
		if (message != null) {
			final HTML m = new HTML(message);
			m.addStyleName(Styles.INSTANCE.toast().message());
			panel.add(m);
		}
		int maxWidth = Window.getClientWidth() * 75 / 100;
		getElement().getStyle().setPropertyPx("maxWidth", maxWidth);
 		setWidget(panel);
	}

	private static synchronized int getAvailableTop(Toast t) {
		int lastTop = 0;
		if (ACTIVE_TOASTS.isEmpty()) {
			lastTop = Window.getClientHeight() / 10;
		} else {
			Toast last = ((LinkedList<Toast>) ACTIVE_TOASTS).getLast();
			lastTop = last.getAbsoluteTop() + last.getOffsetHeight() + SEPARATOR;
		}
		ACTIVE_TOASTS.add(t);
		return lastTop;
	}
	
	private static synchronized void onToastHide(Toast t) {
		ACTIVE_TOASTS.remove(t);
	}

	@Override
	public void show() {
		if (ACTIVE_TOASTS.contains(this)) {
			return;
		}
		setVisible(false);
		super.show();
	    Scheduler scheduler = Scheduler.get();
	    scheduler.scheduleDeferred(new ToastShower());
	}

	private class ToastShower implements ScheduledCommand {
		@Override
        public void execute() {
			int left = (Window.getClientWidth() - getOffsetWidth())/2;
			int top = getAvailableTop(Toast.this); 
			setPopupPosition(left, top);
			setVisible(true);
			Timer t = new Timer() {

				@Override
				public void run() {
					if (!timerStopped){
						Toast.this.hide();
					}
				}
			};
			t.schedule(1500);
        }
	}

	@Override
    public void hide() {
	    super.hide();
	    onToastHide(Toast.this);
    }

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
	
	private void onKeyDown(NativePreviewEvent event) {
		if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE){
			hide();
		}
	}

	private void onMouseOut(NativePreviewEvent event) {
		if (isShowing() && timerStopped) {
			boolean insidePopup = UITools.isEventInsideWidget(event.getNativeEvent(), this);
			if (!insidePopup){
				hide();
			}
		}
	}

	private void onMouseOver(NativePreviewEvent event) {
		if (isShowing()) {
			boolean insidePopup = UITools.isEventInsideWidget(event.getNativeEvent(), this);
			if (insidePopup){
				timerStopped = true;
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

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

	@Override
	public String toString() {
		return "Toast [message=" + message + ", title=" + title + ", level="
				+ level + "]";
	}
}