package org.pepstock.charba.showcase.client.samples.jsinterop;


import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class GridLinesDisplayView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GridLinesDisplayView> {
	}

	@UiField
	LineChart chartGtrue;

	@UiField
	LineChart chartGfalse;

	@UiField
	LineChart chartGfalseNoborder;

	@UiField
	LineChart chartGNoDraw;

	@UiField
	LineChart chartGNoTicks;

	private double[] data1 = null;
	
	private double[] data2 = null;
	
	public GridLinesDisplayView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		data1 = getRandomDigits(months, false);
		data2 = getRandomDigits(months, false);
		configChart(chartGtrue, "Display: true", true, null, null, null);
		configChart(chartGfalse, "Display: false", false, null, null, null);
		configChart(chartGfalseNoborder, "Display: false, no border", false, Boolean.FALSE, null, null);
		configChart(chartGNoDraw, "DrawOnChartArea: false", true, Boolean.TRUE, Boolean.FALSE, null);
		configChart(chartGNoTicks, "DrawTicks: false", true, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		data1 = getRandomDigits(months, false);
		data2 = getRandomDigits(months, false);
		loadNewData(chartGtrue);
		loadNewData(chartGfalse);
		loadNewData(chartGfalseNoborder);
		loadNewData(chartGNoDraw);
		loadNewData(chartGNoTicks);
	}
		
	private void loadNewData(LineChart chart){
		Dataset ds1 = chart.getData().getDatasets().get(0);
		ds1.setData(data1);
		Dataset ds2 = chart.getData().getDatasets().get(1);
		ds2.setData(data2);
		chart.update();
	}

	
	private void configChart(LineChart chart, String title, boolean display, Boolean drawBorder, Boolean drawOnChartArea, Boolean drawTicks){
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Chart - "+title); 
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(data1);
		dataset1.setFill(Fill.nofill);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(data2);
		dataset2.setFill(Fill.nofill);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		axis1.getGrideLines().setDisplay(display);
		if (drawBorder != null){
			axis1.getGrideLines().setDrawBorder(drawBorder.booleanValue());
		}
		if (drawOnChartArea != null){
			axis1.getGrideLines().setDrawOnChartArea(drawOnChartArea.booleanValue());
		}
		if (drawTicks != null){
			axis1.getGrideLines().setDrawTicks(drawTicks.booleanValue());
		}
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.getGrideLines().setDisplay(display);
		if (drawBorder != null){
			axis2.getGrideLines().setDrawBorder(drawBorder.booleanValue());
		}
		if (drawOnChartArea != null){
			axis2.getGrideLines().setDrawOnChartArea(drawOnChartArea.booleanValue());
		}
		if (drawTicks != null){
			axis2.getGrideLines().setDrawTicks(drawTicks.booleanValue());
		}
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
	}
}
