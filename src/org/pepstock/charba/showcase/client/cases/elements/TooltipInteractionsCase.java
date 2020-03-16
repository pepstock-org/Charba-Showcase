package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
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

public class TooltipInteractionsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TooltipInteractionsCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox mode;

	@UiField
	CheckBox intersect;

	private LineDataset dataset1 = null;

	private LineDataset dataset2 = null;

	public TooltipInteractionsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (InteractionMode cMode : InteractionMode.values()) {
			mode.addItem(cMode.name(), cMode.name());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Tooltip mode and intesect options");
		chart.getOptions().getTooltips().setMode(InteractionMode.POINT);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(InteractionMode.POINT);
		chart.getOptions().getHover().setIntersect(false);

		dataset1 = new LineDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.FALSE);

		dataset2 = new LineDataset();
		dataset2.setLabel("dataset 2");
		IsColor color2 = GoogleChartColor.values()[1];
		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.FALSE);

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

	@UiHandler("mode")
	protected void handleMode(ChangeEvent event) {
		String selected = mode.getSelectedValue();
		for (InteractionMode cMode : InteractionMode.values()) {
			if (cMode.name().equalsIgnoreCase(selected)) {
				chart.getOptions().getTooltips().setMode(cMode);
				chart.getOptions().getHover().setMode(cMode);
				chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
				return;
			}
		}
	}

	@UiHandler("intersect")
	protected void handleIntersect(ClickEvent event) {
		chart.getOptions().getTooltips().setIntersect(intersect.getValue());
		chart.getOptions().getHover().setIntersect(intersect.getValue());
		chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
