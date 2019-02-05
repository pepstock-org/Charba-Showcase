package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.logging.Logger;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Andrea "Stock" Stocchero
 */
public class LineView extends BaseComposite {
	
	Logger LOG = Logger.getLogger("mio");

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LineView> {
	}

	@UiField
	VerticalPanel panel;
	
	LineChart chart;

	public LineView() {
		initWidget(uiBinder.createAndBindUi(this));

		LineChart chart = new LineChart();
		chart = new LineChart();
//		chart.setWidth("100%");
		chart.getOptions().getLayout().getPadding().setTop(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().setResponsive(true);
//		chart.getOptions().setMaintainAspectRatio(false);
		chart.getOptions().getLegend().setDisplay(true);
		
		LineDataset dataSet = chart.newDataset();
		dataSet.setBackgroundColor("#4863A0");
		dataSet.setData(new double[] { 90.0, 80.0, 88.0 });
		chart.getData().setDatasets(dataSet);
		chart.getData().setLabels("one", "two", "three");
		
		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.setDisplay(true);
		axis.getTicks().setBeginAtZero(true);
		axis.getScaleLabel().setDisplay(true);
		axis.getScaleLabel().setLabelString("Value");
		chart.getOptions().getScales().setYAxes(axis);
		
		panel.add(chart);
		
	}
	@UiHandler("add")
	protected void handleRandomize(ClickEvent event) {
		LOG.info(chart.toJSONString());
		panel.add(chart);
	}
}
