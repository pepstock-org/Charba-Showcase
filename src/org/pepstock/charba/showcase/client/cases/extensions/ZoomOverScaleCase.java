package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.dom.enums.CursorType;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.events.AxisClickEvent;
import org.pepstock.charba.client.events.AxisClickEventHandler;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class ZoomOverScaleCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 200;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomOverScaleCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	@UiField
	CheckBox enableZoom;
	
	@UiField
	CheckBox enablePan;
	
	public ZoomOverScaleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Zooming on scatter chart");

		ScatterDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys1 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(xs1[i]);
			dp1[i].setY(ys1[i]);
		}
		dataset1.setDataPoints(dp1);

		ScatterDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");

		IsColor color2 = GoogleChartColor.values()[3];

		dataset2.setBackgroundColor(color2.toHex());
		dataset2.setBorderColor(color2.toHex());
		double[] xs2 = getRandomDigits(AMOUNT_OF_POINTS);
		double[] ys2 = getRandomDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp2 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp2[i] = new DataPoint();
			dp2[i].setX(xs2[i]);
			dp2[i].setY(ys2[i]);
		}
		dataset2.setDataPoints(dp2);

		chart.getData().setDatasets(dataset1, dataset2);
		
		CartesianLinearAxis axis1 = new CartesianLinearAxis(chart, AxisKind.X);
		axis1.setDisplay(true);
		axis1.getGrid().setDisplay(true);
		axis1.getBorder().setColor(HtmlColor.RED);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getGrid().setDisplay(true);
		axis2.getBorder().setColor(HtmlColor.RED);

		chart.getOptions().getScales().setAxes(axis1, axis2);
		
		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(true);
		options.getPan().setOverScaleMode(Mode.XY);
		options.getZoom().setOverScaleMode(Mode.XY);
		options.getZoom().getWheel().setEnabled(true);
		options.getLimits().getX().setMin(-200);
		options.getLimits().getX().setMax(200);
		options.getLimits().getX().setMinRange(20);
		options.getLimits().getY().setMin(-200);
		options.getLimits().getY().setMax(200);
		options.getLimits().getY().setMinRange(50);
		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);
				
		chart.addHandler(new AxisClickEventHandler() {

			@Override
			public void onClick(AxisClickEvent event) {
				// nothing
			}
		}, AxisClickEvent.TYPE);
		
		ChartPointerOptions pointer = new ChartPointerOptions();
		pointer.setElements(PointerElement.AXES);
		pointer.setCursorPointer(CursorType.GRAB);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, pointer);

		chart.getPlugins().add(ChartPointer.get());

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()) {
			ScatterDataset scDataset = (ScatterDataset) dataset;
			for (DataPoint dp : scDataset.getDataPoints()) {
				dp.setX(getRandomDigit());
				dp.setY(getRandomDigit());
			}
		}
		chart.update();
	}

	@UiHandler("reset")
	protected void handleResetZoom(ClickEvent event) {
		ZoomPlugin.reset(chart);
	}
	
	@UiHandler("enableZoom")
	protected void handleZoom(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.FACTORY);
		options.getZoom().getWheel().setEnabled(enableZoom.getValue());
		updatePointer();
		chart.update();
	}
	
	@UiHandler("enablePan")
	protected void handlePan(ClickEvent event) {
		ZoomOptions options = chart.getOptions().getPlugins().getOptions(ZoomPlugin.FACTORY);
		options.getPan().setEnabled(enablePan.getValue());
		updatePointer();
		chart.update();
	}
	
	private void updatePointer() {
		ChartPointerOptions pointer = chart.getOptions().getPlugins().getOptions(ChartPointer.FACTORY);
		if (enablePan.getValue() || enableZoom.getValue()) {
			pointer.setCursorPointer(CursorType.GRAB);
		} else {
			pointer.setCursorPointer(CursorType.DEFAULT);
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
