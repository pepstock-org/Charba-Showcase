package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.annotation.AnnotationContext;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.callbacks.ValueCallback;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AnnotationCurveLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationCurveLineCase> {
	}

	@UiField
	BarChartWidget chart;

	public AnnotationCurveLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Curve line annotation on bar chart");
		chart.getOptions().getTooltips().setIntersect(true);
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		AnnotationOptions options = new AnnotationOptions();
		options.setClip(false);

		LineAnnotation line = new LineAnnotation();
		line.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		line.setBorderColor(HtmlColor.BLACK);
		line.setBorderWidth(5);
		line.setCurve(true);
		line.setXMin(chart.getData().getLabels().getString(1));
		line.setXMax(chart.getData().getLabels().getString(months - 2));
		line.setYMin(new ValueCallback() {
			
			@Override
			public Double invoke(AnnotationContext context) {
				Dataset ds = context.getChart().getData().getDatasets().get(0);
				return ds.getData().get(1) / 2;
			}
		});
		line.setYMax(new ValueCallback() {
			
			@Override
			public Double invoke(AnnotationContext context) {
				Dataset ds = context.getChart().getData().getDatasets().get(0);
				return ds.getData().get(months - 2) / 2;
			}
		});
		line.getLabel().setDisplay(true);
		line.getLabel().setContent("Jump!");
		line.getArrowHeads().getEnd().setDisplay(true);
		options.addAnnotations(line);
		
		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
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
