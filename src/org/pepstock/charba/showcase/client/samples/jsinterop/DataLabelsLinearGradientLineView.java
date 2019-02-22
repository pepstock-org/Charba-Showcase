package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientOrientation;
import org.pepstock.charba.client.colors.GradientScope;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.Context;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.datalabels.callbacks.BorderColorCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.datalabels.enums.Weight;
import org.pepstock.charba.client.enums.Fill;

import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsLinearGradientLineView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsLinearGradientLineView> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsLinearGradientLineView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(32);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		Gradient gradient1  = new Gradient(GradientType.linear, GradientOrientation.leftRight, GradientScope.chart);

		gradient1.addColorStop(0, HtmlColor.Orange);
		gradient1.addColorStop(1, HtmlColor.Purple);
		
		dataset1.setBackgroundColor(gradient1);
		
		dataset1.setBorderColor(gradient1);
		dataset1.setPointBackgroundColor(gradient1);
		dataset1.setPointHoverBackgroundColor(gradient1);
		dataset1.setPointHoverBorderColor(gradient1);
		
		dataset1.setPointRadius(5);
		
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill(Fill.nofill);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setAlign(Align.end);
		option.setAnchor(Anchor.end);
		option.setBorderRadius(4);
		option.setBorderWidth(4);
		option.setColor(HtmlColor.White);
		option.getFont().setWeight(Weight.bold);
		option.getFont().setSize(11);
		option.setOffset(4);
		option.getPadding().set(8);
		
		option.setBackgroundColor(new BackgroundColorCallback<CanvasGradient>() {
			
			CanvasGradient gr0 = null;

			@Override
			public CanvasGradient backgroundColor(AbstractChart<?, ?> chart, Context context) {
				if (gr0 == null) {
					gr0 = chart.getCanvas().getContext2d().createLinearGradient(-25, -25, 25, 25);
					gr0.addColorStop(0, "#fff000");
					gr0.addColorStop(1, "#000fff");
				}
				return gr0;
			}
		});
		option.setBorderColor(new BorderColorCallback<CanvasGradient>() {
			
			CanvasGradient gr1 = null;
			
			@Override
			public CanvasGradient borderColor(AbstractChart<?, ?> chart, Context context) {
				if (gr1 == null) {
					gr1 = chart.getCanvas().getContext2d().createLinearGradient(-25, -25, 25, 25);
					gr1.addColorStop(1, "#fff000");
					gr1.addColorStop(0, "#000fff");
				}
				return gr1;
			}
		});
		
		option.setFormatter(new FormatterCallback() {
			
			@Override
			public String format(AbstractChart<?, ?> chart, double value, Context context) {
				return value < 20 ? "Poor\n"+value : value < 50 ? "Good\n"+value : "Great\n"+value;
			}
		});
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
