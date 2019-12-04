package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.jsinterop.LineViewWithGwtMaterialColors;
import org.pepstock.charba.showcase.client.cases.jsinterop.LinearGradientBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LinearGradientLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PatternBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PatternLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.RadialGradientPieView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TilesBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.TilesDoughnutView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class ColoringView extends AbstractCategoryView {

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
		 content.add(new PatternBarView());
	}

	@UiHandler("coloringPatternOnLine")
	protected void handleColoringPatternOnLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PatternLineView());
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
