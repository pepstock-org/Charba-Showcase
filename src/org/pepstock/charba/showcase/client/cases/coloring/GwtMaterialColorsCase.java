package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.ColorBuilder;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
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

public class GwtMaterialColorsCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, GwtMaterialColorsCase> {
	}

	@UiField
	LineChart chart;

	@UiField
	ListBox color;

	LineDataset dataset1 = null;

	public GwtMaterialColorsCase() {
		initWidget(uiBinder.createAndBindUi(this));

		GwtMaterialColor first = null;

		for (GwtMaterialColor myColor : GwtMaterialColor.values()) {
			if (first == null) {
				first = myColor;
			}
			color.addItem(myColor.name(), myColor.toRGB());
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("GWT material colors on line chart");

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		dataset1.setBackgroundColor(first);
		dataset1.setBorderColor(first.darker());
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.ORIGIN);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(months));
		chart.update();
	}

	@UiHandler("color")
	protected void handleColors(ChangeEvent event) {
		String selectedColor = color.getSelectedValue();
		dataset1.setBackgroundColor(selectedColor);
		IsColor borderColor = ColorBuilder.parse(selectedColor);
		dataset1.setBorderColor(borderColor.darker());
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
