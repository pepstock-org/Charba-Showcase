package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.EllipseAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.IsColor;
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

public class AnnotationEllipseOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationEllipseOnLineCase> {
	}

	@UiField
	LineChartWidget chart;

	public AnnotationEllipseOnLineCase() {
		super.months = 12;

		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Ellipse annotations on line chart");

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

		AnnotationOptions options = new AnnotationOptions();

		EllipseAnnotation ellipse = new EllipseAnnotation();
		ellipse.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		ellipse.setXScaleID(DefaultScaleId.X);
		ellipse.setYScaleID(DefaultScaleId.Y);
		ellipse.setXMin("February");
		ellipse.setXMax("April");
		ellipse.setYMin(10);
		ellipse.setYMax(60);
		ellipse.setBackgroundColor(GwtMaterialColor.INDIGO_LIGHTEN_3.alpha(0.3D));
		ellipse.setBorderWidth(4);
		ellipse.setBorderColor(GwtMaterialColor.INDIGO_LIGHTEN_3);
		ellipse.setBorderDash(3,3);

		options.setAnnotations(ellipse);

		chart.getOptions().getPlugins().setOptions(AnnotationPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		LineDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));
		IsColor color = GoogleChartColor.values()[datasets.size()];
		dataset.setBackgroundColor(color.toHex());
		dataset.setBorderColor(color.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(false);
		datasets.add(dataset);
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
