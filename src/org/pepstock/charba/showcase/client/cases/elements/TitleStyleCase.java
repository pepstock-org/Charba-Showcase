package org.pepstock.charba.showcase.client.cases.elements;

import java.util.List;

import org.pepstock.charba.client.Defaults;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
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

public class TitleStyleCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TitleStyleCase> {
	}

	private static final HtmlColor[] COLORS = { HtmlColor.BLACK, HtmlColor.RED, HtmlColor.BLUE, HtmlColor.GREEN, HtmlColor.ORANGE };

	private static final int[] FONT_SIZES = { 8, 12, 16, 20, 24 };

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox color;

	@UiField
	ListBox fontSize;

	public TitleStyleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		color.addItem("Default", Defaults.get().getGlobal().getTitle().getColorAsString());
		for (HtmlColor myColor : COLORS) {
			color.addItem(myColor.name(), myColor.toRGB());
		}

		fontSize.addItem("Default", String.valueOf(Defaults.get().getGlobal().getTitle().getFont().getSize()));
		for (int myFontSize : FONT_SIZES) {
			fontSize.addItem(String.valueOf(myFontSize), String.valueOf(myFontSize));
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Changing title style on line chart");

		List<Dataset> datasets = chart.getData().getDatasets(true);

		LineDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setFill(false);
		double[] values = getRandomDigits(months);
		List<Double> data = dataset1.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset1);

		LineDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(false);
		datasets.add(dataset2);

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

	}

	@UiHandler("color")
	protected void handleColor(ChangeEvent event) {
		String selected = color.getSelectedValue();
		chart.getOptions().getTitle().setColor(selected);
		chart.reconfigure();
	}

	@UiHandler("fontSize")
	protected void handleFontSize(ChangeEvent event) {
		String selected = fontSize.getSelectedValue();
		chart.getOptions().getTitle().getFont().setSize(Integer.parseInt(selected));
		chart.reconfigure();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
