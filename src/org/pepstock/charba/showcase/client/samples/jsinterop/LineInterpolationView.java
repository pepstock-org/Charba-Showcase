package org.pepstock.charba.showcase.client.samples.jsinterop;


import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.enums.Fill;
import org.pepstock.charba.client.jsinterop.enums.InteractionMode;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.jsinterop.data.Dataset;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
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
public class LineInterpolationView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
	
	private static final int AMOUNT = 12;

	interface ViewUiBinder extends UiBinder<Widget, LineInterpolationView> {
	}
	
	private double[] data = null;

	@UiField
	LineChart chart;
	
	public LineInterpolationView() {
		initWidget(uiBinder.createAndBindUi(this));

		super.months = AMOUNT;
		createData();
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Interpolation Line Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Cubic interpolation (monotone)");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(data);
		dataset1.setFill(Fill.nofill);
		dataset1.setCubicInterpolationMode("monotone");

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("Cubic interpolation (default)");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(data);
		dataset2.setFill(Fill.nofill);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("Linear interpolation");
		
		IsColor color3 = Colors.ALL[2];
		
		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(data);
		dataset3.setFill(Fill.nofill);
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

}
