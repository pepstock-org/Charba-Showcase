package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.List;
import java.util.logging.Logger;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.client.plugins.impl.DatasetsItemsSelector;
import org.pepstock.charba.client.plugins.impl.DatasetsItemsSelectorOptions;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class VerticalBarView extends BaseComposite{
	Logger LOG = Logger.getLogger("selection");
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, VerticalBarView> {
	}

	@UiField
	BarChart chart;
	
	public VerticalBarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getCanvas().setWidth("1090px");
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(false);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		chart.getOptions().getTooltips().setEnabled(false);
		
//		chart.getOptions().setEvents(Event.click, Event.touchstart);
		
//		try {
//			chart.getOptions().getPlugins().setEnabled("title", false);
//		} catch (InvalidPluginIdException e) {
//			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
//		}
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
//		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		
//		dataset1.setData(getRandomDigits(months));
		dataset1.setData(getFixedDigits(months));

//		BarDataset dataset2 = chart.newDataset();
//		dataset2.setLabel("dataset 2");
//		
//		IsColor color2 = Colors.ALL[1];
//		
//		dataset2.setBackgroundColor(color2.alpha(0.2));
//		dataset2.setBorderColor(color2.toHex());
//		dataset2.setBorderWidth(1);
//		
//		dataset2.setData(getRandomDigits(months));
		chart.getData().setLabels(getLabels());
//		chart.getData().setDatasets(dataset1, dataset2);
		chart.getData().setDatasets(dataset1);
		
		chart.addHandler(new DatasetRangeSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset from: <b>").append(event.getFrom()).append("</b><br>");
				sb.append("Dataset to: <b>").append(event.getTo()).append("</b><br>");
				new Toast("Dataset Range Selected!", sb.toString()).show();
			}
		}, DatasetRangeSelectionEvent.TYPE);

		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(1);
		pOptions.setBorderDash(6);
		try {
			chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions.getObject());
			chart.getPlugins().add(new DatasetsItemsSelector());
		} catch (InvalidPluginIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		
		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		
		IsColor color = Colors.ALL[datasets.size()]; 
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(1);
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
