package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Date;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesBarView extends BaseComposite{
	
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	
	private static final int AMOUNT_OF_POINTS = 30;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesBarView> {
	}

	@UiField
	BarChart chart;
	
	final DatasetsItemsSelector selector = new DatasetsItemsSelector();
	
	public TimeSeriesBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Timeseries Chart");
		long time = System.currentTimeMillis();
		
	    BarDataset dataset1 = chart.newDataset();
	    dataset1.setType(ChartType.BAR);
		dataset1.setLabel("dataset 2");

	    DataPoint[] points = new DataPoint[AMOUNT_OF_POINTS];
	    DataPoint[] rainPoints = new DataPoint[AMOUNT_OF_POINTS];
	    int idx = 0;
	    for (int i=0; i<AMOUNT_OF_POINTS; i++){
	        DataPoint dataPoint=  new DataPoint();
	        dataPoint.setT(new Date(time));
	        dataPoint.setY(100 * Math.random());
	        points[idx] = dataPoint;

	        DataPoint rainPoint=  new DataPoint();
	        rainPoint.setT(new Date(time));
		    rainPoint.setY(100 * Math.random());
		    rainPoints[idx] =rainPoint;

		    idx++;
		    time = time + DAY;
	    }
	    dataset1.setDataPoints(rainPoints);

	    CartesianTimeAxis axis = new CartesianTimeAxis(chart);
	    axis.setDistribution(ScaleDistribution.SERIES);
	    axis.getTicks().setSource(TickSource.DATA);
	    axis.getTime().setUnit(TimeUnit.DAY);
	    
	    chart.getData().setDatasets(dataset1);
	    chart.getOptions().getScales().setXAxes(axis);
	    
		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderDash(6);
		pOptions.setFireEventOnClearSelection(false);
		
		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(selector);

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

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
