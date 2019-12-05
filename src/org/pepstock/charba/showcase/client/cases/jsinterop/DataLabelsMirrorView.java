package org.pepstock.charba.showcase.client.cases.jsinterop;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.RotationCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.callbacks.AnchorCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsMirrorView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsMirrorView> {
	}

	@UiField
	BarChart chart;
	
	public DataLabelsMirrorView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getLayout().getPadding().setRight(24);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getElements().getPoint().setHoverRadius(7);
		chart.getOptions().getElements().getPoint().setRadius(5);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1);
		dataset1.setBorderColor(color1);
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
		axis2.setStacked(true);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions option = new DataLabelsOptions();
		option.setAlign(new AlignCallback() {
			
			@Override
			public Align invoke(IsChart chart, ScriptableContext context) {
				BarDataset ds = (BarDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getIndex()) > 0 ? Align.END : Align.START;
			}
		});
		
		option.setAnchor(new AnchorCallback() {

			@Override
			public Anchor invoke(IsChart chart, ScriptableContext context) {
				BarDataset ds = (BarDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getIndex()) > 0 ? Anchor.END : Anchor.START;
			}
		});
		option.setRotation(new RotationCallback() {
			
			@Override
			public Double invoke(IsChart chart, ScriptableContext context) {
				BarDataset ds = (BarDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getIndex()) > 0 ? 45D : 100D - 45D;
			}
		});

		option.setBackgroundColor(new BackgroundColorCallback() {
			
			@Override
			public String invoke(IsChart chart, ScriptableContext context) {
				BarDataset ds = (BarDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(0);
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
		
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