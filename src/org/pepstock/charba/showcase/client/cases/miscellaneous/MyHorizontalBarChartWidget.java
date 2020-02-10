package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.gwt.widgets.HorizontalBarChartWidget;

public class MyHorizontalBarChartWidget extends HorizontalBarChartWidget {

	public MyHorizontalBarChartWidget() {
		super(new MyHorizontalBarChart());
	}

	@Override
	public MyHorizontalBarDataset newDataset() {
		return (MyHorizontalBarDataset)getChart().newDataset();
	}
}
