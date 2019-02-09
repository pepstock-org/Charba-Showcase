package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.ext.datalabels.Align;
import org.pepstock.charba.client.ext.datalabels.Anchor;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.Weight;
import org.pepstock.charba.client.impl.callbacks.DataLabelsPointer;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.showcase.client.Charba_Showcase;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsListenersView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsListenersView> {
	}

	@UiField
	LineChart chart;

	@UiField
	SimplePanel log;
	
	PreElement element = Document.get().createPreElement();

	public DataLabelsListenersView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		log.getElement().appendChild(element);
		
//		aspectRatio: 4/3,
//		tooltips: false,
//		layout: {
//			padding: {
//				top: 42,
//				right: 16,
//				bottom: 32,
//				left: 8
//			}
//		},
//		elements: {
//			line: {
//				fill: false
//			}
//		},
//		plugins: {
//			legend: false,
//			title: false
//		}
		
//		data: {
//			labels: labels,
//			datasets: [{
//				backgroundColor: Samples.color(0),
//				borderColor: Samples.color(0),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				}),
//				datalabels: {
//					align: 'start',
//					anchor: 'start'
//				}
//			}, {
//				backgroundColor: Samples.color(1),
//				borderColor: Samples.color(1),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				})
//			}, {
//				backgroundColor: Samples.color(2),
//				borderColor: Samples.color(2),
//				data: Samples.numbers({
//					count: DATA_COUNT,
//					min: 0,
//					max: 100
//				}),
//				datalabels: {
//					align: 'end',
//					anchor: 'end'
//				}
//			}]
//		},
//		options: {
//			plugins: {
//				datalabels: {
//					backgroundColor: function(context) {
//						return context.dataset.backgroundColor;
//					},
//					borderRadius: 4,
//					color: 'white',
//					font: {
//						weight: 'bold'
//					},
//					formatter: Math.round
//				}
//			},
//			scales: {
//				yAxes: [{
//					stacked: true
//				}]
//			}
//		}
//	});
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);
		
		chart.addHandler(new DatasetSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetSelectionEvent event) {
				AbstractChart<?, ?> chart = (AbstractChart<?, ?>)event.getSource();
				Labels labels = chart.getData().getLabels();
				List<Dataset> datasets = chart.getData().getDatasets();
				if (datasets != null && !datasets.isEmpty()){
					StringBuilder sb = new StringBuilder();
					sb.append("Dataset index: <b>").append(event.getItem().getDatasetIndex()).append("</b><br>");
					sb.append("Dataset label: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getLabel()).append("</b><br>");
					sb.append("Dataset data: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getData().get(event.getItem().getIndex())).append("</b><br>");
					sb.append("Index: <b>").append(event.getItem().getIndex()).append("</b><br>");
					sb.append("Value: <b>").append(labels.getStrings(event.getItem().getIndex())[0]).append("</b><br>");
					new Toast("Dataset Selected!", sb.toString()).show();
				}

			}
		}, DatasetSelectionEvent.TYPE);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);
		dataset1.setFill(Fill.nofill);
		
		DataLabelsOptions option1 = new DataLabelsOptions();
		option1.setAlign(Align.start);
		option1.setAnchor(Anchor.start);
		dataset1.setOptions(DataLabelsPlugin.ID, option1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months, false));
		dataset2.setFill(Fill.nofill);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 2");
		
		IsColor color3 = Colors.ALL[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months, false));
		dataset3.setFill(Fill.nofill);

		DataLabelsOptions option3 = new DataLabelsOptions();
		option3.setAlign(Align.end);
		option3.setAnchor(Anchor.end);
		dataset3.setOptions(DataLabelsPlugin.ID, option3);

		
		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		axis2.setStacked(true);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
		
//		backgroundColor: function(context) {
//			return context.dataset.backgroundColor;
//		},
//		borderRadius: 4,
//		color: 'white',
//		font: {
//			weight: 'bold'
//		},
//		formatter: Math.round
		
		DataLabelsOptions option = new DataLabelsOptions();
//		option.setBackgroundColor(color1);
//		option.setFontCallback(new FontCallback() {
//			
//			@Override
//			public Font font(AbstractChart<?, ?> chart, Context context) {
//				Font f = new Font();
//				f.setFontSize(36);
//				Charba_Showcase.LOG.info(f.toJSON());
//				return f;
//			}
//		});
//		
//		Charba_Showcase.LOG.info(option.getFont().getFontSize()+"");
		
