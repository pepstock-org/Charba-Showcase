package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gradient.Colors;
import org.pepstock.charba.client.gradient.GradientOptions;
import org.pepstock.charba.client.gradient.PropertyOptions;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.TableauScheme;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GradientTimeSeriesByLineCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 60;
	
	private static final String FORMAT = "dd/MM/yyyy";
	
	private static final List<IsColor> COLORS = TableauScheme.TABLEAU10.getColors();

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GradientTimeSeriesByLineCase> {
	}

	@UiField
	LineChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();

	private final DateAdapter adapter;

	public GradientTimeSeriesByLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		adapter = new DateAdapter();

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using GRADIENT plugin on cartesian time axis");

		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(adapter.format(dp.getXAsDate(), TimeUnit.DAY));
			}

		});

		final LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.ORIGIN);

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setX(adapter.add(startingPoint, i, TimeUnit.DAY));
		}
		dataset1.setDataPoints(dp1);
		
		final CartesianTimeSeriesAxis axis = new CartesianTimeSeriesAxis(chart);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.getTime().setParser(FORMAT);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);
		chart.getData().setDatasets(dataset1);
		
		GradientOptions options = new GradientOptions();
		PropertyOptions propOptions = options.getBackgroundColor();
		propOptions.setAxis(AxisKind.X);
		Colors stopColors = propOptions.getColors();
		int index = 0;
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			if (i % 12 == 0) {
				Date v = adapter.add(startingPoint, i, TimeUnit.DAY);
				stopColors.setColor(adapter.format(v, FORMAT), COLORS.get(index));
				index++;
			}
		}
		dataset1.setOptions(options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			LineDataset scDataset = (LineDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setY(getRandomDigit(false));
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
