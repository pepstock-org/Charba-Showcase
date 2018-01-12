package org.pepstock.charba.showcase.client;

import java.util.logging.Logger;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.enums.PointStyle;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point
 */
public class Charba_Showcase implements EntryPoint {
	
	final Logger log = Logger.getLogger("mio");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Defaults.getChartGlobal(Type.line).getElements().getPoint().setPointStyle(PointStyle.crossRot);
		log.info(Defaults.getChartGlobal(Type.pie).toString());

		RootPanel.get().add(new DemoView());
	}
}
