package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class FillingBoundariesCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, FillingBoundariesCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	CheckBox smooth;

	@UiField
	ListBox fill;

	private LineDataset dataset = null;

	public FillingBoundariesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (Fill cFill : Fill.values()) {
			fill.addItem(cFill.name(), cFill.name());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Setting filling modes on line chart");
		chart.getOptions().setSpanGaps(false);
		chart.getOptions().getElements().getLine().setTension(0.000001D);

		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		IsColor color = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.START);

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

	@UiHandler("smooth")
	protected void handleSmooth(ClickEvent event) {
		double value = smooth.getValue() ? 0.4D : 0.000001D;
		chart.getOptions().getElements().getLine().setTension(value);
		chart.reconfigure();
	}

	@UiHandler("fill")
	protected void handleFill(ChangeEvent event) {
		String selected = fill.getSelectedValue();
		int i = 0;
		for (Fill cFill : Fill.values()) {
			if (cFill.name().equalsIgnoreCase(selected)) {
				IsColor color = GoogleChartColor.values()[i];
				dataset.setBackgroundColor(color.alpha(0.2));
				dataset.setBorderColor(color.toHex());
				dataset.setFill(cFill);
				chart.update(UpdateConfigurationBuilder.create().setDuration(1000).build());
				return;
			}
			i++;
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
