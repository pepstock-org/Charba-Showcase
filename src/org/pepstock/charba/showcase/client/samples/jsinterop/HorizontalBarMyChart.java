package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.HorizontalBarChart;
import org.pepstock.charba.client.Type;

/**
 * @author Andrea "Stock" Stocchero
 */
public class HorizontalBarMyChart extends HorizontalBarChart {

	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

}
