package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AnnotationContext;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.LabelPosition;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.callbacks.SimpleDisplayCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.LegendClickEvent;
import org.pepstock.charba.client.events.LegendClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.ml.RegressionDataset;
import org.pepstock.charba.client.ml.RegressionDatasetBuilder;
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
	
	private static final String MY_SCALE_ID = "my";

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TrendAndForecastCase> {
	}

	@UiField
	LineChartWidget chart;

	final Date nowDate = new Date();
	@SuppressWarnings("deprecation")
	final JsDate now = JsDate.create(nowDate.getYear() + 1900, nowDate.getMonth(), nowDate.getDate());

	final LineDataset dataset;

	final CartesianTimeSeriesAxis axis;

	public TrendAndForecastCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);

		chart.addHandler(new LegendClickEventHandler() {

			@Override
			public void onClick(LegendClickEvent event) {
				if (event.getItem().getDatasetIndex() == 2) {
					if (chart.isDatasetVisible(2)) {
						chart.getNode().getOptions().getScales().getAxis(MY_SCALE_ID).setMax(new Date((long) now.getTime()));
						axis.setMax(new Date((long) now.getTime()));
					} else {
						chart.getNode().getOptions().getScales().getAxis(MY_SCALE_ID).setMax((Date) null);
						axis.setMax((Date) null);
					}
				}
				Defaults.get().invokeLegendOnClick(event);
			}

		}, LegendClickEvent.TYPE);

		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Trend and forecast on timeseries chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(FORMAT.format(dp.getXAsDate()));
			}

		});

		dataset = chart.newDataset();
		dataset.setLabel("Data");
		dataset.setFill(Fill.FALSE);
		IsColor color1 = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		
		double time = now.getTime() - PREVIOUS_PERIOD * DAY;

		List<DataPoint> dataDp = new LinkedList<>();
		List<Date> forecastDates = new LinkedList<>();
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dataDpi = new DataPoint();
			Date newDate = new Date((long) time);
			if (time < now.getTime()) {
				dataDpi.setX(newDate);
				dataDpi.setY(getUpdatedData(i));
				dataDp.add(dataDpi);
			} else if (time == now.getTime()) {
				dataDpi.setX(newDate);
				dataDpi.setY(getUpdatedData(i));
				dataDp.add(dataDpi);
				forecastDates.add(newDate);
			} else {
				forecastDates.add(newDate);
			}
			time = time + DAY;

		}
		dataset.setDataPoints(dataDp);
		
		RegressionDataset trend = RegressionDatasetBuilder.create().setSamplesByDataPoints(dataDp).buildLinearRegression();
		trend.setLabel("Linear trend");
		IsColor color2 = GoogleChartColor.values()[1];
		trend.setBackgroundColor(color2.toHex());
		trend.setBorderColor(color2.toHex());
		trend.setBorderDash(4, 4);
		trend.setBorderWidth(2);
		trend.setPointRadius(0);

		RegressionDataset forecast = RegressionDatasetBuilder.build(trend);
		forecast.setLabel("Linear forecast");
		forecast.addXDates(forecastDates);
		IsColor color3 = GoogleChartColor.values()[2];
		forecast.setBackgroundColor(color3.toHex());
		forecast.setBorderColor(color3.toHex());
		forecast.setBorderWidth(0);
		forecast.setPointRadius(3);

		axis = new CartesianTimeSeriesAxis(chart, MY_SCALE_ID, AxisKind.X);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);
		chart.getData().setDatasets(dataset, trend, forecast);

		AnnotationOptions options = new AnnotationOptions();

		LineAnnotation line = new LineAnnotation();
		line.setDisplay(new SimpleDisplayCallback<AnnotationContext>() {
			
			@Override
			public Boolean invoke(AnnotationContext context) {
				return context.getChart().isDatasetVisible(2);
			}
		});
		line.setDrawTime(DrawTime.AFTER_DRAW);
		line.setScaleID(MY_SCALE_ID);
		line.setBorderColor(HtmlColor.DARK_GRAY);
		line.setBorderWidth(2);
		line.setValue(new Date((long) now.getTime()));
		line.getLabel().setDisplay(true);
		line.getLabel().setContent("Now");
		line.getLabel().setPosition(LabelPosition.START);

		options.setAnnotations(line);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		double time = now.getTime() - PREVIOUS_PERIOD * DAY;

		List<DataPoint> dataDp = new LinkedList<>();
		List<Date> forecastDates = new LinkedList<>();
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dataDpi = new DataPoint();
			Date newDate = new Date((long) time);
			if (time < now.getTime()) {
				dataDpi.setX(newDate);
				dataDpi.setY(getUpdatedData(i));
				dataDp.add(dataDpi);
			} else if (time == now.getTime()) {
				dataDpi.setX(newDate);
				dataDpi.setY(getUpdatedData(i));
				dataDp.add(dataDpi);
				forecastDates.add(newDate);
			} else {
				forecastDates.add(newDate);
			}
			time = time + DAY;

		}
		dataset.setDataPoints(dataDp);
		
		RegressionDataset trend = RegressionDatasetBuilder.create().setSamplesByDataPoints(dataDp).buildLinearRegression();
		trend.setLabel("Linear trend");
		IsColor color2 = GoogleChartColor.values()[1];
		trend.setBackgroundColor(color2.toHex());
		trend.setBorderColor(color2.toHex());
		trend.setBorderDash(4, 4);
		trend.setBorderWidth(2);
		trend.setPointRadius(0);

		RegressionDataset forecast = RegressionDatasetBuilder.build(trend);
		forecast.setLabel("Linear forecast");
		forecast.addXDates(forecastDates);
		IsColor color3 = GoogleChartColor.values()[2];
		forecast.setBackgroundColor(color3.toHex());
		forecast.setBorderColor(color3.toHex());
		forecast.setBorderWidth(0);
		forecast.setPointRadius(3);

		chart.getData().setDatasets(dataset, trend, forecast);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private double getUpdatedData(int index) {
		double gap = 2.5 + 2.5 * Math.random();
		return getRandomDigit(index - gap, index +gap);		
	}
}
