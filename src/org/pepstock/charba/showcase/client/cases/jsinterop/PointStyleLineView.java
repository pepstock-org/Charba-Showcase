package org.pepstock.charba.showcase.client.cases.jsinterop;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.PointStyle;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

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

		config(chartCircle, PointStyle.CIRCLE, 0);
		config(chartCross, PointStyle.CROSS, 1);
		config(chartCrossRot, PointStyle.CROSS_ROT, 2);
		config(chartDash, PointStyle.DASH, 3);
		config(chartLine, PointStyle.LINE, 4);
		config(chartRect, PointStyle.RECT, 5);
		config(chartRectRounded, PointStyle.RECT_ROUNDED, 6);
		config(chartRectRot, PointStyle.RECT_ROT, 7);
		config(chartStar, PointStyle.STAR, 8);
		config(chartTriangle, PointStyle.TRIANGLE, 9);

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
		lineChart.getOptions().getLegend().getLabels().setUsePointStyle(true);
		lineChart.getOptions().getLegend().getLabels().setFontSize(20);
		lineChart.getOptions().getTitle().setDisplay(true);
		lineChart.getOptions().getTitle().setText("Charba Point Style: "+style.name());
		lineChart.getOptions().getElements().getPoint().setPointStyle(style);
		
		LineDataset dataset = lineChart.newDataset();
		dataset.setLabel("My dataset");
		IsColor color1 = Colors.ALL[index];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.FALSE);
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
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
