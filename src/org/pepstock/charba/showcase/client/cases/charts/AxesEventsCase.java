package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.events.AxisClickEvent;
import org.pepstock.charba.client.events.AxisClickEventHandler;
import org.pepstock.charba.client.events.AxisEnterEvent;
import org.pepstock.charba.client.events.AxisEnterEventHandler;
import org.pepstock.charba.client.events.AxisLeaveEvent;
import org.pepstock.charba.client.events.AxisLeaveEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AxesEventsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AxesEventsCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	LogView mylog;

	public AxesEventsCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Axes events  on line chart");
		chart.getOptions().getTooltips().setEnabled(false);

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
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(false);
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
		chart.addHandler(new AxisClickEventHandler() {

			@Override
			public void onClick(AxisClickEvent event) {
				mylog.addLogEvent("> CLICK: event ScreenX: " + event.getNativeMouseEvent().getScreenX() + ", ScreenY:" + event.getNativeMouseEvent().getScreenY());
				StringBuilder sb = new StringBuilder();
				sb.append("Axis value: <b>").append(event.getValue().getLabel()).append("</b><br>");
				new Toast("Axis Selected!", sb.toString()).show();
			}
		}, AxisClickEvent.TYPE);

		chart.addHandler(new AxisLeaveEventHandler() {

			@Override
			public void onLeave(AxisLeaveEvent event) {
				mylog.addLogEvent("> LEAVE: event ScreenX: " + event.getNativeMouseEvent().getScreenX() + ", ScreenY:" + event.getNativeMouseEvent().getScreenY());
				event.getChart().getNode().getOptions().getScales().getAxis(event.getItem().getId()).setBackgroundColor(HtmlColor.TRANSPARENT);
				event.getChart().update();
			}
		}, AxisLeaveEvent.TYPE);

		chart.addHandler(new AxisEnterEventHandler() {

			@Override
			public void onEnter(AxisEnterEvent event) {
				mylog.addLogEvent("> ENTER: event ScreenX: " + event.getNativeMouseEvent().getScreenX() + ", ScreenY:" + event.getNativeMouseEvent().getScreenY());
				event.getChart().getNode().getOptions().getScales().getAxis(event.getItem().getId()).setBackgroundColor(HtmlColor.LIGHT_GREEN.alpha(0.2));
				event.getChart().update();
			}
		}, AxisEnterEvent.TYPE);

		chart.getPlugins().add(ChartPointer.get());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
