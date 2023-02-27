package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.plugins.AreaBackgroundColorBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.AreaBackgroundLinearGradientBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.AreaBackgroundPatternLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.AreaBackgroundRadialGradientPieCase;
import org.pepstock.charba.showcase.client.cases.plugins.BackgroundColorBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.BackgroundLinearGradientBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.BackgroundPatternLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.BackgroundRadialGradientPieCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemeBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemeBubbleCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemeLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemePieCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemePolarAreaCase;
import org.pepstock.charba.showcase.client.cases.plugins.ColorSchemeRadarCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairGroupCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairHorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairLogarithmicAxisOnScatterCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairScatterCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairStackedAxesCase;
import org.pepstock.charba.showcase.client.cases.plugins.CrosshairTimeSeriesByLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorApiCategoryCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorApiLinearCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorApiTimeCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorBubbleCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorDrillingDownCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorScatterCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorTimeSeriesByBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorZoomingCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendCustomCallbackCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendHorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendLinearGradientCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendMaxItemsCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendPatternCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendPieCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendPointStylesAsCanvasCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendPointStylesAsImageCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendRadialGradientCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendSplittingTextCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendStyleCase;
import org.pepstock.charba.showcase.client.cases.plugins.PointerBarCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PluginsView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, PluginsView> {
	}

	public PluginsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// background color
	// ----------------------------------------------

	@UiHandler("backgroundColorOnBar")
	protected void handleBackgroundColorOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new BackgroundColorBarCase());
	}

	@UiHandler("backgroundGradientOnBar")
	protected void handleBackgroundGradientOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new BackgroundLinearGradientBarCase());
	}

	@UiHandler("backgroundGradientOnPie")
	protected void handleBackgroundGradientOnPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new BackgroundRadialGradientPieCase());
	}

	@UiHandler("backgroundPatternOnLine")
	protected void handleBackgroundPatternOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new BackgroundPatternLineCase());
	}

	@UiHandler("areaBackgroundColorOnBar")
	protected void handleAreaBackgroundColorOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new AreaBackgroundColorBarCase());
	}

	@UiHandler("areaBackgroundGradientOnBar")
	protected void handleAreaBackgroundGradientOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new AreaBackgroundLinearGradientBarCase());
	}

	@UiHandler("areaBackgroundPatternOnLine")
	protected void handleAreaBackgroundPatternOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AreaBackgroundPatternLineCase());
	}

	@UiHandler("areaBackgroundGradientOnPie")
	protected void handleAreaBackgroundGradientOnPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new AreaBackgroundRadialGradientPieCase());
	}

	// ----------------------------------------------
	// Dataset item selector
	// ----------------------------------------------

	@UiHandler("datasetItemsSelectorBar")
	protected void handleDatasetItemsSelectorBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorBarCase());
	}

	@UiHandler("datasetItemsSelectorLine")
	protected void handleDatasetItemsSelectorLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorLineCase());
	}

	@UiHandler("datasetItemsSelectorScatter")
	protected void handleDatasetItemsSelectorScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorScatterCase());
	}
	
	@UiHandler("datasetItemsSelectorBubble")
	protected void handleDatasetItemsSelectorBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorBubbleCase());
	}
	
	@UiHandler("datasetItemsSelectorTimeseriesBar")
	protected void handleDatasetItemsSelectorTimeseriesBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorTimeSeriesByBarCase());
	}

	@UiHandler("datasetItemsSelectorDrillingDown")
	protected void handleDatasetItemsSelectorDrillingDown(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorDrillingDownCase());
	}

	@UiHandler("datasetItemsSelectorZooming")
	protected void handleDatasetItemsSelectorZooming(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorZoomingCase());
	}

	@UiHandler("datasetItemsSelectorCartesianAxis")
	protected void handleDatasetItemsSelectorCartesianAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorApiCategoryCase());
	}
	
	@UiHandler("datasetItemsSelectorTimeAxis")
	protected void handleDatasetItemsSelectorTimeAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorApiTimeCase());
	}
	
	@UiHandler("datasetItemsSelectorLinearAxis")
	protected void handleDatasetItemsSelectorLinearAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetItemsSelectorApiLinearCase());
	}	
	
	// ----------------------------------------------
	// color scheme
	// ----------------------------------------------

	@UiHandler("colorSchemeOnBar")
	protected void handleColorSchemeOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemeBarCase());
	}

	@UiHandler("colorSchemeOnLine")
	protected void handleColorSchemeOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemeLineCase());
	}

	@UiHandler("colorSchemeOnRadar")
	protected void handleColorSchemeOnRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemeRadarCase());
	}

	@UiHandler("colorSchemeOnPie")
	protected void handleColorSchemeOnPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemePieCase());
	}

	//
	@UiHandler("colorSchemeOnPolar")
	protected void handleColorSchemeOnPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemePolarAreaCase());
	}

	@UiHandler("colorSchemeOnBubble")
	protected void handleColorSchemeOnBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new ColorSchemeBubbleCase());
	}

	// ----------------------------------------------
	// html legend
	// ----------------------------------------------

	@UiHandler("htmlLegendOnBar")
	protected void handleHtmlLegendBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendBarCase());
	}

	@UiHandler("htmlLegendOnLine")
	protected void handleHtmlLegendLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendLineCase());
	}

	@UiHandler("htmlLegendOnHorizontalBar")
	protected void handleHtmlLegendHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendHorizontalBarCase());
	}

	@UiHandler("htmlLegendOnPie")
	protected void handleHtmlLegendPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendPieCase());
	}

	@UiHandler("htmlLegendMaxItems")
	protected void handleHtmlLegendMaxItems(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendMaxItemsCase());
	}

	@UiHandler("htmlLegendLineSeparator")
	protected void handleHtmlLegendLineSeparator(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendSplittingTextCase());
	}

	@UiHandler("htmlLegendWithLinearGradient")
	protected void handleHtmlLegendLinearGradient(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendLinearGradientCase());
	}

	@UiHandler("htmlLegendWithRadialGradient")
	protected void handleHtmlLegendRadialGradient(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendRadialGradientCase());
	}

	@UiHandler("htmlLegendWithPattern")
	protected void handleHtmlLegendPattern(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendPatternCase());
	}

	@UiHandler("htmlLegendWithPointStyle")
	protected void handleHtmlLegendPointStyle(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendStyleCase());
	}

	@UiHandler("htmlLegendWithPointStyleImage")
	protected void handleHtmlLegendPointStyleImage(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendPointStylesAsImageCase());
	}

	@UiHandler("htmlLegendWithPointStyleCanvas")
	protected void handleHtmlLegendPointStyleCanvas(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendPointStylesAsCanvasCase());
	}

	@UiHandler("htmlLegendWithCallback")
	protected void handleHtmlLegendWithCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendCustomCallbackCase());
	}

	// ----------------------------------------------
	// Chart pointer
	// ----------------------------------------------

	@UiHandler("pointerCursorOnLine")
	protected void handlePointerCursorOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new PointerBarCase());
	}

	// ----------------------------------------------
	// Crosshair
	// ----------------------------------------------

	@UiHandler("crosshairBar")
	protected void handleCrosshairBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairBarCase());
	}

	@UiHandler("crosshairHorizontalBar")
	protected void handleCrosshairHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairHorizontalBarCase());
	}

	@UiHandler("crosshairScatter")
	protected void handleCrosshairScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairScatterCase());
	}

	@UiHandler("crosshairLogarithmic")
	protected void handleCrosshairLogarithmic(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairLogarithmicAxisOnScatterCase());
	}

	@UiHandler("crosshairTime")
	protected void handleCrosshairTime(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairTimeSeriesByLineCase());
	}
	
	@UiHandler("crosshairStacked")
	protected void handleCrosshairStacked(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairStackedAxesCase());
	}

	@UiHandler("crosshairGrouping")
	protected void handleCrosshairgrouping(ClickEvent event) {
		clearPreviousChart();
		content.add(new CrosshairGroupCase());
	}

	
}
