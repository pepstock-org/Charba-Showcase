package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.callbacks.TickCallback;
import org.pepstock.charba.client.jsinterop.configuration.Axis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
import org.pepstock.charba.client.jsinterop.enums.Fill;
import org.pepstock.charba.client.jsinterop.enums.InteractionMode;
import org.pepstock.charba.client.jsinterop.enums.Position;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class FilteringLabelsView extends BaseComposite{
	
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
			
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.jsinterop.callbacks.TickCallback#onCallback(org.pepstock.charba.client.jsinterop.configuration.Axis, double, int, java.util.List)
			 */
			@Override
			public String onCallback(Axis axis, double value, int index, List<Double> values) {
				String label = chart.getData().getLabels().getStrings(index)[0];
				return index % 2 == 0 ? label : "";
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
	
}
