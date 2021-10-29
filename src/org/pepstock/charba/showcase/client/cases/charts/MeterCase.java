package org.pepstock.charba.showcase.client.cases.charts;

import java.util.ArrayList;
import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.FontCallback;
import org.pepstock.charba.client.callbacks.MeterFormatCallback;
import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.ArcBorderRadius;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.MeterChartWidget;
import org.pepstock.charba.client.impl.charts.MeterContext;
import org.pepstock.charba.client.impl.charts.MeterDataset;
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

public class MeterCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MeterCase> {
	}

	@UiField
	MeterChartWidget chartPercent;
	@UiField
	MeterChartWidget chartValue;
	@UiField
	MeterChartWidget chartValueColor;

	private final List<MeterDataset> datasets = new ArrayList<MeterDataset>();

	public MeterCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chartPercent.getOptions().getTitle().setDisplay(true);
		chartPercent.getOptions().getTitle().setText("METER chart to represent percentage value");
		
		MeterDataset dataset1 = getDataset(chartPercent, "Percent", 100D);
		
		dataset1.getValueLabel().setPercentage(true);
		dataset1.getValueLabel().setAutoFontSize(true);
		dataset1.getValueLabel().setFont(new FontCallback<MeterContext>() {
			
			@Override
			public FontItem invoke(MeterContext context) {
				FontItem item = new FontItem();
				item.setWeight(Weight.BOLD);
				return item;
			}
		});
		
		chartPercent.getData().setDatasets(dataset1);

		chartValue.getOptions().getTitle().setDisplay(true);
		chartValue.getOptions().getTitle().setText("METER chart to represent value and custom label");

		MeterDataset dataset2 = getDataset(chartPercent, "memory", 2048D);
		dataset2.getDescriptionLabel().setDisplay(true);
		dataset2.getDescriptionLabel().setContent("RAM utilization");
		FontItem item = new FontItem();
		item.setFamily(chartValue.getOptions().getFont().getFamily());
		item.setSize(14);
		dataset2.getDescriptionLabel().setFont(item);
		dataset2.getDescriptionLabel().setColor(chartValue.getOptions().getColor());
		dataset2.getDescriptionLabel().setAutoFontSize(false);
		
		dataset2.getValueLabel().setColor(new ColorCallback<MeterContext>() {

			@Override
			public Object invoke(MeterContext context) {
				return context.getValue() > (2048D*0.75) ? HtmlColor.RED : HtmlColor.LIGHT_GREEN;
			}
			
		});
		dataset2.getValueLabel().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue(), 0)+" MB";
			}
		});
		chartValue.getData().setDatasets(dataset2);

		chartValueColor.getOptions().getTitle().setDisplay(true);
		chartValueColor.getOptions().getTitle().setText("METER chart to represent value and dataset label", "changing the color of label");

		MeterDataset dataset3 = getDataset(chartValueColor, "Storage", 200D);
		ArcBorderRadius radius = new ArcBorderRadius(0);
		radius.setInnerEnd(10);
		radius.setOuterEnd(10);
		dataset3.setBorderRadius(radius);
		dataset3.setColor(ColorBuilder.build(90, 173, 255));
		dataset3.getDescriptionLabel().setDisplay(true);
		dataset3.getValueLabel().setFormatCallback(new MeterFormatCallback() {
			
			@Override
			public String invoke(MeterContext context) {
				return Utilities.applyPrecision(context.getValue(), 0)+" GB";
			}
		});
		dataset3.getValueLabel().setColor(ColorBuilder.build(90, 173, 255));
		chartValueColor.getData().setDatasets(dataset3);

	}

	private MeterDataset getDataset(MeterChartWidget chart, String label, double max) {
		chart.getOptions().setResponsive(true);
		MeterDataset dataset = chart.newDataset(max);
		dataset.setLabel(label);
		dataset.setValue(Math.random() * max);
		datasets.add(dataset);
		return dataset;
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (MeterDataset dataset : datasets) {
			dataset.setValue(Math.random() * dataset.getMax());
		}
		chartPercent.update();
		chartValue.update();
		chartValueColor.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
