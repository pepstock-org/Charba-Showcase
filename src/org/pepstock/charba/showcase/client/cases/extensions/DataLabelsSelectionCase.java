package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.HashMap;
import java.util.Map;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.BorderColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.ColorCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Weight;
import org.pepstock.charba.client.datalabels.events.ClickEventHandler;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsSelectionCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsSelectionCase> {
	}

	@UiField
	LineChart chart;
	
	final Map<Integer, SelectionItem> items = new HashMap<>(); 
	
	public DataLabelsSelectionCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		chart.getOptions().getElements().getLine().setFill(false);
		
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);	

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(Align.START);
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
		option3.setAlign(Align.END);
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
		option.setBackgroundColor(new BackgroundColorCallback() {

			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				int key = context.getDatasetIndex() * 1000 + context.getIndex();
				return items.containsKey(key) ? ds.getBackgroundColor() : HtmlColor.WHITE;
			}
		});
		option.setBorderColor(new BorderColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColor();
			}
		});
		option.setColor(new ColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, ScriptableContext context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				int key = context.getDatasetIndex() * 1000 + context.getIndex();
				return !items.containsKey(key) ? ds.getBackgroundColor() : HtmlColor.WHITE;
			}
		});
		option.setBorderRadius(16);
		option.setBorderWidth(1);
		option.setOffset(8);
		option.getPadding().set(4);
		option.getFont().setWeight(Weight.BOLD);
		
		option.getListeners().setClickEventHandler(new ClickEventHandler() {
			
			@Override
			public boolean onClick(IsChart chart, ScriptableContext context) {
				int key = context.getDatasetIndex() * 1000 + context.getIndex();
				if (items.containsKey(key)) {
					items.remove(key);
				} else {
					LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
					items.put(key, new SelectionItem(context.getDatasetIndex(), context.getIndex(),ds.getData().get(context.getIndex())));
				}
				if (!items.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					for (SelectionItem item : items.values()) {
						sb.append("<b>").append(item.toString()).append("</b><br>");
					}
					new Toast("Dataset Selected!", sb.toString()).show();
				}
				return true;
			}
		});
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
		
	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		items.clear();
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart, false);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		items.clear();
		removeData(chart);
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	static final class SelectionItem {
		
		private final int datasetIndex;
		
		private final int index;
		
		private final double value;

		SelectionItem(int datasetIndex, int index, double value) {
			super();
			this.datasetIndex = datasetIndex;
			this.index = index;
			this.value = value;
		}

		public int getDatasetIndex() {
			return datasetIndex;
		}

		public int getIndex() {
			return index;
		}

		public double getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "SelectionItem [datasetIndex=" + datasetIndex + ", index=" + index + ", value=" + value + "]";
		}

	}
}
