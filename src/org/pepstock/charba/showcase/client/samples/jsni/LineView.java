package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.AxisFitCallback;
import org.pepstock.charba.client.callbacks.impl.AtLeastOneDatasetHandler;
import org.pepstock.charba.client.callbacks.impl.NoSelectedDatasetTicksCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.ChartResizeEvent;
import org.pepstock.charba.client.events.ChartResizeEventHandler;
import org.pepstock.charba.client.events.LegendClickEvent;
import org.pepstock.charba.client.items.AxisItem;
import org.pepstock.charba.client.options.scales.CartesianCategoryAxis;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class LineView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LineView> {
	}

	@UiField
	LineChart chart;
	
	public LineView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.nearest);
		chart.getOptions().getHover().setIntersect(true);
		
		chart.addHandler(new ChartResizeEventHandler() {
			
			@Override
			public void onResize(ChartResizeEvent event) {
			}
		},ChartResizeEvent.TYPE);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months);
		dataset1.setData(values);
		dataset1.setFill(Fill.nofill);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);
		dataset2.setHidden(false);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		axis1.setFitCallback(new AxisFitCallback() {
			
			@Override
			public void onBeforeFit(AbstractChart<?, ?> chart, AxisItem item) {
			}
			
			@Override
			public void onAfterFit(AbstractChart<?, ?> chart, AxisItem item) {
			}
		});
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
//		axis2.getTicks().setReverse(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		axis2.getTicks().setCallback(new NoSelectedDatasetTicksCallback());
		
		chart.addHandler(new AtLeastOneDatasetHandler(), LegendClickEvent.TYPE);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
		try {
			chart.getPlugins().add(new AbstractPlugin() {
				
				@Override
				public String getId() {
					return "size";
				}

				/* (non-Javadoc)
				 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeInit(org.pepstock.charba.client.AbstractChart, com.google.gwt.core.client.JavaScriptObject)
				 */
				@Override
				public void onBeforeInit(AbstractChart<?, ?> chart, JavaScriptObject options) {
//					Window.getConsole().log("Canvas "+chart.getCanvas().getParentElement().getOffsetWidth());
//					Window.getConsole().log("Canvas "+chart.getCanvas().getWidth());
//					Window.getConsole().log("Canvas "+chart.getCanvas().getHeight());
					
					int w = chart.getCanvas().getParent().getOffsetWidth();
//					chart.getCanvas().setWidth(w);
//					chart.getCanvas().setHeight(w/3);
//					Window.getConsole().log("Canvas "+chart.getCanvas().getWidth());
//					Window.getConsole().log("Canvas "+chart.getCanvas().getHeight());

				}

//				/* (non-Javadoc)
//				 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeUpdate(org.pepstock.charba.client.AbstractChart, com.google.gwt.core.client.JavaScriptObject)
//				 */
//				@Override
//				public boolean onBeforeUpdate(AbstractChart<?, ?> chart, JavaScriptObject options) {
//					Window.getConsole().log("Canvas "+chart.getCanvas().getParentElement().getOffsetWidth());
//					Window.getConsole().log("Canvas "+chart.getCanvas().getWidth());
//					Window.getConsole().log("Canvas "+chart.getCanvas().getHeight());
//
//					int w = chart.getCanvas().getParentElement().getOffsetWidth();
//					chart.getCanvas().setWidth(w);
//					chart.getCanvas().setHeight(100);
//					return true;
//				}

				
				
			});
		} catch (InvalidPluginIdException e) {
			// TODO Auto-generated catch block
			
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
		
		LineDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		
		IsColor color = Colors.ALL[datasets.size()]; 
		dataset.setBackgroundColor(color.toHex());
		dataset.setBorderColor(color.toHex());
		dataset.setFill(Fill.nofill);
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
