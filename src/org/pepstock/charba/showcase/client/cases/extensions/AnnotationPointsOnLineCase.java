package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.PointAnnotation;
import org.pepstock.charba.client.annotation.PolygonAnnotation;
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

public class AnnotationPointsOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationPointsOnLineCase> {
	}

	@UiField
	LineChartWidget chart;

	public AnnotationPointsOnLineCase() {
		super.months = 12;

		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Point annotations on line chart");

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

		PointAnnotation point1 = new PointAnnotation();
		point1.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		point1.setXValue("February");
		point1.setXScaleID(DefaultScaleId.X);
		point1.setYScaleID(DefaultScaleId.Y);
		point1.setYValue(50);
		point1.setRadius(10);
		point1.setBackgroundColor(GwtMaterialColor.YELLOW_LIGHTEN_3.alpha(0.3D));
		point1.setBorderWidth(2);
		point1.setBorderColor(GwtMaterialColor.YELLOW_LIGHTEN_3.darker());

		PolygonAnnotation point2 = new PolygonAnnotation();
		point2.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		point2.setXScaleID(DefaultScaleId.X);
		point2.setYScaleID(DefaultScaleId.Y);
		point2.setXValue("April");
		point2.setYValue(20);
		point2.setRadius(25);
		point2.setSides((ctx) -> (int)getRandomDigit(3, 10));
		point2.setBackgroundColor(GwtMaterialColor.BROWN_LIGHTEN_3.alpha(0.3D));
		point2.setBorderWidth(3);
		point2.setBorderColor(GwtMaterialColor.BROWN_LIGHTEN_3.darker());

		PointAnnotation point3 = new PointAnnotation();
		point3.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		point3.setXScaleID(DefaultScaleId.X);
		point3.setYScaleID(DefaultScaleId.Y);
		point3.setXValue("July");
		point3.setYValue(30);
		point3.setRadius(15);
		point3.setBackgroundColor(GwtMaterialColor.GREEN_LIGHTEN_3.alpha(0.3D));
		point3.setBorderWidth(4);
		point3.setBorderColor(GwtMaterialColor.GREEN_LIGHTEN_3.darker());

		PointAnnotation point4 = new PointAnnotation();
		point4.setXScaleID(DefaultScaleId.X);
		point4.setYScaleID(DefaultScaleId.Y);
		point4.setDrawTime(DrawTime.AFTER_DATASETS_DRAW);
		point4.setXValue("November");
		point4.setYValue(60);
		point4.setRadius(25);
		point4.setBackgroundColor(GwtMaterialColor.INDIGO_LIGHTEN_3.alpha(0.3D));
		point4.setBorderWidth(5);
		point4.setBorderColor(GwtMaterialColor.INDIGO_LIGHTEN_3.darker());

		options.setAnnotations(point1, point2, point3, point4);

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
