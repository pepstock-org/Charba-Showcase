package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LegendPositioningView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LegendPositioningView> {
	}

	@UiField
	LineChart chartTop;
	
	@UiField
	LineChart chartRight;

	@UiField
	LineChart chartBottom;
	
	@UiField
	LineChart chartLeft;

	public LegendPositioningView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chartTop.getOptions().setResponsive(true);
		chartTop.getOptions().getLegend().setPosition(Position.TOP);
		chartTop.getOptions().getTitle().setDisplay(true);
		chartTop.getOptions().getTitle().setText("Charba Legend top");
		
		LineDataset dataset1 = chartTop.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = Colors.ALL[0];
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.ORIGIN);
		chartTop.getData().setLabels(getLabels());
		chartTop.getData().setDatasets(dataset1);
		
		chartRight.getOptions().setResponsive(true);
		chartRight.getOptions().getLegend().setPosition(Position.RIGHT);
		chartRight.getOptions().getTitle().setDisplay(true);
		chartRight.getOptions().getTitle().setText("Charba Legend right");
		
		LineDataset dataset2 = chartRight.newDataset();
		dataset2.setLabel("dataset 1");
		IsColor color2 = Colors.ALL[1];
		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.ORIGIN);
		chartRight.getData().setLabels(getLabels());
		chartRight.getData().setDatasets(dataset2);
		

		chartBottom.getOptions().setResponsive(true);
		chartBottom.getOptions().getLegend().setPosition(Position.BOTTOM);
		chartBottom.getOptions().getTitle().setDisplay(true);
		chartBottom.getOptions().getTitle().setText("Charba Legend bottom");
		
		LineDataset dataset3 = chartBottom.newDataset();
		dataset3.setLabel("dataset 1");
		IsColor color3 = Colors.ALL[2];
		dataset3.setBackgroundColor(color3.alpha(0.2));
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months));
		dataset3.setFill(Fill.ORIGIN);
		chartBottom.getData().setLabels(getLabels());
		chartBottom.getData().setDatasets(dataset3);

		chartLeft.getOptions().setResponsive(true);
		chartLeft.getOptions().getLegend().setPosition(Position.LEFT);
		chartLeft.getOptions().getTitle().setDisplay(true);
		chartLeft.getOptions().getTitle().setText("Charba Legend left");
		
		LineDataset dataset4 = chartLeft.newDataset();
		dataset4.setLabel("dataset 1");
		IsColor color4 = Colors.ALL[3];
		dataset4.setBackgroundColor(color4.alpha(0.2));
		dataset4.setBorderColor(color4.toHex());
		dataset4.setData(getRandomDigits(months));
		dataset4.setFill(Fill.ORIGIN);
		chartLeft.getData().setLabels(getLabels());
		chartLeft.getData().setDatasets(dataset4);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		newData(chartTop);
		newData(chartRight);
		newData(chartBottom);
		newData(chartLeft);
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
