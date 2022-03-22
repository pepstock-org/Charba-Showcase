package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.annotation.AnnotationContext;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.callbacks.ContentCallback;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.LabelPosition;
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
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
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

public class AnnotationLineOnTimeSeriesLineCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationLineOnTimeSeriesLineCase> {
	}

	@UiField
	TimeSeriesLineChartWidget chart;

	final LineAnnotation line1 = new LineAnnotation();

	final TimeSeriesLineDataset dataset1;
	
	final TimeSeriesLineDataset dataset2;

	private final long startingPoint = System.currentTimeMillis();

	public AnnotationLineOnTimeSeriesLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Line annotation on timeseries line chart");
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

		dataset1 = chart.newDataset();

		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.FALSE);

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		dataset2 = chart.newDataset();

		dataset2.setLabel("dataset 2");
		dataset2.setFill(Fill.FALSE);

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());

		DateAdapter adapter = new DateAdapter();

		int gap = (int) (AMOUNT_OF_POINTS / 2);

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS, false);
		List<TimeSeriesItem> data = new LinkedList<>();
		List<TimeSeriesItem> data1 = new LinkedList<>();
		
		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			data.add(new TimeSeriesItem(adapter.add(startingPoint, i - gap, TimeUnit.DAY), xs1[i]));
			data1.add(new TimeSeriesItem(adapter.add(startingPoint, i - gap, TimeUnit.DAY), xs2[i]));
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
		line.setDrawTime(DrawTime.AFTER_DRAW);
		line.setScaleID(DefaultScaleId.X.value());
		line.setBorderColor(HtmlColor.DARK_GRAY);
		line.setBorderWidth(2);
		line.setValue((context) -> new Date());
		line.getLabel().setDisplay(true);
		line.getLabel().setContent("Now");
		line.getLabel().setPosition(LabelPosition.START);

		line1.setDrawTime(DrawTime.AFTER_DRAW);
		line1.setScaleID(DefaultScaleId.Y.value());
		line1.setBorderColor(HtmlColor.ORANGE);
		line1.setBorderWidth(4);
		line1.setBorderDash(4, 4);
		line1.setValue((context) -> getAverage());
		line1.getLabel().setDisplay(true);
		
		line1.getLabel().setContent(new ContentCallback() {
			
			@Override
			public String invoke(AnnotationContext context) {
				return "Average of datasets is "+Utilities.applyPrecision(getAverage(), 2);
			}
		});
		
		line1.getLabel().setPosition(LabelPosition.END);
		line1.getLabel().setBackgroundColor(HtmlColor.ORANGE);
		line1.getLabel().setColor(HtmlColor.BLACK);
		line1.getLabel().getFont().setSize(18);
		
		options.setAnnotations(line, line1);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);
	}
	
	private double getAverage() {
		double sum = 0;
		double count = 0;
		double dsvisible = 0;
		List<Dataset> datasets = chart.getData().getDatasets();
		for (int i=0; i<datasets.size(); i++) {
			if (chart.isDatasetVisible(i)) {
				dsvisible++;
				TimeSeriesLineDataset scDataset = (TimeSeriesLineDataset) datasets.get(i);
				for (TimeSeriesItem dp : scDataset.getTimeSeriesData()) {
					sum += dp.getValue();
					count++;
				}
			}
		}
		return sum / Math.max(1, count / Math.max(1, dsvisible));
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
}
