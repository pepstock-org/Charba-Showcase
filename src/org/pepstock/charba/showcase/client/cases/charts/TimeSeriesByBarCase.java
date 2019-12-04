package org.pepstock.charba.showcase.client.cases.charts;

import java.util.Date;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.showcase.client.cases.jsinterop.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesByBarCase extends BaseComposite {

	private static final long DAY = 1000 * 60 * 60 * 24;

	private static final int AMOUNT_OF_POINTS = 15;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesByBarCase> {
	}

	@UiField
	BarChart chart;

	private long starting = System.currentTimeMillis();

	final DatasetsItemsSelector selector = new DatasetsItemsSelector();

	public TimeSeriesByBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Timeseries by bar chart");
		long time = starting;

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(HtmlColor.GREEN);

		DataPoint[] points = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] rainPoints = new DataPoint[AMOUNT_OF_POINTS];
		int idx = 0;
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dataPoint = new DataPoint();
			dataPoint.setT(new Date(time));
			dataPoint.setX(100 * Math.random());
			points[idx] = dataPoint;

			DataPoint rainPoint = new DataPoint();
			rainPoint.setT(new Date(time));
			rainPoint.setY(100 * Math.random());
			rainPoints[idx] = rainPoint;

			idx++;
			time = time + DAY;
		}
		dataset1.setDataPoints(rainPoints);

		BarDataset dataset2 = chart.newDataset();
		dataset2.setBackgroundColor(HtmlColor.ORANGE);
		dataset2.setLabel("dataset 2");

		DataPoint[] rainPoints2 = new DataPoint[AMOUNT_OF_POINTS];
		time = starting;
		idx = 0;
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dataPoint = new DataPoint();
			dataPoint.setT(new Date(time));
			dataPoint.setX(100 * Math.random());
			points[idx] = dataPoint;

			DataPoint rainPoint2 = new DataPoint();
			rainPoint2.setT(new Date(time));
			rainPoint2.setY(100 * Math.random());
			rainPoints2[idx] = rainPoint2;

			idx++;
			time = time + DAY;
		}
		dataset2.setDataPoints(rainPoints2);

		CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.setBounds(ScaleBounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setOffset(true);

		chart.getData().setDatasets(dataset1, dataset2);
		chart.getOptions().getScales().setXAxes(axis);

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
