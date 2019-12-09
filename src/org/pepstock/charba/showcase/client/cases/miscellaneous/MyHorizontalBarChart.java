package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.HorizontalBarChart;
import org.pepstock.charba.client.Type;

public class MyHorizontalBarChart extends HorizontalBarChart {

	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

	@Override
	public MyHorizontalBarDataset newDataset() {
		return new MyHorizontalBarDataset();
	}

}
