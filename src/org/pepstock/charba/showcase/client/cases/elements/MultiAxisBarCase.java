package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.AxisPosition;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class MultiAxisBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, MultiAxisBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public MultiAxisBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Multiple axes on bar chart");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(true);

		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart, "y-axis-1");
		axis1.setPosition(AxisPosition.LEFT);
		axis1.setDisplay(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart, "y-axis-2");
		axis2.setPosition(AxisPosition.RIGHT);
		axis2.setDisplay(true);
		axis2.getGrid().setDrawOnChartArea(false);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBackgroundColor(getSequenceColors(months, 0.2));
		dataset1.setBorderColor(getSequenceColors(months, 1));
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));
		dataset1.setYAxisID("y-axis-1");

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));
		dataset2.setYAxisID("y-axis-2");

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

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
