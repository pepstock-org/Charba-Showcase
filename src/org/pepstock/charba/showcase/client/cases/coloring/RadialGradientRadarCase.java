package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.colors.Gradient;
import org.pepstock.charba.client.colors.GradientOrientation;
import org.pepstock.charba.client.colors.GradientScope;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.colors.UiGradient;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.RadarDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.RadarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class RadialGradientRadarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RadialGradientRadarCase> {
	}

	@UiField
	RadarChartWidget chart;

	public RadialGradientRadarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Applying a radial gradient on radar chart dataset");

		RadarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		// FIXME SOUNDS wrong
		Gradient gradient1 = UiGradient.TELEGRAM.createGradient(GradientType.RADIAL, GradientOrientation.OUT_IN, GradientScope.CHART);
		IsColor firstColor = gradient1.getColors().get(0).getColor();

		dataset1.setBackgroundColor(gradient1);
		dataset1.setBorderColor(firstColor.darker());
		dataset1.setPointBackgroundColor(gradient1);
		dataset1.setPointHoverBackgroundColor(gradient1);
		dataset1.setPointHoverBorderColor(firstColor.darker());
		dataset1.setPointRadius(5);

		double[] values = getRandomDigits(months);
		dataset1.setData(values);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
