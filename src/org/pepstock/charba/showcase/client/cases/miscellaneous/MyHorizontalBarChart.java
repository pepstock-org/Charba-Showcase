package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.HorizontalBarChart;

public class MyHorizontalBarChart extends HorizontalBarChart {

	public MyHorizontalBarChart() {
		super(MyHorizontalBarController.TYPE);
	}

	@Override
	public MyHorizontalBarDataset newDataset() {
		return new MyHorizontalBarDataset();
	}

}
