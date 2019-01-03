package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.showcase.client.samples.jsni.DemoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point
 */
public class Charba_Showcase implements EntryPoint {
	
	public static final Logger LOG = Logger.getLogger("charba-showcase");
	
	private static final String TYPE_PARAM = "type";
	
	private static final String PARAM_JSINTEROP = "jsinterop";
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String value = Window.Location.getParameter(TYPE_PARAM);
		if (PARAM_JSINTEROP.equals(value)) {
			RootPanel.get().add(new org.pepstock.charba.showcase.client.samples.jsinterop.DemoView());
		} else {
			RootPanel.get().add(new DemoView());
		}
	}

}