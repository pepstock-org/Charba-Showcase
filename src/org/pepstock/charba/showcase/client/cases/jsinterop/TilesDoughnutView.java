package org.pepstock.charba.showcase.client.cases.jsinterop;

import org.pepstock.charba.client.DoughnutChart;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.CharacterShape;
import org.pepstock.charba.client.colors.tiles.ImageShape;
import org.pepstock.charba.client.colors.tiles.Shape;
import org.pepstock.charba.client.colors.tiles.TilesBuilder;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.DoughnutDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TilesDoughnutView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TilesDoughnutView> {
	}

	@UiField
	DoughnutChart chart;

	@UiField
	VerticalPanel container;
	
	@UiField
	HTMLPanel image;

	public TilesDoughnutView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Doughnut Chart");
		
		DoughnutDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		
		ImageShape imageShape = new ImageShape(Images.INSTANCE.githubWhite());
		
		CharacterShape charShape = new CharacterShape("m");
		
		Pattern p1 = TilesBuilder.create().setShape(Shape.EMPTY_STAR).setBackgroundColor(Colors.ALL[4]).asPattern();
		Pattern p2 = TilesBuilder.create().setShape(imageShape).setBackgroundColor(Colors.ALL[5]).asPattern();
		Pattern p3 = TilesBuilder.create().setShape(charShape).setBackgroundColor(Colors.ALL[6]).setSize(20).asPattern();
		Pattern p4 = TilesBuilder.create().setShape(Shape.STAR).setBackgroundColor(Colors.ALL[7]).asPattern();
		dataset.setBackgroundColor(p1, p2, p3, p4);

		dataset.setData(getRandomDigits(4, false));

		chart.getData().setLabels(getLabels(4));
		chart.getData().setDatasets(dataset);
		
		// FIXME
		image.getElement().getStyle().setProperty("background", Utilities.toCSSBackgroundProperty(p1));

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
