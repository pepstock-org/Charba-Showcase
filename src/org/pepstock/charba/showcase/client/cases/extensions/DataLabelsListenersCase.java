package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.dom.DOMBuilder;
import org.pepstock.charba.client.enums.DefaultPluginId;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.events.ChartEventContext;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.callbacks.DataLabelsPointerHandler;
import org.pepstock.charba.client.items.DatasetElement;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetReference;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.LogView;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsListenersCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsListenersCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	LogView mylog;

	public DataLabelsListenersCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);

		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.TITLE, false);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

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

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.FALSE);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");

		IsColor color3 = GoogleChartColor.values()[2];

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
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");
		axis2.setStacked(true);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public String invoke(DataLabelsContext context) {
				if (context.isActive()) {
					return null;
				}
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString();
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);

		MyListener listener = new MyListener();
		option.setListenersHandler(listener);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
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

   class MyListener extends DataLabelsPointerHandler {

		MyListener() {
			super();
		}

		@Override
		public boolean onLeave(DataLabelsContext context, ChartEventContext event) {
			super.onLeave(context, event);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			mylog.addLogEvent("> LEAVE: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			return true;
		}

		@Override
		public boolean onEnter(DataLabelsContext context, ChartEventContext event) {
			super.onEnter(context, event);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			mylog.addLogEvent("> ENTER: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			return true;
		}

		@Override
		public boolean onClick(DataLabelsContext context, ChartEventContext event) {
			super.onClick(context, event);
			LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
			Labels labels = chart.getData().getLabels();
			List<Dataset> datasets = chart.getData().getDatasets();
			DatasetItem item = chart.getDatasetItem(context.getDatasetIndex());
			DatasetElement element = item.getElements().get(context.getDataIndex());
			if (datasets != null && !datasets.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset index: <b>").append(context.getDatasetIndex()).append("</b><br>");
				sb.append("Dataset label: <b>").append(datasets.get(context.getDatasetIndex()).getLabel()).append("</b><br>");
				sb.append("Dataset data: <b>").append(datasets.get(context.getDatasetIndex()).getData().get(context.getDataIndex())).append("</b><br>");
				sb.append("Index: <b>").append(context.getDataIndex()).append("</b><br>");
				sb.append("Value: <b>").append(labels.getStrings(context.getDataIndex()).get(0)).append("</b><br>");
				new Toast("Dataset Selected!", sb.toString()).show();
			}
			mylog.addLogEvent("> CLICK: Dataset index: " + context.getDatasetIndex() + ", data index: " + context.getDataIndex() + ", value(" + ds.getData().get(context.getDataIndex()) + ")");
			DatasetReference referenceItem = new DatasetReference(context, element);
			chart.fireEvent(new DatasetSelectionEvent(DOMBuilder.get().createChangeEvent(), chart, referenceItem));
			return true;
		}
	}
}
