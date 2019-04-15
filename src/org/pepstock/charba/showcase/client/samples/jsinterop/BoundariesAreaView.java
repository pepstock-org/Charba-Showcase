package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class BoundariesAreaView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	private boolean isSmooth = false;
	
	interface ViewUiBinder extends UiBinder<Widget, BoundariesAreaView> {
	}

	@UiField
	LineChart chartNoFill;
	
	@UiField
	LineChart chartFillOrigin;

	@UiField
	LineChart chartFillStart;
	
	@UiField
	LineChart chartFillEnd;

	public BoundariesAreaView() {
		initWidget(uiBinder.createAndBindUi(this));

		configureChart(chartNoFill, 0, Fill.FALSE);
		configureChart(chartFillOrigin, 1, Fill.ORIGIN);
		configureChart(chartFillStart, 2, Fill.START);
		configureChart(chartFillEnd, 3, Fill.END);
	}
	
	private void configureChart(LineChart chart, int index, Fill fill){
		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.getTicks().setAutoSkip(false);
		axis.getTicks().setMaxRotation(0);
	
		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba "+fill.name()+" Fill Line Chart");
		chart.getOptions().setSpanGaps(false);
		chart.getOptions().getElements().getLine().setTension(0.000001D);
		chart.getOptions().getScales().setYAxes(axis);
		
		LineDataset dataset = chart.newDataset();
		dataset.setLabel("fill: "+fill.name());
		IsColor color = Colors.ALL[index];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(fill);
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}
	

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		newData(chartNoFill);
		newData(chartFillOrigin);
		newData(chartFillStart);
		newData(chartFillEnd);
	}

	@UiHandler("smooth")
	protected void handleSmooth(ClickEvent event) {
		isSmooth = !isSmooth;
		double value = isSmooth ? 0.4D : 0.000001D;
		chartNoFill.getOptions().getElements().getLine().setTension(value);
		chartFillOrigin.getOptions().getElements().getLine().setTension(value);
		chartFillStart.getOptions().getElements().getLine().setTension(value);
		chartFillEnd.getOptions().getElements().getLine().setTension(value);
		chartNoFill.draw();
		chartFillOrigin.draw();
		chartFillStart.draw();
		chartFillEnd.draw();
	}

	private void newData(LineChart chart) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}


}
