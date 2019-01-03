package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Date;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.jsinterop.BarChart;
import org.pepstock.charba.client.jsinterop.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.jsinterop.data.BarDataset;
import org.pepstock.charba.client.jsinterop.data.DataPoint;
import org.pepstock.charba.client.jsinterop.data.Dataset;

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
	
	
	private static final int AMOUNT_OF_POINTS = 30;

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
		
//		LineDataset dataset1 = new LineDataset();
//		dataset1.setType(ChartType.line);
//		dataset1.setLabel("dataset 1");
//		
//		IsColor color3 = Colors.ALL[3];
//		
//		dataset1.setBackgroundColor(color3.toHex());
//		dataset1.setBorderColor(color3.toHex());
//		dataset1.setFill(Fill.nofill);

		long time = System.currentTimeMillis();
		
	    BarDataset dataset2 = chart.newDataset();
	    dataset2.setType(ChartType.bar);
		dataset2.setLabel("dataset 2");

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

//	    dataset1.setDataPoints(points);
	    dataset2.setDataPoints(rainPoints);

	    CartesianTimeAxis axis = new CartesianTimeAxis(chart);
	    axis.setDistribution(ScaleDistribution.series);
	    axis.getTicks().setSource(TickSource.data);
	    axis.getTime().setUnit(TimeUnit.day);
	    
//		try {
//			chart.getPlugins().add(new DatasetsItemsSelector());
//		} catch (InvalidPluginIdException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		chart.addHandler(new DatasetRangeSelectionEventHandler() {
//			
//			@Override
//			public void onSelect(DatasetRangeSelectionEvent event) {
//				StringBuilder sb = new StringBuilder();
//				sb.append("Dataset from: <b>").append(event.getFrom()).append("</b><br>");
//				sb.append("Dataset to: <b>").append(event.getTo()).append("</b><br>");
//				new Toast("Dataset Range Selected!", sb.toString()).show();
//			}
//		}, DatasetRangeSelectionEvent.TYPE);

//	    chart.getData().setDatasets(dataset1, dataset2);
	    
	    chart.getData().setDatasets(dataset2);

	    chart.getOptions().getScales().setXAxes(axis);
	    

		
//		BarDataset dataset1 = chart.newDataset();
//		dataset1.setLabel("dataset 1");
//		
//		long time = System.currentTimeMillis();
//	
//		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
//		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
//		for (int i=0; i<AMOUNT_OF_POINTS; i++){
//			dp1[i] = new DataPoint();
//			dp1[i].setY(xs1[i]);
//			dp1[i].setT(new Date(time));
//			time = time + DAY;
//		}
//		dataset1.setDataPoints(dp1);
//		
//		CartesianTimeAxis axis = new CartesianTimeAxis(chart);
//		axis.setDistribution(ScaleDistribution.series);
//		axis.getTicks().setSource(TickSource.data);
//		axis.getTime().setUnit(TimeUnit.day);
//		axis.getTime().setTooltipFormat(TimeUnit.day.getDefaultFormat());
//		
//		chart.getData().setDatasets(dataset1);
//		
//		chart.getOptions().getScales().setXAxes(axis);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			// FIXME con multi types da errore
			BarDataset scDataset = (BarDataset)dataset;
			for (DataPoint dp : scDataset.getDataPoints()){
				dp.setY(getRandomDigit(false));
			}
		}
		chart.update();
	}

}
