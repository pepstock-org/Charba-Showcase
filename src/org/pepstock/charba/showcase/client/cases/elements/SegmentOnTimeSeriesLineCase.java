package org.pepstock.charba.showcase.client.cases.elements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.AbstractTooltipTitleCallback;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.SegmentContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeSeriesAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.data.TimeSeriesItem;
import org.pepstock.charba.client.data.TimeSeriesLineDataset;
import org.pepstock.charba.client.enums.Bounds;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.TimeSeriesLineChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
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

public class SegmentOnTimeSeriesLineCase extends BaseComposite {

	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);

	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SegmentOnTimeSeriesLineCase> {
	}

	@UiField
	TimeSeriesLineChartWidget chart;

	private final long startingPoint = System.currentTimeMillis();
	
	private String week = null;
	
	private int counter = -1;
	
	private final DateAdapter adapter = new DateAdapter();

	public SegmentOnTimeSeriesLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Timeseries line chart with different segment colors for week");
		chart.getOptions().getInteraction().setIntersect(false);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new AbstractTooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(FORMAT.format(dp.getXAsDate()));
			}

		});
		
		chart.getOptions().getSegment().setBorderColor(new ColorCallback<SegmentContext>() {
			
			@Override
			public Object invoke(SegmentContext context) {
				String myWeek = adapter.format((long)context.getEndPoint().getParsed().getX(), TimeUnit.WEEK);
				if (!myWeek.equals(week)) {
					week = myWeek;
					counter++;
				}
				return GoogleChartColor.values()[counter];
			}
		});

		chart.getOptions().getSegment().setBackgroundColor(new ColorCallback<SegmentContext>() {
			
			@Override
			public Object invoke(SegmentContext context) {
				String myWeek = adapter.format((long)context.getEndPoint().getParsed().getX(), TimeUnit.WEEK);
				if (!myWeek.equals(week)) {
					week = myWeek;
					counter++;
				}
				return GoogleChartColor.values()[counter].alpha(0.5);
			}
		});

		final TimeSeriesLineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.ORIGIN);
		dataset1.setPointRadius(0);
		
		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		List<TimeSeriesItem> data = new LinkedList<>();

		for (int i = AMOUNT_OF_POINTS - 1; i >= 0; i--) {
			data.add(new TimeSeriesItem(adapter.add(startingPoint, i, TimeUnit.DAY), xs1[i]));
		}
		dataset1.setTimeSeriesData(data);

		CartesianTimeSeriesAxis axis = chart.getOptions().getScales().getTimeAxis();
		axis.getTicks().setSource(TickSource.DATA);
		axis.setBounds(Bounds.DATA);
		axis.getTime().setUnit(TimeUnit.DAY);

		CartesianLinearAxis axis2 = chart.getOptions().getScales().getLinearAxis();
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);
		axis2.setStacked(true);

		chart.getData().setDatasets(dataset1);
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		counter = -1;
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
