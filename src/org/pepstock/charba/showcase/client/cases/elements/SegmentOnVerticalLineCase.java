package org.pepstock.charba.showcase.client.cases.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.pepstock.charba.client.callbacks.BorderDashCallback;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.SegmentContext;
import org.pepstock.charba.client.callbacks.WidthCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.gwt.widgets.VerticalLineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SegmentOnVerticalLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SegmentOnVerticalLineCase> {
	}

	@UiField
	VerticalLineChartWidget chart;

	public SegmentOnVerticalLineCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Vertical line chart with custom segments rendering");
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);

		chart.getOptions().getSegment().setBorderColor(new ColorCallback<SegmentContext>() {
			
			@Override
			public Object invoke(SegmentContext context) {
				return context.getEndPoint().getParsed().getX() <  context.getStartPoint().getParsed().getX() ? HtmlColor.GRAY : HtmlColor.GREEN;
			}
		});

		chart.getOptions().getSegment().setBackgroundColor(new ColorCallback<SegmentContext>() {
			
			@Override
			public Object invoke(SegmentContext context) {
				return context.getEndPoint().getParsed().getX() <  context.getStartPoint().getParsed().getX() ? HtmlColor.LIGHT_GRAY.alpha(0.8) : HtmlColor.LIGHT_GREEN.alpha(0.8);
			}
		});
		
		chart.getOptions().getSegment().setBorderWidth(new WidthCallback<SegmentContext>() {
			
			@Override
			public Integer invoke(SegmentContext context) {
				return context.getEndPoint().getParsed().getX() <  context.getStartPoint().getParsed().getX() ? 5 : null;
			}
		});

		chart.getOptions().getSegment().setBorderDash(new BorderDashCallback<SegmentContext>() {
			
			@Override
			public List<Integer> invoke(SegmentContext context) {
				return context.getEndPoint().getParsed().getX() <  context.getStartPoint().getParsed().getX() ? Arrays.asList(6,6) : Collections.emptyList();
			}
		});
		
		List<Dataset> datasets = chart.getData().getDatasets(true);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setFill(true);
		
		double[] values = getRandomDigits(months, false);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart, AxisKind.Y);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart, AxisKind.X);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);
		chart.getData().setLabels(getLabels());
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		chart.getNode().getOptions();
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
