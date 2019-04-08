package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.SteppedLine;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SteppedLineView extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SteppedLineView> {
	}

	@UiField
	LineChart chartNoStepped;

	@UiField
	LineChart chartStepped;

	@UiField
	LineChart chartBeforeStepped;

	@UiField
	LineChart chartAfterStepped;

	public SteppedLineView() {
		initWidget(uiBinder.createAndBindUi(this));

		chartNoStepped.getOptions().setResponsive(true);
		chartNoStepped.getOptions().getLegend().setPosition(Position.TOP);
		chartNoStepped.getOptions().getTitle().setDisplay(true);
		chartNoStepped.getOptions().getTitle().setText("Charba NO Stepped Line Chart");

		LineDataset dataset1 = chartNoStepped.newDataset();
		dataset1.setLabel("No stepped");
		IsColor color1 = Colors.ALL[0];
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.FALSE);
		dataset1.setSteppedLine(SteppedLine.FALSE);
		chartNoStepped.getData().setLabels(getLabels());
		chartNoStepped.getData().setDatasets(dataset1);

		chartStepped.getOptions().setResponsive(true);
		chartStepped.getOptions().getLegend().setPosition(Position.TOP);
		chartStepped.getOptions().getTitle().setDisplay(true);
		chartStepped.getOptions().getTitle().setText("Charba Stepped Line Chart");

		LineDataset dataset2 = chartStepped.newDataset();
		dataset2.setLabel("Stepped");
		IsColor color2 = Colors.ALL[1];
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.FALSE);
		dataset2.setSteppedLine(SteppedLine.BEFORE);
		chartStepped.getData().setLabels(getLabels());
		chartStepped.getData().setDatasets(dataset2);

		chartBeforeStepped.getOptions().setResponsive(true);
		chartBeforeStepped.getOptions().getLegend().setPosition(Position.TOP);
		chartBeforeStepped.getOptions().getTitle().setDisplay(true);
		chartBeforeStepped.getOptions().getTitle().setText("Charba BEFORE Stepped Line Chart");

		LineDataset dataset3 = chartBeforeStepped.newDataset();
		dataset3.setLabel("Before Stepped");
		IsColor color3 = Colors.ALL[2];
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months));
		dataset3.setFill(Fill.FALSE);
		dataset3.setSteppedLine(SteppedLine.BEFORE);
		chartBeforeStepped.getData().setLabels(getLabels());
		chartBeforeStepped.getData().setDatasets(dataset3);

		chartAfterStepped.getOptions().setResponsive(true);
		chartAfterStepped.getOptions().getLegend().setPosition(Position.TOP);
		chartAfterStepped.getOptions().getTitle().setDisplay(true);
		chartAfterStepped.getOptions().getTitle().setText("Charba AFTER Stepped Line Chart");

		LineDataset dataset4 = chartAfterStepped.newDataset();
		dataset4.setLabel("After Stepped");
		IsColor color4 = Colors.ALL[3];
		dataset4.setBorderColor(color4.toHex());
		dataset4.setData(getRandomDigits(months));
		dataset4.setFill(Fill.FALSE);
		dataset4.setSteppedLine(SteppedLine.AFTER);
		chartAfterStepped.getData().setLabels(getLabels());
		chartAfterStepped.getData().setDatasets(dataset4);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		newData(chartNoStepped);
		newData(chartStepped);
		newData(chartBeforeStepped);
		newData(chartAfterStepped);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private void newData(LineChart chart) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
}
