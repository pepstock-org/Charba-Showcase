package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.gwt.widgets.LineChartWidget;

public class MyLineChartWidget extends LineChartWidget {

	public MyLineChartWidget() {
		super(new MyLineChart());
	}

	@Override
	public MyLineDataset newDataset() {
		return newDataset(false);
	}

	@Override
	public MyLineDataset newDataset(boolean hidden) {
		return (MyLineDataset) getChart().newDataset();
	}

}
