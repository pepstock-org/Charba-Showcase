package org.pepstock.charba.showcase.client.samples.jsni;

import org.pepstock.charba.client.HorizontalBarChart;
import org.pepstock.charba.client.Type;

public class HorizontalBarMyChart extends HorizontalBarChart {
	
	

	/* (non-Javadoc)
	 * @see org.pepstock.charba.client.LineChart#getType()
	 */
	@Override
	public Type getType() {
		return MyHorizontalBarController.TYPE;
	}

}
