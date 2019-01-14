package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.jsinterop.ChartType;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.Type;
import org.pepstock.charba.client.jsinterop.controllers.ControllerType;

public class LineMyChart extends LineChart {
	
	public static final ControllerType TYPE = new ControllerType("stock", ChartType.line);

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.LineChart#getType()
	 */
	@Override
	public Type getType() {
		return TYPE;
	}

}
