package org.pepstock.charba.showcase.client.cases.jsinterop;

import org.pepstock.charba.client.HorizontalBarChart;
import org.pepstock.charba.client.Type;

public class HorizontalBarMyChart extends HorizontalBarChart {

	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

	@Override
	public HorizontalBarMyDataset newDataset() {
		return new HorizontalBarMyDataset();
	}

}
