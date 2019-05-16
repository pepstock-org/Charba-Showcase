package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.ChartType;
import org.pepstock.charba.client.TimeSeriesBarChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.TimeSeriesBarDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesBarChartView extends BaseComposite{
	
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	
	private static final int AMOUNT_OF_POINTS = 30;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesBarChartView> {
	}

	@UiField
	TimeSeriesBarChart chart;
	
	final DatasetsItemsSelector selector = new DatasetsItemsSelector();
	
	public TimeSeriesBarChartView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Timeseries Chart");
		long time = System.currentTimeMillis();
		
		TimeSeriesBarDataset dataset1 = chart.newDataset();
	    dataset1.setType(ChartType.BAR);
		dataset1.setLabel("dataset 1");
		dataset1.setStackGroup("mio");
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());


		TimeSeriesBarDataset dataset2 = chart.newDataset();
	    dataset2.setType(ChartType.BAR);
		dataset2.setLabel("dataset 2");
		dataset2.setStackGroup("mio");

		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());


	    List<TimeSeriesItem> data1 = new LinkedList<>();
	    List<TimeSeriesItem> data2 = new LinkedList<>();
	    for (int i=0; i<AMOUNT_OF_POINTS; i++){
	        data1.add(new TimeSeriesItem(new Date(time), 100 * Math.random()));
	        data2.add(new TimeSeriesItem(new Date(time), 100 * Math.random()));
		    time = time + DAY;
	    }
	    dataset1.setTimeSeriesData(data1);
	    dataset2.setTimeSeriesData(data2);

		CartesianTimeAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		
		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1,dataset2);
    
		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderDash(6);
		pOptions.setFireEventOnClearSelection(false);
		
		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(selector);

	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			TimeSeriesBarDataset scDataset = (TimeSeriesBarDataset)dataset;
			for (TimeSeriesItem dp : scDataset.getTimeSeriesData()){
				dp.setValue(getRandomDigit(false));
			}
		}
		chart.update();
	}

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
