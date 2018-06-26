package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.ScatterChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.enums.CartesianAxisType;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class MultiAxisScatterView extends BaseComposite{
	
	private static final int AMOUNT_OF_POINTS = 16;
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MultiAxisScatterView> {
	}

	@UiField
	ScatterChart chart;
	
	public MultiAxisScatterView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Multi Axis Scatter Chart");
		chart.getOptions().getHover().setIntersect(true);
		chart.getOptions().getHover().setMode(InteractionMode.nearest);

		CartesianLinearAxis xAxes = new CartesianLinearAxis(chart, CartesianAxisType.x);
		xAxes.setPosition(Position.bottom);
		xAxes.getGrideLines().setZeroLineColor("rgba(0,0,0,1)");
		xAxes.setId("x-axis-1");
		
		CartesianLinearAxis yAxes1 = new CartesianLinearAxis(chart);
		yAxes1.setId("y-axis-1");
		yAxes1.setDisplay(true);
		yAxes1.setPosition(Position.left);

		CartesianLinearAxis yAxes2 = new CartesianLinearAxis(chart);
		yAxes2.setId("y-axis-2");
		yAxes2.setDisplay(true);
		yAxes2.setPosition(Position.right);
		yAxes2.getGrideLines().setDrawOnChartArea(false);
		
		chart.getOptions().getScales().setXAxes(xAxes);
		chart.getOptions().getScales().setYAxes(yAxes1, yAxes2);

		ScatterDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys1 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setX(xs1[i]);
			dp1[i].setY(ys1[i]);
		}
		dataset1.setDataPoints(dp1);
		dataset1.setXAxisID("x-axis-1");
		dataset1.setYAxisID("y-axis-1");

		ScatterDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys2 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp2 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp2[i] = new DataPoint();
			dp2[i].setX(xs2[i]);
			dp2[i].setY(ys2[i]);
		}
		dataset2.setDataPoints(dp2);
		dataset2.setXAxisID("x-axis-1");
		dataset2.setYAxisID("y-axis-2");
		
		chart.getData().setDatasets(dataset1, dataset2);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			ScatterDataset scDataset = (ScatterDataset)dataset;
			for (DataPoint dp : scDataset.getDataPoints()){
				dp.setX(getRandomDigit());
				dp.setY(getRandomDigit());
			}
		}
		chart.update();
	}
}
