package org.pepstock.charba.showcase.client.cases.commons;

public class Toast {

	private final ToastingOptions options = new ToastingOptions();

	public Toast(String title, String message) {
		options.setTitle(title);
		options.setText(message);
		options.setType("success");	}

	public void show() {
		Toasting.show(options);
	}
}