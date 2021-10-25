package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.adapters.DateAdapter;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
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
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.MatrixChartWidget;
import org.pepstock.charba.client.items.ChartAreaNode;
import org.pepstock.charba.client.items.IsArea;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.matrix.MatrixDataPoint;
import org.pepstock.charba.client.matrix.MatrixDataset;
import org.pepstock.charba.client.matrix.callbacks.SizeCallback;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class MatrixCalendarCase extends BaseComposite {

	private static final Key DATE = Key.create("date");

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MatrixCalendarCase> {
	}

	@UiField
	MatrixChartWidget chart;

	final DateAdapter adapter = new DateAdapter();

	public MatrixCalendarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getChartElement().getStyle().setWidth("50%");
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Calendar by a matrix chart");
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

		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				if (context.getDataIndex() < 0) {
					return null;
				}
				List<MatrixDataPoint> points = dataset1.getDataPoints();
				MatrixDataPoint point = points.get(context.getDataIndex());
				double alpha = Math.min(Math.max((10 + point.getValue()) / 110, 0), 1);
				return HtmlColor.GREEN.alpha(alpha);
			}

		});

		dataset1.setBorderColor(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				if (context.getDataIndex() < 0) {
					return null;
				}
				List<MatrixDataPoint> points = dataset1.getDataPoints();
				MatrixDataPoint point = points.get(context.getDataIndex());
				double alpha = Math.min(Math.max((10 + point.getValue()) / 110, 0), 1);
				return HtmlColor.DARK_GREEN.alpha(alpha);
			}

		});
		dataset1.setBorderWidth(2);
		dataset1.setHoverBackgroundColor(HtmlColor.YELLOW);
		dataset1.setHoverBorderColor(HtmlColor.YELLOW_GREEN);

		dataset1.setWidth(new SizeCallback() {

			@Override
			public Double invoke(DatasetContext context) {
				ChartAreaNode area = context.getChart().getNode().getChartArea();
				ScaleItem x = context.getChart().getNode().getScales().getItems().get(DefaultScaleId.X.value());
				return IsArea.isConsistent(area) ? area.getWidth() / x.getTicks().size() - 3 : MatrixDataset.DEFAULT_WIDTH;
			}
		});

		dataset1.setHeight(new SizeCallback() {

			@Override
			public Double invoke(DatasetContext context) {
				ChartAreaNode area = context.getChart().getNode().getChartArea();
				ScaleItem y = context.getChart().getNode().getScales().getItems().get(DefaultScaleId.Y.value());
				return IsArea.isConsistent(area) ? area.getHeight() / y.getTicks().size() - 3 : MatrixDataset.DEFAULT_WIDTH;
			}
		});

		List<MatrixDataPoint> points = new LinkedList<>();

		Date start = adapter.startOf(new Date(), TimeUnit.MONTH);
		Date end = adapter.endOf(start, TimeUnit.MONTH);

		while (start.getTime() <= end.getTime()) {
			MatrixDataPoint dp = new MatrixDataPoint(adapter.format(start, "EEE"), adapter.format(start, "yyyy-MM-dd"), getRandomDigit(0, 100));
			dp.setAttribute(DATE, start);
			points.add(dp);
			start = adapter.add(start, 1, TimeUnit.DAY);
		}
		dataset1.setDataPoints(points);

		CartesianTimeAxis axis = new CartesianTimeAxis(chart, DefaultScaleId.X, AxisKind.X);
		axis.setPosition(AxisPosition.TOP);
		axis.setOffset(true);
		axis.setReverse(false);
		axis.getTime().setUnit(TimeUnit.DAY);
		axis.getTime().setParser("EEE");
		axis.getTime().setIsoWeekday(IsoWeekDay.MONDAY);
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.DAY, "EEE");
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTicks().setPadding(0);
		axis.getTicks().setMaxRotation(0);
		axis.getGrid().setDisplay(false);
		axis.getGrid().setDrawBorder(false);

		CartesianTimeAxis axis1 = new CartesianTimeAxis(chart, DefaultScaleId.Y, AxisKind.Y);
		axis1.setPosition(AxisPosition.LEFT);
		axis1.setOffset(true);
		axis1.getTime().setUnit(TimeUnit.WEEK);
		axis1.getTime().setRound(TimeUnit.WEEK);
		axis1.getTime().setIsoWeekday(IsoWeekDay.MONDAY);
		axis1.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.WEEK, "W");

		axis1.getTicks().setPadding(3);
		axis1.getTicks().setMaxRotation(0);
		axis1.getTicks().setAutoSkip(true);

		axis1.getGrid().setDisplay(false);
		axis1.getGrid().setDrawBorder(false);
		axis1.getGrid().setTickLength(0);

		axis1.getTitle().setDisplay(true);
		axis1.getTitle().getFont().setSize(16);
		axis1.getTitle().getFont().setWeight(Weight.BOLD);
		axis1.getTitle().setText(adapter.format(end, "MMMM, yyyy"));

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
