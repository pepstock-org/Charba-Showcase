package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Arrays;

import org.pepstock.charba.client.PolarAreaChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.ext.labels.LabelsOptions;
import org.pepstock.charba.client.ext.labels.LabelsPlugin;
import org.pepstock.charba.client.ext.labels.Position;
import org.pepstock.charba.client.ext.labels.Render;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class PieceLabelPolarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PieceLabelPolarView> {
	}

	@UiField
	PolarAreaChart chart;
	
	final int myMonths = 3; 
	
	public PieceLabelPolarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(false);
		
		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(myMonths, 1));
		dataset.setData(getRandomDigits(myMonths, false));

		chart.getData().setLabels(getLabels(myMonths));
		chart.getData().setDatasets(dataset);
		
		LabelsOptions option1 = new LabelsOptions();
		option1.setRender(Render.label);
		option1.setFontColor(HtmlColor.Black);
		option1.setArc(true);
		option1.setPosition(Position.outside);

		LabelsOptions option2 = new LabelsOptions();
		option2.setRender(Render.percentage);
		option2.setFontColor(HtmlColor.White);
		option2.setPrecision(2);

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, Arrays.asList(option1, option2));
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(myMonths, false));
		}
		chart.update();
	}
}
