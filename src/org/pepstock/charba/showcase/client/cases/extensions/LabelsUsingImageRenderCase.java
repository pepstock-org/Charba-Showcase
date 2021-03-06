package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.gwt.widgets.PieChartWidget;
import org.pepstock.charba.client.labels.Label;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.enums.Render;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LabelsUsingImageRenderCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsUsingImageRenderCase> {
	}

	@UiField
	PieChartWidget chart;

	final int myMonths = 3;

	public LabelsUsingImageRenderCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using images as labels");

		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(myMonths, 1));
		dataset.setData(getRandomDigits(myMonths, false));

		chart.getData().setLabels(getLabels(myMonths));
		chart.getData().setDatasets(dataset);

		LabelsOptions options = new LabelsOptions();
		Label label = options.createLabel("myLabel");
		label.setRender(Render.IMAGE);
		label.setImages(ImagesHelper.toImg(Images.INSTANCE.flagIT()), ImagesHelper.toImg(Images.INSTANCE.flagFR()), ImagesHelper.toImg(Images.INSTANCE.flagDE()));
		label.setOverlap(false);

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
