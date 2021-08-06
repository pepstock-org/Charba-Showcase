package org.pepstock.charba.showcase.client.cases.commons;

import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;

public final class ToastingOptions extends NativeObjectContainer {

	private enum Property implements Key
	{
		TITLE("title"),
		TEXT("text"),
		TYPE("type"),
		PROGRESS_BAR_TYPE("progressBarType"),
		TIMEOUT("timeout"),
		AUTO_HIDE("autoHide"),
		HIDE_PROGRESS_BAR("hideProgressBar");

		private final String value;

		private Property(String value) {
			this.value = value;
		}
		@Override
		public String value() {
			return value;
		}

	}

	public ToastingOptions() {
		super();
	}

	public void setTitle(String text) {
		setValue(Property.TITLE, text);
	}

	public void setText(String text) {
		setValue(Property.TEXT, text);
	}
	
	public void setType(String text) {
		setValue(Property.TYPE, text);
	}

	public void setProgressBarType(String text) {
		setValue(Property.PROGRESS_BAR_TYPE, text);
	}

	public void setHideProgressBar(boolean hide) {
		setValue(Property.HIDE_PROGRESS_BAR, hide);
	}

	public void setAutoHide(boolean hide) {
		setValue(Property.AUTO_HIDE, hide);
	}

	public void setTimeout(int timeout) {
		setValue(Property.TIMEOUT, timeout);
	}
	
	NativeObject nativeObject() {
		return getNativeObject();
	}

}