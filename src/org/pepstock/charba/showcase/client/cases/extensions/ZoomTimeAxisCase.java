package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.DefaultTransitionMode;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.zoom.ScaleRange;
import org.pepstock.charba.client.zoom.ZoomOptions;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.client.zoom.enums.Mode;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class ZoomTimeAxisCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final long DAY = 1000 * 60 * 60 * 24;
	
	private static final long MAX_DAYS = DAY * 20;

	private static final int AMOUNT_OF_POINTS = 500;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomTimeAxisCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	@UiField
	CheckBox enableZoom;
	
	@UiField
	CheckBox enablePan;
	
	private final long time;

	public ZoomTimeAxisCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Zooming on cartesian time axis");
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

		final ScatterDataset dataset1 = chart.newDataset();

		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.FALSE);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		time = new Date().getTime();

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, 0, 1000);
		
		List<DataPoint> points = dataset1.getDataPoints(true);

		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			long newTime = time + (long)(Math.random() * MAX_DAYS);
			DataPoint dp = new DataPoint();
			dp.setX(new Date(newTime));
			dp.setY(xs1[i]);
			points.add(dp);
		}

		CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.getTicks().setAutoSkip(true);
		axis.getTicks().setAutoSkipPadding(50);
		axis.getTicks().setMaxRotation(0);
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.HOUR, "HH:mm");
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.MINUTE, "HH:mm");
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.SECOND, "HH:mm:ss");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1);
		chart.getOptions().getScales().setAxes(axis, axis2);

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(false);
		options.getPan().setMode(Mode.XY);
		options.getZoom().setMode(Mode.X);
		options.getZoom().getWheel().setEnabled(true);
		options.store(chart);

		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			ScatterDataset scDataset = (ScatterDataset) dataset;
			List<DataPoint> points = scDataset.getDataPoints(true);
			for (DataPoint dp : points) {
				dp.setY(getRandomDigit(0, 1000));
			}
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.reset(chart);
	}

	@UiHandler("zoomNextWeek")
	protected void handleZoomNextWeek(ClickEvent event) {
		ScaleRange range = new ScaleRange();
		range.setMin(time);
		range.setMax(time + DAY * 8);
		ZoomPlugin.zoomScale(chart, DefaultScaleId.X, range, DefaultTransitionMode.DEFAULT);
	}
	
	@UiHandler("zoom400600")
	protected void handleZoom400600(ClickEvent event) {
		ScaleRange range = new ScaleRange();
		range.setMin(400);
		range.setMax(600);
		ZoomPlugin.zoomScale(chart, DefaultScaleId.Y, range, DefaultTransitionMode.DEFAULT);
	}
	
	@UiHandler("enableZoom")
	protected void handleZoom(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.FACTORY);
		options.getZoom().getWheel().setEnabled(enableZoom.getValue());
		chart.update();
	}
	
	@UiHandler("enablePan")
	protected void handlePan(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.FACTORY);
		options.getPan().setEnabled(enablePan.getValue());
		chart.update();
	}

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
