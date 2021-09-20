package org.pepstock.charba.showcase.client.cases.commons;

import org.pepstock.charba.client.utils.Console;
import org.pepstock.charba.client.utils.toast.IsToastType;
import org.pepstock.charba.client.utils.toast.ToastOptions;
import org.pepstock.charba.client.utils.toast.Toaster;
import org.pepstock.charba.client.utils.toast.enums.DefaultToastType;

public class Toast {

	private final ToastOptions options = new ToastOptions();

	public Toast(String title, String message) {
		this(title, message, DefaultToastType.SUCCESS);	
	}

	public Toast(String title, String message, IsToastType type) {
		options.getTitle().setContent(title);
		options.getLabel().setContent(message);
		options.setType(type);
		Console.log(options);
	}

	public void show() {
		Toaster.get().show(options);
	}
}