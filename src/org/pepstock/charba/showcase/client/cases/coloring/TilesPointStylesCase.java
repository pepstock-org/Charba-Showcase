package org.pepstock.charba.showcase.client.cases.coloring;

import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.colors.tiles.TilesFactoryDefaults;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.PointStyle;
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

public class TilesPointStylesCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TilesPointStylesCase> {
	}

	private static final HtmlColor[] BACKGROUND = { HtmlColor.ORANGE_RED, HtmlColor.LIGHT_BLUE, HtmlColor.LIGHT_GREEN, HtmlColor.ORANGE, HtmlColor.IVORY };

	private static final HtmlColor[] COLORS = { HtmlColor.BLACK, HtmlColor.WHITE, HtmlColor.LIGHT_GRAY };

	@UiField
	LineChart chart;

	@UiField
	ListBox pointStyles;

	@UiField
	ListBox background;

	@UiField
	ListBox color;

	private static final int ITEMS = 12;

	LineDataset dataset1 = null;

	public TilesPointStylesCase() {
		initWidget(uiBinder.createAndBindUi(this));

		for (PointStyle myPointStyle : PointStyle.values()) {
			pointStyles.addItem(myPointStyle.name(), myPointStyle.name());
		}

		background.addItem("Default", TilesFactoryDefaults.DEFAULT_BACKGROUND_COLOR_AS_STRING);
		for (HtmlColor myColor : BACKGROUND) {
			background.addItem(myColor.name(), myColor.toRGB());
		}

		color.addItem("Default", TilesFactoryDefaults.DEFAULT_SHAPE_COLOR_AS_STRING);
		for (HtmlColor myColor : COLORS) {
			color.addItem(myColor.name(), myColor.toRGB());
		}

		Pattern p = TilesFactory.createPattern(PointStyle.CIRCLE);

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Using tiles on line chart datasets");

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		dataset1.setBackgroundColor(p);
		dataset1.setData(getFixedDigits(ITEMS));

		chart.getData().setLabels(getLabels(ITEMS));
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset1.setData(getRandomDigits(ITEMS));
		applyTile();
	}

	@UiHandler(value = { "pointStyles", "color", "background" })
	protected void handleShapes(ChangeEvent event) {
		applyTile();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	protected void applyTile() {
		String selectedPointStyle = pointStyles.getSelectedValue();
		String selectedColor = color.getSelectedValue();
		String selectedBackground = background.getSelectedValue();
		PointStyle pointStyle = PointStyle.valueOf(selectedPointStyle);
		Pattern p = TilesFactory.createPattern(pointStyle, selectedBackground, selectedColor);
		dataset1.setBackgroundColor(p);
		chart.update();
	}

}
