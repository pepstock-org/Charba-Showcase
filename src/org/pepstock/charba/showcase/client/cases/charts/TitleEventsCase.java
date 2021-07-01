package org.pepstock.charba.showcase.client.cases.charts;

import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.events.TitleClickEvent;
import org.pepstock.charba.client.events.TitleClickEventHandler;
import org.pepstock.charba.client.events.TitleEnterEvent;
import org.pepstock.charba.client.events.TitleEnterEventHandler;
import org.pepstock.charba.client.events.TitleLeaveEvent;
import org.pepstock.charba.client.events.TitleLeaveEventHandler;
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

public class TitleEventsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TitleEventsCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	LogView mylog;

	public TitleEventsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Title event on line chart");
		chart.getOptions().getTitle().setFullSize(false);
		chart.getOptions().getTooltips().setEnabled(false);

		chart.addHandler(new TitleClickEventHandler() {

			@Override
			public void onClick(TitleClickEvent event) {
				IsChart chart = (IsChart) event.getSource();
				List<String> values = chart.getOptions().getTitle().getText();
				StringBuilder title = new StringBuilder();
				if (!values.isEmpty()) {
					for (String value : values) {
						title.append(value).append(" ");
					}
				}
				mylog.addLogEvent("> CLICK: event ScreenX: " + event.getNativeEvent().getScreenX() + ", ScreenY:" + event.getNativeEvent().getScreenY());
				StringBuilder sb = new StringBuilder();
				sb.append("Title: <b>").append(title.toString()).append("</b><br>");
				new Toast("Title Selected!", sb.toString()).show();
			}

		}, TitleClickEvent.TYPE);

		chart.addHandler(new TitleEnterEventHandler() {

			@Override
			public void onEnter(TitleEnterEvent event) {
				mylog.addLogEvent("> ENTER: event ScreenX: " + event.getNativeEvent().getScreenX() + ", ScreenY:" + event.getNativeEvent().getScreenY());
				event.getItem().setColor(HtmlColor.RED);
				event.getChart().update();

			}

		}, TitleEnterEvent.TYPE);

		chart.addHandler(new TitleLeaveEventHandler() {

			@Override
			public void onLeave(TitleLeaveEvent event) {
				mylog.addLogEvent("> LEAVE: event ScreenX: " + event.getNativeEvent().getScreenX() + ", ScreenY:" + event.getNativeEvent().getScreenY());
				event.getItem().setColor(Defaults.get().getGlobal().getTitle().getColor());
				event.getChart().update();
			}

		}, TitleLeaveEvent.TYPE);

		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);
		dataset1.setData(getRandomDigits(months));

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(false);

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
		chart.getData().setDatasets(dataset1, dataset2);

		chart.getPlugins().add(ChartPointer.get());

	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
