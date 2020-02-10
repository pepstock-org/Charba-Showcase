package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.configuration.RadialAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.enums.InteractionMode;
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
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.NEAREST);
		chart.getOptions().getHover().setIntersect(true);

		PolarAreaDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		Pattern pattern = new Pattern(ImagesHelper.toImageElement(Images.INSTANCE.backgroundPattern()));
		Pattern pattern1 = new Pattern(ImagesHelper.toImageElement(Images.INSTANCE.backgroundPattern1()));
		Pattern pattern2 = new Pattern(ImagesHelper.toImageElement(Images.INSTANCE.backgroundPattern2()));

		dataset1.setBackgroundColor(pattern, pattern1, pattern2);

		double[] values = getRandomDigits(ITEMS, 20, 100);
		dataset1.setData(values);
		dataset1.setBorderColor(HtmlColor.DARK_GREY, HtmlColor.DARK_GREY, HtmlColor.RED);

		RadialAxis axis = new RadialAxis(chart);
		axis.getTicks().setBeginAtZero(true);
		axis.getGrideLines().setCircular(true);
		chart.getOptions().setAxis(axis);

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
