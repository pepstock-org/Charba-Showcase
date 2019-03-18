package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.patterns.Patternomaly;
import org.pepstock.charba.client.patterns.Shape;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class PatternomalyBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget,PatternomalyBarView> {
	}

	@UiField
	BarChart chart;
	
	public PatternomalyBarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);;
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.80D));
	
		Pattern p1 = Patternomaly.get().createPattern(Shape.square, Colors.ALL[0]);
		Pattern p2 = Patternomaly.get().createPattern(Shape.zigzag_vertical, Colors.ALL[1]);
		Pattern p3 = Patternomaly.get().createPattern(Shape.diagonal, Colors.ALL[2]);
		Pattern p4 = Patternomaly.get().createPattern(Shape.ring, Colors.ALL[3]);
		dataset1.setBackgroundColor(p1, p2, p3, p4);

		dataset1.setData(getFixedDigits(4));
		
		chart.getData().setLabels(getLabels(4));
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(4));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
