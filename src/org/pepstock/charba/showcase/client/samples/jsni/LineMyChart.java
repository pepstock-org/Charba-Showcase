package org.pepstock.charba.showcase.client.samples.jsni;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.controllers.ControllerType;

public class LineMyChart extends LineChart {
	
	public static final ControllerType TYPE = new ControllerType("stock");

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.LineChart#getType()
	 */
	@Override
	public Type getType() {
		return TYPE;
	}

}
