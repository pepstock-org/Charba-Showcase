package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.BoxAnnotation;
import org.pepstock.charba.client.annotation.LineAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.LabelPosition;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.HorizontalBarDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.HorizontalBarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class AnnotationLineOnHorizontalBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationLineOnHorizontalBarCase> {
	}

	@UiField
	HorizontalBarChartWidget chart;

	public AnnotationLineOnHorizontalBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Line annotation on horizontal bar chart");
		chart.getOptions().getTooltips().setIntersect(true);
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);

		HorizontalBarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset");

		dataset.setBackgroundColor(new Color(75, 192, 192));
		dataset.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		
		AnnotationOptions options = new AnnotationOptions();

		LineAnnotation line = new LineAnnotation();
		line.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		line.setScaleID(DefaultScaleId.Y.value());
		line.setBorderColor(HtmlColor.ORANGE);
		line.setBorderDash(4, 4);
		line.setBorderWidth(2);
		line.setValue("April");

		line.getLabel().setDisplay(true);
		line.getLabel().setContent("Crtitical month");
		line.getLabel().setBackgroundColor(HtmlColor.LIGHT_SALMON);
		line.getLabel().setPosition(LabelPosition.END);

		BoxAnnotation box = new BoxAnnotation();
		box.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box.setXScaleID(DefaultScaleId.X.value());
		box.setYScaleID(DefaultScaleId.Y.value());
		box.setBackgroundColor(HtmlColor.LIGHT_GRAY.alpha(0.3D));
		box.setBorderWidth(0);

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
