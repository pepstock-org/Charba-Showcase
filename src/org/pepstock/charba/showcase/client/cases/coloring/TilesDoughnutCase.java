package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.Shape;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.jsinterop.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TilesDoughnutCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TilesDoughnutCase> {
	}

	@UiField
	DoughnutChart chart;

	private static final int ITEMS = 6;

	public TilesDoughnutCase() {
		initWidget(uiBinder.createAndBindUi(this));

		Pattern[] tiles = new Pattern[ITEMS];
		int startingPoint = Shape.values().length - 1;
		int colorStartingPoint = Colors.ALL.length - 1;
		for (int i = 0; i < ITEMS; i++) {
			Pattern p = TilesFactory.createPattern(Shape.values()[startingPoint - i], Colors.ALL[colorStartingPoint - i]);
			tiles[i] = p;
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using tiles on doughnut chart datasets");

		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(tiles);

		dataset.setData(getRandomDigits(ITEMS, false));

		chart.getData().setLabels(getLabels(ITEMS));
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(ITEMS, false));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
