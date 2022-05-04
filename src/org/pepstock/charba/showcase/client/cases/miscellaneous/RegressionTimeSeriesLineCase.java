package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.Bounds;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.ml.RegressionDataset;
import org.pepstock.charba.client.ml.RegressionDatasetBuilder;
import org.pepstock.charba.client.ml.RegressionScore;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class RegressionTimeSeriesLineCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RegressionTimeSeriesLineCase> {
	}

	@UiField
	TimeSeriesLineChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();

	final TimeSeriesLineDataset dataset;
	
	RegressionDataset trend;
	
	public RegressionTimeSeriesLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				return "Linear regression formula: "+trend.getRegression().toFormula();
			}
			
		});
		chart.getOptions().getSubtitle().setDisplay(true);
		chart.getOptions().getSubtitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				RegressionScore score = trend.getRegression().scoreByTimeSeriesItems(dataset.getTimeSeriesData());
				return "Score R: "+Utilities.applyPrecision(score != null ? score.getR() * 100 : 0, 2)+"%, R2: "+Utilities.applyPrecision(score != null ? score.getR2() * 100 : 0, 2)+"%";
			}
			
		});

		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				TimeSeriesItem dp = dataset.getTimeSeriesData().get(item.getDataIndex());
				return Arrays.asList(FORMAT.format(dp.getTime()));
			}

		});

		dataset = chart.newDataset();
		dataset.setLabel("Dataset");
		dataset.setFill(Fill.FALSE);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		DateAdapter adapter = new DateAdapter();

		double[] xs1 = getLinearRegressionDigits(AMOUNT_OF_POINTS);
		List<TimeSeriesItem> data = new LinkedList<>();

		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			data.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), xs1[i]));
		}
		dataset.setTimeSeriesData(data);

		CartesianTimeSeriesAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setSource(TickSource.DATA);
		axis.setBounds(Bounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		trend = RegressionDatasetBuilder.create().setSamples(dataset).setLoadData(true).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(4);
		trend.setBorderDash(4, 6);
		
		chart.getData().setDatasets(dataset, trend);
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		double[] xs1 = getLinearRegressionDigits(AMOUNT_OF_POINTS);
		int index = 0;
		for (TimeSeriesItem item : dataset.getTimeSeriesData()) {
			item.setValue(xs1[index]);
			index++;
		}
		boolean hidden = !chart.isDatasetVisible(1);
		trend = RegressionDatasetBuilder.create().setSamples(dataset).setLoadData(true).setHidden(hidden).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(4);
		trend.setBorderDash(4, 6);
		
		chart.getData().setDatasets(dataset, trend);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
