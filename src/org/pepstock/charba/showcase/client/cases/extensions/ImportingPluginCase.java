package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.plugins.AbstractPluginOptions;
import org.pepstock.charba.client.resources.InjectableTextResource;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.MyResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ImportingPluginCase extends BaseComposite {
	
	static {
		Injector.ensureInjected(new InjectableTextResource(MyResources.INSTANCE.chartJsStacked100Source()));
	}

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ImportingPluginCase> {
	}

	@UiField
	BarChartWidget chart;

	private static final String STACKED100_PLUGIN = "stacked100";

	public ImportingPluginCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Importing Stacked100 plugin on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];
		dataset1.setBackgroundColor(color1.alpha(0.2D));
		dataset1.setData(getRandomDigits(months, false));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		IsColor color2 = GoogleChartColor.values()[1];
		dataset2.setBackgroundColor(color2.alpha(0.2D));
		dataset2.setData(getRandomDigits(months, false));

		BarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 3");
		IsColor color3 = GoogleChartColor.values()[2];
		dataset3.setBackgroundColor(color3.alpha(0.2D));
		dataset3.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

		Stacked100Options options = new Stacked100Options();
		options.setEnable(true);
		options.store(chart);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();

		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset " + (datasets.size() + 1));

		IsColor color = GoogleChartColor.values()[datasets.size()];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setData(getRandomDigits(months, false));
		datasets.add(dataset);
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
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

	private static class Stacked100Options extends AbstractPluginOptions {

		private Key enableKey = Key.create("enable");

		Stacked100Options() {
			super(STACKED100_PLUGIN, null);
		}

		void setEnable(boolean enable) {
			setValue(enableKey, enable);
		}

	}

}
