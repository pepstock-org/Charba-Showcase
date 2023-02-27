package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Collections;

import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.BoxAnnotation;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.Fill;
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

public class AnnotationsOnCombinedCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationsOnCombinedCase> {
	}

	@UiField
	BarChartWidget chart;

	public AnnotationsOnCombinedCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Line and box annotations on combined bar/line chart");
		chart.getOptions().getTooltips().setIntersect(true);
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);

		LineDataset dataset1 = new LineDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBorderColor(new Color(71, 170, 237));
		dataset1.setBorderWidth(2);
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.FALSE);

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		dataset2.setBackgroundColor(new Color(255, 99, 132));
		dataset2.setBorderColor(HtmlColor.WHITE);
		dataset2.setBorderWidth(2);
		dataset2.setData(getRandomDigits(months));

		BarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 3");

		dataset3.setBackgroundColor(new Color(75, 192, 192));
		dataset3.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

		double max = Math.max(Collections.max(dataset1.getData()), Collections.max(dataset2.getData()));
		max = Math.max(max, Collections.max(dataset3.getData()));

		double min = Math.min(Collections.min(dataset1.getData()), Collections.min(dataset2.getData()));
		min = Math.min(min, Collections.min(dataset3.getData()));

		AnnotationOptions options = new AnnotationOptions();

		LineAnnotation line = new LineAnnotation();
		line.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		line.setScaleID(DefaultScaleId.Y.value());
		line.setBorderColor(HtmlColor.BLACK);
		line.setBorderWidth(5);
		line.setValue(getRandomDigits(1, min, max)[0]);

		line.getLabel().setDisplay(true);
		line.getLabel().setContent("My threshold");
		line.getLabel().setBackgroundColor(HtmlColor.RED);

		BoxAnnotation box = new BoxAnnotation("stock123");
		box.setDisplay(true);
		box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box.setXScaleID(DefaultScaleId.X.value());
		box.setYScaleID(DefaultScaleId.Y.value());
		box.setXMin("February");
		box.setXMax("April");
		double value = getRandomDigits(1, min, max)[0];
		box.setYMax(value);
		box.setYMin(value - 40);

		box.setBackgroundColor("rgba(101, 33, 171, 0.5)");
		box.setBorderColor("rgb(101, 33, 171)");
		box.setBorderWidth(1);
		options.setAnnotations(line, box);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
		;
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
