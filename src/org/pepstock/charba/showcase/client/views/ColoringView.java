package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.coloring.ColoringPatternBarCase;
import org.pepstock.charba.showcase.client.cases.coloring.ColoringPatternBubbleCase;
import org.pepstock.charba.showcase.client.cases.coloring.ColoringPatternLineCase;
import org.pepstock.charba.showcase.client.cases.coloring.ColoringPatternPolarCase;
import org.pepstock.charba.showcase.client.cases.coloring.ColoringPatternRadarCase;
import org.pepstock.charba.showcase.client.cases.jsinterop.LineViewWithGwtMaterialColors;
import org.pepstock.charba.showcase.client.cases.jsinterop.LinearGradientBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LinearGradientLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.RadialGradientPieView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TilesBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TilesDoughnutView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ColoringView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ColoringView> {
	}

	public ColoringView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// Pattern
	// ----------------------------------------------
	
	@UiHandler("coloringPatternOnBar")
	protected void handleColoringPatternOnBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColoringPatternBarCase());
	}

	@UiHandler("coloringPatternOnLine")
	protected void handleColoringPatternOnLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColoringPatternLineCase());
	}

	@UiHandler("coloringPatternOnRadar")
	protected void handleColoringPatternOnRadar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColoringPatternRadarCase());
	}

	@UiHandler("coloringPatternOnPolar")
	protected void handleColoringPatternOnPolar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColoringPatternPolarCase());
	}

	@UiHandler("coloringPatternOnBubble")
	protected void handleColoringPatternOnBubble(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ColoringPatternBubbleCase());
	}
	
	// ----------------------------------------------
	// Gradient
	// ----------------------------------------------

	@UiHandler("coloringLinearGradientOnBar")
	protected void handleColoringLinearGradientOnBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LinearGradientBarView());
	}

	@UiHandler("coloringLinearGradientOnLine")
	protected void handleColoringLinearGradientOnLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LinearGradientLineView());
	}


	@UiHandler("coloringRadialGradientOnPie")
	protected void handleColoringRadialGradientOnPie(ClickEvent event) {
		clearPreviousChart();
		 content.add(new RadialGradientPieView());
	}

	// ----------------------------------------------
	// Tiles
	// ----------------------------------------------

	@UiHandler("coloringTilesOnBar")
	protected void handleColoringTilesOnBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TilesBarView());
	}

	@UiHandler("coloringTilesOnDoughnut")
	protected void handletilesDoughnut(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TilesDoughnutView());
	}

	// ----------------------------------------------
	// GWT material
	// ----------------------------------------------

	@UiHandler("coloringGwtMaterial")
	protected void handlecoloringGwtMaterial(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LineViewWithGwtMaterialColors());
	}

}
