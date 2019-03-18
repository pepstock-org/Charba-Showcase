package org.pepstock.charba.showcase.client.samples.jsinterop;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.patterns.Patternomaly;
import org.pepstock.charba.client.patterns.Shape;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PatternomalyDoughnutView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PatternomalyDoughnutView> {
	}

	@UiField
	DoughnutChart chart;

	@UiField
	VerticalPanel container;

	public PatternomalyDoughnutView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Doughnut Chart");
		
		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		
		Pattern p1 = Patternomaly.get().createPattern(Shape.square, Colors.ALL[0]);
		Pattern p2 = Patternomaly.get().createPattern(Shape.zigzag_vertical, Colors.ALL[1]);
		Pattern p3 = Patternomaly.get().createPattern(Shape.diagonal, Colors.ALL[2]);
		Pattern p4 = Patternomaly.get().createPattern(Shape.ring, Colors.ALL[3]);
		dataset.setBackgroundColor(p1, p2, p3, p4);

		dataset.setData(getRandomDigits(4, false));

		chart.getData().setLabels(getLabels(4));
		chart.getData().setDatasets(dataset);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(4, false));
		}
		chart.update();
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
