package org.pepstock.charba.showcase.client.samples.jsinterop;

import java.util.List;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.ext.labels.FontColorCallback;
import org.pepstock.charba.client.ext.labels.FontColorItem;
import org.pepstock.charba.client.ext.labels.LabelsOptions;
import org.pepstock.charba.client.ext.labels.LabelsPlugin;
import org.pepstock.charba.client.ext.labels.RenderCallback;
import org.pepstock.charba.client.ext.labels.RenderItem;
import org.pepstock.charba.client.utils.Window;
import org.pepstock.charba.showcase.client.samples.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrea "Stock" Stocchero
 */
public class PieceLabelBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, PieceLabelBarView> {
	}

	@UiField
	BarChart chart;
	
	public PieceLabelBarView() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1);
		
		dataset1.setData(getFixedDigits(months));
		
		
		LabelsOptions option = new LabelsOptions();
//		option.setRender(Render.image);
//		option.setPrecision(2);
//		option.setFontColor("black");
//		option.setFontSize(16);
//		option.setOverlap(false);
		
//		set(option);
		
		option.setRender(new RenderCallback() {
			
			@Override
			public Object render(AbstractChart<?, ?> chart, RenderItem item) {
				return "$$ "+ (int)(item.getValue() * item.getPercentage() / 100);
			}
		});
//		
//		option.setRenderImageCallback(new RenderImageCallback() {
//
//			@Override
//			public ImageElement render(AbstractChart<?, ?> chart, RenderItem item) {
//				return img;
//			}
//		});
		
		
//		chart.getOptions().getPlugins().setEnabled(LabelsPlugin.ID, true);
//		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
		
		option.setFontColor(new FontColorCallback() {
			
			@Override
			public String color(AbstractChart<?, ?> chart, FontColorItem item) {
				Window.getConsole().log(chart.getId());
				//Window.getConsole().log("Dataset Index "+item.getDatasetIndex()+", "+"Index "+item.getIndex());
				return item.getValue() > 25 ? HtmlColor.Red.toRGBA() : HtmlColor.Black.toRGBA();
			}
		});
		
		chart.getOptions().getPlugins().setOptions(LabelsPlugin.ID, option);
		
		
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1);

	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	@UiHandler("add_dataset")
	protected void handleAddDataset(ClickEvent event) {
		List<Dataset> datasets = chart.getData().getDatasets();
		
		BarDataset dataset = chart.newDataset();
		dataset.setLabel("dataset "+(datasets.size()+1));
		
		IsColor color = Colors.ALL[datasets.size()]; 
		dataset.setBackgroundColor(color.alpha(0.2));
		dataset.setBorderColor(color.toHex());
		dataset.setBorderWidth(1);
		dataset.setData(getRandomDigits(months));

		datasets.add(dataset);
		
		chart.update();
	}

	@UiHandler("remove_dataset")
	protected void handleRemoveDataset(ClickEvent event) {
		removeDataset(chart);
	}

	@UiHandler("add_data")
	protected void handleAddData(ClickEvent event) {
		addData(chart);
	}

	@UiHandler("remove_data")
	protected void handleRemoveData(ClickEvent event) {
		removeData(chart);
	}
}
