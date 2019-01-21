package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.showcase.client.samples.jsni.DemoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point
 */
public class Charba_Showcase implements EntryPoint {
	
	public static final Logger LOG = Logger.getLogger("charba-showcase");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().add(new DemoView());
	}

}