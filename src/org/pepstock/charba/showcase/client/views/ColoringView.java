package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.coloring.FillingBoundariesCase;
import org.pepstock.charba.showcase.client.cases.coloring.FillingDatasetsOnLineCase;
import org.pepstock.charba.showcase.client.cases.coloring.FillingDatasetsOnRadarCase;
import org.pepstock.charba.showcase.client.cases.coloring.GwtMaterialColorsCase;
import org.pepstock.charba.showcase.client.cases.coloring.LinearGradientBarCase;
import org.pepstock.charba.showcase.client.cases.coloring.LinearGradientLineCase;
import org.pepstock.charba.showcase.client.cases.coloring.PatternBarCase;
import org.pepstock.charba.showcase.client.cases.coloring.PatternBubbleCase;
import org.pepstock.charba.showcase.client.cases.coloring.PatternLineCase;
import org.pepstock.charba.showcase.client.cases.coloring.PatternPolarCase;
import org.pepstock.charba.showcase.client.cases.coloring.PatternRadarCase;
import org.pepstock.charba.showcase.client.cases.coloring.RadialGradientPieCase;
import org.pepstock.charba.showcase.client.cases.coloring.RadialGradientPolarCase;
import org.pepstock.charba.showcase.client.cases.coloring.RadialGradientRadarCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesBarCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesCharactersCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesDoughnutCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesImagesCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesLineCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesPointStylesCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesPolarCase;
import org.pepstock.charba.showcase.client.cases.coloring.TilesRadarCase;
import org.pepstock.charba.showcase.client.cases.coloring.UiGradientsCase;

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
		content.add(new PatternBarCase());
	}

	@UiHandler("coloringPatternOnLine")
	protected void handleColoringPatternOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new PatternLineCase());
	}

	@UiHandler("coloringPatternOnRadar")
	protected void handleColoringPatternOnRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new PatternRadarCase());
	}

	@UiHandler("coloringPatternOnPolar")
	protected void handleColoringPatternOnPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new PatternPolarCase());
	}

	@UiHandler("coloringPatternOnBubble")
	protected void handleColoringPatternOnBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new PatternBubbleCase());
	}

	// ----------------------------------------------
	// Gradient
	// ----------------------------------------------

	@UiHandler("coloringLinearGradientOnBar")
	protected void handleColoringLinearGradientOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new LinearGradientBarCase());
	}

	@UiHandler("coloringLinearGradientOnLine")
	protected void handleColoringLinearGradientOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LinearGradientLineCase());
	}

	@UiHandler("coloringRadialGradientOnRadar")
	protected void handleColoringRadialGradientOnRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new RadialGradientRadarCase());
	}

	@UiHandler("coloringRadialGradientOnPie")
	protected void handleColoringRadialGradientOnPie(ClickEvent event) {
		clearPreviousChart();
		content.add(new RadialGradientPieCase());
	}

	@UiHandler("coloringRadialGradientOnPolar")
	protected void handleColoringRadialGradientOnPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new RadialGradientPolarCase());
	}

	// ----------------------------------------------
	// Tiles
	// ----------------------------------------------

	@UiHandler("coloringTilesOnBar")
	protected void handleTilesOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesBarCase());
	}

	@UiHandler("coloringTilesOnLine")
	protected void handleTilesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesLineCase());
	}

	@UiHandler("coloringTilesOnRadar")
	protected void handleTilesOnRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesRadarCase());
	}

	@UiHandler("coloringTilesOnPolar")
	protected void handleTilesOnPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesPolarCase());
	}

	@UiHandler("coloringTilesOnDoughnut")
	protected void handleTilesOnDoughnut(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesDoughnutCase());
	}
	
	@UiHandler("coloringTilesByImageShapes")
	protected void handleImagesTiles(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesImagesCase());
	}

	@UiHandler("coloringTilesByCharShapes")
	protected void handleCharactersTiles(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesCharactersCase());
	}

	@UiHandler("coloringTilesByPointStyleShapes")
	protected void handlePoinStylesTiles(ClickEvent event) {
		clearPreviousChart();
		content.add(new TilesPointStylesCase());
	}	
	
	// ----------------------------------------------
	// Palette
	// ----------------------------------------------

	@UiHandler("coloringGwtMaterial")
	protected void handleColoringGwtMaterial(ClickEvent event) {
		clearPreviousChart();
		content.add(new GwtMaterialColorsCase());
	}

	@UiHandler("coloringUiGradients")
	protected void handleColoringUiGradients(ClickEvent event) {
		clearPreviousChart();
		content.add(new UiGradientsCase());
	}

	// ----------------------------------------------
	// Filling type
	// ----------------------------------------------

	@UiHandler("fillingBoundaries")
	protected void handleFillingBoundaries(ClickEvent event) {
		clearPreviousChart();
		content.add(new FillingBoundariesCase());
	}

	@UiHandler("fillingDatasetsOnLine")
	protected void handleFillingDatasetsOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new FillingDatasetsOnLineCase());
	}

	@UiHandler("fillingDatasetsOnRadar")
	protected void handleFillingDatasetsOnRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new FillingDatasetsOnRadarCase());
	}

}
