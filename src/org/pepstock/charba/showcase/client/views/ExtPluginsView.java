package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.extensions.AnnotationBoxesOnLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationEllipseOnLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationLabelCalloutOnLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationLabelOnLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationLineOnHorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationLineOnLogarithmicAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationLineOnTimeSeriesLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationObliqueLineOnTimeSeriesLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationPointsOnLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationsEventsOnTimeSeriesCase;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationsEventsWithModifierKey;
import org.pepstock.charba.showcase.client.cases.extensions.AnnotationsOnCombinedCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsBarCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsBubbleCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsCustomLabelsCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsDataCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsDatasetCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsDoughnutCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsHighlightCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsIndicesCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsInteractionsCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsLinearGradientLineCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsListenersCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsMirrorCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsMultiLabelsCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsPolarAreaCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsRadarCase;
import org.pepstock.charba.showcase.client.cases.extensions.DataLabelsSelectionCase;
import org.pepstock.charba.showcase.client.cases.extensions.ImportingPluginCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsBarCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsMultiOptionsCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsPolarCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsPositioningCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingImageRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingLabelRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingPercentageRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingValueRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomApiPanCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomApiZoomCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomApiZoomScaleCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomBasicCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomCallbacksOnTimeSeriesCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomCategoryAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomDragCategoryAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomDragLinearAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomDragTimeAxisCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomOverScaleCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomPanRegionCase;
import org.pepstock.charba.showcase.client.cases.extensions.ZoomTimeAxisCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ExtPluginsView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ExtPluginsView> {
	}

	public ExtPluginsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// Datalabels
	// ----------------------------------------------

	@UiHandler("datalabelsBar")
	protected void handleDatalabelsBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsBarCase());
	}

	@UiHandler("datalabelsBubble")
	protected void handleDatalabelsBubble(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsBubbleCase());
	}

	@UiHandler("datalabelsLine")
	protected void handleDatalabelsLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsLineCase());
	}

	@UiHandler("datalabelsDoughnut")
	protected void handleDatalabelsDoughnut(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsDoughnutCase());
	}

	@UiHandler("datalabelsPolar")
	protected void handleDatalabelsPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsPolarAreaCase());
	}

	@UiHandler("datalabelsRadar")
	protected void handleDatalabelsRadar(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsRadarCase());
	}

	@UiHandler("datalabelsInteractions")
	protected void handleDatalabelsInteractions(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsInteractionsCase());
	}

	@UiHandler("datalabelsData")
	protected void handleDatalabelsData(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsDataCase());
	}

	@UiHandler("datalabelsDataset")
	protected void handleDatalabelsDataset(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsDatasetCase());
	}

	@UiHandler("datalabelsIndices")
	protected void handleDatalabelsIndices(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsIndicesCase());
	}

	@UiHandler("datalabelsMirror")
	protected void handleDatalabelsMirror(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsMirrorCase());
	}

	@UiHandler("datalabelsListeners")
	protected void handleDatalabelsListeners(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsListenersCase());
	}

	@UiHandler("datalabelsHighlight")
	protected void handleDatalabelsHighlight(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsHighlightCase());
	}

	@UiHandler("datalabelsGradient")
	protected void handleDatalabelsGradient(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsLinearGradientLineCase());
	}

	@UiHandler("datalabelsSelection")
	protected void handleDatalabelsSelection(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsSelectionCase());
	}

	@UiHandler("datalabelsCustom")
	protected void handleDatalabelsCustomLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsCustomLabelsCase());
	}

	@UiHandler("datalabelsMulti")
	protected void handleDatalabelsMultiLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new DataLabelsMultiLabelsCase());
	}

	// ----------------------------------------------
	// Labels
	// ----------------------------------------------

	@UiHandler("labelsPieUsingLabels")
	protected void handleLabelsPieLabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsUsingLabelRenderCase());
	}

	@UiHandler("labelsPieUsingPercentage")
	protected void handleLabelsPiePercentage(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsUsingPercentageRenderCase());
	}

	@UiHandler("labelsPieUsingValue")
	protected void handleLabelsPieValue(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsUsingValueRenderCase());
	}

	@UiHandler("labelsPieUsingImages")
	protected void handleLabelsPieImages(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsUsingImageRenderCase());
	}

	@UiHandler("labelsPieMulti")
	protected void handleLabelsPieMulti(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsMultiOptionsCase());
	}

	@UiHandler("labelsOnPolar")
	protected void handleLabelsPolar(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsPolarCase());
	}

	@UiHandler("labelsOnBar")
	protected void handleLabelsBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsBarCase());
	}

	@UiHandler("labelsPiePosition")
	protected void handleLabelsPiePosition(ClickEvent event) {
		clearPreviousChart();
		content.add(new LabelsPositioningCase());
	}

	// ----------------------------------------------
	// Zoom
	// ----------------------------------------------

	@UiHandler("zoomBasic")
	protected void handleZoomBasic(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomBasicCase());
	}
	
	@UiHandler("zoomCategoryAxis")
	protected void handleZoomCategoryAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomCategoryAxisCase());
	}

	@UiHandler("zoomTimeAxis")
	protected void handleZoomTimeAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomTimeAxisCase());
	}

	@UiHandler("zoomOverAxis")
	protected void handleZoomOverAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomOverScaleCase());
	}
	
	@UiHandler("zoomDragCategoryAxis")
	protected void handleZoomDragCategoryAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomDragCategoryAxisCase());
	}

	@UiHandler("zoomDragLinearAxis")
	protected void handleZoomDragLinearAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomDragLinearAxisCase());
	}

	@UiHandler("zoomDragTimeAxis")
	protected void handleZoomDragTimeAxis(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomDragTimeAxisCase());
	}
	
	@UiHandler("zoomApiZoom")
	protected void handleZoomApiZoom(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomApiZoomCase());
	}
	
	@UiHandler("zoomApiZoomScale")
	protected void handleZoomApiZoomScale(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomApiZoomScaleCase());
	}

	@UiHandler("zoomApiPan")
	protected void handleZoomApiPan(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomApiPanCase());
	}
	
	@UiHandler("zoomZoomCallbacksOnTimeseriesLine")
	protected void handleZoomCallbacksOnTimeseriesLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomCallbacksOnTimeSeriesCase());
	}
	
	@UiHandler("zoomPanRegion")
	protected void handleZoomPanRegion(ClickEvent event) {
		clearPreviousChart();
		content.add(new ZoomPanRegionCase());
	}

	// ----------------------------------------------
	// Annotation
	// ----------------------------------------------

	@UiHandler("annotationBoxesOnLine")
	protected void handleAnnotationBoxesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationBoxesOnLineCase());
	}

	@UiHandler("annotationsLineAndBoxOnCombined")
	protected void handleAnnotationsLineAndBoxOnCombined(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationsOnCombinedCase());
	}

	@UiHandler("annotationLineOnTimeseries")
	protected void handleAnnotationTimeseriesLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationLineOnTimeSeriesLineCase());
	}

	@UiHandler("annotationLineOnHorizontalBar")
	protected void handleAnnotationLineOnHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationLineOnHorizontalBarCase());
	}

	@UiHandler("annotationLogarithmicAxisOnLine")
	protected void handleAnnotationLogarithmicAxisOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationLineOnLogarithmicAxisCase());
	}

	@UiHandler("annotationObliqueLineOnTimeseries")
	protected void handleAnnotationObliqueLineOnTimeseries(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationObliqueLineOnTimeSeriesLineCase());
	}

	@UiHandler("annotationsEventsOnTimeseries")
	protected void handleAnnotationsEventsOnTimeseries(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationsEventsOnTimeSeriesCase());
	}
	
	@UiHandler("annotationsEventsWithModifier")
	protected void handleAnnotationsEventsWithModifier(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationsEventsWithModifierKey());
	}
	
	@UiHandler("annotationEllipseOnLine")
	protected void handleAnnotationEllipseOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationEllipseOnLineCase());
	}
	
	@UiHandler("annotationPointOnLine")
	protected void handleAnnotationsPointOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationPointsOnLineCase());
	}

	@UiHandler("annotationLabelOnLine")
	protected void handleAnnotationsLabelOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationLabelOnLineCase());
	}

	@UiHandler("annotationLabelCalloutOnLine")
	protected void handleAnnotationsLabelCalloutOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AnnotationLabelCalloutOnLineCase());
	}
	
	// ----------------------------------------------
	// Imported
	// ----------------------------------------------

	@UiHandler("importingChartJsPlugin")
	protected void handleCustomPluginChart(ClickEvent event) {
		clearPreviousChart();
		content.add(new ImportingPluginCase());
	}

}
