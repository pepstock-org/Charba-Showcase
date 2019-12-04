package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.jsinterop.ColorSchemeBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.ColorSchemeBubbleView;
import org.pepstock.charba.showcase.client.cases.jsinterop.ColorSchemePieView;
import org.pepstock.charba.showcase.client.cases.jsinterop.HtmlLegendBuilderBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PiePluginView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TimeSeriesDrillDownView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TimeSeriesZoomView;
import org.pepstock.charba.showcase.client.cases.jsinterop.VerticalBarPluginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
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

	@UiHandler("bgcolorplugin")
	protected void handleBGColorPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarPluginView());
	}

	@UiHandler("piebgcolorplugin")
	protected void handlePieBGColorPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PiePluginView());
	}

	// ----------------------------------------------
	// Dataset item selector
	// ----------------------------------------------

	@UiHandler("drilldown")
	protected void handleDrilldown(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesDrillDownView());
	}

	@UiHandler("zooming")
	protected void handleZooming(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesZoomView());
	}


	// ----------------------------------------------
	// color scheme
	// ----------------------------------------------

	@UiHandler("barScheme")
	protected void handleSchemeBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColorSchemeBarView());
	}

	@UiHandler("pieScheme")
	protected void handleSchemePie(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColorSchemePieView());
	}

	@UiHandler("bubbleScheme")
	protected void handleSchemeBubble(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColorSchemeBubbleView());
	}

	// ----------------------------------------------
	// html legend
	// ----------------------------------------------

	@UiHandler("htmlLegendBarPlugin")
	protected void handleHtmlLegendBarPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HtmlLegendBuilderBarView());
	}

	
	// ----------------------------------------------
	// Chart pointer
	// ----------------------------------------------

}
