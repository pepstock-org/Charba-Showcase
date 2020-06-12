package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.LineLabelPosition;
import org.pepstock.charba.client.annotation.enums.LineMode;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.LegendClickEvent;
import org.pepstock.charba.client.events.LegendClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TrendAndForecastCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final long DAY = 1000 * 60 * 60 * 24;

	private static final int AMOUNT_OF_POINTS = 60;

	private static final int PREVIOUS_PERIOD = 30;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TrendAndForecastCase> {
	}

	@UiField
	LineChartWidget chart;

	final Date nowDate = new Date();
	@SuppressWarnings("deprecation")
	final JsDate now = JsDate.create(nowDate.getYear() + 1900, nowDate.getMonth(), nowDate.getDate());

	final LineDataset dataset;

	final LineDataset trend;

	final LineDataset forecast;

	final CartesianTimeAxis axis;

	public TrendAndForecastCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);

		chart.addHandler(new LegendClickEventHandler() {

			@Override
			public void onClick(LegendClickEvent event) {
				Defaults.get().invokeLegendOnClick(event);
				if (event.getItem().getDatasetIndex() == 2) {
					DatasetMetaItem metadata = chart.getDatasetMeta(event.getItem().getDatasetIndex());
					if (metadata.isHidden()) {
						axis.setMax(new Date((long) now.getTime()));
						chart.reconfigure();
					} else {
						axis.setMax((Date) null);
						chart.reconfigure();
					}
				}
			}

		}, LegendClickEvent.TYPE);

		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Trend and forecast on timeseries chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getIndex());
				return Arrays.asList(FORMAT.format(dp.getXAsDate()));
			}

		});

		dataset = chart.newDataset();
		dataset.setLabel("Data");
		dataset.setFill(Fill.FALSE);
		IsColor color1 = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		trend = chart.newDataset();
		trend.setLabel("Trend");
		trend.setFill(Fill.FALSE);
		IsColor color2 = GoogleChartColor.values()[1];
		trend.setBackgroundColor(color2.toHex());
		trend.setBorderColor(color2.toHex());
		trend.setBorderDash(4, 4);
		trend.setPointRadius(trend.getBorderWidth() / 2);

		forecast = chart.newDataset();
		forecast.setLabel("Forecast");
		forecast.setFill(Fill.FALSE);
		IsColor color3 = GoogleChartColor.values()[2];
		forecast.setBackgroundColor(color3.toHex());
		forecast.setBorderColor(color3.toHex());
		forecast.setBorderDash(4, 4);
		forecast.setPointRadius(forecast.getBorderWidth() / 2);

		double time = now.getTime() - PREVIOUS_PERIOD * DAY;

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dataDp = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] trendDp = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] forecastDp = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dataDp[i] = new DataPoint();
			trendDp[i] = new DataPoint();
			forecastDp[i] = new DataPoint();
			Date newDate = new Date((long) time);
			if (time < now.getTime()) {
				dataDp[i].setY(xs1[i]);
				trendDp[i].setY(xs1[i] * 0.80D);
				forecastDp[i].setY(Double.NaN);
			} else if (time == now.getTime()) {
				dataDp[i].setY(xs1[i]);
				trendDp[i].setY(xs1[i]);
				forecastDp[i].setY(xs1[i]);
			} else {
				dataDp[i].setY(Double.NaN);
				trendDp[i].setY(Double.NaN);
				forecastDp[i].setY(xs1[i]);
			}
			dataDp[i].setX(newDate);
			trendDp[i].setX(newDate);
			forecastDp[i].setX(newDate);
			time = time + DAY;

		}
		dataset.setDataPoints(dataDp);
		trend.setDataPoints(trendDp);
		forecast.setDataPoints(forecastDp);

		axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);
		chart.getData().setDatasets(dataset, trend, forecast);

		AnnotationOptions options = new AnnotationOptions();

		LineAnnotation line = new LineAnnotation();
		line.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		line.setMode(LineMode.VERTICAL);
		line.setScaleID(DefaultScaleId.X.value());
		line.setBorderColor(HtmlColor.DARK_GRAY);
		line.setBorderWidth(2);
		line.setValue(new Date((long) now.getTime()));
		line.getLabel().setEnabled(true);
		line.getLabel().setContent("Now");
		line.getLabel().setPosition(LineLabelPosition.TOP);

		options.setAnnotations(line);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		double time = now.getTime() - PREVIOUS_PERIOD * DAY;

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dataDp = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] trendDp = new DataPoint[AMOUNT_OF_POINTS];
		DataPoint[] forecastDp = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dataDp[i] = new DataPoint();
			trendDp[i] = new DataPoint();
			forecastDp[i] = new DataPoint();
			Date newDate = new Date((long) time);
			if (time < now.getTime()) {
				dataDp[i].setY(xs1[i]);
				trendDp[i].setY(xs1[i] * 0.80D);
				forecastDp[i].setY(Double.NaN);
			} else if (time == now.getTime()) {
				dataDp[i].setY(xs1[i]);
				trendDp[i].setY(xs1[i]);
				forecastDp[i].setY(xs1[i]);
			} else {
				dataDp[i].setY(Double.NaN);
				trendDp[i].setY(Double.NaN);
				forecastDp[i].setY(xs1[i]);
			}
			dataDp[i].setX(newDate);
			trendDp[i].setX(newDate);
			forecastDp[i].setX(newDate);
			time = time + DAY;

		}
		dataset.setDataPoints(dataDp);
		trend.setDataPoints(trendDp);
		forecast.setDataPoints(forecastDp);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
