package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.logging.Logger;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TickCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.ChartNode;
import org.pepstock.charba.client.items.ScaleItem;
import org.pepstock.charba.client.items.ScalesNode;
import org.pepstock.charba.client.items.TickItem;
import org.pepstock.charba.client.options.scales.CartesianCategoryAxis;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class FilteringLabelsView extends BaseComposite{
	
	Logger LOG = Logger.getLogger("handler");
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FilteringLabelsView> {
	}

	@UiField
	LineChart chart;
	
	public FilteringLabelsView() {
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
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.nofill);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");
		axis1.getTicks().setCallback(new TickCallback() {
			
			@Override
			public String onCallback(AbstractChart<?, ?> chart, TickItem item) {
				String label = chart.getData().getLabels().getString(item.getIndex());
				return item.getIndex() % 2 == 0 ? label : "";
			}
		});
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");
		
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Composite#onAttach()
	 */
	@Override
	protected void onAttach() {
		super.onAttach();
		ChartNode node = chart.getChartNode();
		ScaleItem scale = node.getScales().getItems().get(ScalesNode.DEFAULT_X_AXIS_ID);
		LOG.info("Ticks "+scale.getTicks());
		
	}
	
	
	
}