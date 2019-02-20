package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Date;
import java.util.LinkedList;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.impl.plugins.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.impl.plugins.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TimeSeriesDrillDownView extends BaseComposite{
	
	private static final long MINUTE = 1000 * 60;
	
	private static final long HOUR = MINUTE * 60;
	
	private static final long DAY = HOUR * 24;
	
	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesDrillDownView> {
	}

	@UiField
	SimplePanel panel;

	final DatasetsItemsSelector selector = new DatasetsItemsSelector();
	
	final LinkedList<LineChart> charts = new LinkedList<>();
	
	int index = 0;
	
	int currentAmountOfPoint = AMOUNT_OF_POINTS;
	
	public TimeSeriesDrillDownView() {
		initWidget(uiBinder.createAndBindUi(this));
		addChart();
	}
	
	@Override
	protected void onDetach() {
		charts.getFirst().destroy();
		super.onDetach();
	}

	private void addChart() {
		if (!charts.isEmpty()) {
			panel.remove(charts.get(index-1));
		}
		LineChart chart = createChart();
		charts.add(chart);
		panel.add(chart);
		index++;
		chart.draw();
	}
	
	private void removeChart() {
		if (!charts.isEmpty()) {
			panel.remove(charts.get(index-1));
		}
		LineChart chart = charts.get(0);
		charts.removeLast();
		panel.add(chart);
		index--;
		selector.skipNextRefreshFireEvent(chart);
		chart.update();
	}
	
	private LineChart createChart() {
		TimeUnit unit = getTimeUnit();
		LineChart chart = new LineChart();
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Timeseries Chart - "+unit.name());
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		
		LineDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+unit.name());
		dataset.setFill(Fill.nofill);
		
		IsColor color1 = Colors.ALL[index];
		
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		long time = new Date().getTime();
		double[] xs1 = getRandomDigits(currentAmountOfPoint, false);
		DataPoint[] dp1 = new DataPoint[currentAmountOfPoint];
		for (int i=0; i<currentAmountOfPoint; i++){
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setT(new Date(time));
			time = time + getFrame();
		}
		dataset.setDataPoints(dp1);
		chart.getData().setDatasets(dataset);
		
		final CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.series);
		axis.getTicks().setSource(TickSource.data);
		axis.getTime().setUnit(unit);
		axis.getTicks().setAutoSkip(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);
		
		chart.getOptions().getScales().setXAxes(axis);
		chart.getOptions().getScales().setYAxes(axis2);

		if (index == 0) {
			chart.setDrawOnAttach(false);
			chart.setDestroyOnDetach(false);
			DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
			pOptions.setBorderWidth(1);
			pOptions.setBorderDash(6);

			chart.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);
			chart.getPlugins().add(selector);
			
			chart.addHandler(new DatasetRangeSelectionEventHandler() {

				@Override
				public void onSelect(DatasetRangeSelectionEvent event) {
					if (event.getFrom() != DatasetRangeSelectionEvent.RESET_SELECTION) {
						int tot = event.getTo() - event.getFrom() + 1;
						currentAmountOfPoint = getAmounts(index+1) * tot;
						addChart();
					}
				}
			}, DatasetRangeSelectionEvent.TYPE);
		}
		return chart;

	}
	
	private TimeUnit getTimeUnit() {
		TimeUnit result = null;
		switch (index) {
		case 0: 
			result = TimeUnit.day;
			break;
		case 1: 
			result = TimeUnit.hour;
			break;
		default:
			break;
		}
		return result;
	}
	
	private long getFrame() {
		long result = 0;
		switch (index) {
		case 0: 
			result = DAY;
			break;
		case 1: 
			result = HOUR;
			break;
		default:
			break;
		}
		return result;
	}
	
	private int getAmounts(int index) {
		int result = 0;
		switch (index) {
		case 0: 
			result = 60;
			break;
		case 1: 
			result = 24;
			break;
		case 2: 
			result = 60;
			break;
		default:
			break;
		}
		return result;
	}
	
	@UiHandler("back")
	protected void handleBack(ClickEvent event) {
		if (charts.size() == 2) {
			removeChart();
		}
	}

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
