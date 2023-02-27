package org.pepstock.charba.showcase.client.cases.plugins;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.dom.DOMBuilder;
import org.pepstock.charba.client.dom.elements.Canvas;
import org.pepstock.charba.client.dom.elements.Context2dItem;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.DefaultInteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.impl.plugins.HtmlLegend;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HtmlLegendPointStylesAsCanvasCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HtmlLegendPointStylesAsCanvasCase> {
	}

	private static Canvas imageCanvas = null;

	@UiField
	LineChartWidget chart;
	
	LineDataset dataset1;

	public HtmlLegendPointStylesAsCanvasCase() {
		initWidget(uiBinder.createAndBindUi(this));

		if (imageCanvas == null) {
			imageCanvas = initCanvas();
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getLegend().getLabels().setUsePointStyle(true);

		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("HTML legend using canvas as point style on line chart");
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getHover().setMode(DefaultInteractionMode.NEAREST);
		chart.getOptions().getHover().setIntersect(true);

		dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());
		double[] values = getRandomDigits(months);
		dataset1.setData(values);
		dataset1.setFill(Fill.FALSE);
		dataset1.setClip(40);
		dataset1.setPointStyle(imageCanvas);
		setRotations();

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		axis1.getTitle().setDisplay(true);
		axis1.getTitle().setText("Month");

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTitle().setDisplay(true);
		axis2.getTitle().setText("Value");

		chart.getOptions().getScales().setAxes(axis1, axis2);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

		chart.getPlugins().add(HtmlLegend.get());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			dataset.setData(getRandomDigits(months));
		}
		setRotations();
		chart.update();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private void setRotations() {
		double[] rotations = new double[months];
		rotations[0] = 0D;
		for (int i=0; i<(months-1); i++) {
			rotations[i+1] = getRandomDigit(0, 360);
		}
		dataset1.setPointRotation(rotations);
	}
	
	private Canvas initCanvas() {
		Canvas imageCanvas = DOMBuilder.get().createCanvasElement();
		imageCanvas.setWidth(40);
		imageCanvas.setHeight(40);
		
		Context2dItem imageContext = imageCanvas.getContext2d();

		imageContext.setFillColor("#f00");
		imageContext.beginPath();
		imageContext.moveTo(20, 0);
		imageContext.lineTo(10, 40);
		imageContext.lineTo(20, 30);
		imageContext.closePath();
		imageContext.fill();

		imageContext.setFillColor("#a00");
		imageContext.beginPath();
		imageContext.moveTo(20, 0);
		imageContext.lineTo(30, 40);
		imageContext.lineTo(20, 30);
		imageContext.closePath();
		imageContext.fill();
		
		return imageCanvas;
	}
}
