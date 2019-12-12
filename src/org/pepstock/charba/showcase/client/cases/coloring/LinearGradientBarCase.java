package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.LegendLabelsCallback;
import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientOrientation;
import org.pepstock.charba.client.colors.GradientScope;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.LegendLabelItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LinearGradientBarCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LinearGradientBarCase> {
	}

	@UiField
	BarChart chart;
	
	public LinearGradientBarCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getLegend().getLabels().setLabelsCallback(new LegendLabelsCallback() {
			
			@Override
			public List<LegendLabelItem> generateLegendLabels(IsChart chart, List<LegendLabelItem> defaultLabels) {
				org.pepstock.charba.client.utils.Window.getConsole().log("LEGEND CALLBACK "+defaultLabels.get(0).isFillStyleAsCanvasGradient());
				return defaultLabels;
			}
		});
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Applying a linear gradient on bar chart dataset");
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.NEAREST);
		chart.getOptions().getHover().setIntersect(true);
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		Gradient gradient1  = new Gradient(GradientType.LINEAR, GradientOrientation.BOTTOM_UP, GradientScope.CHART);

		gradient1.addColorStop(0, "#3a1c71");
		gradient1.addColorStop(0.5, "#d76d77");
		gradient1.addColorStop(1, "#ffaf7b");
		
		dataset1.setBackgroundColor(gradient1);
		dataset1.setBorderColor(gradient1);
		double[] values = getRandomDigits(months);
		dataset1.setData(values);
		
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
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
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
