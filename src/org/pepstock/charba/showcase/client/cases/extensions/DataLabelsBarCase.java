package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.DisplayCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.datalabels.enums.Weight;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.client.enums.Display;
import org.pepstock.charba.client.impl.callbacks.Percentage;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsBarCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsBarCase> {
	}

	@UiField
	BarChart chart;
	
	public DataLabelsBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getElements().getPoint().setHoverRadius(7);
		chart.getOptions().getElements().getPoint().setRadius(5);
		
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);	
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = GoogleChartColor.values()[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(Align.END);
		option1.setAnchor(Anchor.START);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));

		DataLabelsOptions option2 = new DataLabelsOptions();
		option2.setAlign(Align.CENTER);
		option2.setAnchor(Anchor.CENTER);
		dataset2.setOptions(DataLabelsPlugin.ID, option2);

		BarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");
		
		IsColor color3 = GoogleChartColor.values()[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months, false));

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAlign(Align.START);
		option3.setAnchor(Anchor.END);

		dataset3.setOptions(DataLabelsPlugin.ID, option3);
		
		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.setStacked(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.setStacked(true);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setDisplay(new DisplayCallback() {

			@Override
			public Display invoke(IsChart chart, ScriptableContext context) {
				Dataset ds = chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getIndex());
				return value > 15D ? Display.TRUE : Display.FALSE;
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);
		option.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(IsChart chart, double value, ScriptableContext context) {
				double percentage = Percentage.compute(chart, value, context, true);
				return Math.round(percentage*100)+"%";
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
		addData(chart, false);
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
