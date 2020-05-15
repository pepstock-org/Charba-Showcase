package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.FloatingData;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.enums.ScaleBounds;
import org.pepstock.charba.client.enums.ScaleDistribution;
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
		
		List<String> labels = new LinkedList<>();
		List<FloatingData> data1 = new LinkedList<>();
		
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			Date date = adapter.add(startingPoint, i, TimeUnit.DAY);
			labels.add(adapter.format(date, "YYYY-MM-DD"));
			double value = 100 * Math.random();
			data1.add(new FloatingData(value, Math.min(value + 50 * Math.random(), 100)));
		}
		dataset1.setFloatingData(data1);
		
		BarDataset dataset2 = chart.newDataset();
		dataset2.setBackgroundColor(HtmlColor.ORANGE);
		dataset2.setLabel("dataset 2");

		List<FloatingData> data2 = new LinkedList<>();
		
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			double value = 100 * Math.random();
			data2.add(new FloatingData(value, Math.min(value + 50 * Math.random(), 100)));
		}
		dataset2.setFloatingData(data2);
		
		CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.setBounds(ScaleBounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setOffset(true);

		Charba_Showcase.LOG.info(labels.toString());
		
		chart.getData().setLabels(labels.toArray(new String[0]));
		chart.getData().setDatasets(dataset1, dataset2);
		chart.getOptions().getScales().setXAxes(axis);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			BarDataset scDataset = (BarDataset) dataset;
			for (FloatingData dp : scDataset.getFloatingData()) {
				double value = 100 * Math.random();
				dp.setValues(value, Math.min(value + 50 * Math.random(), 100));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
