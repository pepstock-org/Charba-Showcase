package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.controllers.ControllerType;

public class LineMyChart extends LineChart {
	
	public static final ControllerType TYPE = new ControllerType("stock", ChartType.line);

	@Override
	public Type getType() {
		return TYPE;
	}

}
