package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.showcase.client.resources.Images;
import org.pepstock.charba.showcase.client.samples.jsinterop.DemoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
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
		Defaults.get().getGlobal().getElements().getPoint().setHoverRadius(99999);
		
		Image.prefetch(Images.INSTANCE.pattern().getSafeUri());
		Image.prefetch(Images.INSTANCE.patternHover().getSafeUri());
		RootPanel.get().add(new DemoView());
	}

}