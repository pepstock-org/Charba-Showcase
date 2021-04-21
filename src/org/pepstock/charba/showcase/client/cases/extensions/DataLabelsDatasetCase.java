package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.enums.DefaultPluginId;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsDatasetCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsDatasetCase> {
	}

	@UiField
	LineChartWidget chart;
	
	LineDataset dataset1;
	
	LineDataset dataset2;

	public DataLabelsDatasetCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getLayout().getPadding().setRight(24);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getElements().getLine().setBorderWidth(2);

		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.TITLE, false);


		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.7));
		dataset1.setPointBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill("+1");

		dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setPointBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		double[] values2 = getRandomDigits(months, false);
		dataset2.setData(values2);

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
		chart.getData().setDatasets(dataset1, dataset2);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public String invoke(DataLabelsContext context) {
				LineDataset ds = (LineDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBorderColorAsString();
			}

		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);
		option.setOffset(8);
		option.getFont().setSize(11);
		option.getFont().setWeight(Weight.BOLD);
		option.setAlign(new AlignCallback() {

			@Override
			public Align invoke(DataLabelsContext context) {
				return context.getDatasetIndex() == 0 ? Align.BOTTOM : Align.TOP;
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
