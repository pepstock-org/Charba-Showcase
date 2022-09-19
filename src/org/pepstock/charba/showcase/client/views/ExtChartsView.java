package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.extensions.GeoBubbleMapDatalabelsCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoBubbleMapLogarithmicCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoChoroplethGermanyCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoChoroplethItalyCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoChoroplethLogarithmicCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoChoroplethSelectCountryCase;
import org.pepstock.charba.showcase.client.cases.extensions.GeoChoroplethUSCapitalsCase;
import org.pepstock.charba.showcase.client.cases.extensions.MatrixCalendarCase;
import org.pepstock.charba.showcase.client.cases.extensions.MatrixClickEventCase;
import org.pepstock.charba.showcase.client.cases.extensions.MatrixDatalabelsCase;
import org.pepstock.charba.showcase.client.cases.extensions.MatrixOnCategoryAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.MatrixOnTimeAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyBasicCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyClickCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyColumnsCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyCountriesCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyEnergyCase;
import org.pepstock.charba.showcase.client.cases.extensions.SankeyTreeCase;
import org.pepstock.charba.showcase.client.cases.extensions.TreeMapClickEventCase;
import org.pepstock.charba.showcase.client.cases.extensions.TreeMapDatalabelsCase;
import org.pepstock.charba.showcase.client.cases.extensions.TreeMapDividersCase;
import org.pepstock.charba.showcase.client.cases.extensions.TreeMapUSPopulationCase;
import org.pepstock.charba.showcase.client.cases.extensions.TreeMapUSSwitchableCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ExtChartsView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ExtChartsView> {
	}

	public ExtChartsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// Geo map chart
	// ----------------------------------------------

	@UiHandler("choroplethInterpolation")
	protected void handleChoroplethInterpolation(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethItalyCase());
	}

	@UiHandler("choroplethCustomInterpolation")
	protected void handleChoroplethCustomInterpolation(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethGermanyCase());
	}

	@UiHandler("choroplethClick")
	protected void handleChoroplethClick(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethSelectCountryCase());
	}

	@UiHandler("bubblemapDataLabels")
	protected void handleBubbleMapDataLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoBubbleMapDatalabelsCase());
	}
	
	@UiHandler("choroplethLog")
	protected void handleChoroplethLog(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethLogarithmicCase());
	}

	@UiHandler("bubblemapLog")
	protected void handleBubbleMapLog(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoBubbleMapLogarithmicCase());
	}

	@UiHandler("choroplethCapitals")
	protected void handleChoroplethCapitals(ClickEvent event) {
		clearPreviousChart();
		content.add(new GeoChoroplethUSCapitalsCase());
	}

	// ----------------------------------------------
	// Treemap
	// ----------------------------------------------

	@UiHandler("treemapDividers")
	protected void handleTreemapDividers(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapDividersCase());
	}
	
	@UiHandler("treemapUSpopulation")
	protected void handleTreemapUSpopulation(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapUSPopulationCase());
	}

	@UiHandler("treemapGrouping")
	protected void handleTreemapGrouping(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapUSSwitchableCase());
	}

	@UiHandler("treemapClick")
	protected void handleTreemapClicking(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapClickEventCase());
	}

	@UiHandler("treemapDatalabels")
	protected void handleTreemapDatalabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new TreeMapDatalabelsCase());
	}

	// ----------------------------------------------
	// Matrix
	// ----------------------------------------------

	@UiHandler("matrixCalendar")
	protected void handleMatrixCalendar(ClickEvent event) {
		clearPreviousChart();
		content.add(new MatrixCalendarCase());
	}

	@UiHandler("matrixOnTimeAxis")
	protected void handleMatrixOnTimeAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MatrixOnTimeAxisCase());
	}
	
	@UiHandler("matrixOnCategoryAxis")
	protected void handleMatrixOnCategoryAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MatrixOnCategoryAxisCase());
	}

	@UiHandler("matrixClick")
	protected void handleMatrixClicking(ClickEvent event) {
		clearPreviousChart();
		content.add(new MatrixClickEventCase());
	}
	
	@UiHandler("matrixDatalabels")
	protected void handleMatrixDatalabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new MatrixDatalabelsCase());
	}
	
	// ----------------------------------------------
	// Matrix
	// ----------------------------------------------

	@UiHandler("sankeyEnergy")
	protected void handleSankeyEnergy(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyEnergyCase());
	}
	
	@UiHandler("sankeyCountries")
	protected void handleSankeyCountries(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyCountriesCase());
	}

	@UiHandler("sankeyBasic")
	protected void handleSankeyBasic(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyBasicCase());
	}

	@UiHandler("sankeyTree")
	protected void handleSankeyTree(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyTreeCase());
	}

	@UiHandler("sankeyClick")
	protected void handleSankeyClick(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyClickCase());
	}

	@UiHandler("sankeyColumns")
	protected void handleSankeyColumns(ClickEvent event) {
		clearPreviousChart();
		content.add(new SankeyColumnsCase());
	}

}
