package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.annotation.Annotation;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.BoxAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.colors.UiGradient;
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

public class AnnotationBoxesOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AnnotationBoxesOnLineCase> {
	}

	@UiField
	LineChartWidget chart;

	public AnnotationBoxesOnLineCase() {
		super.months = 12;

		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Box annotations on line chart");

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);
		double[] values = getRandomDigits(months);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		AnnotationOptions options = new AnnotationOptions();

		BoxAnnotation box1 = new BoxAnnotation();
		box1.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box1.setXScaleID(DefaultScaleId.X.value());
		box1.setYScaleID(DefaultScaleId.Y.value());
		box1.setXMin("January");
		box1.setXMax("April");
		
		//box1.setBackgroundColor(GwtMaterialColor.BLUE_GREY_LIGHTEN_3.alpha(0.3D));
		
//		Pattern p = TilesFactory.createPattern(Shape.DISC, HtmlColor.GREEN.alpha(0.2), HtmlColor.WHITE.alpha(0.2));
//		box1.setBackgroundColor(p);

		Gradient g = UiGradient.AFTER_THE_RAIN.createGradient();
		box1.setBackgroundColor(g);
//
		
		box1.setBorderWidth(0);
		box1.setBorderColor(HtmlColor.TRANSPARENT);

		BoxAnnotation box2 = new BoxAnnotation();
		box2.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box2.setXScaleID(DefaultScaleId.X.value());
		box2.setYScaleID(DefaultScaleId.Y.value());
		box2.setXMin("April");
		box2.setXMax("July");
//		box2.setBackgroundColor(GwtMaterialColor.AMBER_LIGHTEN_3.alpha(0.3D));
		
		Gradient g1 = UiGradient.AFTER_THE_RAIN.createGradient(GradientType.RADIAL);
		box2.setBackgroundColor(g1);
		
		box2.setBorderWidth(0);
		box2.setBorderColor(HtmlColor.TRANSPARENT);

		BoxAnnotation box3 = new BoxAnnotation();
		box3.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box3.setXScaleID(DefaultScaleId.X.value());
		box3.setYScaleID(DefaultScaleId.Y.value());
		box3.setXMin("July");
		box3.setXMax("October");
		box3.setBackgroundColor(GwtMaterialColor.GREEN_LIGHTEN_3.alpha(0.3D));
		box3.setBorderWidth(0);
		box3.setBorderColor(HtmlColor.TRANSPARENT);

		BoxAnnotation box4 = new BoxAnnotation();
		box4.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box4.setXScaleID(DefaultScaleId.X.value());
		box4.setYScaleID(DefaultScaleId.Y.value());
		box4.setXMin("October");
		box4.setBackgroundColor(GwtMaterialColor.INDIGO_LIGHTEN_3.alpha(0.3D));
		box4.setBorderWidth(0);
		box4.setBorderColor(HtmlColor.TRANSPARENT);

		options.setAnnotations(box1, box2, box3, box4);
		
		chart.getPlugins().add(Annotation.get());

		chart.getOptions().getPlugins().setOptions(Annotation.ID, options);
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
