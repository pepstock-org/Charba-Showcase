package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Arrays;
import java.util.List;

import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.ScatterDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.gwt.widgets.ScatterChartWidget;
import org.pepstock.charba.client.ml.RegressionDataset;
import org.pepstock.charba.client.ml.RegressionDatasetBuilder;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ExponentialRegressionScatterCase extends BaseComposite {
	
	private static final List<Double> Y = Arrays.asList(4.0, 9.0, 12.0, 25.0, 53.0, 98.0, 150.0);

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ExponentialRegressionScatterCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	final ScatterDataset dataset;
	
	RegressionDataset exp;
	
	public ExponentialRegressionScatterCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				return "Exponential regression formula: "+exp.getRegression().toFormula(3);
			}
			
		});
		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(true);

		List<Dataset> datasets = chart.getData().getDatasets(true);

		dataset = chart.newDataset();
		dataset.setLabel("Dataset");
		
		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.toHex());
		dataset.setBorderColor(color1.toHex());
		dataset.setPointRadius(10);
		
		List<DataPoint> data = dataset.getDataPoints(true);
		for (int i = 1; i <= Y.size(); i++) {
			data.add(new DataPoint(i, Y.get(i-1)));
		}
		datasets.add(dataset);

		exp = RegressionDatasetBuilder.create().setSamplesByDataPoints(data).setLoadData(true).buildExponentialRegression();
		exp.setLabel("Regression");
		exp.setBorderColor(HtmlColor.CRIMSON);
		exp.setBorderWidth(2);
		exp.setBorderDash(4, 6);
		exp.setTension(0.25f);
		List<DataPoint> dataPower = exp.getDataPoints();
		dataPower.add(0, new DataPoint(0, exp.getRegression().predict(0)));
		datasets.add(exp);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
