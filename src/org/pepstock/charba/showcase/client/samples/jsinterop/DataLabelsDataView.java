package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.Context;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.callbacks.ColorCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Weight;
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
public class DataLabelsDataView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsDataView> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsDataView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getLayout().getPadding().setRight(24);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		
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
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new Color(255, 255, 255).alpha(0.7));
		option.setBorderColor(new Color(128, 128, 128).alpha(0.7));
		option.setBorderRadius(4);
		option.setBorderWidth(1);
		option.setOffset(8);
		option.getFont().setFontSize(11);
		option.getFont().setWeight(Weight.bold);
		option.setAlign(new AlignCallback() {
			
			@Override
			public Align align(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double curr = ds.getData().get(context.getIndex());
				double prev = context.getIndex() > 0 ? ds.getData().get(context.getIndex()-1) : 0;
				double next = context.getIndex() < ds.getData().size() ? ds.getData().get(context.getIndex()+1) : 0;
				return prev < curr && next < curr ? Align.end :	prev > curr && next > curr ? Align.start :	Align.center;
			}
		});
		option.setColor(new ColorCallback<IsColor>() {
			
			@Override
			public IsColor color(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getIndex());
				double diff = context.getIndex() > 0 ? value - ds.getData().get(context.getIndex()-1) : 0;
				return diff < 0 ? HtmlColor.Red : diff > 0 ? HtmlColor.Green : HtmlColor.Gray ;
			}
		});
		option.setFormatter(new FormatterCallback() {
			
			@Override
			public String format(AbstractChart<?, ?> chart, double value, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				double diff = context.getIndex() > 0 ? value - ds.getData().get(context.getIndex()-1) : 0;
				StringBuffer sb = new StringBuffer();
				sb.append(diff < 0 ? '\u25B2' : diff > 0 ? '\u25BC' : '\u25C6');
				return sb.append(" ").append(Math.round(value)).toString();
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
}
