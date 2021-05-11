package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.DefaultTransitionKey;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.enums.ModifierKey;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.zoom.Drag;
import org.pepstock.charba.client.zoom.ZoomContext;
import org.pepstock.charba.client.zoom.ZoomOptions;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.client.zoom.callbacks.CompletedCallback;
import org.pepstock.charba.client.zoom.callbacks.ProgressCallback;
import org.pepstock.charba.client.zoom.callbacks.RejectedCallback;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ZoomCallbacksOnTimeSeriesCase extends BaseComposite {

	private static final String FORMAT = "yyyy MMM dd";

	private static final long DAY = 1000 * 60 * 60 * 24;

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
	
	private static final String CSS = "background: linear-gradient(180deg,#eee,#fff); background-color: rgba(0, 0, 0, 0); background-color: #eee; border: 1px solid #cdd5d7; border-radius: 6px; box-shadow: 0 1px 2px 1px #cdd5d7; " +
	"font-family: consolas,courier,monospace; font-size: .9rem; font-weight: 700; line-height: 1; margin: 3px; padding: 4px 6px; white-space: nowrap;";
		

	interface ViewUiBinder extends UiBinder<Widget, ZoomCallbacksOnTimeSeriesCase> {
	}

	@UiField
	TimeSeriesLineChartWidget chart;

	@UiField
	LogView mylog;

	@UiField
	CheckBox dragging;
	
	@UiField
	CheckBox modifier;
	
	@UiField
	HTMLPanel help;

	private final Drag drag;
	
	private final CartesianTimeSeriesAxis axis;

	public ZoomCallbacksOnTimeSeriesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Zoom callbacks on timeseries line chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				DateAdapter adapter = axis.getAdapters().getDate().create();
				return Arrays.asList(adapter.format(dp.getXAsDate(), FORMAT));
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

		long time = new Date().getTime();

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

		axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1, dataset2);

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(false);
		options.getZoom().setEnabled(true);
		options.getZoom().setMode(InteractionAxis.X);
		options.getZoom().setSpeed(0.05D);
		drag = ZoomPlugin.createDrag();
		options.getZoom().setDrag(drag);
		options.getZoom().setCompletedCallback(new CompletedCallback() {

			@Override
			public void onCompleted(ZoomContext context) {
				mylog.addLogEvent("> ZOOM COMPLETE on chart");
			}
		});

		options.getZoom().setProgressCallback(new ProgressCallback() {

			@Override
			public void onProgress(ZoomContext context) {
				mylog.addLogEvent("> ZOOM in PROGRESS on chart");
			}
		});

		options.getZoom().setRejectedCallback(new RejectedCallback() {

			@Override
			public void onRejected(ZoomContext context) {
				mylog.addLogEvent("> ZOOM REJECTED; press ALT to zoom");
			}
		});

		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);

		HTML html = new HTML("<kbd style=\""+CSS+"\">Alt</kbd> + wheeling to zoom");
		help.add(html);
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

	@UiHandler("dragging")
	protected void handleDragging(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.ID, ZoomPlugin.FACTORY);
		if (dragging.getValue()) {
			options.getZoom().setDrag(drag);
			options.getZoom().setWheelModifierKey(null);
			modifier.setValue(false);
			modifier.setEnabled(false);
			help.setVisible(false);
		} else {
			options.getZoom().setDrag(false);
			modifier.setEnabled(true);
		}
		chart.update();
	}
	
	@UiHandler("modifier")
	protected void handleModifier(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.ID, ZoomPlugin.FACTORY);
		if (modifier.getValue()) {
			options.getZoom().setWheelModifierKey(ModifierKey.ALT);
			help.setVisible(true);
		} else {
			options.getZoom().setWheelModifierKey(null);
			help.setVisible(false);
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.reset(chart, DefaultTransitionKey.SHOW);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
