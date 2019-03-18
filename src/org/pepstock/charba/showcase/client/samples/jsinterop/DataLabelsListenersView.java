package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.datalabels.enums.Weight;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.impl.callbacks.DataLabelsPointerHandler;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsListenersView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsListenersView> {
	}

	@UiField
	LineChart chart;

	@UiField
	SimplePanel log;
	
	PreElement element = Document.get().createPreElement();

	public DataLabelsListenersView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		log.getElement().appendChild(element);
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill(Fill.nofill);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(Align.start);
		option1.setAnchor(Anchor.start);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.nofill);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");
		
		IsColor color3 = Colors.ALL[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months, false));
		dataset3.setFill(Fill.nofill);

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAlign(Align.end);
		option3.setAnchor(Anchor.end);
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
			public String invoke(AbstractChart<?, ?> chart, ScriptableContext context) {
				if (context.isActive()) {
					return null;
				}
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString();
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.White);
		option.getFont().setWeight(Weight.bold);
		
		MyListener listener = new MyListener(element);
		option.setListenersHandler(listener);

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
	
	static class MyListener extends DataLabelsPointerHandler{
		
		final PreElement element;

		MyListener(PreElement element) {
			super();
			this.element = element;
		}

		@Override
		public boolean onLeave(AbstractChart<?, ?> chart, ScriptableContext context) {
			super.onLeave(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> LEAVE: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			return true;
		}

		@Override
		public boolean onEnter(AbstractChart<?, ?> chart, ScriptableContext context) {
			super.onEnter(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> ENTER: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			return true;
		}

		@Override
		public boolean onClick(AbstractChart<?, ?> chart, ScriptableContext context) {
			super.onClick(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			Labels labels = chart.getData().getLabels();
			List<Dataset> datasets = chart.getData().getDatasets();
			DatasetMetaItem meta = chart.getDatasetMeta(context.getDatasetIndex());
			DatasetItem item = meta.getDatasets().get(context.getIndex());
			if (datasets != null && !datasets.isEmpty()){
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset index: <b>").append(item.getDatasetIndex()).append("</b><br>");
				sb.append("Dataset label: <b>").append(datasets.get(item.getDatasetIndex()).getLabel()).append("</b><br>");
				sb.append("Dataset data: <b>").append(datasets.get(item.getDatasetIndex()).getData().get(item.getIndex())).append("</b><br>");
				sb.append("Index: <b>").append(item.getIndex()).append("</b><br>");
				sb.append("Value: <b>").append(labels.getStrings(item.getIndex())[0]).append("</b><br>");
				new Toast("Dataset Selected!", sb.toString()).show();
			}
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> CLICK: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			chart.fireEvent(new DatasetSelectionEvent(Document.get().createChangeEvent(), item));
			return true;
		}
	}
}
