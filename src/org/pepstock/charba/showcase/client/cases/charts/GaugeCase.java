package org.pepstock.charba.showcase.client.cases.charts;

import java.util.ArrayList;
import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.MeterContentCallback;
import org.pepstock.charba.client.callbacks.MeterFormatCallback;
import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.enums.FontStyle;
import org.pepstock.charba.client.gwt.widgets.GaugeChartWidget;
import org.pepstock.charba.client.impl.charts.DefaultThreshold;
import org.pepstock.charba.client.impl.charts.GaugeDataset;
import org.pepstock.charba.client.impl.charts.MeterContext;
import org.pepstock.charba.client.items.FontItem;
import org.pepstock.charba.client.utils.Utilities;
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
	GaugeChartWidget chartPercent;
	@UiField
	GaugeChartWidget chartValue;
	@UiField
	GaugeChartWidget chartValueColor;
	@UiField
	GaugeChartWidget chartValueReverse;

	private final List<GaugeDataset> datasets = new ArrayList<GaugeDataset>();

	public GaugeCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chartPercent.getOptions().getTitle().setDisplay(true);
		chartPercent.getOptions().getTitle().setText("GAUGE chart to represent percentage value");
		
		GaugeDataset dataset1 = getDataset(chartPercent, "Percent", 100D);
		dataset1.getValueLabel().setPercentage(true);
		dataset1.getValueLabel().setAnimated(true);
		chartPercent.getData().setDatasets(dataset1);

		chartValue.getOptions().getTitle().setDisplay(true);
		chartValue.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label");

		GaugeDataset dataset2 = getDataset(chartValue, "Memory", 2048D);
		dataset2.getDescriptionLabel().setDisplay(true);
		dataset2.getDescriptionLabel().setContent(new MeterContentCallback() {
			
			private String label = null;
			
			@Override
			public String invoke(MeterContext context) {
				if (label == null) {
					label = context.getDatasetLabel() + " utilization";
				}
				int len = (int)Math.ceil(label.length() * context.getEasing());
				return label.substring(0, len);
			}
		});
		dataset2.getValueLabel().setAnimated(true);
		dataset2.getValueLabel().setAutoFontSize(false);
		FontItem item = new FontItem(chartValue.getOptions().getFont());
		item.setSize(32);
		dataset2.getValueLabel().setFont(item);
		
		dataset2.getValueLabel().setColor(new ColorCallback<MeterContext>() {

			@Override
			public Object invoke(MeterContext context) {
				GaugeDataset dataset = (GaugeDataset)chartValue.getData().getDatasets().get(0);
				return dataset.getCurrent().getColor();
			}
			
		});
		dataset2.getValueLabel().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				double value = context.getValue() * context.getEasing();
				if (value >= 1024) {
					double gb = value / 1024;
					return Utilities.applyPrecision(gb, 1)+ " GB";
				}
				return Utilities.applyPrecision(value, 0)+ " MB";
			}
		});
		chartValue.getOptions().getFont().setStyle(FontStyle.ITALIC);
		chartValue.getData().setDatasets(dataset2);

		chartValueColor.getOptions().getTitle().setDisplay(true);
		chartValueColor.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label", "changing the color of label");
		
		GaugeDataset dataset3 = getDataset(chartValueColor, "Storage", 200D);
		dataset3.getDescriptionLabel().setDisplay(true);
		dataset3.getValueLabel().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue(), 0)+ " GB";
			}
		});
		dataset3.getValueLabel().setColor(ColorBuilder.build(90, 173, 255));
		chartValueColor.getData().setDatasets(dataset3);

		chartValueReverse.getOptions().getTitle().setDisplay(true);
		chartValueReverse.getOptions().getTitle().setText("GAUGE chart with thresholds on reverse mode");

		GaugeDataset dataset4 = getDataset(chartValueReverse, "Reverse", 400D);
		dataset4.getValueLabel().setPrecision(0);
		dataset4.setThresholds(DefaultThreshold.NORMAL.getThreshold().setValue(Double.MAX_VALUE), DefaultThreshold.WARNING.getThreshold().setValue(100), DefaultThreshold.CRITICAL.getThreshold().setValue(40));
		dataset4.setPercentageThreshold(false);
		chartValueReverse.getData().setDatasets(dataset4);
	}

	private GaugeDataset getDataset(GaugeChartWidget chart, String label, double max) {
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
