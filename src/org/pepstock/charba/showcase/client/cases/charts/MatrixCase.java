package org.pepstock.charba.showcase.client.cases.charts;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.gwt.widgets.MatrixChartWidget;
import org.pepstock.charba.client.items.ChartAreaNode;
import org.pepstock.charba.client.items.IsArea;
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

public class MatrixCase extends BaseComposite {
	
	private static final int ROWS_COUNT = 5;

	private static final int COLUMNS_COUNT = 10;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MatrixCase> {
	}

	@UiField
	MatrixChartWidget chart;

	public MatrixCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Matrix chart");
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				return Collections.emptyList();
			}
		
		});
		chart.getOptions().getTooltips().getCallbacks().setLabelCallback(new TooltipLabelCallback() {

			@Override
			public String onLabel(IsChart chart, TooltipItem item) {
				MatrixDataset dataset = (MatrixDataset)chart.getData().retrieveDataset(item);
				MatrixDataPoint v = dataset.getDataPoints().get(item.getDataIndex());
	            return "x: " + v.getX()+ ", y: " + v.getY() + ", v: " + v.getValue();
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
		
		dataset1.setHoverBackgroundColor(HtmlColor.YELLOW);
	
		dataset1.setWidth(new SizeCallback() {
			
			@Override
			public Double invoke(DatasetContext context) {
				ChartAreaNode area = context.getChart().getNode().getChartArea();
				return IsArea.isConsistent(area) ? area.getWidth() / ROWS_COUNT - 1 : MatrixDataset.DEFAULT_WIDTH;
			}
		});

		dataset1.setHeight(new SizeCallback() {
			
			@Override
			public Double invoke(DatasetContext context) {
				ChartAreaNode area = context.getChart().getNode().getChartArea();
				return IsArea.isConsistent(area) ? area.getHeight() / COLUMNS_COUNT - 1 : MatrixDataset.DEFAULT_HEIGHT;
			}
		});

		List<MatrixDataPoint> points = new LinkedList<>();
		for (int i= 0; i<ROWS_COUNT; i++) {
			for (int k= 0; k<COLUMNS_COUNT; k++) {
				points.add(new MatrixDataPoint(i, k, getRandomDigit(0,100)));
			}
		}
		dataset1.setDataPoints(points);
		
		CartesianLinearAxis axis = new CartesianLinearAxis(chart, DefaultScaleId.X, AxisKind.X);
		axis.getTicks().setStepSize(1);
		axis.getGrid().setDisplay(false);
		
		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart, DefaultScaleId.Y, AxisKind.Y);
		axis1.setOffset(true);
		axis1.getTicks().setStepSize(1);
		axis1.getGrid().setDisplay(false);

		chart.getOptions().getScales().setAxes(axis, axis1);
		
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			MatrixDataset ds = (MatrixDataset)dataset;
			List<MatrixDataPoint> points = ds.getDataPoints();
			points.forEach(element -> element.setValue(getRandomDigit(0,100)));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
}
