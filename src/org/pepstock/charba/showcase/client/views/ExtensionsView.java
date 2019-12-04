package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsBubbleView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsCustomLabelsView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsDataView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsDatasetView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsDoughnutView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsHighlightView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsIndicesView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsInteractionsView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsLinearGradientLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsListenersView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsMirrorView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsMultiLabelsView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsPolarAreaView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsRadarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.DataLabelsSelectionView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelImageView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelMultiOptionsView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelPercentageView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelPolarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelPositionView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelValueView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PieceLabelView;
import org.pepstock.charba.showcase.client.cases.jsinterop.VerticalBarCustomPluginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class ExtensionsView extends AbstractCategoryView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, ExtensionsView> {
	}

	public ExtensionsView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// Datalabels
	// ----------------------------------------------

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

	@UiHandler("datalabelsMulti")
	protected void handleDatalabelsMultiLabels(ClickEvent event) {
		clearPreviousChart();
		 content.add(new DataLabelsMultiLabelsView());
	}

	// ----------------------------------------------
	// Labels
	// ----------------------------------------------

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

	// ----------------------------------------------
	// Imported
	// ----------------------------------------------

	@UiHandler("importPlugin")
	protected void handleCustomPluginChart(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarCustomPluginView());
	}

}
