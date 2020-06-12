package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ScriptableContext;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.FloatingData;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.FormatterCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.enums.DefaultPlugin;
import org.pepstock.charba.client.enums.Display;
import org.pepstock.charba.client.enums.Weight;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.items.DataItem;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class FloatingDataDataLabelsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FloatingDataDataLabelsCase> {
	}

	@UiField
	BarChartWidget chart;

	public FloatingDataDataLabelsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(32);
		chart.getOptions().getElements().getLine().setFill(false);

		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.LEGEND, false);
		chart.getOptions().getPlugins().setEnabled(DefaultPlugin.TITLE, false);

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		
		double[] values = getRandomDigits(months);
		double[] gaps = getRandomDigits(months, false);

		double[][] dataToSet = new double[months][2];
		for (int i=0; i<months; i++) {
			dataToSet[i] = new double[] {values[i], values[i] + Math.max(gaps[i], 20)};
		}
		dataset1.setFloatingData(dataToSet);
		
		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.setOffset(true);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);
		axis2.getScaleLabel().setDisplay(true);
		axis2.getScaleLabel().setLabelString("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
		
		DataLabelsOptions top = new DataLabelsOptions();
		top.setAlign(Align.CENTER);
		top.setAnchor(Anchor.END);
		top.setBackgroundColor(HtmlColor.WHITE);
		top.setColor(HtmlColor.GREEN);
		top.setBorderColor(color1);
		top.setBorderRadius(25);
		top.setBorderWidth(2);
		top.setDisplay(Display.AUTO);
		top.getFont().setWeight(Weight.BOLD);
		top.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(IsChart chart, DataItem dataItem, ScriptableContext context) {
				return Utilities.applyPrecision(dataItem.getValueAsFloatingData().getEnd(), 0);
			}

		});
		
		DataLabelsOptions down = new DataLabelsOptions();
		down.setAlign(Align.CENTER);
		down.setAnchor(Anchor.START);
		down.setBackgroundColor(HtmlColor.WHITE);
		down.setColor(HtmlColor.RED);
		down.setBorderColor(color1);
		down.setBorderRadius(25);
		down.setBorderWidth(2);
		down.getFont().setWeight(Weight.BOLD);
		down.setFormatter(new FormatterCallback() {

			@Override
			public String invoke(IsChart chart, DataItem dataItem, ScriptableContext context) {
				return Utilities.applyPrecision(dataItem.getValueAsFloatingData().getStart(), 0);
			}

		});

		DataLabelsOptions option = new DataLabelsOptions();
		option.getLabels().setLabel("top", top);
		option.getLabels().setLabel("down", down);

		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			double[] values = getRandomDigits(months);
			double[] gaps = getRandomDigits(months, false);
			BarDataset barDataset = (BarDataset)dataset;
			List<FloatingData> data = barDataset.getFloatingData();
			for (int i=0; i<months; i++) {
				FloatingData fData = data.get(i);
				fData.setValues(values[i], values[i] + Math.max(gaps[i], 20));		
			}
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
