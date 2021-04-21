package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.ImageShape;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.ImagesHelper;
import org.pepstock.charba.client.gwt.widgets.DoughnutChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TilesImagesCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TilesImagesCase> {
	}

	@UiField
	DoughnutChartWidget chart;

	private static final int ITEMS = 5;

	public TilesImagesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		ImageShape[] imgShapes = { new ImageShape(ImagesHelper.toImg(Images.INSTANCE.cache())), 
				new ImageShape(ImagesHelper.toImg(Images.INSTANCE.extensionWhite())), 
				new ImageShape(ImagesHelper.toImg(Images.INSTANCE.fingerprintWhite())), 
				new ImageShape(ImagesHelper.toImg(Images.INSTANCE.headlineWhite())),
				new ImageShape(ImagesHelper.toImg(Images.INSTANCE.visibilityWhite()))
		};

		Pattern[] tiles = new Pattern[ITEMS];
		for (int i = 0; i < ITEMS; i++) {
			Pattern p = TilesFactory.createPattern(imgShapes[i], GoogleChartColor.values()[i]);
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
