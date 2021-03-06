package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.RotationCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.DataLabelsContext;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.callbacks.AnchorCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.enums.DefaultPluginId;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsMirrorCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsMirrorCase> {
	}

	@UiField
	BarChartWidget chart;

	public DataLabelsMirrorCase() {
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

		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPluginId.TITLE, false);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1);
		dataset1.setBorderColor(color1);
		double[] values = getRandomDigits(months);
		dataset1.setData(values);

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
		chart.getData().setDatasets(dataset1);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setAlign(new AlignCallback() {

			@Override
			public Align invoke(DataLabelsContext context) {
				BarDataset ds = (BarDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getDataIndex()) > 0 ? Align.END : Align.START;
			}
		});

		option.setAnchor(new AnchorCallback() {

			@Override
			public Anchor invoke(DataLabelsContext context) {
				BarDataset ds = (BarDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getDataIndex()) > 0 ? Anchor.END : Anchor.START;
			}
		});
		option.setRotation(new RotationCallback<DataLabelsContext>() {

			@Override
			public Double invoke(DataLabelsContext context) {
				BarDataset ds = (BarDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getData().get(context.getDataIndex()) > 0 ? 45D : 100D - 45D;
			}
		});

		option.setBackgroundColor(new ColorCallback<DataLabelsContext>() {

			@Override
			public String invoke(DataLabelsContext context) {
				BarDataset ds = (BarDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(0);
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.WHITE);

		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
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
