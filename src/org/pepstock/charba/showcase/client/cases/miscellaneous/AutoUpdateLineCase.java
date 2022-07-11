package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.ChartTimerTask;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.configuration.CartesianTimeAxis;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.AxisKind;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.utils.CTimer;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AutoUpdateLineCase extends BaseComposite {

	private static final int INTERVAL = 5 * 1000;

	private static final int AMOUNT_OF_POINTS = 20;
	
	private static final int INITIAL_AMOUNT_OF_POINTS = AMOUNT_OF_POINTS / 2;
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, AutoUpdateLineCase> {
	}

	@UiField
	LineChartWidget chart;
	
	@UiField
	Button start;

	@UiField
	Button stop;
	
	@UiField
	Label countDown;

	final Date nowDate = new Date();

	final LineDataset dataset;

	final CartesianTimeAxis axis;
	
	final CTimer timer = new CTimer(this::countDown, 1000);
	
	private int seconds = 5;

	public AutoUpdateLineCase() {
		initWidget(uiBinder.createAndBindUi(this));

		countDown.setText("Next data in " + seconds + " seconds");
		timer.start();

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Auto update chart (every 5 seconds)");

		dataset = chart.newDataset();
		dataset.setLabel("Data");
		dataset.setFill(Fill.FALSE);
		IsColor color1 = GoogleChartColor.values()[0];
		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		double time = nowDate.getTime() - INTERVAL * (INITIAL_AMOUNT_OF_POINTS - 1);

		double[] xs1 = getRandomDigits(INITIAL_AMOUNT_OF_POINTS, false);
		DataPoint[] dataDp = new DataPoint[INITIAL_AMOUNT_OF_POINTS];
		for (int i = 0; i < INITIAL_AMOUNT_OF_POINTS; i++) {
			dataDp[i] = new DataPoint();
			Date newDate = new Date((long) time);
			dataDp[i].setY(xs1[i]);
			dataDp[i].setX(newDate);
			time = time + INTERVAL;
		}
		dataset.setDataPoints(dataDp);

		axis = new CartesianTimeAxis(chart, AxisKind.X);
		axis.getTicks().setSource(TickSource.DATA);
		axis.getTime().setUnit(TimeUnit.SECOND);
		axis.getTime().getDisplayFormats().setDisplayFormat(TimeUnit.SECOND, "H:mm:ss");
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.setBeginAtZero(true);

		chart.getOptions().getScales().setAxes(axis, axis2);
		chart.getData().setDatasets(dataset);
		
		chart.createAndSetTimer(new ChartTimerTask() {
			
			@Override
			public void execute(IsChart chart) {
				DataPoint dataDp = new DataPoint();
				dataDp.setY(getRandomDigit(false));
				dataDp.setX(new Date());
				List<DataPoint> points = dataset.getDataPoints(true);
				points.add(dataDp);
				if (points.size() > AMOUNT_OF_POINTS) {
					points.remove(0);
				}
				chart.update();
			}
		
		}, INTERVAL);
	}
	
	protected void countDown() {
		seconds--;
		if (seconds == 0) {
			seconds = 5;
		}
		countDown.setText("Next data in " + seconds + " seconds");
	}

	@UiHandler("start")
	protected void handleStart(ClickEvent event) {
		chart.getTimer().start();
		start.setEnabled(false);
		stop.setEnabled(true);
		seconds = 5;
		timer.start();
		countDown.setText("Next data in " + seconds + " seconds");
	}

	@UiHandler("stop")
	protected void handleStop(ClickEvent event) {
		chart.getTimer().stop();
		timer.stop();
		stop.setEnabled(false);
		start.setEnabled(true);
		countDown.setText("Stopped!");
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
