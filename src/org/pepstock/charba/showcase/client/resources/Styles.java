package org.pepstock.charba.showcase.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Styles extends ClientBundle {

	Styles INSTANCE = GWT.create(Styles.class);

	@Source("css/Toast.css")
	Toast toast();

	interface Toast extends CssResource {
		String main();

		String title();

		String message();

		String blue();

		String green();

		String grey();

		String yellow();

		String red();

		String lightGreen();

		String lightBlue();
	}

	@Source("css/Showcase.css")
	Showcase showcase();

	interface Showcase extends CssResource {
		String myItemSelected();
	}

}
