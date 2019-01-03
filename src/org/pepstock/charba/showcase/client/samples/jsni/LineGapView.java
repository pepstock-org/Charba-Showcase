package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.impl.AtLeastOneDatasetHandler;
import org.pepstock.charba.client.callbacks.impl.NoSelectedDatasetTicksCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.LegendClickEvent;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.items.DatasetViewItem;
import org.pepstock.charba.client.options.scales.CartesianCategoryAxis;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;
import org.pepstock.charba.showcase.client.samples.Toast.Level;

import com.google.gwt.canvas.dom.client.Context2d;
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
public class LineGapView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LineGapView> {
	}

	@UiField
	LineChart chart;
	
	public LineGapView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.nearest);
		chart.getOptions().getHover().setIntersect(true);
		
		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months);
		values[4] = Double.NaN;
		dataset1.setData(values);
		dataset1.setFill(Fill.origin);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		axis2.getTicks().setCallback(new NoSelectedDatasetTicksCallback());
		
		chart.addHandler(new AtLeastOneDatasetHandler(), LegendClickEvent.TYPE);
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		AbstractPlugin p = new AbstractPlugin() {

			@Override
			public String getId() {
				return "gap";
			}

			@Override
			public void onAfterDraw(AbstractChart<?, ?> chart, double easing, JavaScriptObject options){
				Context2d ctx = chart.getCanvas().getContext2d();
				DatasetMetaItem metaItem = chart.getDatasetMeta(0);
				if (metaItem != null) {
					boolean hasGap = false;
					double previousX = 0;
					double previousY = 0;
					for (DatasetItem item : metaItem.getDatasets()) {
						DatasetViewItem view = item.getView();
						if (Double.isNaN(view.getY())) {
							hasGap = true;
						} else if (hasGap) {
							double nextX = view.getX();
							double nextY = view.getY();
							double controlPointX = (nextX + previousX) / 2;
							double controlPointY = (nextY + previousY) / 2;

							ctx.beginPath();
							ctx.moveTo(previousX, previousY);
							ctx.setStrokeStyle("red");
							ctx.setLineWidth(view.getBorderWidth());
							ctx.quadraticCurveTo(controlPointX, controlPointY, nextX, nextY);
							ctx.stroke();
							ctx.closePath();
							hasGap = false;
						} else {
							previousX = view.getX();
							previousY = view.getY();
						}
					}
				}
			}
		};
		try {
			chart.getPlugins().add(p);
		} catch (InvalidPluginIdException e) {
			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
		}

	}
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			double[] values = getRandomDigits(months);
			values[4] = Double.NaN;
			dataset.setData(values);
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
