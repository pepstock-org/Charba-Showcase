package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetRangeClearSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeClearSelectionEventHandler;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DatasetItemsSelectorZoomingCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final long DAY = 1000 * 60 * 60 * 24;

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DatasetItemsSelectorZoomingCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	LineChartWidget small;

	@UiField
	VerticalPanel img;

	final DatasetsItemsSelector selector = DatasetsItemsSelector.get();

	final LineDataset dataset1;
	
	public DatasetItemsSelectorZoomingCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Zooming dataset data on timeseries line chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onBeforeTitle(IsChart chart, List<TooltipItem> items) {
				return null;
			}

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(FORMAT.format(dp.getXAsDate()));
			}

			@Override
			public List<String> onAfterTitle(IsChart chart, List<TooltipItem> items) {
				return null;
			}
		});

		dataset1 = chart.newDataset();

		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.ORIGIN);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		final LineDataset dataset2 = small.newDataset();

		long time = new Date().getTime();

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setX(new Date(time));
			time = time + DAY;
		}
		dataset2.setDataPoints(dp1);
		small.getData().setDatasets(dataset2);

		final CartesianTimeSeriesAxis axis = new CartesianTimeSeriesAxis(chart);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);

		small.getOptions().getPlugins().setEnabled(ChartBackgroundColor.ID, false);

		small.getOptions().setResponsive(true);
		small.getOptions().setMaintainAspectRatio(true);
		small.getOptions().setAspectRatio(15);
		small.getOptions().getLegend().setDisplay(false);
		small.getOptions().getTitle().setDisplay(false);
		small.getOptions().getElements().getPoint().setRadius(0);

		CartesianTimeAxis axis1Small = new CartesianTimeAxis(small);
		axis1Small.setDisplay(false);

		small.getOptions().getScales().setAxes(axis1Small);

		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(5);
		pOptions.setBorderDash(6);

		small.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		small.getPlugins().add(selector);

		small.addHandler(new DatasetRangeClearSelectionEventHandler() {

			@Override
			public void onClear(DatasetRangeClearSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("<b>Clear selection event</b>");
				new Toast("Dataset Range Clear Selection!", sb.toString()).show();
				dataset1.setDataPoints(new LinkedList<>());
				chart.getData().setDatasets(dataset1);
				chart.update();
				// FIXME to be checked sounds do not work correctly
			}
		}, DatasetRangeClearSelectionEvent.TYPE);

		small.addHandler(new DatasetRangeSelectionEventHandler() {

			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset from: <b>").append(event.getFrom().getLabel()).append("</b><br>");
				sb.append("Dataset to: <b>").append(event.getTo().getLabel()).append("</b><br>");
				new Toast("Dataset Range Selected!", sb.toString()).show();
				List<DataPoint> newDataPoints = new LinkedList<>();
				for (DataPoint dp : dataset2.getDataPoints()) {
					newDataPoints.add(dp);
				}
				dataset1.setDataPoints(newDataPoints);
				chart.getData().setDatasets(dataset1);
				axis.setMin(event.getFrom().getValueAsDate());
				axis.setMax(event.getTo().getValueAsDate());
				chart.reconfigure();
			}
		}, DatasetRangeSelectionEvent.TYPE);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		List<DataPoint> newDataPoints = new LinkedList<>();
		for (Dataset dataset : small.getData().getDatasets()) {
			LineDataset scDataset = (LineDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setY(getRandomDigit(false));
				newDataPoints.add(dp);
			}
		}
		small.update();
		if (!dataset1.getDataPoints().isEmpty()) {
			dataset1.setDataPoints(newDataPoints);
			chart.getData().setDatasets(dataset1);
			chart.update();
		}
	}

	@UiHandler("reset")
	protected void handleReset(ClickEvent event) {
		selector.clearSelection(small);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
