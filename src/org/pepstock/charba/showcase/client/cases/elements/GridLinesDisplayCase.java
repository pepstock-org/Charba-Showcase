package org.pepstock.charba.showcase.client.cases.elements;


import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class GridLinesDisplayCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GridLinesDisplayCase> {
	}

	@UiField
	LineChart chart;

	@UiField
	CheckBox display;

	@UiField
	CheckBox drawBorder;

	@UiField
	CheckBox drawOnChartArea;

	@UiField
	CheckBox drawTicks;

	CartesianCategoryAxis axis1 = null;

	CartesianLinearAxis axis2 = null;

	public GridLinesDisplayCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Gridlines display options"); 
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months, false));
		dataset1.setFill(Fill.FALSE);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.FALSE);

		axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		axis1.getGrideLines().setDisplay(true);
		axis1.getGrideLines().setColor(HtmlColor.DARK_GRAY);
		
		axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.getGrideLines().setDisplay(true);
		axis2.getGrideLines().setColor(HtmlColor.DARK_GRAY);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler(value = { "display", "drawBorder", "drawOnChartArea", "drawTicks"})
	protected void handleChange(ClickEvent event) {
		configChart();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
		
	private void configChart(){
		axis1.getGrideLines().setDisplay(display.getValue());
		axis1.getGrideLines().setDrawBorder(drawBorder.getValue());
		axis1.getGrideLines().setDrawOnChartArea(drawOnChartArea.getValue());
		axis1.getGrideLines().setDrawTicks(drawTicks.getValue());
		axis2.getGrideLines().setDisplay(display.getValue());
		axis2.getGrideLines().setDrawBorder(drawBorder.getValue());
		axis2.getGrideLines().setDrawOnChartArea(drawOnChartArea.getValue());
		axis2.getGrideLines().setDrawTicks(drawTicks.getValue());
		chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
	}
}
