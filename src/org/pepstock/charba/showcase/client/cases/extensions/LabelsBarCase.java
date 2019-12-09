package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.labels.FontColorItem;
import org.pepstock.charba.client.labels.LabelsOptions;
import org.pepstock.charba.client.labels.LabelsPlugin;
import org.pepstock.charba.client.labels.RenderItem;
import org.pepstock.charba.client.labels.callbacks.FontColorCallback;
import org.pepstock.charba.client.labels.callbacks.RenderCallback;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class LabelsBarCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, LabelsBarCase> {
	}

	@UiField
	BarChart chart;
	
	public LabelsBarCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Labels on bar chart");
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1);
		
		dataset1.setData(getFixedDigits(months));
		
		
		LabelsOptions option = new LabelsOptions();
		option.setRender(new RenderCallback() {
			
			@Override
			public String invoke(IsChart chart, RenderItem item) {
				return "$$ "+ (int)(item.getValue() * item.getPercentage() / 100);
			}
		});
		option.setFontColor(new FontColorCallback() {
			
			@Override
			public IsColor invoke(IsChart chart, FontColorItem item) {
				return item.getValue() > 25 ? HtmlColor.RED : HtmlColor.BLACK;
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
	

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}