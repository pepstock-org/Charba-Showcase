package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.DisplayCallback;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.enums.DefaultPluginId;
import org.pepstock.charba.client.enums.Display;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.DoughnutChartWidget;
import org.pepstock.charba.client.impl.callbacks.Percentage;
import org.pepstock.charba.client.items.DataItem;
import org.pepstock.charba.client.items.Undefined;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsDoughnutCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsDoughnutCase> {
	}

	@UiField
	DoughnutChartWidget chart;

	final NumberFormat percentageFormatter = NumberFormat.getPercentFormat();

	public DataLabelsDoughnutCase() {
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

		DoughnutDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(getSequenceColors(months, 1));
		dataset1.setData(getRandomDigits(months, false));

		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAnchor(Anchor.END);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		DoughnutDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 1");
		dataset2.setBackgroundColor(getSequenceColors(months, 1));
		dataset2.setData(getRandomDigits(months, false));

		DataLabelsOptions option2 = new DataLabelsOptions();
		option2.setAnchor(Anchor.CENTER);
		option2.setBackgroundColor(Undefined.STRING);
		option2.setBorderWidth(0);
		dataset2.setOptions(DataLabelsPlugin.ID, option2);

		DoughnutDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 1");
		dataset3.setBackgroundColor(getSequenceColors(months, 1));
		dataset3.setData(getRandomDigits(months, false));

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAnchor(Anchor.START);
		dataset3.setOptions(DataLabelsPlugin.ID, option3);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public String invoke(DataLabelsContext context) {
				DoughnutDataset ds = (DoughnutDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(context.getDataIndex());
			}

		});
		option.setDisplay(new DisplayCallback() {

			@Override
			public Display invoke(DataLabelsContext context) {
				Dataset ds = chart.getData().getDatasets().get(context.getDatasetIndex());
				int count = ds.getData().size();
				double value = ds.getData().get(context.getDataIndex());
				return value > count * 1.5D ? Display.TRUE : Display.FALSE;
			}
		});
		option.setBorderColor(HtmlColor.WHITE);
		option.setBorderRadius(25);
		option.setBorderWidth(2);
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);
		option.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(DataLabelsContext context, DataItem dataItem) {
				double percentage = Percentage.compute(chart, dataItem.getValue(), context, false);
				return percentageFormatter.format(percentage);
			}

		});

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
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets) {
				DoughnutDataset pds = (DoughnutDataset) ds;
				pds.setBackgroundColor(getSequenceColors(months, 1));
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
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
