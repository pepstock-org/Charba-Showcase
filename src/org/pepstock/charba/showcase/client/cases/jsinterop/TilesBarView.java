package org.pepstock.charba.showcase.client.cases.jsinterop;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.colors.Pattern;
import org.pepstock.charba.client.colors.tiles.Shape;
import org.pepstock.charba.client.colors.tiles.TilesFactory;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.PointStyle;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TilesBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget,TilesBarView> {
	}

	@UiField
	BarChart chart;
	
	public TilesBarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.80D));
	
		Pattern p1 = TilesFactory.createPattern(Shape.SQUARE, Colors.ALL[0]);
		Pattern p2 = TilesFactory.createPattern(Shape.VERTICAL_ZIGZAG, Colors.ALL[1]);
		Pattern p3 = TilesFactory.createPattern(Shape.DIAGONAL, Colors.ALL[2]);
		Pattern p4 = TilesFactory.createPattern(Shape.RING, Colors.ALL[3]);
		Pattern p5 = TilesFactory.createPattern(Shape.DOT_DASH, Colors.ALL[4]);
//		Pattern p6 = TilesFactory.createPattern(Shape.EMPTY_STAR, Colors.ALL[5]);
		Pattern p7 = TilesFactory.createPattern(Shape.ZIGZAG, Colors.ALL[6]);

//		Pattern p1 = TilesFactory.createPattern(PointStyle.CROSS_ROT, Colors.ALL[0], Colors.ALL[7]);
//		Pattern p2 = TilesFactory.createPattern(PointStyle.DASH, Colors.ALL[1], Colors.ALL[8]);
//		Pattern p3 = TilesFactory.createPattern(PointStyle.LINE, Colors.ALL[2], Colors.ALL[9]);
//		Pattern p4 = TilesFactory.createPattern(PointStyle.RECT, Colors.ALL[3], Colors.ALL[10]);
//		Pattern p5 = TilesFactory.createPattern(PointStyle.RECT_ROUNDED, Colors.ALL[4], Colors.ALL[11]);
		Pattern p6 = TilesFactory.createPattern(PointStyle.RECT_ROT, Colors.ALL[5], Colors.ALL[12]);
//		Pattern p7 = TilesFactory.createPattern(PointStyle.STAR, Colors.ALL[6], Colors.ALL[13]);

		
//		TRIANGLE(PointStyle.TRIANGLE, new PointStyleTriangle());

		
		dataset1.setBackgroundColor(p1, p2, p3, p4, p5, p6,p7);

		dataset1.setData(getFixedDigits(7));
		
		chart.getData().setLabels(getLabels(7));
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(7));
		}
		chart.update();
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
