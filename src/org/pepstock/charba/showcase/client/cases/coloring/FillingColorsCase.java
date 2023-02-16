package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.Arrays;
import java.util.List;

import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.items.FillColors;
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

public class FillingColorsCase extends BaseComposite {

	private static final List<HtmlColor> COLORS = Arrays.asList(HtmlColor.BLUE, HtmlColor.RED, HtmlColor.GREEN, HtmlColor.ORANGE, HtmlColor.LIGHT_GREY);
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FillingColorsCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox above;
	
	@UiField
	ListBox below;

	private LineDataset dataset = null;
	
	private FillColors fillColors = new FillColors();

	public FillingColorsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		HtmlColor aboveFirst = null;

		for (HtmlColor myColor : COLORS) {
			if (aboveFirst == null) {
				aboveFirst = myColor;
			}
			above.addItem(myColor.name(), myColor.toRGB());
		}
		
		HtmlColor belowFirst = null;

		for (HtmlColor myColor : COLORS) {
			if (belowFirst == null) {
				belowFirst = myColor;
			}
			below.addItem(myColor.name(), myColor.toRGB());
		}

		fillColors.setFill(Fill.ORIGIN);
		fillColors.setAboveColor(aboveFirst);
		fillColors.setBelowColor(belowFirst);
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Setting filling colors on line chart");

		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBorderWidth(0);
		dataset.setPointRadius(0);
		dataset.setData(getRandomDigits(months));
		dataset.setFillColors(fillColors);

		CartesianLinearAxis axis = new CartesianLinearAxis(chart);
		axis.getTicks().setAutoSkip(false);
		axis.getTicks().setMaxRotation(0);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
		chart.getOptions().getScales().setAxes(axis);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getRandomDigits(months));
		chart.update();
	}

	@UiHandler("above")
	protected void handleAbove(ChangeEvent event) {
		String selected = above.getSelectedValue();
		dataset.getFillColors().setAboveColor(selected);
		chart.update();
	}

	@UiHandler("below")
	protected void handlebelow(ChangeEvent event) {
		String selected = below.getSelectedValue();
		dataset.getFillColors().setBelowColor(selected);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
