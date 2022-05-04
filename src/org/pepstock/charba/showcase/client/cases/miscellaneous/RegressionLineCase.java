package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.List;

import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
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

public class RegressionLineCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RegressionLineCase> {
	}

	@UiField
	LineChartWidget chart;
	
	final LineDataset dataset;
	
	RegressionDataset trend;

	public RegressionLineCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
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
				RegressionScore score = trend.getRegression().score(dataset.getData());
				return "Score R: "+Utilities.applyPrecision(score != null ? score.getR() * 100 : 0, 2)+"%, R2: "+Utilities.applyPrecision(score != null ? score.getR2() * 100 : 0, 2)+"%";
			}
			
		});
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(false);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		dataset = chart.newDataset();
		dataset.setLabel("Dataset");
		
		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		
		double[] values = getLinearRegressionDigits(months);
		List<Double> data = dataset.getData(true);
		for (int i = 0; i < values.length; i++) {
			data.add(values[i]);
		}
		datasets.add(dataset);
		
		trend = RegressionDatasetBuilder.create().setSamples(dataset.getData()).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(1);
		trend.setBorderDash(2, 4);
		datasets.add(trend);

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
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		dataset.setData(getLinearRegressionDigits(months));
		updateRegression();
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12) {
			chart.getData().getLabels().add(getLabel());
			months++;
			dataset.setData(getLinearRegressionDigits(months));
			updateRegression();
		}
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		if (months > 0) {
			months--;
			chart.getData().setLabels(getLabels());
			dataset.setData(getLinearRegressionDigits(months));
			updateRegression();
		}
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	private void updateRegression() {
		boolean hidden = !chart.isDatasetVisible(1);
		trend = RegressionDatasetBuilder.create().setSamples(dataset.getData()).setHidden(hidden).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(1);
		trend.setBorderDash(2, 4);
		chart.getData().setDatasets(dataset, trend);
		chart.update();
	}
}
