package org.pepstock.charba.showcase.client.cases.commons;

import org.pepstock.charba.client.utils.toast.IsToastType;
import org.pepstock.charba.client.utils.toast.Toaster;
import org.pepstock.charba.client.utils.toast.enums.DefaultToastType;

public class Toast {

	private final String title;
	
	private final String label;
	
	private final IsToastType type;

	public Toast(String title, String message) {
		this(title, message, DefaultToastType.SUCCESS);	
	}

	public Toast(String title, String message, IsToastType type) {
		this.title = title;
		this.label = message;
		this.type  =type;
	}

	public void show() {
		Toaster.get().show(type, title, label);
	}
}