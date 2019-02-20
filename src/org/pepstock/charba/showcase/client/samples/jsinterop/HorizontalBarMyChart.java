package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.HorizontalBarChart;
import org.pepstock.charba.client.Type;

public class HorizontalBarMyChart extends HorizontalBarChart {

	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

}
