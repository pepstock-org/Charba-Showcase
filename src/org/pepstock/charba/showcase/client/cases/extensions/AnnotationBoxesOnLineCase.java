package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.annotation.AnnotationContext;
import org.pepstock.charba.client.annotation.AnnotationOptions;
import org.pepstock.charba.client.annotation.AnnotationPlugin;
import org.pepstock.charba.client.annotation.BoxAnnotation;
import org.pepstock.charba.client.annotation.enums.DrawTime;
import org.pepstock.charba.client.annotation.enums.LabelPosition;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.GwtMaterialColor;
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

		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.setMax(120);

		chart.getOptions().getScales().setAxes(axis);

		AnnotationOptions options = new AnnotationOptions();
		
		BoxAnnotation box1 = new BoxAnnotation();
		box1.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box1.setXScaleID(DefaultScaleId.X);
		box1.setYScaleID(DefaultScaleId.Y);
		box1.setXMin("January");
		box1.setXMax("April");
		box1.setBackgroundColor(GwtMaterialColor.YELLOW_LIGHTEN_3.alpha(0.3D));
		box1.setBorderWidth(0);
		box1.setBorderColor(HtmlColor.TRANSPARENT);
		box1.getLabel().setDisplay(true);
		box1.getLabel().setContent("First quarter");
		box1.getLabel().getFont().setSize(24);
		box1.getLabel().getPosition().setY(LabelPosition.START);

		BoxAnnotation box2 = new BoxAnnotation();
		box2.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box2.setXScaleID(DefaultScaleId.X.value());
		box2.setYScaleID(DefaultScaleId.Y.value());
		box2.setXMin("April");
		box2.setXMax("July");
		box2.setBackgroundColor(new ColorCallback<AnnotationContext>() {

			@Override
			public Object invoke(AnnotationContext context) {
				return GwtMaterialColor.BROWN_LIGHTEN_3.alpha(0.3D);
			}
		});
		box2.setBorderWidth(0);
		box2.setBorderColor(HtmlColor.TRANSPARENT);
		box2.getLabel().setDisplay(true);
		box2.getLabel().setContent("Second quarter");
		box2.getLabel().getFont().setSize(24);
		box2.getLabel().getPosition().setY(LabelPosition.START);

		BoxAnnotation box3 = new BoxAnnotation();
		box3.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box3.setXScaleID(DefaultScaleId.X.value());
		box3.setYScaleID(DefaultScaleId.Y.value());
		box3.setXMin("July");
		box3.setXMax("October");
		box3.setBackgroundColor(GwtMaterialColor.GREEN_LIGHTEN_3.alpha(0.3D));
		box3.setBorderWidth(0);
		box3.setBorderColor(HtmlColor.TRANSPARENT);
		box3.getLabel().setDisplay(true);
		box3.getLabel().setContent("Third quarter");
		box3.getLabel().getFont().setSize(24);
		box3.getLabel().getPosition().setY(LabelPosition.START);

		BoxAnnotation box4 = new BoxAnnotation();
		box4.setDrawTime(DrawTime.BEFORE_DATASETS_DRAW);
		box4.setXScaleID(DefaultScaleId.X.value());
		box4.setYScaleID(DefaultScaleId.Y.value());
		box4.setXMin("October");
		box4.setBackgroundColor(GwtMaterialColor.INDIGO_LIGHTEN_3.alpha(0.3D));
		box4.setBorderWidth(0);
		box4.setBorderColor(HtmlColor.TRANSPARENT);
		box4.getLabel().setDisplay(true);
		box4.getLabel().setContent("Fourth quarter");
		box4.getLabel().getFont().setSize(24);
		box4.getLabel().getPosition().setY(LabelPosition.START);

		options.setAnnotations(box1, box2, box3, box4);

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
