package org.pepstock.charba.showcase.client.cases.charts;

import java.util.ArrayList;
import java.util.List;

import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.enums.FontStyle;
import org.pepstock.charba.client.impl.charts.GaugeChart;
import org.pepstock.charba.client.impl.charts.GaugeDataset;
import org.pepstock.charba.client.impl.charts.GaugeThreshold;
import org.pepstock.charba.client.impl.charts.MeterDisplay;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GaugeCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GaugeCase> {
	}

	@UiField
	GaugeChart chartPercent;
	@UiField
	GaugeChart chartValue;
	@UiField
	GaugeChart chartValueColor;
	@UiField
	GaugeChart chartValueReverse;

	private final List<GaugeDataset> datasets = new ArrayList<GaugeDataset>();

	public GaugeCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chartPercent.getOptions().getTitle().setDisplay(true);
		chartPercent.getOptions().getTitle().setText("GAUGE chart to represent percentage value");
		chartPercent.getOptions().setDisplay(MeterDisplay.PERCENTAGE);
		chartPercent.getOptions().setFormat("###.##%");
		chartPercent.getOptions().setAnimatedDisplay(true);
		chartPercent.getData().setDatasets(getDataset(chartPercent, "Percent", 100D));

		chartValue.getOptions().getTitle().setDisplay(true);
		chartValue.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label");
		chartValue.getOptions().setDisplay(MeterDisplay.VALUE_AND_LABEL);
		chartValue.getOptions().setFormat("#### MB");
		chartValue.getOptions().setFontStyle(FontStyle.ITALIC);
		chartValue.getData().setDatasets(getDataset(chartValue, "Memory", 2048D));

		chartValueColor.getOptions().getTitle().setDisplay(true);
		chartValueColor.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label", "changing the color of label");
		chartValueColor.getOptions().setDisplay(MeterDisplay.VALUE_AND_LABEL);
		chartValueColor.getOptions().setFormat("#### GB");
		chartValueColor.getOptions().setDisplayFontColor(ColorBuilder.build(90, 173, 255));
		chartValueColor.getData().setDatasets(getDataset(chartValueColor, "Storage", 200D));

		chartValueReverse.getOptions().getTitle().setDisplay(true);
		chartValueReverse.getOptions().getTitle().setText("GAUGE chart with thresholds on reverse mode");
		chartValueReverse.getOptions().setDisplay(MeterDisplay.VALUE);
		chartValueReverse.getOptions().setFormat("###");

		GaugeDataset ds = getDataset(chartValueReverse, "Reverse", 400D);

		ds.setThresholds(GaugeThreshold.NORMAL.getThreshold().setValue(Double.MAX_VALUE), GaugeThreshold.WARNING.getThreshold().setValue(100), GaugeThreshold.CRITICAL.getThreshold().setValue(40));
		ds.setPercentageThreshold(false);
		chartValueReverse.getData().setDatasets(ds);
	}

	private GaugeDataset getDataset(GaugeChart chart, String label, double max) {
		chart.getOptions().setResponsive(true);
		GaugeDataset dataset = chart.newDataset(max);
		dataset.setLabel(label);
		dataset.setValue(Math.random() * max);
		datasets.add(dataset);
		return dataset;
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (GaugeDataset dataset : datasets) {
			dataset.setValue(Math.random() * dataset.getMax());
		}
		chartPercent.update();
		chartValue.update();
		chartValueColor.update();
		chartValueReverse.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
