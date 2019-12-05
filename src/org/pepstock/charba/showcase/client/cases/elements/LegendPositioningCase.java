package org.pepstock.charba.showcase.client.cases.elements;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.UpdateConfigurationBuilder;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.jsinterop.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class LegendPositioningCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LegendPositioningCase> {
	}

	@UiField
	LineChart chart;
	
	@UiField
	ListBox position;

	LineDataset dataset;
	
	public LegendPositioningCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		for (Position pos : Position.values()) {
			position.addItem(pos.name(), pos.name());
		}
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Legend positioning");
		
		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		IsColor color1 = Colors.ALL[0];
		dataset.setBackgroundColor(color1.alpha(0.2));
		dataset.setBorderColor(color1.toHex());
		dataset.setData(getRandomDigits(months));
		dataset.setFill(Fill.ORIGIN);
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getRandomDigits(months));
		chart.update();
	}
	
	@UiHandler("position")
	protected void handlePosition(ChangeEvent event) {
		String selected = position.getSelectedValue();
		int i = 0;
		for (Position cPos : Position.values()) {
			if (cPos.name().equalsIgnoreCase(selected)) {
				IsColor color = Colors.ALL[i];
				dataset.setBackgroundColor(color.alpha(0.2));
				dataset.setBorderColor(color.toHex());
				chart.getOptions().getLegend().setPosition(cPos);
				chart.reconfigure(UpdateConfigurationBuilder.create().setDuration(1000).build());
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