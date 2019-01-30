package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.PieChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.plugins.AbstractPlugin;

import com.google.gwt.canvas.dom.client.CanvasGradient;
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
public class PieView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PieView> {
	}

	@UiField
	PieChart chart;

	public PieView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Pie Chart");
		
		chart.getPlugins().add(new AbstractPlugin() {

			@Override
			public String getId() {
				return "size";
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.pepstock.charba.client.jsinterop.plugins.AbstractPlugin#onBeforeDraw(org.pepstock.charba.client.jsinterop.
			 * AbstractChart, double)
			 */
			@Override
			public boolean onBeforeDraw(AbstractChart<?, ?> chart, double easing) {

				Context2d ctx = chart.getCanvas().getContext2d();
				int centerX = chart.getCanvas().getOffsetWidth() / 2;
				int centerY = chart.getCanvas().getOffsetHeight() / 2;
				CanvasGradient pattern = ctx.createRadialGradient(centerX, centerY, centerX, centerX, centerY, 0); //LinearGradient(0, 0, 0, );
				pattern.addColorStop(0, "rgba(255, 0, 0, 0.5)");
				pattern.addColorStop(0.5, "rgba(255, 0, 0, 0.25)");
				pattern.addColorStop(1, "rgba(255, 0,0, 0)");
				// set fill canvas color
				ctx.setFillStyle(pattern);
				// fills back ground
				ctx.fillRect(0, 0, chart.getCanvas().getOffsetWidth(), chart.getCanvas().getOffsetHeight());
				// always TRUE
				return true;
			}
		});
		
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		chart.getDatasetMeta(0);
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months, false));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		datasets.add(dataset);
		
		chart.update();

	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		if (months < 12){
			chart.getData().getLabels().add(getLabel());
			months++;
			List<Dataset> datasets = chart.getData().getDatasets();
			for (Dataset ds : datasets){
				PieDataset pds = (PieDataset)ds;
				pds.setBackgroundColor(getSequenceColors(months, 1));	
				pds.getData().add(getRandomDigit(false));
			}
			chart.update();
		}
	}

	@UiHandler("remove_data")
	protected void handleremoveData(ClickEvent event) {
		removeData(chart);
	}
}
