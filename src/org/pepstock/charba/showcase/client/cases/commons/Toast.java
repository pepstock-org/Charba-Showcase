package org.pepstock.charba.showcase.client.cases.commons;

public class Toast {

	private final ToastingOptions options = new ToastingOptions();

	public Toast(String title, String message) {
		this(title, message, "success");	
	}

	public Toast(String title, String message, String type) {
		options.setTitle(title);
		options.setText(message);
		options.setType(type);	
	}

	public void show() {
		Toasting.show(options);
	}
}