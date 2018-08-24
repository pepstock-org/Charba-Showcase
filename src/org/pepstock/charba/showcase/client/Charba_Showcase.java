package org.pepstock.charba.showcase.client;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.client.plugins.impl.ChartBackgroundColor;
import org.pepstock.charba.showcase.client.resources.Resources;
import org.pepstock.charba.showcase.client.samples.Toast;
import org.pepstock.charba.showcase.client.samples.Toast.Level;

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
		try {
			Defaults.getPlugins().register(new ChartBackgroundColor());
		} catch (InvalidPluginIdException e) {
			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
		}
		Injector.ensureInjected(Resources.INSTANCE.pieceLabelJsSource());
		RootPanel.get().add(new DemoView());

	}
}