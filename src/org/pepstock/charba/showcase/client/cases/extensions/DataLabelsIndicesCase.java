package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.BorderColorCallback;
import org.pepstock.charba.client.callbacks.BorderWidthCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.callbacks.ColorCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsIndicesCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsIndicesCase> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsIndicesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getLayout().getPadding().setRight(24);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);	

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = GoogleChartColor.values()[0];
		
		dataset1.setBackgroundColor(color1);
		dataset1.setBorderColor(color1);
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
		option.setAlign(new AlignCallback() {
			@Override
			public Align invoke(IsChart chart, ScriptableContext context) {
				return context.getIndex() % 2 == 0 ? Align.END : Align.CENTER;
			}
		});

		option.setBackgroundColor(new BackgroundColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? ds.getBorderColor() : new Color(255, 255, 255).alpha(0.8D);
			}
		});
		option.setBorderColor(new BorderColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? null : ds.getBorderColor();
			}
		});
		option.setColor(new ColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? HtmlColor.WHITE : ds.getBorderColor();
			}
		});
		option.setBorderWidth(new BorderWidthCallback() {
			
			@Override
			public Integer invoke(IsChart chart, ScriptableContext context) {
				return context.getIndex() % 2 == 0 ? 0 : 1;
			}
		});
		option.setFormatter(new FormatterCallback() {
			
			@Override
			public String invoke(IsChart chart, double value, ScriptableContext context) {
				return context.getIndex()+": "+Math.round(value)+"'";
			}
		});
		option.setOffset(8);
		
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
