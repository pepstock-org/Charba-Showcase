package org.pepstock.charba.showcase.client;

import org.pepstock.charba.showcase.client.samples.AnimationView;
import org.pepstock.charba.showcase.client.samples.BoundariesAreaView;
import org.pepstock.charba.showcase.client.samples.BubbleView;
import org.pepstock.charba.showcase.client.samples.ComboBarLineView;
import org.pepstock.charba.showcase.client.samples.DatasetSelectionPieView;
import org.pepstock.charba.showcase.client.samples.DatasetSelectionView;
import org.pepstock.charba.showcase.client.samples.DoughnutView;
import org.pepstock.charba.showcase.client.samples.FilteringLabelsView;
import org.pepstock.charba.showcase.client.samples.GridLinesDisplayView;
import org.pepstock.charba.showcase.client.samples.GridLinesStyleView;
import org.pepstock.charba.showcase.client.samples.HomeView;
import org.pepstock.charba.showcase.client.samples.HorizontalBarView;
import org.pepstock.charba.showcase.client.samples.LegendPositioningView;
import org.pepstock.charba.showcase.client.samples.LegendStyleView;
import org.pepstock.charba.showcase.client.samples.LineInterpolationView;
import org.pepstock.charba.showcase.client.samples.LineView;
import org.pepstock.charba.showcase.client.samples.LinearLogView;
import org.pepstock.charba.showcase.client.samples.LogScatterView;
import org.pepstock.charba.showcase.client.samples.MinMaxView;
import org.pepstock.charba.showcase.client.samples.MultiAxisBarView;
import org.pepstock.charba.showcase.client.samples.MultiAxisLineView;
import org.pepstock.charba.showcase.client.samples.MultiAxisScatterView;
import org.pepstock.charba.showcase.client.samples.MultilineAxesView;
import org.pepstock.charba.showcase.client.samples.NoNumericYAxesView;
import org.pepstock.charba.showcase.client.samples.PieView;
import org.pepstock.charba.showcase.client.samples.PointSizeLineView;
import org.pepstock.charba.showcase.client.samples.PointStyleLineView;
import org.pepstock.charba.showcase.client.samples.PolarAreaView;
import org.pepstock.charba.showcase.client.samples.RadarView;
import org.pepstock.charba.showcase.client.samples.ScatterView;
import org.pepstock.charba.showcase.client.samples.StackedAreaView;
import org.pepstock.charba.showcase.client.samples.StackedBarView;
import org.pepstock.charba.showcase.client.samples.StackedGroupBarView;
import org.pepstock.charba.showcase.client.samples.StepSizeView;
import org.pepstock.charba.showcase.client.samples.SteppedLineView;
import org.pepstock.charba.showcase.client.samples.StyledLineView;
import org.pepstock.charba.showcase.client.samples.TooltipBorderView;
import org.pepstock.charba.showcase.client.samples.TooltipCallbacksView;
import org.pepstock.charba.showcase.client.samples.TooltipHTMLPieView;
import org.pepstock.charba.showcase.client.samples.TooltipHTMLlineView;
import org.pepstock.charba.showcase.client.samples.TooltipInteractionsView;
import org.pepstock.charba.showcase.client.samples.TooltipPositioningView;
import org.pepstock.charba.showcase.client.samples.VerticalBarPluginLabelView;
import org.pepstock.charba.showcase.client.samples.VerticalBarPluginView;
import org.pepstock.charba.showcase.client.samples.VerticalBarView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class DemoView extends Composite {
	
	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, DemoView> {
	}

	@UiField
	VerticalPanel content;

	public DemoView() {
		initWidget(uiBinder.createAndBindUi(this));
		content.add(new HomeView());
	}

	private void clearPreviousChart() {
		int count = content.getWidgetCount();
		for (int i = 0; i < count; i++) {
			content.remove(0);
		}
	}

	@UiHandler("home")
	protected void handleHome(ClickEvent event) {
		clearPreviousChart();
		content.add(new HomeView());
	}

	@UiHandler("barVertical")
	protected void handleBarVertical(ClickEvent event) {
		clearPreviousChart();
		content.add(new VerticalBarView());
	}

	@UiHandler("barHorizontal")
	protected void handleBarHorizontal(ClickEvent event) {
		clearPreviousChart();
		content.add(new HorizontalBarView());
	}

	@UiHandler("barMultiAxis")
	protected void handleBarMultiAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisBarView());
	}

	@UiHandler("barStacked")
	protected void handleBarStacked(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedBarView());
	}

	@UiHandler("barStackedGroups")
	protected void handleBarStackedGroups(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedGroupBarView());
	}

	@UiHandler("lineBasic")
	protected void handleLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineView());
	}

	@UiHandler("lineMultiAxis")
	protected void handleLineMultiAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisLineView());
	}

	@UiHandler("lineStepped")
	protected void handleLineStepped(ClickEvent event) {
		clearPreviousChart();
		content.add(new SteppedLineView());
	}

	@UiHandler("lineInterpolation")
	protected void handleLineInterpolation(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineInterpolationView());
	}

	@UiHandler("lineStyles")
	protected void handleLineStyles(ClickEvent event) {
		clearPreviousChart();
		content.add(new StyledLineView());
	}

	@UiHandler("linePointStyles")
	protected void handleLinePointStyles(ClickEvent event) {
		clearPreviousChart();
		content.add(new PointStyleLineView());
	}

	@UiHandler("linePointSize")
	protected void handleLinePointSize(ClickEvent event) {
		clearPreviousChart();
		content.add(new PointSizeLineView());
	}

	@UiHandler("areaBoundaries")
	protected void handleAreaBoundaries(ClickEvent event) {
		clearPreviousChart();
		content.add(new BoundariesAreaView());
	}

	@UiHandler("areaStacked")
	protected void handleAreaStacked(ClickEvent event) {
		clearPreviousChart();
		content.add(new StackedAreaView());
	}

	@UiHandler("scatter")
	protected void handleScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new ScatterView());
	}

	@UiHandler("scatterMulti")
	protected void handleScatterMulti(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultiAxisScatterView());
	}

	@UiHandler("doughnut")
	protected void handleDoughnut(ClickEvent event) {
		clearPreviousChart();
		content.add(new DoughnutView());
	}

	@UiHandler("pie")
	protected void handlePie(ClickEvent event) {
		clearPreviousChart();
		content.add(new PieView());
	}

	@UiHandler("polar")
	protected void handlePolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new PolarAreaView());
	}

	@UiHandler("radar")
	protected void handleRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new RadarView());
	}

	@UiHandler("bubble")
	protected void handleBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new BubbleView());
	}

	@UiHandler("combobarline")
	protected void handleComboBarLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ComboBarLineView());
	}

	@UiHandler("stepsize")
	protected void handleStepSize(ClickEvent event) {
		clearPreviousChart();
		content.add(new StepSizeView());
	}

	@UiHandler("minmax")
	protected void handleMinMax(ClickEvent event) {
		clearPreviousChart();
		content.add(new MinMaxView());
	}

	@UiHandler("loglinear")
	protected void handleLogLinear(ClickEvent event) {
		clearPreviousChart();
		content.add(new LinearLogView());
	}

	@UiHandler("logscatter")
	protected void handleLogScatter(ClickEvent event) {
		clearPreviousChart();
		content.add(new LogScatterView());
	}

	@UiHandler("glinesdisplay")
	protected void handleGridLinesDisplay(ClickEvent event) {
		clearPreviousChart();
		content.add(new GridLinesDisplayView());
	}

	@UiHandler("glinesstyle")
	protected void handlegridLinesStyle(ClickEvent event) {
		clearPreviousChart();
		content.add(new GridLinesStyleView());
	}

	@UiHandler("multilabels")
	protected void handleMultiLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new MultilineAxesView());
	}

	@UiHandler("filterlabels")
	protected void handleFilterLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new FilteringLabelsView());
	}

	@UiHandler("nomumericYaxis")
	protected void handleNoMumericYAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new NoNumericYAxesView());
	}

	@UiHandler("legendpositioning")
	protected void handleLegendPositioning(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendPositioningView());
	}

	@UiHandler("legendstyle")
	protected void handleLegendStyle(ClickEvent event) {
		clearPreviousChart();
		content.add(new LegendStyleView());
	}

	@UiHandler("tooltippositioning")
	protected void handleTooltipPositioning(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipPositioningView());
	}

	@UiHandler("tooltipinteractions")
	protected void handletooltipInteractions(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipInteractionsView());
	}

	@UiHandler("tooltipcallbacks")
	protected void handletooltipCallbacks(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipCallbacksView());
	}

	@UiHandler("tooltipborder")
	protected void handleBorder(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipBorderView());
	}

	@UiHandler("tooltiphtmlline")
	protected void handleHTMLline(ClickEvent event) {
		clearPreviousChart();
		content.add(new TooltipHTMLlineView());
	}

	@UiHandler("tooltiphtmlpie")
	protected void handleHTMLpie(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TooltipHTMLPieView());
	}

	@UiHandler("animation")
	protected void handleAnimation(ClickEvent event) {
		clearPreviousChart();
		 content.add(new AnimationView());
	}

	@UiHandler("dsselection")
	protected void handleDatasetSelection(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DatasetSelectionView());
	}

	@UiHandler("dsselectionpie")
	protected void handleDatasetSelectionPie(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DatasetSelectionPieView());
	}

	@UiHandler("bgcolorplugin")
	protected void handleBGColorPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarPluginView());
	}

	@UiHandler("labelplugin")
	protected void handleLabelPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarPluginLabelView());
	}

}
