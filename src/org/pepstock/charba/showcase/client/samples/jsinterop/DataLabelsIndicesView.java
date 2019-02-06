package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.Color;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.ext.datalabels.Align;
import org.pepstock.charba.client.ext.datalabels.AlignCallback;
import org.pepstock.charba.client.ext.datalabels.BackgroundColorCallback;
import org.pepstock.charba.client.ext.datalabels.BorderColorCallback;
import org.pepstock.charba.client.ext.datalabels.BorderWidthCallback;
import org.pepstock.charba.client.ext.datalabels.ColorCallback;
import org.pepstock.charba.client.ext.datalabels.Context;
import org.pepstock.charba.client.ext.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.ext.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.ext.datalabels.FormatterCallback;
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
public class DataLabelsIndicesView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsIndicesView> {
	}

	@UiField
	LineChart chart;
	
	public DataLabelsIndicesView() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		Chart.helpers.merge(Chart.defaults.global, {
//			aspectRatio: 4/3,
//			tooltips: false,
//			layout: {
//				padding: {
//					top: 32,
//					right: 24,
//					bottom: 32,
//					left: 0
//				}
//			},
//			elements: {
//				line: {
//					borderWidth: 2,
//					fill: false
//				}
//			},
//			plugins: {
//				legend: false,
//				title: false
//			}
//		});

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getLayout().getPadding().setRight(24);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(16);
		chart.getOptions().getElements().getLine().setFill(false);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1);
		dataset1.setBorderColor(color1);
		double[] values = getRandomDigits(months, false);
		dataset1.setData(values);

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
		chart.getData().setDatasets(dataset1);
		
//		options: {
//			plugins: {
//				datalabels: {
//					align: function(context) {
//						return context.dataIndex % 2 ? 'end' : 'center';
//					},
//					backgroundColor: function(context) {
//						return context.dataIndex % 2 ?
//							context.dataset.borderColor :
//							'rgba(255, 255, 255, 0.8)';
//					},
//					borderColor: function(context) {
//						return context.dataIndex % 2 ? null : context.dataset.borderColor;
//					},
//					color:  function(context) {
//						return context.dataIndex % 2 ? 'white' : context.dataset.borderColor;
//					},
//					borderWidth: function(context) {
//						return context.dataIndex % 2 ? 0 : 1;
//					},
//					formatter: function(value, context) {
//						return context.dataIndex + ': ' + Math.round(value) + '\'';
//					},
//					offset: 8
//				}
//			}
//		}
//	});

		DataLabelsOptions option = new DataLabelsOptions();
		option.setAlignCallback(new AlignCallback() {
			
			@Override
			public Align align(AbstractChart<?, ?> chart, Context context) {
				return context.getIndex() % 2 == 0 ? Align.end : Align.center;
			}
		});

		option.setBackgroundColorCallback(new BackgroundColorCallback() {
			
			@Override
			public String backgroundColor(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? ds.getBorderColorAsString() : new Color(255, 255, 255).alpha(0.8D).toRGBA();
			}
		});
		option.setBorderColorCallback(new BorderColorCallback() {
			
			@Override
			public String borderColor(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? null : ds.getBorderColorAsString();
			}
		});
		option.setColorCallback(new ColorCallback() {
			
			@Override
			public String color(AbstractChart<?, ?> chart, Context context) {
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return context.getIndex() % 2 == 0 ? HtmlColor.White.toRGBA() : ds.getBorderColorAsString();
			}
		});
		option.setBorderWidthCallback(new BorderWidthCallback() {
			
			@Override
			public int borderWidth(AbstractChart<?, ?> chart, Context context) {
				return context.getIndex() % 2 == 0 ? 0 : 1;
			}
		});
		option.setFormatterCallback(new FormatterCallback() {
			
			@Override
			public String format(AbstractChart<?, ?> chart, double value, Context context) {
				return context.getIndex()+": "+Math.round(value)+"'";
			}
		});
		option.setOffset(8);
		
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
}
