package org.pepstock.charba.showcase.client.cases.extensions;

import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.enums.DefaultScaleId;
import org.pepstock.charba.client.enums.DefaultTransitionKey;
import org.pepstock.charba.client.enums.InteractionAxis;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.zoom.ScaleRange;
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

public class ZoomApiZoomScaleCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 120;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ZoomApiZoomScaleCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	public ZoomApiZoomScaleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Programmatically zoom on linear scale");

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

		IsColor color2 = GoogleChartColor.values()[1];

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

		ZoomOptions options = new ZoomOptions();
		options.getPan().setEnabled(false);
		options.getZoom().setEnabled(true);
		options.getZoom().setMode(InteractionAxis.XY);
		
		chart.getOptions().getPlugins().setOptions(ZoomPlugin.ID, options);

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
	
	@UiHandler("zoomX")
	protected void handleZoomX(ClickEvent event) {
		ScaleRange range = new ScaleRange();
		range.setMin(-100);
		range.setMax(0);
		ZoomPlugin.zoomScale(chart, DefaultScaleId.X, range, DefaultTransitionKey.DEFAULT);
	}
	
	@UiHandler("zoomY")
	protected void handleZoomY(ClickEvent event) {
		ScaleRange range = new ScaleRange();
		range.setMin(0);
		range.setMax(100);
		ZoomPlugin.zoomScale(chart, DefaultScaleId.Y, range, DefaultTransitionKey.DEFAULT);
	}

	@UiHandler("zoomXY")
	protected void handleZoomXY(ClickEvent event) {
		handleZoomX(event);
		handleZoomY(event);
	}
	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
