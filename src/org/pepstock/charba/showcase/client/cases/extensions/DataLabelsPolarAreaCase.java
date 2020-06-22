package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.BackgroundColorCallback;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DataLabelsPolarAreaCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsPolarAreaCase> {
	}

	@UiField
	PolarAreaChartWidget chart;

	public DataLabelsPolarAreaCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().set(16);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getElements().getPoint().setHoverRadius(7);
		chart.getOptions().getElements().getPoint().setRadius(7);

		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);

		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 0.2D));
		dataset.setData(getRandomDigits(months, false));

		RadialAxis axis = new RadialAxis(chart);
		axis.setBeginAtZero(true);
		axis.setReverse(false);
		chart.getOptions().getScales().setAxes(axis);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setBackgroundColor(new BackgroundColorCallback() {

			@Override
			public String invoke(IsChart chart, ScriptableContext context) {
				PolarAreaDataset ds = (PolarAreaDataset) chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColor().get(context.getIndex()).alpha(1D).toRGBA();
			}
		});
		option.setBorderColor(HtmlColor.WHITE);
		option.setBorderRadius(25);
		option.setBorderWidth(2);
		option.setColor(HtmlColor.WHITE);
		option.getFont().setWeight(Weight.BOLD);

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
				PolarAreaDataset pds = (PolarAreaDataset) ds;
				pds.setBackgroundColor(getSequenceColors(months, 0.2D));
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
