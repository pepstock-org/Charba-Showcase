package org.pepstock.charba.showcase.client.cases.jsinterop;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.events.AxisClickEvent;
import org.pepstock.charba.client.events.AxisClickEventHandler;
import org.pepstock.charba.client.events.DatasetSelectionEvent;
import org.pepstock.charba.client.events.DatasetSelectionEventHandler;
import org.pepstock.charba.client.events.TitleClickEvent;
import org.pepstock.charba.client.events.TitleClickEventHandler;
import org.pepstock.charba.client.impl.plugins.ChartPointer;
import org.pepstock.charba.client.impl.plugins.ChartPointerOptions;
import org.pepstock.charba.client.impl.plugins.enums.PointerElement;
import org.pepstock.charba.showcase.client.cases.commons.Colors;
import org.pepstock.charba.showcase.client.cases.commons.Toast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class DatasetSelectionView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, DatasetSelectionView> {
	}

	@UiField
	BarChart chart;
	
	public DatasetSelectionView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
		chart.addHandler(new DatasetSelectionEventHandler() {
			
			@Override
			public void onSelect(DatasetSelectionEvent event) {
				IsChart chart = (IsChart)event.getSource();
				Labels labels = chart.getData().getLabels();
				List<Dataset> datasets = chart.getData().getDatasets();
				if (datasets != null && !datasets.isEmpty()){
					StringBuilder sb = new StringBuilder();
					sb.append("Dataset index: <b>").append(event.getItem().getDatasetIndex()).append("</b><br>");
					sb.append("Dataset label: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getLabel()).append("</b><br>");
					sb.append("Dataset data: <b>").append(datasets.get(event.getItem().getDatasetIndex()).getData().get(event.getItem().getIndex())).append("</b><br>");
					sb.append("Index: <b>").append(event.getItem().getIndex()).append("</b><br>");
					sb.append("Value: <b>").append(labels.getStrings(event.getItem().getIndex()).get(0)).append("</b><br>");
					new Toast("Dataset Selected!", sb.toString()).show();
				}
			}
		}, DatasetSelectionEvent.TYPE);
		
		chart.addHandler(new TitleClickEventHandler() {
			
			@Override
			public void onClick(TitleClickEvent event) {
				StringBuilder sb1 = new StringBuilder();
				for (String t : event.getItem().getText()) {
					sb1.append(t);
				}
				StringBuilder sb = new StringBuilder();
				sb.append("Ttile: <b>").append(sb1).append("</b><br>");
				new Toast("Title Selected!", sb.toString()).show();
			}
		}, TitleClickEvent.TYPE);
		
		chart.addHandler(new AxisClickEventHandler() {
			
			@Override
			public void onClick(AxisClickEvent event) {
				StringBuilder sb = new StringBuilder();
				sb.append("Id: <b>").append(event.getItem().getId()).append("</b><br>");
				sb.append("Type: <b>").append(event.getItem().getType()).append("</b><br>");
				sb.append("Has axis: <b>").append(event.getAxis() != null).append("</b><br>");
				new Toast("Axis Selected!", sb.toString()).show();
			}
		}, AxisClickEvent.TYPE);
		
		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));
		
		chart.getPlugins().add(new ChartPointer());

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
	}

	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		ChartPointerOptions op = new ChartPointerOptions();
		op.setElements(PointerElement.DATASET);
		chart.getOptions().getPlugins().setOptions(ChartPointer.ID, op);
		
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
