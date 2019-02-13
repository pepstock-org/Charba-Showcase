package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.Random;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.BubbleChart;
import org.pepstock.charba.client.data.BubbleDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.datalabels.Context;
import org.pepstock.charba.client.datalabels.DataLabelsOptions;
import org.pepstock.charba.client.datalabels.DataLabelsPlugin;
import org.pepstock.charba.client.datalabels.callbacks.AlignCallback;
import org.pepstock.charba.client.datalabels.callbacks.AnchorCallback;
import org.pepstock.charba.client.datalabels.callbacks.ColorCallback;
import org.pepstock.charba.client.datalabels.enums.Align;
import org.pepstock.charba.client.datalabels.enums.Anchor;
import org.pepstock.charba.client.datalabels.enums.Weight;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class DataLabelsBubbleView extends BaseComposite{
	
	private static final int AMOUNT_OF_POINTS = 16;
	private static final int MIN_XY = -150;
	private static final int MAX_XY = 100;
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DataLabelsBubbleView> {
	}

	@UiField
	BubbleChart chart;
	
	public DataLabelsBubbleView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTooltips().setEnabled(false);
		chart.getOptions().getLayout().getPadding().setTop(42);
		chart.getOptions().getLayout().getPadding().setRight(16);
		chart.getOptions().getLayout().getPadding().setBottom(32);
		chart.getOptions().getLayout().getPadding().setLeft(8);
		
		chart.getOptions().getPlugins().setEnabled("legend", false);
		chart.getOptions().getPlugins().setEnabled("title", false);	
		
		BubbleDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] hcolors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];
		
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			colors[i] = colorize(false, dp1[i]);
			bcolors[i] = colorize(true, dp1[i]);
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
			hcolors[i] = "transparent"; 
			hbwidth[i] = (int)Math.round(8 * dp1[i].getR() / 1000);
		}
		dataset1.setBackgroundColor(colors);
		dataset1.setBorderColor(bcolors);
		dataset1.setBorderWidth(bwidth);
		dataset1.setHoverBackgroundColor(hcolors);
		dataset1.setHoverBorderWidth(hbwidth);
		dataset1.setDataPoints(dp1);
		
		chart.getData().setDatasets(dataset1);

		DataLabelsOptions option = new DataLabelsOptions();
		option.setAnchor(new AnchorCallback() {
			
			@Override
			public Anchor anchor(AbstractChart<?, ?> chart, Context context) {
				BubbleDataset ds = (BubbleDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				DataPoint point = ds.getDataPoints().get(context.getIndex());
				return point.getR() < 20D ? Anchor.end : Anchor.center;
			}
		});
		option.setAlign(new AlignCallback() {
			
			@Override
			public Align align(AbstractChart<?, ?> chart, Context context) {
				BubbleDataset ds = (BubbleDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				DataPoint point = ds.getDataPoints().get(context.getIndex());
				return point.getR() < 20D ? Align.end : Align.center;
			}
		});
		option.setColor(new ColorCallback<String>() {
			
			@Override
			public String color(AbstractChart<?, ?> chart, Context context) {
				BubbleDataset ds = (BubbleDataset)chart.getData().getDatasets().get(context.getDatasetIndex());
				return ds.getBorderColorAsString().get(context.getIndex());
			}
		});
		option.setOffset(2);
		option.getFont().setWeight(Weight.bold);
		option.getPadding().set(0);
		
		chart.getOptions().getPlugins().setOptions(DataLabelsPlugin.ID, option);
	}

	private int getData(){
		return getData(MIN_XY, MAX_XY);
	}

	private int getData(int min, int max){
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
		String[] colors = new String[AMOUNT_OF_POINTS];
		String[] bcolors = new String[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		int[] hbwidth = new int[AMOUNT_OF_POINTS];

		for (Dataset dataset : chart.getData().getDatasets()){
			BubbleDataset bDataset = (BubbleDataset)dataset;
			int i = 0;
			for (DataPoint dp : bDataset.getDataPoints()){
				dp.setX(getData());
				dp.setY(getData());
				dp.setR(getData(0, 50));
				colors[i] = colorize(false, dp);
				bcolors[i] = colorize(true, dp);
				bwidth[i] = Math.min(Math.max(1, i + 1), 5);
				hbwidth[i] = (int)Math.round(8 * dp.getR() / 1000);
				i++;
			}
			bDataset.setBackgroundColor(colors);
			bDataset.setBorderColor(bcolors);
			bDataset.setBorderWidth(bwidth);
		}
		chart.update();
	}
}
