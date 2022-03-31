package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gradient.Colors;
import org.pepstock.charba.client.gradient.GradientOptions;
import org.pepstock.charba.client.gradient.PropertyOptions;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GradientBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GradientBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public GradientBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using GRADIENT plugin on cartesian category axis");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		Labels labels = chart.getData().getLabels();
		GradientOptions options = new GradientOptions();
		PropertyOptions propOptions = options.getBackgroundColor();
		propOptions.setAxis(AxisKind.X);
		Colors stopColors = propOptions.getColors();
		for (int i=0; i<labels.size(); i++) {
			if (i % 3 == 0) {
				stopColors.setColor(labels.getString(i), GoogleChartColor.values()[i]);
			}
		}
		dataset1.setOptions(options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
