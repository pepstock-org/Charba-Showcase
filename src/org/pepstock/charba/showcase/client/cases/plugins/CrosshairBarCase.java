package org.pepstock.charba.showcase.client.cases.plugins;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.impl.plugins.Crosshair;
import org.pepstock.charba.client.impl.plugins.CrosshairOptions;
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

public class CrosshairBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CrosshairBarCase> {
	}

	@UiField
	BarChartWidget chart;
	
	@UiField
	CheckBox hideLabels;

	@UiField
	ListBox interaction;
	
	public CrosshairBarCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		interaction.addItem("Default", InteractionAxis.XY.name());
		for (InteractionAxis cInteraction : InteractionAxis.values()) {
			if (!InteractionAxis.R.equals(cInteraction)) {
				interaction.addItem(cInteraction.name(), cInteraction.name());
			}
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Crosshair on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

		CrosshairOptions options = new CrosshairOptions();
		options.setLineColor(HtmlColor.BLACK);
		options.setLineDash(3, 3);
		chart.getOptions().getPlugins().setOptions(Crosshair.ID, options);
		
		chart.getPlugins().add(Crosshair.get());
	}
	
	@UiHandler("hideLabels")
	protected void handleHideLabels(ClickEvent event) {
		CrosshairOptions options = chart.getOptions().getPlugins().getOptions(Crosshair.FACTORY);
		options.getLabels().setDisplay(!hideLabels.getValue());
		chart.update();
	}

	@UiHandler("interaction")
	protected void handleInteraction(ChangeEvent event) {
		CrosshairOptions options = chart.getOptions().getPlugins().getOptions(Crosshair.FACTORY);
		String selected = interaction.getSelectedValue();
		options.setMode(InteractionAxis.valueOf(selected));
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
