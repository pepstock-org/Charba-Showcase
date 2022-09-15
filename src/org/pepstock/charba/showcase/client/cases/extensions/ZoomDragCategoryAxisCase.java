package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.dom.elements.Div;
import org.pepstock.charba.client.dom.enums.IsKeyboardKey;
import org.pepstock.charba.client.enums.ModifierKey;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.zoom.ZoomOptions;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.client.zoom.enums.Mode;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ZoomDragCategoryAxisCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomDragCategoryAxisCase> {
	}
	
	private static final int AMOUNT = 40;
	
	@UiField
	BarChartWidget chart;
	
	@UiField
	HTMLPanel help;

	public ZoomDragCategoryAxisCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Drag to zoom on cartesian category axis");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[6];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		dataset1.setData(getRandomDigits(AMOUNT, -100, 100));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[4];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(AMOUNT, -100, 100));

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Color");
		axis1.setMinIndex(10);
		axis1.setMaxIndex(30);

		chart.getData().setLabels(getLabelColors(AMOUNT).toArray(new String[0]));
		chart.getData().setDatasets(dataset1, dataset2);
		chart.getOptions().getScales().setAxes(axis1);

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(true);
		options.getPan().setMode(Mode.X);
		options.getPan().setModifierKey(ModifierKey.ALT);
		options.getZoom().setMode(Mode.X);
		options.getZoom().getWheel().setEnabled(true);
		options.getZoom().getDrag().setEnabled(true);

		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);
		
		Div kkey = IsKeyboardKey.getElement(ModifierKey.ALT.getKeyboardKey());
		HTML html = new HTML("Press "+kkey.getInnerHTML()+" to pan");
		help.add(html);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(AMOUNT, -100, 100));
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.reset(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
