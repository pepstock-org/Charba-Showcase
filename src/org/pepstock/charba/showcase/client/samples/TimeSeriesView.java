package org.pepstock.charba.showcase.client.samples;

import java.util.Date;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.options.scales.CartesianTimeAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class TimeSeriesView extends BaseComposite{
	
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	
	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesView> {
	}

	@UiField
	LineChart chart;
	
	public TimeSeriesView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Timeseries Chart");
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.nofill);
		
		Color color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		long time = System.currentTimeMillis();
	
		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setT(new Date(time));
			time = time + DAY;
		}
		dataset1.setDataPoints(dp1);
		
		CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.series);
		axis.getTicks().setSource(TickSource.data);
		axis.getTime().setUnit(TimeUnit.day);
		
		chart.getData().setDatasets(dataset1);
		
		chart.getOptions().getScales().setXAxes(axis);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			LineDataset scDataset = (LineDataset)dataset;
			for (DataPoint dp : scDataset.getDataPoints()){
				dp.setY(getRandomDigit(false));
			}
		}
		chart.update();
	}

}
