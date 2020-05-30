package org.pepstock.charba.showcase.client.cases.elements;

import java.util.Random;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.CartesianAxisType;
import org.pepstock.charba.client.enums.Fill;
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

public class NoNumericYAxisCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, NoNumericYAxisCase> {
	}

	@UiField
	LineChartWidget chart;

	private static final String[] VALUES = new String[] { "Request Added", "Request Viewed", "Request Accepted", "Request Solved", "Solving Confirmed" };

	public NoNumericYAxisCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Non numeric Y axis");

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		dataset1.setDataString(getData(months));
		dataset1.setFill(Fill.FALSE);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());

		dataset2.setDataString(getData(months));
		dataset2.setFill(Fill.FALSE);

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getScaleLabel().setDisplay(true);
		axis1.getScaleLabel().setLabelString("Month");

		CartesianCategoryAxis axis2 = new CartesianCategoryAxis(chart, CartesianAxisType.Y);
		axis2.setDisplay(true);
		axis2.setPosition(Position.LEFT);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Request State");
		axis2.getTicks().setReverse(true);

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setXLabels(getLabels());
		chart.getData().setYLabels(VALUES);
		chart.getData().setDatasets(dataset1, dataset2);
	}

	private String[] getData(int length) {
		String[] result = new String[length];
		Random r = new Random();
		int high = VALUES.length;
		for (int i = 0; i < length; i++) {
			int index = r.nextInt(high);
			result[i] = VALUES[index];
		}
		return result;
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
