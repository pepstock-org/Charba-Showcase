package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.GradientOrientation;
import org.pepstock.charba.client.colors.GradientType;
import org.pepstock.charba.client.colors.UiGradient;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class UiGradientsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, UiGradientsCase> {
	}

	@UiField
	LineChart chart;

	@UiField
	ListBox gradient;

	@UiField
	ListBox orientation;

	LineDataset dataset1 = null;

	public UiGradientsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		UiGradient first = null;
		for (UiGradient myColor : UiGradient.values()) {
			if (first == null) {
				first = myColor;
			}
			gradient.addItem(myColor.name(), myColor.name());
		}

		GradientOrientation firstOrientation = null;
		for (GradientOrientation myOrientation : GradientOrientation.values()) {
			if (firstOrientation == null) {
				firstOrientation = myOrientation;
			}
			orientation.addItem(myOrientation.name(), myOrientation.name());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("GWT material colors on line chart");

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBackgroundColor(first.createGradient(GradientType.LINEAR, firstOrientation));
		dataset1.setData(getRandomDigits(months, false));
		dataset1.setFill(Fill.ORIGIN);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(months, false));
		chart.update();
	}

	@UiHandler("orientation")
	protected void handleOrientation(ChangeEvent event) {
		applyGradient();
	}

	@UiHandler("gradient")
	protected void handleColors(ChangeEvent event) {
		applyGradient();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private void applyGradient() {
		String selectedGradient = gradient.getSelectedValue();
		String selectedOrientation = orientation.getSelectedValue();
		UiGradient gradient = UiGradient.valueOf(selectedGradient);
		GradientOrientation orie = GradientOrientation.valueOf(selectedOrientation);
		dataset1.setBackgroundColor(gradient.createGradient(orie.getType(), orie));
		chart.update();
	}
}
