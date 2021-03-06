package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ApplyingPointSizesOnLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ApplyingPointSizesOnLineCase> {
	}

	@UiField
	LineChartWidget chart;

	public ApplyingPointSizesOnLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.BOTTOM);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Different point sizes on line chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.INDEX);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("Big Points");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.FALSE);
		dataset1.setBorderDash(5, 5);
		dataset1.setPointRadius(15D);
		dataset1.setPointHoverRadius(10D);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("Individual point sizes");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.FALSE);
		dataset2.setBorderDash(5, 5);
		dataset2.setPointRadius(2, 4, 6, 18, 0, 12, 20);

		LineDataset dataset3 = chart.newDataset();
		dataset3.setLabel("Large pointHoverRadius");

		IsColor color3 = GoogleChartColor.values()[2];

		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months));
		dataset3.setFill(Fill.FALSE);
		dataset3.setPointHoverRadius(30D);

		LineDataset dataset4 = chart.newDataset();
		dataset4.setLabel("Large pointHitRadius");

		IsColor color4 = GoogleChartColor.values()[3];

		dataset4.setBackgroundColor(color4.toHex());
		dataset4.setBorderColor(color4.toHex());
		dataset4.setData(getRandomDigits(months));
		dataset4.setFill(Fill.FALSE);
		dataset4.setPointHitRadius(20D);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);
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
