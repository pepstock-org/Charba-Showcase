package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.IsPoint;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.ChartHoverEvent;
import org.pepstock.charba.client.events.ChartHoverEventHandler;
import org.pepstock.charba.client.events.LegendClickEvent;
import org.pepstock.charba.client.events.LegendClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.ActiveDatasetElement;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ActiveElementsOnBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ActiveElementsOnBarCase> {
	}

	@UiField
	BarChartWidget chart;

	@UiField
	ListBox datasets;

	@UiField
	ListBox data;

	private final String[] labels;

	public ActiveElementsOnBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		labels = getLabels();

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Bar chart");
		chart.getOptions().getHover().setIntersect(false);

		chart.addHandler(new LegendClickEventHandler() {

			@Override
			public void onClick(LegendClickEvent event) {
				datasets.setSelectedIndex(0);
				data.setSelectedIndex(0);
				Defaults.get().invokeLegendOnClick(event);
			}
			
		}, LegendClickEvent.TYPE);
		
		chart.addHandler(new ChartHoverEventHandler() {
			
			@Override
			public void onHover(ChartHoverEvent event) {
				datasets.setSelectedIndex(0);
				data.setSelectedIndex(0);
			}
			
		}, ChartHoverEvent.TYPE);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 0");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setHoverBorderWidth(5);
		dataset1.setHoverBorderColor(color1.darker());
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 1");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setHoverBorderWidth(5);
		dataset2.setHoverBorderColor(color2.darker());
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(labels);
		chart.getData().setDatasets(dataset1, dataset2);

		datasets.addItem("none", String.valueOf("-1"));
		datasets.addItem(dataset1.getLabel(), String.valueOf("0"));
		datasets.addItem(dataset2.getLabel(), String.valueOf("1"));
		datasets.setVisibleItemCount(3);

		data.addItem("none", String.valueOf("-1"));
		for (int i = 0; i < labels.length; i++) {
			data.addItem(labels[i], String.valueOf(i));
		}
		data.setVisibleItemCount(labels.length + 1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
		datasets.setSelectedIndex(0);
		data.setSelectedIndex(0);
	}

	@UiHandler(value = { "datasets", "data" })
	protected void handleDatasetsList(ChangeEvent event) {
		int datasetValue = Integer.parseInt(datasets.getSelectedValue());
		int dataValue = Integer.parseInt(data.getSelectedValue());
		if (datasetValue >= 0) {
			if (dataValue >= 0) {
				ActiveDatasetElement active = new ActiveDatasetElement(datasetValue, dataValue);
				chart.setActiveElements(active);
				chart.setTooltipActiveElements((IsPoint)null, active);
			} else {
				List<ActiveDatasetElement> active = chart.getData().createActiveElementsByDatasetIndex(datasetValue);
				chart.setActiveElements(active);
				chart.setTooltipActiveElements(active);
			}
		} else if (dataValue >= 0) {
			List<ActiveDatasetElement> active = chart.getData().createActiveElementsByDataIndex(dataValue);
			chart.setActiveElements(active);
			chart.setTooltipActiveElements(active);
		} else {
			chart.resetActiveElements();
			chart.resetTooltipActiveElements();
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
