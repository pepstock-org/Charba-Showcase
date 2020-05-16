package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.FloatingData;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class FloatingDataOnBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FloatingDataOnBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public FloatingDataOnBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Floating data on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		double[] values = getRandomDigits(months);
		double[] gaps = getRandomDigits(months, false);

		double[][] dataToSet = new double[months][2];
		for (int i=0; i<months; i++) {
			dataToSet[i] = new double[] {values[i], values[i] + gaps[i]};
		}
		dataset1.setFloatingData(dataToSet);

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);

		double[] values1 = getRandomDigits(months);
		double[] gaps1 = getRandomDigits(months, false);

		List<FloatingData> data1 = dataset2.getFloatingData(true);
		for (int i=0; i<months; i++) {
			data1.add(new FloatingData(values1[i], values1[i] + gaps1[i]));
		}
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			double[] values = getRandomDigits(months);
			double[] gaps = getRandomDigits(months, false);
			BarDataset barDataset = (BarDataset)dataset;
			List<FloatingData> data = barDataset.getFloatingData();
			for (int i=0; i<months; i++) {
				FloatingData fData = data.get(i);
				fData.setValues(values[i], values[i] + gaps[i]);		
			}
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
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(1);
		
		double[] values = getRandomDigits(months);
		double[] gaps = getRandomDigits(months, false);

		List<FloatingData> data = dataset.getFloatingData(true);
		for (int i=0; i<months; i++) {
			data.add(new FloatingData(values[i], values[i] + gaps[i]));
		}

		datasets.add(dataset);

		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
