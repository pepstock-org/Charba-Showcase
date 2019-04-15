package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.PointStyle;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LegendStyleView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LegendStyleView> {
	}

	@UiField
	LineChart chartNormal;
	
	@UiField
	LineChart chartStyled;


	public LegendStyleView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chartNormal.getOptions().setResponsive(true);
		chartNormal.getOptions().getLegend().setPosition(Position.TOP);
		chartNormal.getOptions().getTitle().setDisplay(true);
		chartNormal.getOptions().getTitle().setText("Charba Normal Legend");
		
		LineDataset dataset1 = chartNormal.newDataset();
		dataset1.setLabel("dataset 1");
		IsColor color1 = Colors.ALL[0];
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setPointBackgroundColor(color1.toHex());
		dataset1.setPointStyle(PointStyle.RECT_ROT);
		dataset1.setPointRadius(10);
		dataset1.setData(getRandomDigits(months));
		dataset1.setFill(Fill.ORIGIN);
		chartNormal.getData().setLabels(getLabels());
		chartNormal.getData().setDatasets(dataset1);
		
		chartStyled.getOptions().setResponsive(true);
		chartStyled.getOptions().getLegend().setPosition(Position.TOP);
		chartStyled.getOptions().getLegend().getLabels().setUsePointStyle(true);
		chartStyled.getOptions().getLegend().getLabels().setFontSize(20);
		chartStyled.getOptions().getTitle().setDisplay(true);
		chartStyled.getOptions().getTitle().setText("Charba Point Style Legend");
		
		LineDataset dataset2 = chartStyled.newDataset();
		dataset2.setLabel("dataset 1");
		IsColor color2 = Colors.ALL[1];
		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setData(getRandomDigits(months));
		dataset2.setFill(Fill.ORIGIN);
		dataset2.setBorderWidth(1);
		dataset2.setPointStyle(PointStyle.RECT_ROT);
		dataset2.setPointRadius(10);
		dataset2.setPointBackgroundColor(color2.toHex());

		chartStyled.getData().setLabels(getLabels());
		chartStyled.getData().setDatasets(dataset2);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		newData(chartNormal);
		newData(chartStyled);
	}

	private void newData(LineChart chart) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
