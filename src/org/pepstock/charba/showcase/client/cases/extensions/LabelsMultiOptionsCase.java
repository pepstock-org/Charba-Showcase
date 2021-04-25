package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.gwt.widgets.DoughnutChartWidget;
import org.pepstock.charba.client.labels.Label;
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
	DoughnutChartWidget chart;

	public LabelsMultiOptionsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLegend().setPosition(org.pepstock.charba.client.enums.Position.RIGHT);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using multiple labels");
		chart.getOptions().getTitle().getPadding().setBottom(32);

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		LabelsOptions options = new LabelsOptions();
		Label label1 = options.createLabel("label1");
		label1.setRender(Render.LABEL);
		label1.setColor(HtmlColor.BLACK);
		label1.setArc(true);
		label1.setPosition(Position.OUTSIDE);
		label1.getFont().setSize(18);

		Label label2 = options.createLabel("label2");
		label2.setRender(Render.PERCENTAGE);
		label2.setColor(HtmlColor.WHITE);
		label2.setOverlap(false);
		label2.getFont().setSize(18);

		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, options);
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
