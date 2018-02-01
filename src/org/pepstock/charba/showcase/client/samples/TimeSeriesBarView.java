package org.pepstock.charba.showcase.client.samples;

import java.util.Date;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
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
public class TimeSeriesBarView extends BaseComposite{
	
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	
	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesBarView> {
	}

	@UiField
	BarChart chart;
	
	public TimeSeriesBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Timeseries Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
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
		axis.getTime().setTooltipFormat(TimeUnit.day.getDefaultFormat());
		
		chart.getData().setDatasets(dataset1);
		
		chart.getOptions().getScales().setXAxes(axis);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			BarDataset scDataset = (BarDataset)dataset;
			for (DataPoint dp : scDataset.getDataPoints()){
				dp.setY(getRandomDigit(false));
			}
		}
		chart.update();
	}

}
