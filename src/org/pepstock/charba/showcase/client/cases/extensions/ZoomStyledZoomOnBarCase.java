package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.zoom.Drag;
import org.pepstock.charba.client.zoom.ZoomOptions;
import org.pepstock.charba.client.zoom.ZoomPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ZoomStyledZoomOnBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomStyledZoomOnBarCase> {
	}

	@UiField
	BarChartWidget chart;

	public ZoomStyledZoomOnBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Styling zooming area on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);

		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[1];

		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(true);
		options.getPan().setMode(InteractionAxis.X);
		options.getPan().setSpeed(10);
		options.getPan().setThreshold(10);
		options.getZoom().setEnabled(true);
		options.getZoom().setMode(InteractionAxis.Y);

		Drag drag = ZoomPlugin.createDrag();
		drag.setBackgroundColor(HtmlColor.LIGHT_GREEN.alpha(0.3));
		drag.setBorderColor(HtmlColor.LIGHT_GREEN);
		drag.setBorderWidth(4);
		options.getZoom().setDrag(drag);

		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.resetZoom(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
