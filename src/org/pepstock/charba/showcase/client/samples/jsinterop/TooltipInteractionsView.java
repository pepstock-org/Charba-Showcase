package org.pepstock.charba.showcase.client.samples.jsinterop;


import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class TooltipInteractionsView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipInteractionsView> {
	}

	@UiField
	LineChart chart1;

	@UiField
	LineChart chart2;

	@UiField
	LineChart chart3;

	@UiField
	LineChart chart4;

	@UiField
	LineChart chart5;

	@UiField
	LineChart chart6;

	@UiField
	LineChart chart7;

	@UiField
	LineChart chart8;

	@UiField
	LineChart chart9;

	@UiField
	LineChart chart10;

	@UiField
	LineChart chart11;

	@UiField
	LineChart chart12;
	
	private LineDataset dataset1 = null;
	
	private LineDataset dataset2 = null;


	public TooltipInteractionsView() {
		initWidget(uiBinder.createAndBindUi(this));

		dataset1 = new LineDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = Colors.ALL[0];
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.nofill);

		dataset2 = new LineDataset();
		dataset2.setLabel("dataset 1");
		IsColor color2 = Colors.ALL[1];
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.nofill);
		
		configChart(chart1, InteractionMode.point, true);
		configChart(chart2, InteractionMode.point, false);
		configChart(chart3, InteractionMode.nearest, true);
		configChart(chart4, InteractionMode.nearest, false);
		configChart(chart5, InteractionMode.index, true);
		configChart(chart6, InteractionMode.index, false);
		configChart(chart7, InteractionMode.dataset, true);
		configChart(chart8, InteractionMode.dataset, false);
		configChart(chart9, InteractionMode.x, true);
		configChart(chart10, InteractionMode.x, false);
		configChart(chart11, InteractionMode.y, true);
		configChart(chart12, InteractionMode.y, false);
	}

	private void configChart(LineChart chart, InteractionMode mode, boolean intersect){
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Tooltip mode: "+mode.name()+", intersect: "+intersect);
		chart.getOptions().getTooltips().setMode(mode);
		chart.getOptions().getTooltips().setIntersect(intersect);
		chart.getOptions().getHover().setMode(mode);
		chart.getOptions().getHover().setIntersect(intersect);
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

	}
}
