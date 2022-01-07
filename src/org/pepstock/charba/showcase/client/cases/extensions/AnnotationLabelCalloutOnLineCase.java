package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Collections;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.annotation.AlignPosition;
import org.pepstock.charba.client.annotation.AnnotationContext;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.LabelAnnotation;
import org.pepstock.charba.client.annotation.callbacks.AdjustSizeCallback;
import org.pepstock.charba.client.annotation.callbacks.LabelAlignPositionCallback;
import org.pepstock.charba.client.annotation.enums.LabelPosition;
import org.pepstock.charba.client.callbacks.NativeCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AnnotationLabelCalloutOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationLabelCalloutOnLineCase> {
	}

	@UiField
	LineChartWidget chart;
	
	private NativeCallback calloutBordeColor = NativeCallback.create("ctx", "return ctx.chart.data.datasets[0].borderColor;");
	private NativeCallback calloutBordeWidth = NativeCallback.create("ctx", "return ctx.chart.data.datasets[0].borderWidth || ctx.chart.options.elements.line.borderWidth;");

	public AnnotationLabelCalloutOnLineCase() {
		super.months = 12;

		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Label annotation with callout on line chart");

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);
		double[] values = getRandomDigits(months, 0, 100);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.setMax(120);
		
		chart.getOptions().getScales().setAxes(axis);

		AnnotationOptions options = new AnnotationOptions();
		options.setClip(false);

		LabelAnnotation label = new LabelAnnotation();
		label.setDisplay((ctx) -> ctx.getChart().isDatasetVisible(0));
		label.setBackgroundColor(HtmlColor.WHITE_SMOKE.alpha(0.5));
		label.setContent((ctx) -> "Maximum value is " + maxValue(ctx.getChart()));
		label.getFont().setSize(16);
		label.getPadding().set(6);
		label.getPadding().setBottom(12);
		label.setYAdjust(-60);
		label.setXAdjust(new AdjustSizeCallback() {
			
			@Override
			public Double invoke(AnnotationContext context) {
				return maxIndex(context.getChart()) <= 3 ? 60D : maxIndex(context.getChart()) >= 10 ? -60D : 0D;
			}
		});
		label.setPosition(new LabelAlignPositionCallback() {
			
			@Override
			public AlignPosition invoke(AnnotationContext context) {
				AlignPosition result = new AlignPosition();
				LabelPosition xPos = maxIndex(context.getChart()) <= 3 ? LabelPosition.START : maxIndex(context.getChart()) >= 10 ? LabelPosition.END : LabelPosition.CENTER;
				result.setX(xPos);
				result.setY(LabelPosition.END);
				return result;
			}
		});
		label.setXScaleID(DefaultScaleId.X);
		label.setXValue((ctx) -> maxLabel(ctx.getChart()));
		label.setYScaleID(DefaultScaleId.Y);
		label.setYValue((ctx) -> maxValue(ctx.getChart()));
		label.getCallout().setDisplay(true);
		label.getCallout().setBorderColor(calloutBordeColor);
		label.getCallout().setBorderWidth(calloutBordeWidth);
		
		options.setAnnotations(label);
		
		log(options.toJSON());

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, 0, 100));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private double maxValue(IsChart chart) {
		return Collections.max(chart.getData().getDatasets().get(0).getData());
	}

	private int maxIndex(IsChart chart) {
		double max = maxValue(chart);
		return chart.getData().getDatasets().get(0).getData().indexOf(max);
	}

	private String maxLabel(IsChart chart) {
		return getLabels()[maxIndex(chart)];
	}
}
