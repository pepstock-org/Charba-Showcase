package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.NativeCallback;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.AxisPosition;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.IsoWeekDay;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.MatrixChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.matrix.MatrixDataPoint;
import org.pepstock.charba.client.matrix.MatrixDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class MatrixOnTimeAxisCase extends BaseComposite {

	private static final Key DATE = Key.create("date");
	
	private static final NativeCallback BACKGROUND = NativeCallback.create("c", "const value = c.dataset.data[c.dataIndex].v;const alpha = (10 + value) / 110; return Chart.helpers.color('green').alpha(alpha).rgbString();");
	private static final NativeCallback BORDER = NativeCallback.create("c", "const value = c.dataset.data[c.dataIndex].v; const alpha = (10 + value) / 110; return Chart.helpers.color('green').alpha(alpha).darken(0.3).rgbString();");
	private static final NativeCallback WIDTH = NativeCallback.create("c", "const a = c.chart.chartArea || {}; return (a.right - a.left) / 53 - 2;");
	private static final NativeCallback HEIGHT = NativeCallback.create("c", "const a = c.chart.chartArea || {}; return (a.bottom - a.top) / 7 - 2;");

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MatrixOnTimeAxisCase> {
	}

	@UiField
	MatrixChartWidget chart;

	final DateAdapter adapter = new DateAdapter();

	public MatrixOnTimeAxisCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getChartElement().getStyle().setHeight("300px");
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(false);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Matrix chart using time axes");
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				return Collections.emptyList();
			}

		});
		chart.getOptions().getTooltips().getCallbacks().setLabelCallback(new TooltipLabelCallback() {

			@Override
			public List<String> onLabel(IsChart chart, TooltipItem item) {
				MatrixDataset dataset = (MatrixDataset) chart.getData().retrieveDataset(item);
				MatrixDataPoint v = dataset.getDataPoints().get(item.getDataIndex());
				Date date = v.getAttributeAsDate(DATE);
				return Arrays.asList(adapter.format(date, "EEEE, dd MMMM yyyy") + ", v: " + v.getValue());
			}

		});
		MatrixDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBackgroundColor(BACKGROUND);
		dataset1.setBorderColor(BORDER);
		dataset1.setBorderWidth(2);
		dataset1.setHoverBackgroundColor(HtmlColor.YELLOW);
		dataset1.setHoverBorderColor(HtmlColor.YELLOW_GREEN);

		dataset1.setWidth(WIDTH);
		dataset1.setHeight(HEIGHT);

		List<MatrixDataPoint> points = new LinkedList<>();

		Date end = adapter.startOf(new Date(), TimeUnit.DAY);
		Date start = adapter.add(end, -365, TimeUnit.DAY);

		while (start.getTime() <= end.getTime()) {
			MatrixDataPoint dp = new MatrixDataPoint(adapter.format(start, "yyyy-MM-dd"), adapter.format(start, "EEE"), getRandomDigit(0, 100));
			dp.setAttribute(DATE, start);
			points.add(dp);
			start = adapter.add(start, 1, TimeUnit.DAY);
		}
		dataset1.setDataPoints(points);

		CartesianTimeAxis axis = new CartesianTimeAxis(chart, DefaultScaleId.Y, AxisKind.Y);
		axis.setPosition(AxisPosition.RIGHT);
		axis.setOffset(true);
		axis.setReverse(true);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.getTime().setRound(TimeUnit.DAY);
		axis.getTime().setParser("EEE");
		axis.getTime().setIsoWeekday(IsoWeekDay.MONDAY);
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.DAY, "EEE");
		axis.getTicks().setPadding(3);
		axis.getTicks().setMaxRotation(0);
		axis.getTicks().setAutoSkip(true);
		axis.getGrid().setDisplay(false);
		axis.getGrid().setDrawBorder(false);
		axis.getGrid().setTickLength(0);

		CartesianTimeAxis axis1 = new CartesianTimeAxis(chart, DefaultScaleId.X, AxisKind.X);
		axis1.setPosition(AxisPosition.BOTTOM);
		axis1.setOffset(true);
		axis1.getTime().setUnit(TimeUnit.WEEK);
		axis1.getTime().setRound(TimeUnit.WEEK);
		axis1.getTime().setIsoWeekday(IsoWeekDay.MONDAY);
		axis1.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.WEEK, "MMM d");

		axis1.getTicks().setAutoSkip(true);

		axis1.getGrid().setDisplay(false);
		axis1.getGrid().setDrawBorder(false);
		axis1.getGrid().setTickLength(0);

		chart.getOptions().getScales().setAxes(axis, axis1);

		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			MatrixDataset ds = (MatrixDataset) dataset;
			List<MatrixDataPoint> points = ds.getDataPoints();
			points.forEach(element -> element.setValue(getRandomDigit(0, 100)));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
