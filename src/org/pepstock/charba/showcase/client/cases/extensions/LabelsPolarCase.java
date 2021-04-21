package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PolarAreaDataset;
import org.pepstock.charba.client.gwt.widgets.PolarAreaChartWidget;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsOptionsBuilder;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.enums.Position;
import org.pepstock.charba.client.labels.enums.Render;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LabelsPolarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsPolarCase> {
	}

	@UiField
	PolarAreaChartWidget chart;

	final int myMonths = 3;

	public LabelsPolarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLayout().getPadding().setBottom(50);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTitle().setText("Using labels on polar chart");

		PolarAreaDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(myMonths, 1));
		dataset.setData(getRandomDigits(myMonths, false));

		chart.getData().setLabels(getLabels(myMonths));
		chart.getData().setDatasets(dataset);

		LabelsOptionsBuilder optionBuilder = LabelsOptionsBuilder.create().createLabel("label").setRender(Render.LABEL).setColor(HtmlColor.BLACK).setFontSize(32).setArc(true).setPosition(Position.OUTSIDE).getOptionsBuilder();
		LabelsOptions options = optionBuilder.createLabel("label2").setRender(Render.PERCENTAGE).setColor(HtmlColor.WHITE).setPrecision(2).setFontSize(32).setOverlap(false).getOptionsBuilder().build();
		
		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(myMonths, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
