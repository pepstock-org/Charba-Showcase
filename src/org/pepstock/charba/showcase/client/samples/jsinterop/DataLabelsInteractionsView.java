package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.ext.datalabels.Align;
import org.pepstock.charba.client.ext.datalabels.AlignCallback;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.BorderColorCallback;
import org.pepstock.charba.client.ext.datalabels.ColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.FormatterCallback;
import org.pepstock.charba.client.ext.datalabels.TextAlign;
import org.pepstock.charba.client.ext.datalabels.Weight;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsInteractionsView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsInteractionsView> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsInteractionsView() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		Chart.helpers.merge(Chart.defaults.global, {
//			aspectRatio: 4/3,
//            tooltips: false,
//			layout: {
//				padding: {
//					bottom: 25,
//					right: 50,
//					left: 25,
//					top: 50
//				}
//			},
//			elements: {
//				line: {
//					fill: false
//				}
//			},
//			plugins: {
//				legend: false,
//				title: false
//			}
//		});
//		
//		hover: {
//			mode: 'index',
//			intersect: false
//		},

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(50);
		chart.getOptions().getLayout().getPadding().setRight(50);
		chart.getOptions().getLayout().getPadding().setBottom(25);
		chart.getOptions().getLayout().getPadding().setLeft(25);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getHover().setMode(InteractionMode.index);
		chart.getOptions().getHover().setIntersect(false);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(new AlignCallback() {
			
			@Override
			public Align align(AbstractChart<?, ?> chart, Context context) {
				return context.isActive() ? Align.start : Align.center;
			}
		});
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");
		
		IsColor color3 = Colors.ALL[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months, false));

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAlign(new AlignCallback() {
			
			@Override
			public Align align(AbstractChart<?, ?> chart, Context context) {
				return context.isActive() ? Align.end : Align.center;
			}
		});
		dataset3.setOptions(DataLabelsPlugin.ID, option3);

		
		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
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
		option.setBackgroundColor(new BackgroundColorCallback<String>() {

			@Override
			public String backgroundColor(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.isActive() ? ds.getBackgroundColorAsString() : HtmlColor.White.toRGBA();
			}
		});
		option.setBorderColor(new BorderColorCallback<String>() {
			
			@Override
			public String borderColor(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString();
			}
		});
		option.setColor(new ColorCallback<String>() {
			
			@Override
			public String color(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.isActive() ? HtmlColor.White.toRGBA() : ds.getBackgroundColorAsString();
			}
		});
		option.setFormatter(new FormatterCallback() {
			
			@Override
			public String format(AbstractChart<?, ?> chart, double value, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double myValue = Math.round(value * 100) /100;
				return context.isActive() ? ds.getLabel() + "\n" + myValue + "%" : Math.round(myValue)+ "";
			}
		});
		option.setBorderWidth(1);
		option.setOffset(8);
		option.setTextAlign(TextAlign.center);
		option.getFont().setWeight(Weight.bold);
		
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
}
