package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.FloatingData;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class FloatingDataTimeSeriesByBarCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 15;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FloatingDataTimeSeriesByBarCase> {
	}

	@UiField
	BarChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();

	public FloatingDataTimeSeriesByBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Floating data on timeseries by bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(HtmlColor.GREEN);

		DateAdapter adapter = new DateAdapter();
		
		List<DataPoint> dataPoints1 = new LinkedList<>();
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			Date date = adapter.add(startingPoint, i, TimeUnit.DAY);
			double value = 100 * Math.random();

			DataPoint dp = new DataPoint();
			dp.setX(date);
			dp.setY(new FloatingData(value, Math.min(value + 50 * Math.random(), 100)));
			dataPoints1.add(dp);
		}
		dataset1.setDataPoints(dataPoints1);
		
		BarDataset dataset2 = chart.newDataset();
		dataset2.setBackgroundColor(HtmlColor.ORANGE);
		dataset2.setLabel("dataset 2");

		List<DataPoint> dataPoints2 = new LinkedList<>();
		
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			Date date = adapter.add(startingPoint, i, TimeUnit.DAY);
			double value = 100 * Math.random();

			DataPoint dp = new DataPoint();
			dp.setX(date);
			dp.setY(new FloatingData(value, Math.min(value + 50 * Math.random(), 100)));
			dataPoints2.add(dp);
		}
		dataset2.setDataPoints(dataPoints2);
		
		CartesianTimeSeriesAxis axis = new CartesianTimeSeriesAxis(chart);
		axis.setBounds(ScaleBounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setOffset(true);
		
		chart.getData().setDatasets(dataset1, dataset2);
		chart.getOptions().getScales().setAxes(axis);
		
		Charba_Showcase.LOG.info(chart.getData().toJSON());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			BarDataset bDataset = (BarDataset) dataset;
			for (DataPoint dp : bDataset.getDataPoints()) {
				double value = 100 * Math.random();
				dp.getYAsFloatingData().setValues(value, Math.min(value + 50 * Math.random(), 100));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
