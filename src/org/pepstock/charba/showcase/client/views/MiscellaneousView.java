package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.miscellaneous.ActiveElementsOnBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ApplyingPointSizesOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ApplyingPointStylesAsCanvasOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ApplyingPointStylesAsImageOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ApplyingPointStylesOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ApplyingStylesOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.AutoUpdateLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksBubbleCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksDoughnutCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksPieCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksPolarAreaCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksRadarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.CallbacksWithThresholdBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ControllerMyHorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ControllerMyLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.DatasetSelectionWithModifierCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.ExponentialRegressionScatterCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FlagsPluginOnBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FloatingDataDataLabelsCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FloatingDataOnBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FloatingDataOnHorizontalBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FloatingDataOnStackedBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.FloatingDataTimeSeriesByBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.HTMLAnnnotationByElementCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.HTMLAnnnotationCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.HoverStyleOnStackedAreaCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.InteractionByKeyboardCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.InterpolationOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.PolynomialRegressionBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.PowerRegressionScatterCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.RegressionBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.RegressionLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.RegressionScatterCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.RegressionTimeSeriesLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.SimpleLabelPluginOnBarCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.StandingPluginOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.SteppedLineOnLineCase;
import org.pepstock.charba.showcase.client.cases.miscellaneous.TrendAndForecastCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MiscellaneousView extends AbstractView {

	private static DemoViewUiBinder uiBinder = GWT.create(DemoViewUiBinder.class);

	interface DemoViewUiBinder extends UiBinder<Widget, MiscellaneousView> {
	}

	public MiscellaneousView(VerticalPanel content) {
		super(content);
		initWidget(uiBinder.createAndBindUi(this));
	}

	// ----------------------------------------------
	// options
	// ----------------------------------------------

	@UiHandler("miscellaneousSteppedOnLine")
	protected void handleMiscellaneousSteppedOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new SteppedLineOnLineCase());
	}

	@UiHandler("miscellaneousInterpolationOnLine")
	protected void handleMiscellaneousInterpolationOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new InterpolationOnLineCase());
	}

	@UiHandler("miscellaneousStylesOnLine")
	protected void handleMiscellaneousStylesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ApplyingStylesOnLineCase());
	}

	@UiHandler("miscellaneousPointStylesOnLine")
	protected void handleMiscellaneousPointStylesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ApplyingPointStylesOnLineCase());
	}

	@UiHandler("miscellaneousPointStyleImagesOnLine")
	protected void handleMiscellaneousPointStyleImagesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ApplyingPointStylesAsImageOnLineCase());
	}

	@UiHandler("miscellaneousPointStyleCanvasOnLine")
	protected void handleMiscellaneousPointStyleCanvasOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ApplyingPointStylesAsCanvasOnLineCase());
	}

	@UiHandler("miscellaneousPointSizeOnLine")
	protected void handleMiscellaneousPointSizeOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ApplyingPointSizesOnLineCase());
	}

	@UiHandler("miscellaneousHoverStyleOnStackedArea")
	protected void handleMiscellaneousHoverStyleOnStackedArea(ClickEvent event) {
		clearPreviousChart();
		content.add(new HoverStyleOnStackedAreaCase());
	}

	@UiHandler("miscellaneousActiveElements")
	protected void handleMiscellaneousActiveElements(ClickEvent event) {
		clearPreviousChart();
		content.add(new ActiveElementsOnBarCase());
	}

	@UiHandler("miscellaneousAutoUpdateLine")
	protected void handleMiscellaneousAutoUpdateLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new AutoUpdateLineCase());
	}

	@UiHandler("miscellaneousModifierKey")
	protected void handleMiscellaneousModifierKey(ClickEvent event) {
		clearPreviousChart();
		content.add(new DatasetSelectionWithModifierCase());
	}

	// ----------------------------------------------
	// plugins
	// ----------------------------------------------

	@UiHandler("miscellaneousSimplePluginLabelOnBar")
	protected void handleMiscellaneousPluginLabel(ClickEvent event) {
		clearPreviousChart();
		content.add(new SimpleLabelPluginOnBarCase());
	}

	@UiHandler("miscellaneousPluginFlags")
	protected void handleMiscellaneousPluginFlags(ClickEvent event) {
		clearPreviousChart();
		content.add(new FlagsPluginOnBarCase());
	}

	@UiHandler("miscellaneousPluginStandings")
	protected void handleMiscellaneousPluginStandings(ClickEvent event) {
		clearPreviousChart();
		content.add(new StandingPluginOnLineCase());
	}

	@UiHandler("htmlAnnotation")
	protected void handleHTMLAnnotation(ClickEvent event) {
		clearPreviousChart();
		content.add(new HTMLAnnnotationCase());
	}

	@UiHandler("htmlAnnotationByElement")
	protected void handleHTMLAnnotationByElement(ClickEvent event) {
		clearPreviousChart();
		content.add(new HTMLAnnnotationByElementCase());
	}

	@UiHandler("interactionByKeyboard")
	protected void handleInteractionByKeyboard(ClickEvent event) {
		clearPreviousChart();
		content.add(new InteractionByKeyboardCase());
	}
	
	// ----------------------------------------------
	// callbacks
	// ----------------------------------------------

	@UiHandler("callbacksOnBar")
	protected void handleBarVerticalCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksBarCase());
	}

	@UiHandler("callbacksOnLine")
	protected void handleLineCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksLineCase());
	}

	@UiHandler("callbacksOnRadar")
	protected void handleRadarCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksRadarCase());
	}

	@UiHandler("callbacksOnDoughnut")
	protected void handleDoughnutCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksDoughnutCase());
	}

	@UiHandler("callbacksOnPie")
	protected void handlePieCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksPieCase());
	}

	@UiHandler("callbacksOnPolar")
	protected void handlePolarCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksPolarAreaCase());
	}

	@UiHandler("callbacksOnBubble")
	protected void handleBubbleCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksBubbleCase());
	}

	@UiHandler("callbacksWithThresholdOnBar")
	protected void handleBarWithThresholdCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new CallbacksWithThresholdBarCase());
	}

	// ----------------------------------------------
	// Controllers
	// ----------------------------------------------

	@UiHandler("controllerMyLine")
	protected void handleMyLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new ControllerMyLineCase());
	}

	@UiHandler("controllerMyHorizontalBar")
	protected void handleMyHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new ControllerMyHorizontalBarCase());
	}
	
	// ----------------------------------------------
	// Datasets
	// ----------------------------------------------

	@UiHandler("miscellaneousFloatingDataOnBar")
	protected void handleMyFloatingDataOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new FloatingDataOnBarCase());
	}

	@UiHandler("miscellaneousFloatingDataOnHorizontalBar")
	protected void handleMyFloatingDataOnHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new FloatingDataOnHorizontalBarCase());
	}

	@UiHandler("miscellaneousFloatingDataOnStackedBar")
	protected void handleMyFloatingDataOnStackedBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new FloatingDataOnStackedBarCase());
	}
	
	@UiHandler("miscellaneousFloatingDataTimeseriesOnBar")
	protected void handleMyFloatingTimeseriesDataOnBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new FloatingDataTimeSeriesByBarCase());
	}	

	@UiHandler("miscellaneousFloatingDataOnBarAndDatalabels")
	protected void handleMyFloatingDataOnBarAndDatalabels(ClickEvent event) {
		clearPreviousChart();
		content.add(new FloatingDataDataLabelsCase());
	}
	
	// ----------------------------------------------
	// Regression
	// ----------------------------------------------

	@UiHandler("linearRegressionBarCase")
	protected void handleRegressionBarCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new RegressionBarCase());
	}

	@UiHandler("linearRegressionLineCase")
	protected void handleRegressionLineCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new RegressionLineCase());
	}

	@UiHandler("linearRegressionScatterCase")
	protected void handleRegressionScatterCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new RegressionScatterCase());
	}

	@UiHandler("linearRegressionTimeSeriesCase")
	protected void handleRegressionTimeSeriesCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new RegressionTimeSeriesLineCase());
	}

	@UiHandler("polynomialRegressionBarCase")
	protected void handlePolynomialRegressionBarCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new PolynomialRegressionBarCase());
	}
	
	@UiHandler("powerRegressionScatterCase")
	protected void handlePowerRegressionScatterCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new PowerRegressionScatterCase());
	}
	
	
	@UiHandler("regressionTrendAndForecasetOnTimeseries")
	protected void handleRegressionTrendAndForecasetOnTimeseries(ClickEvent event) {
		clearPreviousChart();
		content.add(new TrendAndForecastCase());
	}

	@UiHandler("exponentialRegressionScatterCase")
	protected void handleExponentialRegressionScatterCase(ClickEvent event) {
		clearPreviousChart();
		content.add(new ExponentialRegressionScatterCase());
	}	

}
