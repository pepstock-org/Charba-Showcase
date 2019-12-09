package org.pepstock.charba.showcase.client.views;

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
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorBarCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorDrillingDownCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorLineCase;
import org.pepstock.charba.showcase.client.cases.plugins.DatasetItemsSelectorZoomingCase;
import org.pepstock.charba.showcase.client.cases.plugins.HtmlLegendBuilderBarCase;

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

	@UiHandler("htmlLegendBarPlugin")
	protected void handleHtmlLegendBarPlugin(ClickEvent event) {
		clearPreviousChart();
		content.add(new HtmlLegendBuilderBarCase());
	}

	// ----------------------------------------------
	// Chart pointer
	// ----------------------------------------------

}
