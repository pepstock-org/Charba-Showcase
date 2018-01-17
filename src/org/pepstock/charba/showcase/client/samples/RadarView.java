package org.pepstock.charba.showcase.client.samples;

import java.util.List;

import org.pepstock.charba.client.RadarChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.RadarDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.options.scales.RadialAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class RadarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RadarView> {
	}

	@UiField
	RadarChart chart;
	
	public RadarView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Radar Chart");
		
		RadialAxis axis = new RadialAxis(chart);
		axis.getTicks().setBeginAtZero(true);
		chart.getOptions().setAxis(axis);
			
		
		RadarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		Color color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2).toRGBA());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setPointBackgroundColor(color1.toHex());
		dataset1.setBorderWidth(2);
		dataset1.setData(getRandomDigits(months));

		RadarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		Color color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2).toRGBA());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setPointBackgroundColor(color2.toHex());
		dataset2.setBorderWidth(2);
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		
		RadarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		
		Color color = Colors.ALL[datasets.size()]; 
		dataset.setBackgroundColor(color.alpha(0.2).toRGBA());
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(2);
		dataset.setData(getRandomDigits(months));

		datasets.add(dataset);
		
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}
}
