package org.pepstock.charba.showcase.client.samples.jsinterop;


import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.enums.Fill;
import org.pepstock.charba.client.jsinterop.enums.InteractionMode;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.client.jsinterop.enums.TooltipPosition;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class TooltipPositioningView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipPositioningView> {
	}

	@UiField
	LineChart chartAverage;
	
	@UiField
	LineChart chartNearest;
	
	private LineDataset dataset1 = null;
	
	private LineDataset dataset2 = null;


	public TooltipPositioningView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chartAverage.getOptions().setResponsive(true);
		chartAverage.getOptions().getLegend().setPosition(Position.top);
		chartAverage.getOptions().getTitle().setDisplay(true);
		chartAverage.getOptions().getTitle().setText("Charba Tooltip average");
		chartAverage.getOptions().getTooltips().setPosition(TooltipPosition.average);
		chartAverage.getOptions().getTooltips().setMode(InteractionMode.index);
		chartAverage.getOptions().getTooltips().setIntersect(false);

		chartNearest.getOptions().setResponsive(true);
		chartNearest.getOptions().getLegend().setPosition(Position.top);
		chartNearest.getOptions().getTitle().setDisplay(true);
		chartNearest.getOptions().getTitle().setText("Charba Tooltip nearest");
		chartNearest.getOptions().getTooltips().setPosition(TooltipPosition.nearest);
		chartNearest.getOptions().getTooltips().setMode(InteractionMode.index);
		chartNearest.getOptions().getTooltips().setIntersect(false);
	
		dataset1 = chartAverage.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = Colors.ALL[0];
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.nofill);

		dataset2 = chartNearest.newDataset();
		dataset2.setLabel("dataset 1");
		IsColor color2 = Colors.ALL[1];
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);
		
		chartAverage.getData().setLabels(getLabels());
		chartAverage.getData().setDatasets(dataset1, dataset2);
		
		chartNearest.getData().setLabels(getLabels());
		chartNearest.getData().setDatasets(dataset1, dataset2);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(months));
		dataset2.setData(getRandomDigits(months));
		chartAverage.update();
		chartNearest.update();
	}
}
