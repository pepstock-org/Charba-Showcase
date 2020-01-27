package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.labels.LabelsOptions;
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

public class LabelsMultiOptionsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsMultiOptionsCase> {
	}

	@UiField
	DoughnutChart chart;

	public LabelsMultiOptionsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTitle().setText("Using multiple labels");

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		LabelsOptions option1 = new LabelsOptions();
		option1.setRender(Render.LABEL);
		option1.setFontColor(HtmlColor.BLACK);
		option1.setArc(true);
		option1.setPosition(Position.OUTSIDE);

		LabelsOptions option2 = new LabelsOptions();
		option2.setRender(Render.PERCENTAGE);
		option2.setFontColor(HtmlColor.WHITE);
		option2.setOverlap(false);

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, Arrays.asList(option1, option2));
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
