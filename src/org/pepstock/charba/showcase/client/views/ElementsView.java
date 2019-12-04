package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.elements.FilteringAxisLabelsCase;
import org.pepstock.charba.showcase.client.cases.elements.GridLinesDisplayCase;
import org.pepstock.charba.showcase.client.cases.elements.GridLinesStyleCase;
import org.pepstock.charba.showcase.client.cases.elements.LegendPositioningCase;
import org.pepstock.charba.showcase.client.cases.elements.LegendStyleCase;
import org.pepstock.charba.showcase.client.cases.elements.TicksMinMaxCase;
import org.pepstock.charba.showcase.client.cases.elements.MultiAxisBarCase;
import org.pepstock.charba.showcase.client.cases.elements.MultiAxisLineCase;
import org.pepstock.charba.showcase.client.cases.elements.MultiAxisScatterCase;
import org.pepstock.charba.showcase.client.cases.elements.MultiLineAxisLabelsCase;
import org.pepstock.charba.showcase.client.cases.elements.NoNumericYAxisCase;
import org.pepstock.charba.showcase.client.cases.elements.TicksStepSizeCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipBorderCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipCallbacksCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipHTMLPieCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipHTMLlineCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipInteractionsCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipPositionerCase;
import org.pepstock.charba.showcase.client.cases.elements.TooltipPositioningCase;
import org.pepstock.charba.showcase.client.cases.jsinterop.LinearLogView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LogScatterView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class ElementsView extends AbstractCategoryView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ElementsView> {
	}

	public ElementsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// Legend
	// ----------------------------------------------

	@UiHandler("legendPositioning")
	protected void handleLegendPositioning(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendPositioningCase());
	}

	@UiHandler("legendStyle")
	protected void handleLegendStyle(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendStyleCase());
	}

	// ----------------------------------------------
	// Tooltip
	// ----------------------------------------------

	@UiHandler("tooltippositioning")
	protected void handleTooltipPositioning(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipPositioningCase());
	}

	@UiHandler("tooltipinteractions")
	protected void handletooltipInteractions(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipInteractionsCase());
	}

	@UiHandler("tooltipcallbacks")
	protected void handletooltipCallbacks(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipCallbacksCase());
	}

	@UiHandler("tooltipborder")
	protected void handleBorder(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipBorderCase());
	}

	@UiHandler("tooltiphtmlline")
	protected void handleHTMLline(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipHTMLlineCase());
	}

	@UiHandler("tooltiphtmlpie")
	protected void handleHTMLpie(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TooltipHTMLPieCase());
	}

	@UiHandler("tooltipPositioner")
	protected void handlePositioner(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TooltipPositionerCase());
	}

	// ----------------------------------------------
	// Axis
	// ----------------------------------------------
	
	@UiHandler("multiAxesBar")
	protected void handleBarMultiAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisBarCase());
	}

	@UiHandler("multiAxesLine")
	protected void handleLineMultiAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisLineCase());
	}

	@UiHandler("multiAxesScatter")
	protected void handleScatterMulti(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisScatterCase());
	}

	@UiHandler("gridLinesDisplay")
	protected void handleGridLinesDisplay(ClickEvent event) {
		clearPreviousChart();
		content.add(new GridLinesDisplayCase());
	}

	@UiHandler("gridLinesStyle")
	protected void handlegridLinesStyle(ClickEvent event) {
		clearPreviousChart();
		content.add(new GridLinesStyleCase());
	}

	@UiHandler("multiLinesAxisLabels")
	protected void handleMultiLinesAxisLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiLineAxisLabelsCase());
	}

	@UiHandler("filterLabels")
	protected void handleFilterLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new FilteringAxisLabelsCase());
	}

	@UiHandler("noNumericYaxis")
	protected void handleNoMumericYAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new NoNumericYAxisCase());
	}
	
	@UiHandler("ticksStepsize")
	protected void handleTicksStepsize(ClickEvent event) {
		clearPreviousChart();
		content.add(new TicksStepSizeCase());
	}

	@UiHandler("ticksMinMax")
	protected void handleTicksMinMax(ClickEvent event) {
		clearPreviousChart();
		content.add(new TicksMinMaxCase());
	}

	@UiHandler("logarithmicOnLine")
	protected void handleLogarithmicOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LinearLogView());
	}

	@UiHandler("logarithmicOnScatter")
	protected void handleLogarithmicOnScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new LogScatterView());
	}

	// ----------------------------------------------
	// Title
	// ----------------------------------------------

}
