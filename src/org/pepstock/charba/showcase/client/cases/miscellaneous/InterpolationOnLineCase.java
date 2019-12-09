package org.pepstock.charba.showcase.client.cases.miscellaneous;


import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.CubicInterpolationMode;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class InterpolationOnLineCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
	
	private static final int AMOUNT = 12;

	interface ViewUiBinder extends UiBinder<Widget, InterpolationOnLineCase> {
	}
	
	private double[] data = null;

	@UiField
	LineChart chart;
	
	public InterpolationOnLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		super.months = AMOUNT;
		createData();
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Interpolation options on line chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Cubic interpolation (monotone)");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(data);
		dataset1.setFill(Fill.FALSE);
		dataset1.setCubicInterpolationMode(CubicInterpolationMode.MONOTONE);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("Cubic interpolation (default)");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(data);
		dataset2.setFill(Fill.FALSE);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("Linear interpolation");
		
		IsColor color3 = Colors.ALL[2];
		
		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(data);
		dataset3.setFill(Fill.FALSE);
		dataset3.setLineTension(0);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.getTicks().setSuggestedMax(200);
		axis2.getTicks().setSuggestedMin(-10);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
		
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		createData();
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(data);
		}
		chart.update();
	}
	
	private void createData(){
		data = getRandomDigits(AMOUNT);
		for (int i=0; i<AMOUNT; i++){
			if (i == 5){
				data[i] = Double.NaN;
			}
		}
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
