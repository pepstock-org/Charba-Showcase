package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.jsinterop.HorizontalBarChart;
import org.pepstock.charba.client.jsinterop.Type;

public class HorizontalBarMyChart extends HorizontalBarChart {
	
	

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.LineChart#getType()
	 */
	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

}
