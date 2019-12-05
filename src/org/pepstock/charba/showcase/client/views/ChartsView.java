package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.charts.AreaCase;
import org.pepstock.charba.showcase.client.cases.charts.BarCase;
import org.pepstock.charba.showcase.client.cases.charts.BubbleCase;
import org.pepstock.charba.showcase.client.cases.charts.ComboBarLineCase;
import org.pepstock.charba.showcase.client.cases.charts.DoughnutCase;
import org.pepstock.charba.showcase.client.cases.charts.GaugeCase;
import org.pepstock.charba.showcase.client.cases.charts.HorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.charts.LineCase;
import org.pepstock.charba.showcase.client.cases.charts.MeterCase;
import org.pepstock.charba.showcase.client.cases.charts.PieCase;
import org.pepstock.charba.showcase.client.cases.charts.PolarAreaCase;
import org.pepstock.charba.showcase.client.cases.charts.RadarCase;
import org.pepstock.charba.showcase.client.cases.charts.ScatterCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedBarCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedGroupBarCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedLineCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesBarCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesByBarCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesByLineCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesLineCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChartsView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ChartsView> {
	}

	public ChartsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// CHARTS
	// ----------------------------------------------

	@UiHandler("bar")
	protected void handleBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new BarCase());
	}

	@UiHandler("horizontalBar")
	protected void handleBarHorizontal(ClickEvent event) {
		clearPreviousChart();
		content.add(new HorizontalBarCase());
	}

	@UiHandler("line")
	protected void handleLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineCase());
	}

	@UiHandler("scatter")
	protected void handleScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new ScatterCase());
	}

	@UiHandler("doughnut")
	protected void handleDoughnut(ClickEvent event) {
		clearPreviousChart();
		content.add(new DoughnutCase());
	}

	@UiHandler("pie")
	protected void handlePie(ClickEvent event) {
		clearPreviousChart();
		content.add(new PieCase());
	}

	@UiHandler("polar")
	protected void handlePolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new PolarAreaCase());
	}

	@UiHandler("radar")
	protected void handleRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new RadarCase());
	}

	@UiHandler("bubble")
	protected void handleBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new BubbleCase());
	}

	// ----------------------------------------------
	// Others CHARTS
	// ----------------------------------------------

	@UiHandler("stackedBar")
	protected void handleStackedBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedBarCase());
	}

	@UiHandler("stackedGroupBar")
	protected void handleStackedGroupBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedGroupBarCase());
	}

	@UiHandler("stackedLine")
	protected void handleStackedLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedLineCase());
	}

	@UiHandler("area")
	protected void handleArea(ClickEvent event) {
		clearPreviousChart();
		content.add(new AreaCase());
	}

	@UiHandler("combo")
	protected void handleCombo(ClickEvent event) {
		clearPreviousChart();
		content.add(new ComboBarLineCase());
	}
	
	@UiHandler("timeSeriesLine")
	protected void handleTimeSeriesLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesLineCase());
	}

	@UiHandler("timeSeriesBar")
	protected void handleTimeSeriesBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesBarCase());
	}

	@UiHandler("timeSeriesByLine")
	protected void handleTimeSeriesByLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesByLineCase());
	}

	@UiHandler("timeSeriesByBar")
	protected void handleTimeSeriesByBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesByBarCase());
	}

	// ----------------------------------------------
	// EXTENDED CHARTS
	// ----------------------------------------------

	@UiHandler("meter")
	protected void handleMeter(ClickEvent event) {
		clearPreviousChart();
		content.add(new MeterCase());
	}

	@UiHandler("gauge")
	protected void handleGauge(ClickEvent event) {
		clearPreviousChart();
		content.add(new GaugeCase());
	}

}