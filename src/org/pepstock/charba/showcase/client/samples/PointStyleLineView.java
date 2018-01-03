package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.PointStyle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class PointStyleLineView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PointStyleLineView> {
	}

	@UiField
	LineChart chartCircle;
	
	@UiField
	LineChart chartCross;

	@UiField
	LineChart chartCrossRot;
	
	@UiField
	LineChart chartDash;

	@UiField
	LineChart chartLine;

	@UiField
	LineChart chartRect;

	@UiField
	LineChart chartRectRounded;

	@UiField
	LineChart chartRectRot;

	@UiField
	LineChart chartStar;

	@UiField
	LineChart chartTriangle;
	
	public PointStyleLineView() {
		initWidget(uiBinder.createAndBindUi(this));

		config(chartCircle, PointStyle.circle, 0);
		config(chartCross, PointStyle.cross, 1);
		config(chartCrossRot, PointStyle.crossRot, 2);
		config(chartDash, PointStyle.dash, 3);
		config(chartLine, PointStyle.line, 4);
		config(chartRect, PointStyle.rect, 5);
		config(chartRectRounded, PointStyle.rectRounded, 6);
		config(chartRectRot, PointStyle.rectRot, 7);
		config(chartStar, PointStyle.star, 8);
		config(chartTriangle, PointStyle.triangle, 9);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		newData(chartCircle);
		newData(chartCross);
		newData(chartCrossRot);
		newData(chartDash);
		newData(chartLine);
		newData(chartRect);
		newData(chartRectRot);
		newData(chartRectRounded);
		newData(chartStar);
		newData(chartTriangle);
	}
	
	private void config(LineChart lineChart, PointStyle style, int index){
		lineChart.getOptions().setResponsive(true);
		lineChart.getOptions().getLegend().setDisplay(false);
		lineChart.getOptions().getTitle().setDisplay(true);
		lineChart.getOptions().getTitle().setText("Charba Point Style: "+style.name());
		lineChart.getOptions().getElements().getPoint().setPointStyle(style);
		
		LineDataset dataset = lineChart.newDataset();
		dataset.setLabel("My dataset");
		Color color1 = Colors.ALL[index];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.nofill);
		dataset.setPointRadius(10D);
		dataset.setPointHoverRadius(20D);
		dataset.setShowLine(false);
		lineChart.getData().setLabels(getLabels());
		lineChart.getData().setDatasets(dataset);
	}

	private void newData(LineChart chart) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
}
