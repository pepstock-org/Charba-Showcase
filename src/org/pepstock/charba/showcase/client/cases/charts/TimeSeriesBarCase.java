package org.pepstock.charba.showcase.client.cases.charts;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.TimeSeriesBarDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesBarChartWidget;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesBarCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 15;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesBarCase> {
	}

	@UiField
	TimeSeriesBarChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();

	public TimeSeriesBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Timeseries bar chart");

		TimeSeriesBarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		TimeSeriesBarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);

		List<TimeSeriesItem> data1 = new LinkedList<>();
		List<TimeSeriesItem> data2 = new LinkedList<>();

		DateAdapter adapter = ResourcesType.getClientBundle().getModule().createDateAdapter();

		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			data1.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), 100 * Math.random()));
			data2.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), 100 * Math.random()));
		}
		dataset1.setTimeSeriesData(data1);
		dataset2.setTimeSeriesData(data2);

		CartesianTimeAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setOffset(true);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);

		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			TimeSeriesBarDataset scDataset = (TimeSeriesBarDataset) dataset;
			for (TimeSeriesItem dp : scDataset.getTimeSeriesData()) {
				dp.setValue(getRandomDigit(false));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
