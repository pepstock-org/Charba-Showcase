package org.pepstock.charba.showcase.client.views;

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
import org.pepstock.charba.showcase.client.cases.extensions.LabelsBarCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsMultiOptionsCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsPolarCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsPositioningCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingImageRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingLabelRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingPercentageRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.LabelsUsingValueRenderCase;
import org.pepstock.charba.showcase.client.cases.extensions.ImportingPluginCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class ExtensionsView extends AbstractView {

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
	// Imported
	// ----------------------------------------------

	@UiHandler("importPlugin")
	protected void handleCustomPluginChart(ClickEvent event) {
		clearPreviousChart();
		 content.add(new ImportingPluginCase());
	}

}
