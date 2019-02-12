package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.ext.labels.LabelsOptions;
import org.pepstock.charba.client.ext.labels.LabelsPlugin;
import org.pepstock.charba.client.ext.labels.Position;
import org.pepstock.charba.client.ext.labels.Render;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class PieceLabelPositionView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PieceLabelPositionView> {
	}

	@UiField
	DoughnutChart chart;
	
	final LabelsOptions option = new LabelsOptions();
	
	public PieceLabelPositionView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(false);
		
		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);


		option.setRender(Render.label);
		option.setFontColor(HtmlColor.White);
		option.setFontSize(16);
		option.setFontFamily("'Lucida Console', Monaco, monospace");
		option.setArc(true);
		option.setPosition(Position.border);
		
		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12){
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets){
				PieDataset pds = (PieDataset)ds;
				pds.setBackgroundColor(getSequenceColors(months, 1));	
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
		
	}

	@UiHandler("remove_data")
	protected void handleremoveData(ClickEvent event) {
		removeData(chart);
	}
	
	@UiHandler("outside")
	protected void handleOutside(ClickEvent event) {
		boolean checked = ((CheckBox) event.getSource()).getValue();
		option.setPosition(checked ? Position.outside : Position.border);
		option.setFontColor(checked ? HtmlColor.Black : HtmlColor.White);
		chart.getNode().getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
		chart.update();
	}

}
