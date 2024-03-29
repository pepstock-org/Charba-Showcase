package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.PatternBuilder;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class PatternPolarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PatternPolarCase> {
	}

	private static final int ITEMS = 3;

	@UiField
	PolarAreaChartWidget chart;

	public PatternPolarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Applying a pattern on polar area chart dataset");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(DefaultInteractionMode.NEAREST);
		chart.getOptions().getHover().setIntersect(true);

		PolarAreaDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		Pattern pattern = PatternBuilder.create(ImagesHelper.toImg(Images.INSTANCE.pattern())).build(); 
		Pattern pattern1 = PatternBuilder.create(ImagesHelper.toImg(Images.INSTANCE.background())).build(); ;
		Pattern pattern2 = PatternBuilder.create(ImagesHelper.toImg(Images.INSTANCE.patternHover())).build(); 

		dataset1.setBackgroundColor(pattern, pattern1, pattern2);

		double[] values = getRandomDigits(ITEMS, 20, 100);
		dataset1.setData(values);
		dataset1.setBorderColor(HtmlColor.DARK_GREY, HtmlColor.DARK_GREY, HtmlColor.BLACK);

		RadialAxis axis = new RadialAxis(chart);
		axis.setBeginAtZero(true);
		axis.getGrid().setCircular(true);
		chart.getOptions().getScales().setAxes(axis);

		chart.getData().setLabels(getLabels(ITEMS));
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(ITEMS, 20, 100));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