//		option.setFormatterCallback(new FormatterCallback() {
//			
//			@Override
//			public String format(AbstractChart<?, ?> chart, double value, Context context) {
//				Charba_Showcase.LOG.info("value "+value);
//				return null;
//			}
//		});
//		option.getListeners().setClickEventHandler(new ClickEventHandler() {
//			
//			@Override
//			public boolean onClick(AbstractChart<?, ?> chart, Context context) {
//				Charba_Showcase.LOG.info("click "+context.getIndex());
//				return true;
//			}
//		});
		option.setBackgroundColor(new BackgroundColorCallback() {


			@Override
			public String backgroundColor(AbstractChart<?, ?> chart, Context context) {
				if (context.isActive()) {
					return null;
				}
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBackgroundColorAsString();
			}
		});
		option.setBorderRadius(4);
		option.setColor(HtmlColor.White);
		option.getFont().setWeight(Weight.bold);
		
		MyListener listener = new MyListener(element);
		
		option.getListeners().setEnterEventHandler(listener);
		option.getListeners().setLeaveEventHandler(listener);
		option.getListeners().setClickEventHandler(listener);
		
		
//		listeners: {
//			enter: function(context) {
//				log('enter', context);
//			},
//			leave: function(context) {
//				log('leave', context);
//			},
//			click: function(context) {
//				log('click', context);
//			}
//		}		
		
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
		addData(chart, false);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}
	
	static class MyListener extends DataLabelsPointer{
		
		final PreElement element;

		/**
		 * @param element
		 */
		MyListener(PreElement element) {
			super();
			this.element = element;
		}

		/* (non-Javadoc)
		 * @see org.pepstock.charba.client.impl.callbacks.DataLabelsPointer#onClick(org.pepstock.charba.client.AbstractChart, org.pepstock.charba.client.ext.datalabels.Context)
		 */
		@Override
		public boolean onClick(AbstractChart<?, ?> chart, Context context) {
			super.onClick(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			Labels labels = chart.getData().getLabels();
			List<Dataset> datasets = chart.getData().getDatasets();
			DatasetMetaItem meta = chart.getDatasetMeta(context.getDatasetIndex());
			DatasetItem item = meta.getDatasets().get(context.getIndex());
			if (datasets != null && !datasets.isEmpty()){
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset index: <b>").append(item.getDatasetIndex()).append("</b><br>");
				sb.append("Dataset label: <b>").append(datasets.get(item.getDatasetIndex()).getLabel()).append("</b><br>");
				sb.append("Dataset data: <b>").append(datasets.get(item.getDatasetIndex()).getData().get(item.getIndex())).append("</b><br>");
				sb.append("Index: <b>").append(item.getIndex()).append("</b><br>");
				sb.append("Value: <b>").append(labels.getStrings(item.getIndex())[0]).append("</b><br>");
				new Toast("Dataset Selected!", sb.toString()).show();
			}
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> CLICK: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			Charba_Showcase.LOG.info("LISTENER ");
			chart.fireEvent(new DatasetSelectionEvent(Document.get().createChangeEvent(), item));
			return true;

		}

		/* (non-Javadoc)
		 * @see org.pepstock.charba.client.impl.callbacks.DataLabelsPointer#onLeave(org.pepstock.charba.client.AbstractChart, org.pepstock.charba.client.ext.datalabels.Context)
		 */
		@Override
		public boolean onLeave(AbstractChart<?, ?> chart, Context context) {
			super.onLeave(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> LEAVE: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see org.pepstock.charba.client.impl.callbacks.DataLabelsPointer#onEnter(org.pepstock.charba.client.AbstractChart, org.pepstock.charba.client.ext.datalabels.Context)
		 */
		@Override
		public boolean onEnter(AbstractChart<?, ?> chart, Context context) {
			super.onEnter(chart, context);
			LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
			DivElement newDiv= Document.get().createDivElement();
			newDiv.setInnerHTML("> ENTER: " + context.getDatasetIndex() + "-" + context.getIndex() + " (" + ds.getData().get(context.getIndex()) + ")");
			element.insertBefore(newDiv, element.getFirstChild());
			if (element.getChildCount() > 8) {
				element.removeChild(element.getLastChild());
			}
			return true;
		}
		
		
		
	}
}
