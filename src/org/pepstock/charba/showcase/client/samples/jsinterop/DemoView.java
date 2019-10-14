package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.Injector;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.controllers.AbstractController;
import org.pepstock.charba.client.controllers.ControllerContext;
import org.pepstock.charba.client.controllers.ControllerType;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.impl.plugins.ChartBackgroundColor;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelector;
import org.pepstock.charba.client.impl.plugins.DatasetsItemsSelectorOptions;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.items.DatasetViewItem;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.resources.ResourcesType;
import org.pepstock.charba.showcase.client.resources.MyResources;
import org.pepstock.charba.showcase.client.samples.HomeView;

import com.google.gwt.canvas.dom.client.Context2d;
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
		
		Injector.ensureInjected(ResourcesType.getClientBundle().chartJs());
		Injector.ensureInjected(ResourcesType.getClientBundle().charbaHelper());
		Injector.ensureInjected(MyResources.INSTANCE.chartJsAnnotationSource());
		
		Defaults.get().getScale().getScaleLabel().setDisplay(true);
		
		Defaults.get().getPlugins().register(new ChartBackgroundColor());

		Defaults.get().getControllers().extend(new AbstractController() {

			@Override
			public ControllerType getType() {
				return LineMyChart.TYPE;
			}

			@Override
			public void draw(ControllerContext jsThis, IsChart chart, double ease) {
				super.draw(jsThis, chart, ease);

				DatasetMetaItem metaItem = chart.getDatasetMeta(jsThis.getIndex());
				List<DatasetItem> items = metaItem.getDatasets();
				for (DatasetItem item : items) {
					DatasetViewItem view = item.getView();
					Context2d ctx = chart.getCanvas().getContext2d();
					ctx.save();
					ctx.setStrokeStyle(view.getBorderColorAsString());
					ctx.setLineWidth(1D);
					ctx.strokeRect(view.getX() - 10, view.getY() - 10,  20, 20);
					ctx.restore();
				}
			}
		});
		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(1);
		pOptions.setBorderDash(6);
		Defaults.get().getGlobal().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions);

		Defaults.get().getControllers().extend(new MyHorizontalBarController());
		
		LabelsPlugin.enable();
		
		DataLabelsPlugin.enable();
	}

	private void clearPreviousChart() {
		int count = content.getWidgetCount();
		for (int i = 0; i < count; i++) {
			content.remove(i);
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

	@UiHandler("barCallback")
	protected void handleBarVerticalCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new VerticalBarCallbackView());
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

	@UiHandler("lineCallback")
	protected void handleLineCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineCallbackView());
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

	@UiHandler("tooltipPositioner")
	protected void handlePositioner(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TooltipPositionerView());
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

	@UiHandler("piebgcolorplugin")
	protected void handlePieBGColorPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PiePluginView());
	}

	

	@UiHandler("labelplugin")
	protected void handleLabelPlugin(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarPluginLabelView());
	}

	@UiHandler("gauge")
	protected void handleGauge(ClickEvent event) {
		clearPreviousChart();
		 content.add(new GaugeView());
	}

	@UiHandler("meter")
	protected void handleMeter(ClickEvent event) {
		clearPreviousChart();
		 content.add(new MeterView());
	}

	@UiHandler("linets")
	protected void handleTimeseries(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesView());
	}

	@UiHandler("barts")
	protected void handleBarTimeseries(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesBarView());
	}

	@UiHandler("lineWithGwtMaterialColors")
	protected void handleGwtMaterialColors(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LineViewWithGwtMaterialColors());
	}

	@UiHandler("flagsplugin")
	protected void handleFlags(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HorizontalFlagsBarView());
	}
	
	@UiHandler("standings")
	protected void handleStandings(ClickEvent event) {
		clearPreviousChart();
		 content.add(new StandingView());
	}
	
	
	@UiHandler("myline")
	protected void handleMyLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LineMyView());
	}
	
	@UiHandler("myflags")
	protected void handleMyFlags(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HorizontalMyFlagsBarView());
	}
	
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

	@UiHandler("linePattern")
	protected void handleLinePattern(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PatternLineView());
	}

	@UiHandler("lineGradient")
	protected void handleLineGradient(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LinearGradientLineView());
	}

	@UiHandler("barGradient")
	protected void handleBarGradient(ClickEvent event) {
		clearPreviousChart();
		 content.add(new LinearGradientBarView());
	}

	@UiHandler("pieGradient")
	protected void handlePieGradient(ClickEvent event) {
		clearPreviousChart();
		 content.add(new RadialGradientPieView());
	}

	@UiHandler("barPattern")
	protected void handleBarPattern(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PatternBarView());
	}

	@UiHandler("linePointStyleImages")
	protected void handlePointStylesImages(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PointStyleImageView());
	}

	@UiHandler("datalabelsBar")
	protected void handleDatalabelsBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsBarView());
	}

	@UiHandler("datalabelsBubble")
	protected void handleDatalabelsBubble(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsBubbleView());
	}

	@UiHandler("datalabelsLine")
	protected void handleDatalabelsLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsLineView());
	}

	@UiHandler("datalabelsDoughnut")
	protected void handleDatalabelsDoughnut(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsDoughnutView());
	}

	@UiHandler("datalabelsPolar")
	protected void handleDatalabelsPolar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsPolarAreaView());
	}
	
	@UiHandler("datalabelsRadar")
	protected void handleDatalabelsRadar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsRadarView());
	}

	@UiHandler("datalabelsInteractions")
	protected void handleDatalabelsInteractions(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsInteractionsView());
	}	
	
	@UiHandler("datalabelsData")
	protected void handleDatalabelsData(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsDataView());
	}	
	
	@UiHandler("datalabelsDataset")
	protected void handleDatalabelsDataset(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsDatasetView());
	}	


	@UiHandler("datalabelsIndices")
	protected void handleDatalabelsIndices(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsIndicesView());
	}	

	@UiHandler("datalabelsMirror")
	protected void handleDatalabelsMirror(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsMirrorView());
	}	

	@UiHandler("datalabelsListeners")
	protected void handleDatalabelsListeners(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsListenersView());
	}	

	@UiHandler("datalabelsHighlight")
	protected void handleDatalabelsHighlight(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsHighlightView());
	}	

	@UiHandler("datalabelsGradient")
	protected void handleDatalabelsGradient(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsLinearGradientLineView());
	}	
	
	@UiHandler("datalabelsSelection")
	protected void handleDatalabelsSelection(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsSelectionView());
	}	
	
	@UiHandler("datalabelsCustom")
	protected void handleDatalabelsCustomLabels(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsCustomLabelsView());
	}
	
	@UiHandler("piecelabelPieLabels")
	protected void handleLabelsPieLabels(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelView());
	}

	@UiHandler("piecelabelPiePercentage")
	protected void handleLabelsPiePercentage(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelPercentageView());
	}

	@UiHandler("piecelabelPieValue")
	protected void handleLabelsPieValue(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelValueView());
	}

	@UiHandler("piecelabelPiePosition")
	protected void handleLabelsPiePosition(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelPositionView());
	}

	@UiHandler("piecelabelPieImages")
	protected void handleLabelsPieImages(ClickEvent event) {
		clearPreviousChart();
		content.add(new PieceLabelImageView());
	}

	@UiHandler("piecelabelPieMulti")
	protected void handleLabelsPieMulti(ClickEvent event) {
		clearPreviousChart();
		content.add(new PieceLabelMultiOptionsView());
	}

	@UiHandler("piecelabelPolar")
	protected void handleLabelsPolar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelPolarView());
	}

	@UiHandler("piecelabelBar")
	protected void handleLabelsBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PieceLabelBarView());
	}

	@UiHandler("htmlAnnotation")
	protected void handleHTMLAnnotation(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HTMLAnnnotationView());
	}

	@UiHandler("htmlAnnotationByElement")
	protected void handleHTMLAnnotationByElement(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HTMLAnnnotationByElementView());
	}

	@UiHandler("doughnutTiles")
	protected void handletilesDoughnut(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TilesDoughnutView());
	}

	@UiHandler("barTiles")
	protected void handleTilesBar(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TilesBarView());
	}

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

	@UiHandler("linetschart")
	protected void handleLineTimeseriesChart(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesChartView());
	}

	@UiHandler("bartschart")
	protected void handleBarTimeseriesChart(ClickEvent event) {
		clearPreviousChart();
		 content.add(new TimeSeriesBarChartView());
	}

	@UiHandler("customplugin")
	protected void handleCustomPluginChart(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarCustomPluginView());
	}

}
