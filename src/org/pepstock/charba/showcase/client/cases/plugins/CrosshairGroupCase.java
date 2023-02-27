package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.callbacks.MinMaxCallback;
import org.pepstock.charba.client.callbacks.ScaleContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.CallbackProxy;
import org.pepstock.charba.client.commons.JsHelper;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.dom.BaseEventTarget.EventListenerCallback;
import org.pepstock.charba.client.dom.DOM;
import org.pepstock.charba.client.dom.elements.Div;
import org.pepstock.charba.client.dom.enums.IsKeyboardKey;
import org.pepstock.charba.client.dom.enums.KeyboardEventType;
import org.pepstock.charba.client.dom.enums.KeyboardUiKey;
import org.pepstock.charba.client.dom.events.NativeBaseEvent;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.Crosshair;
import org.pepstock.charba.client.impl.plugins.CrosshairLabel;
import org.pepstock.charba.client.impl.plugins.CrosshairOptions;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class CrosshairGroupCase extends BaseComposite {

	private static final List<IsChart> CHARTS = new LinkedList<>();

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CrosshairGroupCase> {
	}

	@UiField
	LineChartWidget chart1;

	@UiField
	LineChartWidget chart2;

	@UiField
	LineChartWidget chart3;

	@UiField
	HTMLPanel help;

	private final long startingPoint = System.currentTimeMillis();

	private final DateAdapter adapter;

	private double minDate = Double.NaN;

	private double maxDate = Double.NaN;

	public CrosshairGroupCase() {
		initWidget(uiBinder.createAndBindUi(this));

		CHARTS.clear();
		adapter = new DateAdapter();

		loadChart(chart1, 0, 1.5);
		loadChart(chart2, 1, 1.5);
		loadChart(chart3, 2, 3.5);

		CallbackProxy<EventListenerCallback> keyboardCallbackProxy = JsHelper.get().newCallbackProxy();
		keyboardCallbackProxy.setCallback(new EventListenerCallback() {

			@Override
			public void call(NativeBaseEvent event) {
				event.preventDefault();
				if (KeyboardUiKey.ESCAPE.is(event)) {
					clearZooming();
				}
			}
		});
		DOM.getDocument().addEventListener(KeyboardEventType.KEY_UP, keyboardCallbackProxy.getProxy());

		Div aEsc = IsKeyboardKey.getElement(KeyboardUiKey.ESCAPE);
		String clean = "Press " + aEsc.getInnerHTML() + " to clear zooming";

		HTML html = new HTML(clean);
		help.add(html);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private void loadChart(LineChartWidget chart, int col, double ratio) {
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(ratio);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Crosshair grouping by line chart");

		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(adapter.format(dp.getXAsDate(), TimeUnit.DAY));
			}

		});

		final LineDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setFill(false);

		IsColor color1 = GoogleChartColor.values()[col];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setX(adapter.add(startingPoint, i, TimeUnit.DAY));
		}
		dataset.setDataPoints(dp1);

		final CartesianTimeSeriesAxis axis = new CartesianTimeSeriesAxis(chart);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.setMin(new MinMaxCallback<Object>() {

			@Override
			public Double invoke(ScaleContext context) {
				return minDate;
			}
		});
		axis.setMax(new MinMaxCallback<Object>() {

			@Override
			public Double invoke(ScaleContext context) {
				return maxDate;
			}
		});

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);
		chart.getData().setDatasets(dataset);

		CrosshairOptions options = new CrosshairOptions();
		options.setGroup("groupA");
		options.setLineColor(HtmlColor.BLACK);
		options.setMode(InteractionAxis.XY);
		options.setLineDash(3, 3);
		options.setLineWidth(1);
		options.getLabels().setDisplay(col == 2);
		options.getLabels().setBackgroundColor(HtmlColor.WHITE_SMOKE);
		options.getLabels().setBorderRadius(0);
		options.getLabels().setBorderWidth(1);
		options.getLabels().setColor(CrosshairLabel.DEFAULT_BACKGROUND_COLOR);
		options.getLabels().setBorderColor(CrosshairLabel.DEFAULT_BACKGROUND_COLOR);

		chart.getOptions().getPlugins().setOptions(Crosshair.ID, options);
		chart.getPlugins().add(Crosshair.get());

		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(2);
		pOptions.setBorderDash(6, 2);
		pOptions.setBorderColor(HtmlColor.GREY);
		pOptions.setColor(HtmlColor.LIGHT_GREEN.alpha(DatasetsItemsSelectorOptions.DEFAULT_ALPHA));
		pOptions.getSelectionCleaner().setDisplay(false);

		chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
		chart.getPlugins().add(DatasetsItemsSelector.get());

		chart.addHandler(new DatasetRangeSelectionEventHandler() {

			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				minDate = event.getFrom().getValue();
				maxDate = event.getTo().getValue();
				for (IsChart c : CHARTS) {
					if (c.getId().equals(event.getChart().getId())) {
						DatasetsItemsSelector.get().cleanSelection(c, false);
					}
					c.reconfigure();
				}
			}
		}, DatasetRangeSelectionEvent.TYPE);

		// adds chart
		CHARTS.add(chart);
	}

	private void clearZooming() {
		minDate = Double.NaN;
		maxDate = Double.NaN;
		for (IsChart c : CHARTS) {
			c.reconfigure();
		}
	}
}
