package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Easing;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.PointStyle;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.options.Options;
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

public class LegendStyleCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LegendStyleCase> {
	}

	@UiField
	LineChartWidget chart;

	@UiField
	ListBox pointStyle;

	@UiField
	CheckBox usePointStyle;

	LineDataset dataset = null;

	public LegendStyleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (PointStyle style : PointStyle.values()) {
			pointStyle.addItem(style.name(), style.name());
		}

		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Legend labels styling");

		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		IsColor color1 = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color1.alpha(0.2));
		dataset.setBorderColor(color1.toHex());
		dataset.setBorderWidth(1);
		dataset.setPointBackgroundColor(color1.toHex());
		dataset.setPointStyle(PointStyle.CIRCLE);
		dataset.setPointRadius(10);
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.ORIGIN);
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getRandomDigits(months));
		chart.update(UpdateConfigurationBuilder.create().setEasing(Easing.EASE_IN_EXPO).build());
	}

	@UiHandler("pointStyle")
	protected void handlePointStyle(ChangeEvent event) {
		String selected = pointStyle.getSelectedValue();
		int i = 0;
		for (PointStyle cPos : PointStyle.values()) {
			if (cPos.name().equalsIgnoreCase(selected)) {
				IsColor color = GoogleChartColor.values()[i];
				dataset.setBackgroundColor(color.alpha(0.2));
				dataset.setBorderColor(color.toHex());
				dataset.setPointBackgroundColor(color.toHex());
				dataset.setPointStyle(cPos);
				usePointStyle.setValue(false);
				handleUsePointStyle(null);
				return;
			}
			i++;
		}
	}

	@UiHandler("usePointStyle")
	protected void handleUsePointStyle(ClickEvent event) {
		Options options = chart.getNode().getOptions();
		options.getLegend().getLabels().setUsePointStyle(usePointStyle.getValue());
		options.getTooltips().setUsePointStyle(usePointStyle.getValue());
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
