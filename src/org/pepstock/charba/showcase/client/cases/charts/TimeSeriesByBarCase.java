package org.pepstock.charba.showcase.client.cases.charts;

import java.util.Date;

import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Bounds;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesByBarCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 15;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesByBarCase> {
	}

	@UiField
	BarChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();

	public TimeSeriesByBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Timeseries by bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(HtmlColor.GREEN);

		DateAdapter adapter = new DateAdapter();

		DataPoint[] points = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] rainPoints = new DataPoint[AMOUNT_OF_POINTS];
		int idx = 0;
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dataPoint = new DataPoint();
			dataPoint.setX(adapter.add(startingPoint, i, TimeUnit.DAY));
			dataPoint.setY(100 * Math.random());
			points[idx] = dataPoint;

			DataPoint rainPoint = new DataPoint();
			rainPoint.setX(adapter.add(startingPoint, i, TimeUnit.DAY));
			rainPoint.setY(100 * Math.random());
			rainPoints[idx] = rainPoint;

			idx++;
		}
		dataset1.setDataPoints(rainPoints);

		BarDataset dataset2 = chart.newDataset();
		dataset2.setBackgroundColor(HtmlColor.ORANGE);
		dataset2.setLabel("dataset 2");

		DataPoint[] rainPoints2 = new DataPoint[AMOUNT_OF_POINTS];
		idx = 0;
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			Date newDate = adapter.add(startingPoint, i, TimeUnit.DAY);

			DataPoint dataPoint = new DataPoint();
			dataPoint.setX(newDate);
			dataPoint.setY(100 * Math.random());
			points[idx] = dataPoint;

			DataPoint rainPoint2 = new DataPoint();
			rainPoint2.setX(newDate);
			rainPoint2.setY(100 * Math.random());
			rainPoints2[idx] = rainPoint2;

			idx++;
		}
		dataset2.setDataPoints(rainPoints2);

		CartesianTimeSeriesAxis axis = new CartesianTimeSeriesAxis(chart);
		axis.setBounds(Bounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setOffset(true);

		chart.getData().setDatasets(dataset1, dataset2);
		chart.getOptions().getScales().setAxes(axis);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			BarDataset scDataset = (BarDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setY(getRandomDigit(false));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
