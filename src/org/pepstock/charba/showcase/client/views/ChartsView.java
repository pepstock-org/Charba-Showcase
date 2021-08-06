package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.charts.AxesEventsCase;
import org.pepstock.charba.showcase.client.cases.charts.BarCase;
import org.pepstock.charba.showcase.client.cases.charts.BubbleCase;
import org.pepstock.charba.showcase.client.cases.charts.ChartClickEventCase;
import org.pepstock.charba.showcase.client.cases.charts.ChartHoverEventCase;
import org.pepstock.charba.showcase.client.cases.charts.ChartResizeEventCase;
import org.pepstock.charba.showcase.client.cases.charts.ComboBarLineCase;
import org.pepstock.charba.showcase.client.cases.charts.DatasetSelectionBarCase;
import org.pepstock.charba.showcase.client.cases.charts.DatasetSelectionPieCase;
import org.pepstock.charba.showcase.client.cases.charts.DoughnutCase;
import org.pepstock.charba.showcase.client.cases.charts.GaugeCase;
import org.pepstock.charba.showcase.client.cases.charts.GeoBubbleMapUSCase;
import org.pepstock.charba.showcase.client.cases.charts.GeoChoroplethCase;
import org.pepstock.charba.showcase.client.cases.charts.GeoChoroplethUSCase;
import org.pepstock.charba.showcase.client.cases.charts.HorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.charts.LegendClickEventCase;
import org.pepstock.charba.showcase.client.cases.charts.LegendHoverAndLeaveEventsCase;
import org.pepstock.charba.showcase.client.cases.charts.LineCase;
import org.pepstock.charba.showcase.client.cases.charts.MeterCase;
import org.pepstock.charba.showcase.client.cases.charts.PieCase;
import org.pepstock.charba.showcase.client.cases.charts.PolarAreaCase;
import org.pepstock.charba.showcase.client.cases.charts.RadarCase;
import org.pepstock.charba.showcase.client.cases.charts.ScatterCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedAreaCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedBarCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedGroupBarCase;
import org.pepstock.charba.showcase.client.cases.charts.StackedLineCase;
import org.pepstock.charba.showcase.client.cases.charts.SubtitleEventsCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesBarCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesByBarCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesByLineCase;
import org.pepstock.charba.showcase.client.cases.charts.TimeSeriesLineCase;
import org.pepstock.charba.showcase.client.cases.charts.TitleEventsCase;
import org.pepstock.charba.showcase.client.cases.charts.TreeMapCase;
import org.pepstock.charba.showcase.client.cases.charts.VerticalLineCase;

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

	@UiHandler("verticalLine")
	protected void handleVerticalLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new VerticalLineCase());
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

	@UiHandler("stackedArea")
	protected void handleStackedArea(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedAreaCase());
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

	@UiHandler("treemap")
	protected void handleTreeMap(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapCase());
	}

	@UiHandler("choroplethEarth")
	protected void handleChoropleth(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethCase());
	}

	@UiHandler("choroplethUS")
	protected void handleChoroplethUS(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethUSCase());
	}

	@UiHandler("bubbleMapUS")
	protected void handleBubbleMapUS(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoBubbleMapUSCase());
	}


	// ----------------------------------------------
	// Events
	// ----------------------------------------------

	@UiHandler("chartClickEvent")
	protected void handleChartClickEvent(ClickEvent event) {
		clearPreviousChart();
		content.add(new ChartClickEventCase());
	}

	@UiHandler("chartHoverEvent")
	protected void handleChartHoverEvent(ClickEvent event) {
		clearPreviousChart();
		content.add(new ChartHoverEventCase());
	}

	@UiHandler("chartResizeEvent")
	protected void handleChartResizeEvent(ClickEvent event) {
		clearPreviousChart();
		content.add(new ChartResizeEventCase());
	}

	@UiHandler("chartDatasetSelectionEventOnBar")
	protected void handleChartDatasetSelectionEventOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetSelectionBarCase());
	}

	@UiHandler("chartDatasetSelectionEventOnPie")
	protected void handleChartDatasetSelectionEventOnPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetSelectionPieCase());
	}

	@UiHandler("legendClickEvent")
	protected void handleLegendClickEvent(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendClickEventCase());
	}

	@UiHandler("legendHoverAndLeaveEvent")
	protected void handleLegendHoverAndLeaveEvent(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendHoverAndLeaveEventsCase());
	}

	@UiHandler("titleEvents")
	protected void handleTitlevents(ClickEvent event) {
		clearPreviousChart();
		content.add(new TitleEventsCase());
	}

	@UiHandler("subtitleEvents")
	protected void handleSubtitlevents(ClickEvent event) {
		clearPreviousChart();
		content.add(new SubtitleEventsCase());
	}
	
	@UiHandler("axesEvents")
	protected void handleAxesClickEvents(ClickEvent event) {
		clearPreviousChart();
		content.add(new AxesEventsCase());
	}

	public void setGallery(String gallery) {
		if (gallery != null) {
			if ("bar".equalsIgnoreCase(gallery)) {
				handleBar(null);
			} else if ("horizontalbar".equalsIgnoreCase(gallery)) {
				handleBarHorizontal(null);;
			} else if ("line".equalsIgnoreCase(gallery)) {
				handleLine(null);
			} else if ("verticalline".equalsIgnoreCase(gallery)) {
				handleVerticalLine(null);
			} else if ("scatter".equalsIgnoreCase(gallery)) {
				handleScatter(null);
			} else if ("doughnut".equalsIgnoreCase(gallery)) {
				handleDoughnut(null);
			} else if ("pie".equalsIgnoreCase(gallery)) {
				handlePie(null);
			} else if ("polararea".equalsIgnoreCase(gallery)) {
				handlePolar(null);
			} else if ("radar".equalsIgnoreCase(gallery)) {
				handleRadar(null);
			} else if ("bubble".equalsIgnoreCase(gallery)) {
				handleBubble(null);
			} else if ("timeseries".equalsIgnoreCase(gallery)) {
				handleTimeSeriesLine(null);
			} else if ("stacked".equalsIgnoreCase(gallery)) {
				handleStackedBar(null);
			} else if ("meter".equalsIgnoreCase(gallery)) {
				handleMeter(null);
			} else if ("gauge".equalsIgnoreCase(gallery)) {
				handleGauge(null);
			} else if ("choropleth".equalsIgnoreCase(gallery)) {
				handleChoroplethUS(null);
			} else if ("bubblemap".equalsIgnoreCase(gallery)) {
				handleBubbleMapUS(null);
			} else if ("treemap".equalsIgnoreCase(gallery)) {
				handleTreeMap(null);
			}
		}
	}
}
