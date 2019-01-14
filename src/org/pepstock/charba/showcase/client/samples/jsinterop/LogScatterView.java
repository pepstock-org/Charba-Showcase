package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.enums.CartesianAxisType;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.client.jsinterop.ScatterChart;
import org.pepstock.charba.client.jsinterop.callbacks.TickCallback;
import org.pepstock.charba.client.jsinterop.configuration.Axis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLogarithmicAxis;
import org.pepstock.charba.client.jsinterop.data.DataPoint;
import org.pepstock.charba.client.jsinterop.data.ScatterDataset;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class LogScatterView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LogScatterView> {
	}

	private final static double[] X = new double[]{ 1, 1.26, 1.58, 2.0, 2.51, 3.16, 3.98, 5.01, 6.31, 7.94, 10.00, 12.6, 15.8, 20.0, 25.1, 31.6, 39.8, 50.1, 63.1, 79.4, 100.00, 126, 158, 200, 251, 316, 398, 501, 631, 794, 1000,};
	private final static double[] Y = new double[]{ -1.711e-2, -2.708e-2, -4.285e-2, -6.772e-2, -1.068e-1, -1.681e-1, -2.635e-1, -4.106e-1, -6.339e-1, -9.659e-1, -1.445, -2.110, -2.992, -4.102, -5.429, -6.944, -8.607, -1.038e1, -1.223e1, -1.413e1, -1.607e1, -1.803e1, -2e1, -2.199e1, -2.398e1, -2.597e1, -2.797e1, -2.996e1, -3.196e1, -3.396e1, -3.596e1,};
	
	@UiField
	ScatterChart chart;
	
	public LogScatterView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Logarithmic X-Axis");
	
		ScatterDataset dataset1 = chart.newDataset();
		dataset1.setLabel("V(node2)");
		
		IsColor color1 = Colors.ALL[1];

		DataPoint[] points = new DataPoint[X.length];
		for (int i=0; i<X.length; i++){
			points[i] = new DataPoint();
			points[i].setX(X[i]);
			points[i].setY(Y[i]);
		}
		dataset1.setDataPoints(points);
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		
		CartesianLogarithmicAxis axis1 = new CartesianLogarithmicAxis(chart, CartesianAxisType.x);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.setPosition(Position.bottom);
		axis1.getTicks().setCallback(new TickCallback() {
			
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.jsinterop.callbacks.TickCallback#onCallback(org.pepstock.charba.client.jsinterop.configuration.Axis, double, int, java.util.List)
			 */
			@Override
			public String onCallback(Axis axis, double value, int index, List<Double> values) {
				int remain = (int)(value / (Math.pow(10, Math.floor(Math.log10(value)))));
				if (remain == 1 || remain == 2 || remain == 5) {
					return value + "Hz";
				}
				return "";
			}
		});
		axis1.getScaleLabel().setLabelString("Frequency");
		axis1.getScaleLabel().setDisplay(true);
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setCallback(new TickCallback() {
			
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.jsinterop.callbacks.TickCallback#onCallback(org.pepstock.charba.client.jsinterop.configuration.Axis, double, int, java.util.List)
			 */
			@Override
			public String onCallback(Axis axis, double value, int index, List<Double> values) {
				return value + "dB";
			}
		});

		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("voltage");
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setDatasets(dataset1);
		

	}

}
