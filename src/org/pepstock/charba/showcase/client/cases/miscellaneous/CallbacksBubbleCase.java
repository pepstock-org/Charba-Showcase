package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Random;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.data.BubbleDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.gwt.widgets.BubbleChartWidget;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CallbacksBubbleCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 16;
	private static final int MIN_XY = -150;
	private static final int MAX_XY = 100;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, CallbacksBubbleCase> {
	}

	@UiField
	BubbleChartWidget chart;

	BubbleDataset dataset = null;

	public CallbacksBubbleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Callbacks on bubble chart dataset");

		dataset = chart.newDataset();
		dataset.setLabel("dataset 1");

		String[] hcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent";
			hbwidth[i] = (int) Math.round(8 * dp1[i].getR() / 1000);
		}
		dataset.setBackgroundColor(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				DataPoint dp = dataset.getDataPoints().get(context.getDataIndex());
				return colorize(false, dp);
			}

		});
		dataset.setBorderColor(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				DataPoint dp = dataset.getDataPoints().get(context.getDataIndex());
				return colorize(true, dp);
			}

		});
		dataset.setBorderWidth(bwidth);
		dataset.setHoverBackgroundColor(hcolors);
		dataset.setHoverBorderWidth(hbwidth);
		dataset.setDataPoints(dp1);

		chart.getData().setDatasets(dataset);
	}

	private int getData() {
		return getData(MIN_XY, MAX_XY);
	}

	private int getData(int min, int max) {
		Random random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}

	private String colorize(boolean opaque, DataPoint value) {
		double x = value.getX() / 100;
		double y = value.getY() / 100;
		int r = x < 0 && y < 0 ? 250 : x < 0 ? 150 : y < 0 ? 50 : 0;
		int g = x < 0 && y < 0 ? 0 : x < 0 ? 50 : y < 0 ? 150 : 250;
		int b = x < 0 && y < 0 ? 0 : x > 0 && y > 0 ? 250 : 150;
		double a = opaque ? 1 : 0.2 * value.getR() / 50;

		return "rgba(" + r + "," + g + "," + b + "," + a + ")";
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		int i = 0;
		for (DataPoint dp : dataset.getDataPoints()) {
			dp.setX(getData());
			dp.setY(getData());
			dp.setR(getData(0, 50));
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hbwidth[i] = (int) Math.round(8 * dp.getR() / 1000);
			i++;
		}
		dataset.setBorderWidth(bwidth);

		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
