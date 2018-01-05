package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.client.plugins.InvalidPluginIdException;
import org.pepstock.charba.showcase.client.samples.Toast.Level;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class VerticalBarPluginView extends BaseComposite{

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, VerticalBarPluginView> {
	}

	@UiField
	BarChart chart;
	
	public VerticalBarPluginView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		Color color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2).toRGBA());
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		Color color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2).toRGBA());
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		
		dataset2.setData(getRandomDigits(months));
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
		AbstractPlugin p = new AbstractPlugin() {
			
			@Override
			public String getId() {
				return "test";
			}
			
			/* (non-Javadoc)
			 * @see org.pepstock.charba.client.plugins.AbstractPlugin#onBeforeDraw(org.pepstock.charba.client.AbstractChart, double)
			 */
			@Override
			public boolean onBeforeDraw(AbstractChart<?, ?> chart, double easing) {
				Context2d ctx = chart.getCanvas().getContext2d();
				ctx.setFillStyle(Colors.COLOR_DATASET_3.alpha(0.1).toRGBA());
				ctx.fillRect(0, 0, chart.getCanvas().getWidth(), chart.getCanvas().getHeight());
				return true;
			}
		};
		try {
			chart.getPlugins().add(p);
		} catch (InvalidPluginIdException e) {
			new Toast("Invalid PlugiID!", Level.ERROR, e.getMessage()).show();
		}
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

}
