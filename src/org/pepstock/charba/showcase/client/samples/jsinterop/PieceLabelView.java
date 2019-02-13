package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.PieChart;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.PieDataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.enums.Render;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class PieceLabelView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PieceLabelView> {
	}

	@UiField
	PieChart chart;

	public PieceLabelView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Pie Chart with PieceLabel plugin");
		
		PieDataset dataset = chart.newDataset();
		dataset.setLabel("dataset 1");
		dataset.setBackgroundColor(getSequenceColors(months, 1));
		dataset.setData(getRandomDigits(months, false));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset);

		
		LabelsOptions option = new LabelsOptions();
		option.setRender(Render.label);
//		option.setRender(Render.image);
//		option.setPrecision(2);
//		option.setFontColor("black");
//		option.setFontSize(16);
//		option.setOverlap(false);
		
//		set(option);
		
//		option.setRenderStringCallback(new RenderStringCallback() {
//			
//			@Override
//			public String render(AbstractChart<?, ?> chart, RenderItem item) {
//				return "$$ "+ (int)(item.getValue() * item.getPercentage() / 100);
//			}
//		});
//		
//		option.setRender(new RenderCallback() {
//
//			@Override
//			public Object render(AbstractChart<?, ?> chart, RenderItem item) {
//				return img;
//			}
//		});
//		
		
//		chart.getOptions().getPlugins().setEnabled(LabelsPlugin.ID, true);
//		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
		
//		option.setFontColor(new FontColorCallback() {
//			
//			@Override
//			public String color(AbstractChart<?, ?> chart, FontColorItem item) {
//				return item.getValue() > 25 ? HtmlColor.Red.toRGBA() : HtmlColor.White.toRGBA();
//			}
//		});
		
		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);

		//chart.getOptions().merge(option, PieceLabelOptions.ID);
		

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
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
