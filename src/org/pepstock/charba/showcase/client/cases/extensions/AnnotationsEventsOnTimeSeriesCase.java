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
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.listeners.ClickCallback;
import org.pepstock.charba.client.annotation.listeners.DoubleClickCallback;
import org.pepstock.charba.client.annotation.listeners.EnterCallback;
import org.pepstock.charba.client.annotation.listeners.LeaveCallback;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.ChartEventContext;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.utils.toast.enums.DefaultToastType;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

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

	final MyEventsHandler eventHandler = new MyEventsHandler();

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
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(FORMAT.format(dp.getXAsDate()));
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

		CartesianTimeSeriesAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1, dataset2);

		AnnotationOptions options = new AnnotationOptions();

		LineAnnotation line = new LineAnnotation();
		line.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		line.setScaleID(DefaultScaleId.Y.value());
		line.setBorderColor(HtmlColor.BLACK);
		line.setBorderWidth(5);
		line.setValue(40);
		line.getLabel().setDisplay(true);
		line.getLabel().setContent("My threshold");
		line.getLabel().setBackgroundColor(HtmlColor.RED);
		line.setEnterCallback(eventHandler);
		line.setLeaveCallback(eventHandler);
		line.setClickCallback(eventHandler);
		line.setDoubleClickCallback(eventHandler);

		BoxAnnotation box = new BoxAnnotation();
		box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box.setXScaleID(DefaultScaleId.X.value());
		box.setYScaleID(DefaultScaleId.Y.value());
		time = (long) myDate.getTime() + DAY * (int) (AMOUNT_OF_POINTS / 4);
		box.setXMin(new Date(time));
		time = (long) myDate.getTime() + DAY * (int) (AMOUNT_OF_POINTS / 4 * 2);
		box.setXMax(new Date(time));
		box.setYMax(100);
		box.setYMin(60);
		box.setBackgroundColor("rgba(101, 33, 171, 0.5)");
		box.setBorderColor("rgb(101, 33, 171)");
		box.setBorderWidth(1);
		box.setEnterCallback(eventHandler);
		box.setLeaveCallback(eventHandler);
		box.setClickCallback(eventHandler);
		box.setDoubleClickCallback(eventHandler);
		
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

	class MyEventsHandler implements ClickCallback,  DoubleClickCallback,  LeaveCallback, EnterCallback {

		@Override
		public void onEnter(IsChart chart, AbstractAnnotation annotation, ChartEventContext event) {
			mylog.addLogEvent("> Enter on annotation '"+annotation.getId().value()+"' type " + annotation.getType());
		}

		@Override
		public void onLeave(IsChart chart, AbstractAnnotation annotation, ChartEventContext event) {
			mylog.addLogEvent("> Leave on annotation '"+annotation.getId().value()+"' type " + annotation.getType());
		}

		@Override
		public void onDoubleClick(IsChart chart, AbstractAnnotation annotation, ChartEventContext event) {
			click(chart, annotation, event, ClickType.DOUBLE_CLICK);
		}

		@Override
		public void onClick(IsChart chart, AbstractAnnotation annotation, ChartEventContext event) {
			click(chart, annotation, event, ClickType.CLICK);
		}
		
		private void click(IsChart chart, AbstractAnnotation annotation, ChartEventContext event, ClickType type) {
			StringBuilder sb = new StringBuilder();
			sb.append("Annotation: <b>").append(annotation.getId().value()).append("</b><br>");
			sb.append("Annotation type: <b>").append(annotation.getType().value()).append("</b><br>");
			sb.append("Event type: <b>").append(event.getNativeEvent().getType()).append("</b><br>");
			new Toast(type.getTitle()+" on annotation!", sb.toString(), type.getLevel()).show();
		}
	}
	
	private enum ClickType
	{

		CLICK("Click", DefaultToastType.SUCCESS),
		DOUBLE_CLICK("Double click", DefaultToastType.INFO);

		private final String title;

		private final DefaultToastType level;

		private ClickType(String title, DefaultToastType level) {
			this.title = title;
			this.level = level;
		}

		private String getTitle() {
			return title;
		}

		private DefaultToastType getLevel() {
			return level;
		}

	}
}
