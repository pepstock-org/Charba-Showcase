package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.ArrayList;
import java.util.List;

import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.impl.charts.MeterChart;
import org.pepstock.charba.client.impl.charts.MeterDataset;
import org.pepstock.charba.client.impl.charts.MeterDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MeterView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MeterView> {
	}

	@UiField
	MeterChart chartPercent;
	@UiField
	MeterChart chartValue;
	@UiField
	MeterChart chartValueColor;

	@UiField
	VerticalPanel container;
	
	private final List<MeterDataset> datasets = new ArrayList<MeterDataset>();

	public MeterView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chartPercent.getOptions().getTitle().setDisplay(true);
		chartPercent.getOptions().getTitle().setText("METER chart to represent percentage value");
		chartPercent.getOptions().setDisplay(MeterDisplay.PERCENTAGE);
		chartPercent.getOptions().setFormat("###.##%");
		chartPercent.getData().setDatasets(getDataset(chartPercent, "Percent", 100D));

		chartValue.getOptions().getTitle().setDisplay(true);
		chartValue.getOptions().getTitle().setText("METER chart to represent value and dataset label");
		chartValue.getOptions().setDisplay(MeterDisplay.VALUE_AND_LABEL);
		chartValue.getOptions().setFormat("#### MB");
		chartValue.getData().setDatasets(getDataset(chartPercent, "memory", 2048D));

		chartValueColor.getOptions().getTitle().setDisplay(true);
		chartValueColor.getOptions().getTitle().setText("METER chart to represent value and dataset label", "changing the color of label");
		chartValueColor.getOptions().setDisplay(MeterDisplay.VALUE_AND_LABEL);
		chartValueColor.getOptions().setFormat("#### GB");
		chartValueColor.getOptions().setDisplayFontColor(ColorBuilder.build(90, 173, 255));
		chartValueColor.getData().setDatasets(getDataset(chartValueColor, "Storage", 200D));

	}
	
	private MeterDataset getDataset(MeterChart chart, String label, double max){
		chart.getOptions().setResponsive(true);
		MeterDataset dataset = chart.newDataset(max);
		dataset.setLabel(label);
		dataset.setValue(Math.random()*max);
		datasets.add(dataset);
		return dataset;
	}
	

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (MeterDataset dataset : datasets){
		    dataset.setValue(Math.random()*dataset.getMax());
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
