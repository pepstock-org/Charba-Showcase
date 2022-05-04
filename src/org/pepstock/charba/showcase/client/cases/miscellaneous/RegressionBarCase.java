package org.pepstock.charba.showcase.client.cases.miscellaneous;

import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
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

public class RegressionBarCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, RegressionBarCase> {
	}

	@UiField
	BarChartWidget chart;

	final BarDataset dataset;
	
	RegressionDataset trend;
	
	public RegressionBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
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

		dataset = chart.newDataset();
		dataset.setLabel("Dataset");
		
		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.alpha(0.2));
		dataset.setBorderColor(color1.toHex());
		dataset.setBorderWidth(1);
		dataset.setData(getLinearRegressionDigits(months));
		
		trend = RegressionDatasetBuilder.create().setSamples(dataset.getData()).buildLinearRegression();
		trend.setLabel("Regression");
		trend.setBorderColor(HtmlColor.CRIMSON);
		trend.setBorderWidth(4);
		trend.setBorderDash(4, 6);

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset, trend);

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
		trend.setBorderWidth(4);
		trend.setBorderDash(4, 6);
		chart.getData().setDatasets(dataset, trend);
		chart.update();
	}
}
