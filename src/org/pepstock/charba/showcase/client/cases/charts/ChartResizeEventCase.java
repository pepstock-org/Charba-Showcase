package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.dom.enums.Unit;
import org.pepstock.charba.client.events.ChartResizeEvent;
import org.pepstock.charba.client.events.ChartResizeEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ChartResizeEventCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ChartResizeEventCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	LogView mylog;

	private double width = Double.NaN;

	private double halfWidth = 0D;

	private boolean resized = false;

	public ChartResizeEventCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Resize events on line chart");
		chart.getOptions().getTooltips().setEnabled(false);

		chart.addHandler(new ChartResizeEventHandler() {

			@Override
			public void onResize(ChartResizeEvent event) {
				mylog.addLogEvent("> RESIZE: width: " + event.getSize().getWidth() + "px , height: " + event.getSize().getHeight() + "px");
			}

		}, ChartResizeEvent.TYPE);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);

		double[] values = getRandomDigits(months);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setFill(false);

		dataset2.setData(getRandomDigits(months));

		datasets.add(dataset2);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("resize")
	protected void handleResize(ClickEvent event) {
		if (Double.isNaN(width)) {
			width = chart.getChartElement().getOffsetWidth();
			halfWidth = width / 2D;
		}
		if (resized) {
			chart.getChartElement().getStyle().setWidth(Unit.PX.format(width));
			resized = false;
		} else {
			chart.getChartElement().getStyle().setWidth(Unit.PX.format(halfWidth));
			resized = true;
		}
		chart.resize();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
