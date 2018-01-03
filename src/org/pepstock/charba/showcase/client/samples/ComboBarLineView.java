package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.Type;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.Fill;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class ComboBarLineView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, ComboBarLineView> {
	}

	@UiField 
	BarChart chart;
	
	public ComboBarLineView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Combo Bar Line Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(true);
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setType(Type.bar.name());
		dataset1.setLabel("dataset 1");
		
		Color color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2).toRGBA());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setType(Type.bar.name());
		dataset2.setLabel("dataset 2");
		
		Color color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2).toRGBA());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		LineDataset dataset3 = new LineDataset();
		dataset3.setType(Type.line.name());
		dataset3.setLabel("dataset 3");
		
		Color color3 = Colors.ALL[3];
		
		dataset3.setBackgroundColor(color3.toHex());
		dataset3.setBorderColor(color3.toHex());
		dataset3.setData(getRandomDigits(months));
		dataset3.setFill(Fill.nofill);

		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2, dataset3);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}
}
