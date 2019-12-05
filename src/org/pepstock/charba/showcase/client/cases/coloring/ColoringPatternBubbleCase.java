package org.pepstock.charba.showcase.client.cases.coloring;

import java.util.Random;

import org.pepstock.charba.client.BubbleChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.data.BubbleDataset;
import org.pepstock.charba.client.data.DataPoint;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.showcase.client.cases.jsinterop.BaseComposite;
import org.pepstock.charba.showcase.client.resources.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class ColoringPatternBubbleCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ColoringPatternBubbleCase> {
	}

	private static final int AMOUNT_OF_POINTS = 8;
	private static final int MIN_XY = -150;
	private static final int MAX_XY = 100;
	
	@UiField
	BubbleChart chart;

	public ColoringPatternBubbleCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Applying a pattern on bubble chart");

		Pattern pattern = new Pattern(Images.INSTANCE.backgroundPattern());
		Pattern pattern2 = new Pattern(Images.INSTANCE.backgroundPattern2());

		BubbleDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");

		Pattern[] patterns = new Pattern[AMOUNT_OF_POINTS];
		IsColor[] colors = new IsColor[AMOUNT_OF_POINTS];
		int[] bwidth = new int[AMOUNT_OF_POINTS];
		
		DataPoint[] dp1 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp1[i] = new DataPoint();
			dp1[i].setX(getData());
			dp1[i].setY(getData());
			dp1[i].setR(getData(0, 50));
			patterns[i] = pattern;
			colors[i] = HtmlColor.DARK_GREY;
			bwidth[i] = Math.min(Math.max(1, i + 1), 5);
		}
		dataset1.setBackgroundColor(patterns);
		dataset1.setBorderColor(colors);
		dataset1.setBorderWidth(bwidth);
		dataset1.setDataPoints(dp1);

		BubbleDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		Pattern[] patterns2 = new Pattern[AMOUNT_OF_POINTS];
		IsColor[] colors2 = new IsColor[AMOUNT_OF_POINTS];
		int[] bwidth2 = new int[AMOUNT_OF_POINTS];
		DataPoint[] dp12 = new DataPoint[AMOUNT_OF_POINTS];
		for (int i=0; i<AMOUNT_OF_POINTS; i++){
			dp12[i] = new DataPoint();
			dp12[i].setX(getData());
			dp12[i].setY(getData());
			dp12[i].setR(getData(0, 50));
			patterns2[i] = pattern2;
			colors2[i] = HtmlColor.RED;
			bwidth2[i] = Math.min(Math.max(1, i + 1), 5);
		}
		dataset2.setBackgroundColor(patterns2);
		dataset2.setBorderColor(colors2);
		dataset2.setBorderWidth(bwidth2);
		dataset2.setDataPoints(dp12);

		chart.getData().setDatasets(dataset1, dataset2);
	}
	
	private int getData(){
		return getData(MIN_XY, MAX_XY);
	}

	private int getData(int min, int max){
		Random random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		int[] bwidth = new int[AMOUNT_OF_POINTS];

		for (Dataset dataset : chart.getData().getDatasets()){
			BubbleDataset bDataset = (BubbleDataset)dataset;
			int i = 0;
			for (DataPoint dp : bDataset.getDataPoints()){
				dp.setX(getData());
				dp.setY(getData());
				dp.setR(getData(0, 50));
				bwidth[i] = Math.min(Math.max(1, i + 1), 5);
				i++;
			}
			bDataset.setBorderWidth(bwidth);
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
