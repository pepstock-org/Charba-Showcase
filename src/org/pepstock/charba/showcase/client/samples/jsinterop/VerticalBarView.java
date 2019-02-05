package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.ext.datalabels.DataLabelsConfiguration;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.Weight;
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
public class VerticalBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, VerticalBarView> {
	}

	@UiField
	BarChart chart;
	
	public VerticalBarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1);
		dataset1.setBorderColor(color1);
		
		dataset1.setData(getFixedDigits(months));
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		
//		backgroundColor: function(context) {
//			return context.dataset.backgroundColor;
//		},
//		borderRadius: 4,
//		color: 'white',
//		font: {
//			weight: 'bold'
//		},
//		formatter: Math.round
		
		DataLabelsConfiguration option = new DataLabelsConfiguration();
//		option.setBackgroundColorCallback(new BackgroundColorCallback() {
//			
//			@Override
//			public String backgroundColor(AbstractChart<?, ?> chart, int datasetIndex, int index, boolean isActive) {
//				if (isActive) {
//					return null;
//				}
//				BarDataset ds = (BarDataset)chart.getData().getDatasets().get(datasetIndex);
//				List<String> colorsList = ds.getBackgroundColorAsString();
//				if (colorsList.size() == 1) {
//					Charba_Showcase.LOG.info(colorsList.get(0));
//					return colorsList.get(0);
//				} else {
//					return colorsList.get(index);
//				}
//			}
//		});
//		option.setBorderRadius(4);
		option.setColor(HtmlColor.Black);
		option.getFont().setWeight(Weight.bold);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

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
