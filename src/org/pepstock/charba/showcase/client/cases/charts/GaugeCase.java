package org.pepstock.charba.showcase.client.cases.charts;

import java.util.ArrayList;
import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.MeterFormatCallback;
import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.enums.FontStyle;
import org.pepstock.charba.client.enums.Render;
import org.pepstock.charba.client.gwt.widgets.GaugeChartWidget;
import org.pepstock.charba.client.impl.charts.DefaultThreshold;
import org.pepstock.charba.client.impl.charts.GaugeDataset;
import org.pepstock.charba.client.impl.charts.MeterContext;
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
		chartPercent.getOptions().setRender(Render.PERCENTAGE);
		chartPercent.getOptions().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue()*100, 2)+ "%";
			}
		});
		chartPercent.getOptions().setAnimated(true);
		chartPercent.getData().setDatasets(getDataset(chartPercent, "Percent", 100D));

		chartValue.getOptions().getTitle().setDisplay(true);
		chartValue.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label");
		chartValue.getOptions().setRender(Render.VALUE_AND_LABEL);
		chartValue.getOptions().setAnimated(true);
		chartValue.getOptions().setFontColor(new ColorCallback<MeterContext>() {

			@Override
			public Object invoke(MeterContext context) {
				GaugeDataset dataset = (GaugeDataset)chartValue.getData().getDatasets().get(0);
				return dataset.getCurrent().getColor();
			}
			
		});
		chartValue.getOptions().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue() * context.getEasing(), 0)+ " MB";
			}
		});
		chartValue.getOptions().getFont().setStyle(FontStyle.ITALIC);
		chartValue.getData().setDatasets(getDataset(chartValue, "Memory", 2048D));

		chartValueColor.getOptions().getTitle().setDisplay(true);
		chartValueColor.getOptions().getTitle().setText("GAUGE chart to represent value and dataset label", "changing the color of label");
		chartValueColor.getOptions().setRender(Render.VALUE_AND_LABEL);
		chartValueColor.getOptions().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue(), 0)+ " GB";
			}
		});
		chartValueColor.getOptions().setFontColor(ColorBuilder.build(90, 173, 255));
		chartValueColor.getData().setDatasets(getDataset(chartValueColor, "Storage", 200D));

		chartValueReverse.getOptions().getTitle().setDisplay(true);
		chartValueReverse.getOptions().getTitle().setText("GAUGE chart with thresholds on reverse mode");
		chartValueReverse.getOptions().setRender(Render.VALUE);
		chartValueReverse.getOptions().setPrecision(0);

		GaugeDataset ds = getDataset(chartValueReverse, "Reverse", 400D);

		ds.setThresholds(DefaultThreshold.NORMAL.getThreshold().setValue(Double.MAX_VALUE), DefaultThreshold.WARNING.getThreshold().setValue(100), DefaultThreshold.CRITICAL.getThreshold().setValue(40));
		ds.setPercentageThreshold(false);
		chartValueReverse.getData().setDatasets(ds);
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
