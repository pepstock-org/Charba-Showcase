package org.pepstock.charba.showcase.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point
 */
public class Charba_Showcase implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().add(new DemoView());
	}
}
