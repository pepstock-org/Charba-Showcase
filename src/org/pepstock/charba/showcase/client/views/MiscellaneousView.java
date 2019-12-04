package org.pepstock.charba.showcase.client.views;

import org.pepstock.charba.showcase.client.cases.jsinterop.AnimationView;
import org.pepstock.charba.showcase.client.cases.jsinterop.HTMLAnnnotationByElementView;
import org.pepstock.charba.showcase.client.cases.jsinterop.HTMLAnnnotationView;
import org.pepstock.charba.showcase.client.cases.jsinterop.HorizontalFlagsBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.HorizontalMyFlagsBarView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LineCallbackView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LineInterpolationView;
import org.pepstock.charba.showcase.client.cases.jsinterop.LineMyView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PointSizeLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PointStyleImageView;
import org.pepstock.charba.showcase.client.cases.jsinterop.PointStyleLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.StandingView;
import org.pepstock.charba.showcase.client.cases.jsinterop.SteppedLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.StyledLineView;
import org.pepstock.charba.showcase.client.cases.jsinterop.VerticalBarCallbackView;
import org.pepstock.charba.showcase.client.cases.jsinterop.VerticalBarPluginLabelView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * MAIN VIEW
 */
public class MiscellaneousView extends AbstractCategoryView {

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
	
	@UiHandler("animation")
	protected void handleAnimation(ClickEvent event) {
		clearPreviousChart();
		 content.add(new AnimationView());
	}
	
	@UiHandler("miscellaneousSteppedOnLine")
	protected void handleMiscellaneousSteppedOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new SteppedLineView());
	}

	@UiHandler("miscellaneousInterpolationOnLine")
	protected void handleMiscellaneousInterpolationOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineInterpolationView());
	}

	@UiHandler("miscellaneousStylesOnLine")
	protected void handleMiscellaneousStylesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new StyledLineView());
	}

	@UiHandler("miscellaneousPointStylesOnLine")
	protected void handleMiscellaneousPointStylesOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new PointStyleLineView());
	}

	@UiHandler("miscellaneousPointStyleImagesOnLine")
	protected void handleMiscellaneousPointStyleImagesOnLine(ClickEvent event) {
		clearPreviousChart();
		 content.add(new PointStyleImageView());
	}

	@UiHandler("miscellaneousPointSizeOnLine")
	protected void handleMiscellaneousPointSizeOnLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new PointSizeLineView());
	}
	
	// ----------------------------------------------
	// plugins
	// ----------------------------------------------

	@UiHandler("miscellaneousPluginLabel")
	protected void handleMiscellaneousPluginLabel(ClickEvent event) {
		clearPreviousChart();
		 content.add(new VerticalBarPluginLabelView());
	}
	
	@UiHandler("miscellaneousPluginFlags")
	protected void handleMiscellaneousPluginFlags(ClickEvent event) {
		clearPreviousChart();
		 content.add(new HorizontalFlagsBarView());
	}
	
	@UiHandler("miscellaneousPluginStandings")
	protected void handleMiscellaneousPluginStandings(ClickEvent event) {
		clearPreviousChart();
		 content.add(new StandingView());
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

	// ----------------------------------------------
	// callbacks
	// ----------------------------------------------

	@UiHandler("barCallback")
	protected void handleBarVerticalCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new VerticalBarCallbackView());
	}

	@UiHandler("lineCallback")
	protected void handleLineCallback(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineCallbackView());
	}
	
	// ----------------------------------------------
	// Controllers
	// ----------------------------------------------

	@UiHandler("myLine")
	protected void handleMyLine(ClickEvent event) {
		clearPreviousChart();
		content.add(new LineMyView());
	}

	@UiHandler("myHorizontalBar")
	protected void handleMyHorizontalBar(ClickEvent event) {
		clearPreviousChart();
		content.add(new HorizontalMyFlagsBarView());
	}


}
