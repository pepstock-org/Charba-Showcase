package org.pepstock.charba.showcase.client.samples.jsni;

import java.util.Date;
import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.LineChart;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.ScaleDistribution;
import org.pepstock.charba.client.enums.TickSource;
import org.pepstock.charba.client.enums.TimeUnit;
import org.pepstock.charba.client.events.DatasetRangeSelectionEvent;
import org.pepstock.charba.client.events.DatasetRangeSelectionEventHandler;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.options.scales.CartesianLinearAxis;
import org.pepstock.charba.client.options.scales.CartesianTimeAxis;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.client.plugins.impl.DatasetsItemsSelector;
import org.pepstock.charba.client.plugins.impl.DatasetsItemsSelectorOptions;
import org.pepstock.charba.showcase.client.samples.Colors;
import org.pepstock.charba.showcase.client.samples.Toast;
import org.pepstock.charba.showcase.client.samples.jsinterop.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class TimeSeriesView extends BaseComposite{
	
	private static final DateTimeFormat FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
	
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	private static final int AMOUNT_OF_POINTS = 60;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TimeSeriesView> {
	}

	@UiField
	LineChart chart;

	@UiField
	LineChart small;
	
	public TimeSeriesView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().setMaintainAspectRatio(true);
		chart.getOptions().setAspectRatio(3);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Line Timeseries Chart");
		chart.getOptions().getTooltips().setTitleMarginBottom(10);
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public String[] onBeforeTitle(AbstractChart<?, ?> chart, List<TooltipItem> items) {
				return null;
			}

			@Override
			public String[] onTitle(AbstractChart<?, ?> chart, List<TooltipItem> items) {
				TooltipItem item = items.iterator().next();
				LineDataset ds = (LineDataset)chart.getData().getDatasets().get(0);
				DataPoint dp = ds.getDataPoints().get(item.getIndex());
				return new String[] {FORMAT.format(dp.getT())};
			}

			@Override
			public String[] onAfterTitle(AbstractChart<?, ?> chart, List<TooltipItem> items) {
				return null;
			}
		});
		
		
		final LineDataset dataset1 = chart.newDataset();

		dataset1.setLabel("dataset 1");
		dataset1.setFill(Fill.origin);
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.toHex());
		dataset1.setBorderColor(color1.toHex());

		LineDataset dataset2 = small.newDataset();
//		IsColor color2 = Colors.ALL[2].alpha(0.5);
//		dataset2.setBackgroundColor(color2.toRGBA());
//		dataset2.setBorderColor(color2.toRGBA());

		//long time = System.currentTimeMillis();
		long time = new Date().getTime();
	
		double[] xs1 = getRandomDigits(AMOUNT_OF_POINTS, false);
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setY(xs1[i]);
			dp1[i].setT(new Date(time));
			time = time + DAY;
		}
//		dataset1.setDataPoints(dp1);
		dataset2.setDataPoints(dp1);
		
		final CartesianTimeAxis axis = new CartesianTimeAxis(chart);
		axis.setDistribution(ScaleDistribution.series);
		axis.getTicks().setSource(TickSource.data);
		axis.getTime().setUnit(TimeUnit.day);

		CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
		axis2.setDisplay(true);
		axis2.getTicks().setBeginAtZero(true);

		
//		chart.getData().setDatasets(dataset1);
		
		chart.getOptions().getScales().setXAxes(axis);
		chart.getOptions().getScales().setYAxes(axis2);
		
		small.getOptions().setResponsive(true);
//		small.getOptions().setMaintainAspectRatio(true);
		small.getOptions().setMaintainAspectRatio(true);
		small.getOptions().setAspectRatio(15);
		small.getOptions().getLegend().setDisplay(false);
		small.getOptions().getTitle().setDisplay(false);
		small.getOptions().getElements().getPoint().setRadius(0);
		
		CartesianTimeAxis axis1Small = new CartesianTimeAxis(small);
		axis1Small.setDisplay(false);

//		CartesianLinearAxis axis2Small = new CartesianLinearAxis(small);
//		axis2Small.setDisplay(false);

		small.getData().setDatasets(dataset2);
		
		small.getOptions().getScales().setXAxes(axis1Small);
		
		DatasetsItemsSelectorOptions pOptions = new DatasetsItemsSelectorOptions();
		pOptions.setBorderWidth(1);
		pOptions.setBorderDash(6);
		try {
			small.getOptions().getPlugins().setOptions(DatasetsItemsSelector.ID, pOptions.getObject());
			small.getPlugins().add(new DatasetsItemsSelector());
		} catch (InvalidPluginIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		small.addHandler(new DatasetRangeSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetRangeSelectionEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Dataset from: <b>").append(event.getFrom()).append("</b><br>");
				sb.append("Dataset to: <b>").append(event.getTo()).append("</b><br>");
				new Toast("Dataset Range Selected!", sb.toString()).show();
//				List<DataPoint> points = dataset1.getDataPoints();
				int tot = event.getTo() - event.getFrom() + 1;
				DataPoint[] dp1 = new DataPoint[tot];
				for (int i=0; i<tot; i++) {
					dp1[i] = dataset2.getDataPoints().get(i+event.getFrom());
					
				}
				dataset1.setDataPoints(dp1);
				chart.getData().setDatasets(dataset1);
//				Date min = points.get().getT();
//				Date max = points.get(event.getTo()).getT();
//			    axis.getTime().setMin(min);
//			    axis.getTime().setMax(max);
			    chart.update();

			}
		}, DatasetRangeSelectionEvent.TYPE);
	}
	
	
	
	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : small.getData().getDatasets()){
			LineDataset scDataset = (LineDataset)dataset;
			for (DataPoint dp : scDataset.getDataPoints()){
				dp.setY(getRandomDigit(false));
			}
		}
//		chart.update();
		small.update();
	}
}