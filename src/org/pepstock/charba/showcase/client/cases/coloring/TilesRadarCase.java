package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.Random;

import org.pepstock.charba.client.RadarChart;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.Shape;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.RadarDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TilesRadarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TilesRadarCase> {
	}

	@UiField
	RadarChart chart;

	Random random = new Random();

	private static final int ITEMS = 10;

	public TilesRadarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		int index = getIndex(0, Shape.values().length-1);
		Pattern p = TilesFactory.createPattern(Shape.values()[index], Colors.ALL[1]);

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using tiles on radar chart datasets");

		RadarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(p);
		dataset1.setData(getFixedDigits(ITEMS));

		chart.getData().setLabels(getLabels(ITEMS));
		chart.getData().setDatasets(dataset1);
	}

	private int getIndex(int min, int max) {
		return random.nextInt(max + 1 - min) + min;
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			RadarDataset dataset1 = (RadarDataset) dataset;
			dataset1.setData(getRandomDigits(ITEMS));
			int index = getIndex(0, Shape.values().length-1);
			Pattern p = TilesFactory.createPattern(Shape.values()[index], Colors.ALL[1]);
			dataset1.setBackgroundColor(p);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
