package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.PolarAreaChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.Weight;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsPolarAreaView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsPolarAreaView> {
	}

	@UiField
	PolarAreaChart chart;

	public DataLabelsPolarAreaView() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		Chart.helpers.merge(Chart.defaults.global, {
//		maintainAspectRatio: false,
//		tooltips: false,
//		layout: {
//			padding: 16
//		},
//		elements: {
//			line: {
//				fill: false
//			},
//			point: {
//				hoverRadius: 7,
//				radius: 5
//			}
//		},
//		plugins: {
//			legend: false,
//			title: false
//		}
//	});
//</script>
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(16);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(16);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		chart.getOptions().getElements().getPoint().setHoverRadius(7);
		chart.getOptions().getElements().getPoint().setRadius(7);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);

		
		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 0.2D));
		dataset.setData(getRandomDigits(months, false));

//
//	<script id="script-construct">
//		var chart = new Chart('chart-0', {
//			type: 'polarArea',
//			data: {
//				labels: labels,
//				datasets: [{
//					backgroundColor: Samples.color(0),
//					data: Samples.numbers({
//						count: DATA_COUNT,
//						min: 0,
//						max: 100
//					})
//				}, {
//					backgroundColor: Samples.color(1),
//					data: Samples.numbers({
//						count: DATA_COUNT,
//						min: 0,
//						max: 100
//					})
//				}, {
//					backgroundColor: Samples.color(2),
//					data: Samples.numbers({
//						count: DATA_COUNT,
//						min: 0,
//						max: 100
//					})
//				}]
//			},


		RadialAxis axis = new RadialAxis(chart);
		axis.getTicks().setBeginAtZero(true);
		axis.getTicks().setReverse(false);
		chart.getOptions().setAxis(axis);
		

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		
		DataLabelsOptions option = new DataLabelsOptions();
//		options: {
//		plugins: {
//			datalabels: {
//				anchor: 'end',
//				backgroundColor: function(context) {
//					return context.dataset.backgroundColor;
//				},
//				borderColor: 'white',
//				borderRadius: 25,
//				borderWidth: 2,
//				color: 'white',
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
				PolarAreaDataset ds = (PolarAreaDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColor().get(context.getIndex()).alpha(1D).toRGBA();
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
				PolarAreaDataset pds = (PolarAreaDataset)ds;
				pds.setBackgroundColor(getSequenceColors(months, 0.2D));	
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
