package org.pepstock.charba.showcase.client.cases.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.HtmlLegendTextCallback;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.impl.plugins.HtmlLegend;
import org.pepstock.charba.client.impl.plugins.HtmlLegendOptions;
import org.pepstock.charba.client.items.LegendItem;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class HtmlLegendCustomCallbackCase extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, HtmlLegendCustomCallbackCase> {
	}

	@UiField
	BarChart chart;
	
	public HtmlLegendCustomCallbackCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("HTML legend callback to manage item text on bar chart");

		BarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("This is dataset 1 which contains data");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		
		dataset1.setData(getRandomDigits(months));

		BarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("This is dataset 2 which contains data");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));

		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
		HtmlLegendOptions options = new HtmlLegendOptions();
		options.setLegendTextCallback(new HtmlLegendTextCallback() {

			Map<String, SafeHtml> values = new HashMap<>(); 

			@Override
			public SafeHtml generateLegendText(IsChart chart, LegendItem item, String currentText) {
				if (!values.containsKey(currentText)) {
					SafeHtmlBuilder builder = new SafeHtmlBuilder();
					String newText = currentText.replaceAll("dataset", "<b>dataset</b>");
					newText = newText.replaceAll("which contains data", "<font style='color: "+item.getStrokeStyle().toRGBA()+"'>which contains data</font>");
					builder.appendHtmlConstant(newText);
					values.put(currentText, builder.toSafeHtml());
				}
				return values.get(currentText);
			}
		});
		
		chart.getOptions().getPlugins().setOptions(HtmlLegend.ID, options);
		chart.getPlugins().add(HtmlLegend.get());
		
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
		dataset.setLabel("This is dataset "+(datasets.size()+1)+" which contains data ");
		
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
