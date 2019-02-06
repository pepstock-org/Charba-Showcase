package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.ext.datalabels.Anchor;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.Display;
import org.pepstock.charba.client.ext.datalabels.DisplayCallback;
import org.pepstock.charba.client.ext.datalabels.Weight;
import org.pepstock.charba.client.items.UndefinedValues;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsDoughnutView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsDoughnutView> {
	}

	@UiField
	DoughnutChart chart;

	@UiField
	VerticalPanel container;

	public DataLabelsDoughnutView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);

		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);

		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		DoughnutDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(getSequenceColors(months, 1));
		dataset1.setData(getRandomDigits(months, false));
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAnchor(Anchor.end);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		DoughnutDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 1");
		dataset2.setBackgroundColor(getSequenceColors(months, 1));
		dataset2.setData(getRandomDigits(months, false));

		DataLabelsOptions option2 = new DataLabelsOptions();
		option2.setAnchor(Anchor.center);
		option2.setBackgroundColor(UndefinedValues.STRING);
		option2.setBorderWidth(0);
		dataset2.setOptions(DataLabelsPlugin.ID, option2);

		DoughnutDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 1");
		dataset3.setBackgroundColor(getSequenceColors(months, 1));
		dataset3.setData(getRandomDigits(months, false));

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAnchor(Anchor.start);
		dataset3.setOptions(DataLabelsPlugin.ID, option3);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

//		data: {
//			labels: labels,
//			datasets: [{
//				backgroundColor: Samples.colors({
//					color: Samples.color(0),
//					count: DATA_COUNT
//				}),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				}),
//				datalabels: {
//					anchor: 'end'
//				}
//			}, {
//				backgroundColor: Samples.colors({
//					color: Samples.color(1),
//					count: DATA_COUNT
//				}),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				}),
//				datalabels: {
//					anchor: 'center',
//					backgroundColor: null,
//					borderWidth: 0
//				}
//			}, {
//				backgroundColor: Samples.colors({
//					color: Samples.color(2),
//					count: DATA_COUNT
//				}),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				}),
//				datalabels: {
//					anchor: 'start'
//				}
//			}]
//		},

		DataLabelsOptions option = new DataLabelsOptions();
//		options: {
//		plugins: {
//			datalabels: {
//				backgroundColor: function(context) {
//					return context.dataset.backgroundColor;
//				},
//				borderColor: 'white',
//				borderRadius: 25,
//				borderWidth: 2,
//				color: 'white',
//				display: function(context) {
//					var dataset = context.dataset;
//					var count = dataset.data.length;
//					var value = dataset.data[context.dataIndex];
//					return value > count * 1.5;
//				},
//				font: {
//					weight: 'bold'
//				},
//				formatter: Math.round
//			}
//		}
//	}
//});
		option.setBackgroundColorCallback(new BackgroundColorCallback() {


			@Override
			public String backgroundColor(AbstractChart<?, ?> chart, Context context) {
//				if (context.isActive()) {
//					return null;
//				}
				DoughnutDataset ds = (DoughnutDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString().get(context.getIndex());
			}
		});
		option.setDisplayCallback(new DisplayCallback() {
			
			@Override
			public Display display(AbstractChart<?, ?> chart, Context context) {
				Dataset ds = chart.getData().getDatasets().get(context.getDatasetIndex());
				int count  = ds.getData().size();
				double value = ds.getData().get(context.getIndex());
				return value > count * 1.5D ? Display.isTrue : Display.isFalse;
			}
		});
		option.setBorderColor(HtmlColor.White);
		option.setBorderRadius(25);
		option.setBorderWidth(2);
		option.setColor(HtmlColor.White);
		option.getFont().setWeight(Weight.bold);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
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
				DoughnutDataset pds = (DoughnutDataset)ds;
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
}
