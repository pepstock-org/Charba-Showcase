package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.IsChart;
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
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.impl.callbacks.DataLabelsPointerHandler;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

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

public class DataLabelsListenersCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsListenersCase> {
	}

	@UiField
	LineChart chart;

	@UiField
	SimplePanel log;
	
	PreElement element = Document.get().createPreElement();

	public DataLabelsListenersCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		#logs > div {
//			border-bottom: 1px dashed #ddd;
//			padding: 0.5em 1em;
//		}

//		#logs > div:nth-child(n) { opacity: 0.2; }
//		#logs > div:nth-child(5) { opacity: 0.4; }
//		#logs > div:nth-child(4) { opacity: 0.6; }
//		#logs > div:nth-child(3) { opacity: 0.8; }
//		#logs > div:nth-child(2) { opacity: 1.0; }
//		#logs > div:nth-child(1) { opacity: 1.0; font-weight: bold; }
		
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
		
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);	
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill(Fill.FALSE);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(Align.START);
		option1.setAnchor(Anchor.START);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.FALSE);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");
		
		IsColor color3 = Colors.ALL[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months, false));
		dataset3.setFill(Fill.FALSE);

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAlign(Align.END);
		option3.setAnchor(Anchor.END);
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
			public String invoke(IsChart chart, ScriptableContext context) {
				if (context.isActive()) {
					return null;
				}
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString();
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);
		
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
		public boolean onLeave(IsChart chart, ScriptableContext context) {
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
		public boolean onEnter(IsChart chart, ScriptableContext context) {
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
		public boolean onClick(IsChart chart, ScriptableContext context) {
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
				sb.append("Value: <b>").append(labels.getStrings(item.getIndex()).get(0)).append("</b><br>");
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
