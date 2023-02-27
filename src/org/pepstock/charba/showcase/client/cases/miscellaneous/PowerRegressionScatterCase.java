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
import org.pepstock.charba.client.enums.DefaultInteractionMode;
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

public class PowerRegressionScatterCase extends BaseComposite {
	
	private static final List<Double> Y = Arrays.asList(159.9, 320.9, 326.8, 329.9, 360.6, 361.6, 361.7, 367.6, 374.1, 375.2, 376.7, 400.2);

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PowerRegressionScatterCase> {
	}

	@UiField
	ScatterChartWidget chart;
	
	final ScatterDataset dataset;
	
	RegressionDataset power;
	
	public PowerRegressionScatterCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText(new TextCallback<ChartContext>() {

			@Override
			public Object invoke(ChartContext context) {
				return "Power regression formula: "+power.getRegression().toFormula(3);
			}
			
		});
		chart.getOptions().getTooltips().setMode(DefaultInteractionMode.INDEX);
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

		power = RegressionDatasetBuilder.create().setSamplesByDataPoints(data).setLoadData(true).buildPowerRegression();
		power.setLabel("Regression");
		power.setBorderColor(HtmlColor.CRIMSON);
		power.setBorderWidth(2);
		power.setBorderDash(4, 6);
		power.setTension(0.25f);
		
		List<DataPoint> dataPower = power.getDataPoints();
		dataPower.add(0, new DataPoint(0, power.getRegression().predict(0)));
		datasets.add(power);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
