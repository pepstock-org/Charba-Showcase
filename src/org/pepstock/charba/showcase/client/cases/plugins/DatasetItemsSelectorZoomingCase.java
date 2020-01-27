package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
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
	LineChart chart;

	@UiField
	LineChart small;

	@UiField
	VerticalPanel img;

	final DatasetsItemsSelector selector = DatasetsItemsSelector.get();

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
				DataPoint dp = ds.getDataPoints().get(item.getIndex());
				return Arrays.asList(FORMAT.format(dp.getT()));
			}

			@Override
			public List<String> onAfterTitle(IsChart chart, List<TooltipItem> items) {
				return null;
			}
		});

		final LineDataset dataset1 = chart.newDataset();

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
			dp1[i].setT(new Date(time));
			time = time + DAY;
		}
		dataset2.setDataPoints(dp1);
		small.getData().setDatasets(dataset2);

		final CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);

		chart.getOptions().getScales().setXAxes(axis);
		chart.getOptions().getScales().setYAxes(axis2);

		small.getOptions().getPlugins().setEnabled(ChartBackgroundColor.ID, false);

		small.getOptions().setResponsive(true);
		small.getOptions().setMaintainAspectRatio(true);
		small.getOptions().setAspectRatio(15);
		small.getOptions().getLegend().setDisplay(false);
		small.getOptions().getTitle().setDisplay(false);
		small.getOptions().getElements().getPoint().setRadius(0);

		CartesianTimeAxis axis1Small = new CartesianTimeAxis(small);
		axis1Small.setDisplay(false);

		small.getOptions().getScales().setXAxes(axis1Small);

		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(5);
		pOptions.setBorderDash(6);
		pOptions.setFireEventOnClearSelection(true);

		small.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		small.getPlugins().add(selector);

		small.addHandler(new DatasetRangeSelectionEventHandler() {

			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset from: <b>").append(event.getFrom()).append("</b><br>");
				sb.append("Dataset to: <b>").append(event.getTo()).append("</b><br>");
				new Toast("Dataset Range Selected!", sb.toString()).show();
				if (event.getFrom() != DatasetRangeSelectionEvent.CLEAR_SELECTION) {
					int tot = event.getTo() - event.getFrom() + 1;
					DataPoint[] dp1 = new DataPoint[tot];
					for (int i = 0; i < tot; i++) {
						dp1[i] = dataset2.getDataPoints().get(i + event.getFrom());

					}
					dataset1.setDataPoints(dp1);
					chart.getData().setDatasets(dataset1);
					chart.update();
				}
			}
		}, DatasetRangeSelectionEvent.TYPE);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : small.getData().getDatasets()) {
			LineDataset scDataset = (LineDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setY(getRandomDigit(false));
			}
		}
		small.update();
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
