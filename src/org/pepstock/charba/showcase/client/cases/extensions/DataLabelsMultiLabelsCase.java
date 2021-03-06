package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.LabelItem;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.callbacks.OpacityCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.enums.DefaultPluginId;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.PieChartWidget;
import org.pepstock.charba.client.items.DataItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsMultiLabelsCase extends BaseComposite {

	private static final int DATASET_NUMBER = 4;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsMultiLabelsCase> {
	}

	@UiField
	PieChartWidget chart;

	public DataLabelsMultiLabelsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);

		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.TITLE, false);

		PieDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(getSequenceColors(DATASET_NUMBER, 1));
		dataset1.setData(getRandomDigits(DATASET_NUMBER, false));

		DataLabelsOptions option1 = new DataLabelsOptions();

		LabelItem index = option1.getLabels().createLabel("index");
		index.setAlign(Align.END);
		index.setAnchor(Anchor.END);
		index.setDisplay(true);
		index.getFont().setSize(18);
		index.setColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public Object invoke(DataLabelsContext context) {
				PieDataset ds = (PieDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(context.getDataIndex());
			}
		});
		index.setOffset(8);
		index.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				return context.isActive() ? "index" : "#" + (context.getDataIndex() + 1);
			}
		});

		index.setOpacity(new OpacityCallback() {

			@Override
			public Double invoke(DataLabelsContext context) {
				return context.isActive() ? 1D : 0.5D;
			}
		});

		LabelItem name = option1.getLabels().createLabel("name");
		name.setAlign(Align.TOP);
		name.getFont().setSize(18);
		name.setDisplay(true);
		name.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				return context.isActive() ? "name" : chart.getData().getLabels().getString(context.getDataIndex());
			}
		});

		LabelItem value = option1.getLabels().createLabel("value");
		value.setAlign(Align.BOTTOM);
		value.setBackgroundColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public Object invoke(DataLabelsContext context) {
				PieDataset ds = (PieDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getDataIndex());
				IsColor color = ds.getBackgroundColor().get(context.getDataIndex());
				return value > 50 ? HtmlColor.WHITE : color;
			}
		});
		value.setBorderColor(HtmlColor.WHITE);
		value.setBorderWidth(2);
		value.setBorderRadius(4);
		value.setColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public Object invoke(DataLabelsContext context) {
				PieDataset ds = (PieDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				double value = ds.getData().get(context.getDataIndex());
				return value > 50 ? HtmlColor.BLACK : HtmlColor.WHITE;
			}
		});
		value.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				return context.isActive() ? "value" : String.valueOf(dataItem.getValue());
			}
		});
		value.getPadding().set(6);
		value.setDisplay(true);
		
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);
		option.setDisplay(false);
		option.setOffset(0);
		option.getPadding().set(0);

		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(DATASET_NUMBER, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
