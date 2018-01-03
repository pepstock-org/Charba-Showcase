package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class MultiAxisBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MultiAxisBarView> {
	}

	@UiField
	BarChart chart;
	
	public MultiAxisBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Multi Axis Bar Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(true);
		
		CartesianLinearAxis axis1 = new CartesianLinearAxis();
		axis1.setId("y-axis-1");
		axis1.setPosition(Position.left);
		axis1.setDisplay(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis();
		axis2.setId("y-axis-2");
		axis2.setPosition(Position.right);
		axis2.setDisplay(true);
		axis2.getGrideLines().setDrawOnChartArea(false);

		chart.getOptions().getScales().setYAxes(axis1, axis2);
		
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		dataset1.setBackgroundColor(getSequenceColors(months, 0.2));
		dataset1.setBorderColor(getSequenceColors(months, 1));
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));
		dataset1.setYAxisID("y-axis-1");

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));
		dataset2.setYAxisID("y-axis-2");

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
}
