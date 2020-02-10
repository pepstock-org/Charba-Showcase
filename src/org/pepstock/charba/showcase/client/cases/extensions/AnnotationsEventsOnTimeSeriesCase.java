package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AbstractAnnotation;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.BoxAnnotation;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.callbacks.ClickCallback;
import org.pepstock.charba.client.annotation.callbacks.DoubleClickCallback;
import org.pepstock.charba.client.annotation.callbacks.MouseOutCallback;
import org.pepstock.charba.client.annotation.callbacks.MouseOverCallback;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.Event;
import org.pepstock.charba.client.annotation.enums.LineMode;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.dom.BaseNativeEvent;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.options.Scales;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AnnotationsEventsOnTimeSeriesCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final long DAY = 1000 * 60 * 60 * 24;

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationsEventsOnTimeSeriesCase> {
	}

	@UiField
	TimeSeriesLineChartWidget chart;

	@UiField
	LogView mylog;

	final MyEventsCallback callback = new MyEventsCallback();

	public AnnotationsEventsOnTimeSeriesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Annotations events on timeseries line chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getIndex());
				return Arrays.asList(FORMAT.format(dp.getT()));
			}

		});

		final TimeSeriesLineDataset dataset1 = chart.newDataset();

		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.FALSE);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		final TimeSeriesLineDataset dataset2 = chart.newDataset();

		dataset2.setLabel("dataset 2");
		dataset2.setFill(Fill.FALSE);

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());

		Date myDate = new Date();
		long time = myDate.getTime();

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS, false);
		List<TimeSeriesItem> data = new LinkedList<>();
		List<TimeSeriesItem> data1 = new LinkedList<>();

		time = time + DAY * AMOUNT_OF_POINTS;
		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			data.add(new TimeSeriesItem(new Date(time), xs1[i]));
			data1.add(new TimeSeriesItem(new Date(time), xs2[i]));
			time = time - DAY;
		}
		dataset1.setTimeSeriesData(data);
		dataset2.setTimeSeriesData(data1);

		CartesianTimeAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.setDistribution(ScaleDistribution.SERIES);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1, dataset2);

		AnnotationOptions options = new AnnotationOptions();
		options.setEvents(Event.CLICK, Event.DOUBLE_CLICK, Event.MOUSE_OUT, Event.MOUSE_OVER);

		LineAnnotation line = new LineAnnotation();
		line.setName("LineAnnotation");
		line.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		line.setMode(LineMode.HORIZONTAL);
		line.setScaleID(Scales.DEFAULT_Y_AXIS_ID);
		line.setBorderColor(HtmlColor.BLACK);
		line.setBorderWidth(5);
		line.setValue(40);
		line.getLabel().setEnabled(true);
		line.getLabel().setContent("My threshold");
		line.getLabel().setBackgroundColor(HtmlColor.RED);
		line.setClickCallback(callback);
		line.setMouseOverCallback(callback);
		line.setMouseOutCallback(callback);
		line.setDoubleClickCallback(callback);

		BoxAnnotation box = new BoxAnnotation();
		box.setName("BoxAnnotation");
		box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box.setXScaleID(Scales.DEFAULT_X_AXIS_ID);
		box.setYScaleID(Scales.DEFAULT_Y_AXIS_ID);
		time = (long) myDate.getTime() + DAY * (int) (AMOUNT_OF_POINTS / 4);
		box.setXMin(new Date(time));
		time = (long) myDate.getTime() + DAY * (int) (AMOUNT_OF_POINTS / 4 * 3);
		box.setXMax(new Date(time));
		box.setYMax(100);
		box.setYMin(60);
		box.setBackgroundColor("rgba(101, 33, 171, 0.5)");
		box.setBorderColor("rgb(101, 33, 171)");
		box.setBorderWidth(1);
		box.setClickCallback(callback);
		box.setMouseOverCallback(callback);
		box.setMouseOutCallback(callback);
		box.setDoubleClickCallback(callback);

		options.setAnnotations(line, box);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			TimeSeriesLineDataset scDataset = (TimeSeriesLineDataset) dataset;
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

	class MyEventsCallback implements ClickCallback, MouseOverCallback, MouseOutCallback, DoubleClickCallback {

		@Override
		public void onMouseOut(IsChart chart, BaseNativeEvent event, AbstractAnnotation annotation) {
			mylog.addLogEvent("> MOUSEOUT on annotation " + annotation.getName() + " type " + annotation.getType());
		}

		@Override
		public void onMouseOver(IsChart chart, BaseNativeEvent event, AbstractAnnotation annotation) {
			mylog.addLogEvent("> MOUSEOVER on annotation " + annotation.getName() + " type " + annotation.getType());
		}

		@Override
		public void onClick(IsChart chart, BaseNativeEvent event, AbstractAnnotation annotation) {
			mylog.addLogEvent("> CLICK on annotation " + annotation.getName() + " type " + annotation.getType());
		}

		@Override
		public void onDoubleClick(IsChart chart, BaseNativeEvent event, AbstractAnnotation annotation) {
			mylog.addLogEvent("> DOUBLE CLICK on annotation " + annotation.getName() + " type " + annotation.getType());
		}

	}
}
