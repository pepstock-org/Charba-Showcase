package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.LinkedList;
import java.util.List;

import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.jsinterop.enums.Fill;
import org.pepstock.charba.client.jsinterop.AbstractChart;
import org.pepstock.charba.client.jsinterop.LineChart;
import org.pepstock.charba.client.jsinterop.callbacks.TickCallback;
import org.pepstock.charba.client.jsinterop.configuration.Axis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.jsinterop.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.jsinterop.data.Dataset;
import org.pepstock.charba.client.jsinterop.data.LineDataset;
import org.pepstock.charba.client.jsinterop.items.ScaleItem;
import org.pepstock.charba.client.jsinterop.options.Scales;
import org.pepstock.charba.client.jsinterop.plugins.AbstractPlugin;
import org.pepstock.charba.client.jsinterop.plugins.InvalidPluginIdException;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;
import org.pepstock.charba.showcase.client.samples.Toast.Level;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class StandingView extends BaseComposite{
	
	private static final String[] YEARS = {"2004",
			"2006",
			"2008",
			"2010",
			"2012",
			"2013"};
	
	private static final String[] FACTORS = {"Market Factors",
			"People Skills",
			"Macro-economic Factors",
			"Globalization",
			"Regulatory Concerns",
			"Technology Factors",
			"Socio-economic Factors",
			"Environmental Issues",
			"Geopolitical Factors"};
	
	private static final double[] STANDINGS_1 = new double[]{1,1,1,1,3,2}; 
	private static final double[] STANDINGS_2 = new double[]{2,2,2,4,2,4}; 
	private static final double[] STANDINGS_3 = new double[]{3,6,6,3,4,3}; 
	private static final double[] STANDINGS_4 = new double[]{4,5,4,6,6,7}; 
	private static final double[] STANDINGS_5 = new double[]{5,4,5,5,5,5}; 
	private static final double[] STANDINGS_6 = new double[]{6,3,3,2,1,1}; 
	private static final double[] STANDINGS_7 = new double[]{7,7,8,8,7,6}; 
	private static final double[] STANDINGS_8 = new double[]{8,8,7,7,8,8}; 
	private static final double[] STANDINGS_9 = new double[]{9,9,9,9,9,9}; 
	
	private static final double[][] STANDINGS = new double[][]{STANDINGS_1, STANDINGS_2, STANDINGS_3, STANDINGS_4, STANDINGS_5, STANDINGS_6, STANDINGS_7, STANDINGS_8, STANDINGS_9};
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, StandingView> {
	}

	@UiField
	LineChart chart;
	
	public StandingView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(false);
		chart.getOptions().getTitle().setText("Charba Line Chart");
		chart.getOptions().getLayout().getPadding().setTop(40);
		chart.getOptions().getLayout().getPadding().setRight(40);
//		chart.getOptions().getTooltips().setMode(InteractionMode.index);
//		chart.getOptions().getTooltips().setIntersect(false);
//		chart.getOptions().getHover().setMode(InteractionMode.nearest);
//		chart.getOptions().getHover().setIntersect(true);
		
		List<Dataset> datasets = new LinkedList<Dataset>();
		for (int i=0; i<FACTORS.length; i++) {
			LineDataset dataset = chart.newDataset();
			dataset.setLabel(FACTORS[i]);
			IsColor color = Colors.ALL[i];
			dataset.setBackgroundColor(color.toHex());
			dataset.setBorderColor(color.toHex());
			double[] values = STANDINGS[i];
			dataset.setData(values);
			dataset.setFill(Fill.nofill);
			datasets.add(dataset);
		}

		CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
		axis1.setDisplay(true);
		
		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setReverse(true);
		
		axis2.getTicks().setCallback(new TickCallback() {
		
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.jsinterop.callbacks.TickCallback#onCallback(org.pepstock.charba.client.jsinterop.configuration.Axis, double, int, java.util.List)
			 */
			@Override
			public String onCallback(Axis axis, double value, int index, List<Double> values) {
				List<Dataset> dss = chart.getData().getDatasets();
				Dataset ds = dss.get(index);
				return ds.getLabel();
			}
		});
		
		
		chart.getOptions().getScales().setXAxes(axis1);
		chart.getOptions().getScales().setYAxes(axis2);
		
		chart.getData().setLabels(YEARS);
//		chart.getData().setDatasets(dataset1, dataset2);
		chart.getData().setDatasets(datasets.toArray(new Dataset[0]));
		
		AbstractPlugin p = new AbstractPlugin() {

			@Override
			public String getId() {
				return "standings";
			}

			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onAfterDatasetsDraw(org.pepstock.charba.client.AbstractChart, double)
			 */
			@Override
			public void onAfterDatasetsDraw(AbstractChart<?, ?> chart, double easing) {
				final int padding = 30;
				final int datasetsCount = chart.getData().getDatasets().size();
				Context2d ctx = chart.getCanvas().getContext2d();

				ScaleItem scaleX = chart.getNode().getScales().getItems().get(Scales.DEFAULT_X_AXIS_ID);
				ScaleItem scaleY = chart.getNode().getScales().getItems().get(Scales.DEFAULT_Y_AXIS_ID);
				
				int heightAmongLabels = (scaleY.getBottom() - scaleY.getTop()) / (datasetsCount-1);

				int x = scaleX.getRight() + padding;
				int y = scaleY.getTop();
				
				for (int i=0; i<datasetsCount; i++) {
					int index = i + 1;
					ctx.setFillStyle("rgb(0, 0, 0)");
					ctx.setFont("22px bold Helvetica Neue");
					ctx.setTextAlign(TextAlign.CENTER);
					ctx.setTextBaseline(TextBaseline.MIDDLE);
					ctx.fillText(String.valueOf(index), x, y);
					y = y + heightAmongLabels;
				}
			}
		};
		try {
			chart.getPlugins().add(p);
		} catch (InvalidPluginIdException e) {
			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
		}

	}
	

}
