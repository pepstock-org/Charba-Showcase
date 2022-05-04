package org.pepstock.charba.showcase.client.cases.miscellaneous;

import java.util.Arrays;
import java.util.List;

import org.pepstock.charba.client.callbacks.ChartContext;
import org.pepstock.charba.client.callbacks.TextCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.ml.PolynomialRegression;
import org.pepstock.charba.client.ml.RegressionBuilder;
import org.pepstock.charba.client.ml.RegressionDataset;
import org.pepstock.charba.client.ml.RegressionDatasetBuilder;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class PolynomialRegressionBarCase extends BaseComposite {
	
	private static final String[] LABELS = {"Business analyst", "Junior Consultant", "Senior Consultant", "Manager", "Country Manager", "Region Manager", "Partner", "Senior partner", "C-level", "CEO"};
	
	private static final List<Double> DATA = Arrays.asList(45000D, 50000D, 60000D, 80000D, 110000D, 150000D, 200000D, 300000D, 500000D, 1000000D);
	
	private static final List<Double> INDEX = Arrays.asList(0D, 1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D);

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PolynomialRegressionBarCase> {
	}

	@UiField
	BarChartWidget chart;
	
	final RegressionDataset quadratic;
	
	final PolynomialRegression cubic;
	
	public PolynomialRegressionBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText(new TextCallback<ChartContext>() {

			@Override
			public List<String> invoke(ChartContext context) {
				return Arrays.asList("Quadratic regression formula: "+quadratic.getRegression().toFormula(), "Cubic regression formula: "+cubic.toFormula());
			}
			
		});
		chart.getOptions().getSubtitle().setDisplay(true);
		chart.getOptions().getSubtitle().setText(new TextCallback<ChartContext>() {

			@Override
			public List<String> invoke(ChartContext context) {
				return Arrays.asList("Quadratic score: "+Utilities.applyPrecision(quadratic.getRegression().score(DATA).getR() * 100, 2), "Cubic score: "+Utilities.applyPrecision(cubic.score(DATA).getR() * 100, 2));
			}
			
		});

		chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
		chart.getOptions().getTooltips().setIntersect(true);
		
		BarDataset dataset = chart.newDataset();
		dataset.setLabel("Salary");
		
		IsColor color1 = GoogleChartColor.values()[0];

		dataset.setBackgroundColor(color1.alpha(0.2));
		dataset.setBorderWidth(0);
		dataset.setData(DATA);
		
		quadratic = RegressionDatasetBuilder.create().setSamples(DATA).setLoadData(false).buildPolynomialRegression();
		quadratic.setLabel("Quadratic");
		quadratic.setBorderColor(HtmlColor.BLACK);
		quadratic.setPointRadius(3);
		quadratic.setTension(0.4);
		quadratic.setData(quadratic.getRegression().predict(INDEX));
		

		cubic = RegressionBuilder.create().setSamples(DATA).buildPolynomialRegression(3);
		LineDataset cubicDataset = new LineDataset(true);
		cubicDataset.setLabel("Cubic");
		cubicDataset.setBorderColor(HtmlColor.GRAY);
		cubicDataset.setBorderDash(6, 2);
		cubicDataset.setPointRadius(3);
		cubicDataset.setTension(0.4);
		cubicDataset.setData(cubic.predict(INDEX));
		
		chart.getData().setLabels(LABELS);
		chart.getData().setDatasets(dataset, quadratic, cubicDataset);

	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
	
	
}
