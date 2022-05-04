package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.ml.RegressionDataset;
import org.pepstock.charba.client.ml.RegressionDatasetBuilder;
import org.pepstock.charba.client.ml.RegressionScore;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class RegressionScatterCase extends BaseComposite {

	private static final int AMOUNT_OF_POINTS = 100;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RegressionScatterCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	RegressionDataset trend;
	
	ScatterDataset dataset;

	public RegressionScatterCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				return "Linear regression formula: "+trend.getRegression().toFormula();
			}
			
		});
		chart.getOptions().getSubtitle().setDisplay(true);
		chart.getOptions().getSubtitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				RegressionScore score = trend.getRegression().scoreByDataPoints(dataset.getDataPoints());
				return "Score R: "+Utilities.applyPrecision(score != null ? score.getR() * 100 : 0, 2)+"%, R2: "+Utilities.applyPrecision(score != null ? score.getR2() * 100 : 0, 2)+"%";
			}
			
		});

		dataset = chart.newDataset();
		dataset.setLabel("Dataset");

		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());

		double[] xs1 = getX();
		double[] ys1 = getLinearRegressionDigits(AMOUNT_OF_POINTS);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			dp1[i] = new DataPoint();
			dp1[i].setX(xs1[i]);
			dp1[i].setY(ys1[i]);
		}
		dataset.setDataPoints(dp1);
		
		trend = RegressionDatasetBuilder.create().setSamples(dataset).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(2);
		trend.setBorderDash(4, 6);
		trend.addXValues(0, 100);
	
		chart.getData().setDatasets(dataset, trend);
	
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		List<DataPoint> points = dataset.getDataPoints();
		double[] xs1 = getX();
		double[] ys1 = getLinearRegressionDigits(AMOUNT_OF_POINTS);
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			DataPoint dp = points.get(i);
			dp.setX(xs1[i]);
			dp.setY(ys1[i]);
		}
		boolean hidden = !chart.isDatasetVisible(1);
		trend = RegressionDatasetBuilder.create().setSamples(dataset).setHidden(hidden).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(2);
		trend.setBorderDash(4, 6);
		trend.addXValues(0, 100);
		chart.getData().setDatasets(dataset, trend);
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private double[] getX() {
		double[] x = new double[AMOUNT_OF_POINTS];
		for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
			x[i] = i;
		}
		return x;
	}
	
}
