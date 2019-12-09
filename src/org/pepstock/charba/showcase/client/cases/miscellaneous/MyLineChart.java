package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.controllers.ControllerType;

public class MyLineChart extends LineChart {
	
	public static final ControllerType TYPE = new ControllerType("stock", ChartType.LINE);

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public MyLineDataset newDataset() {
		return new MyLineDataset();
	}
	
	

}
