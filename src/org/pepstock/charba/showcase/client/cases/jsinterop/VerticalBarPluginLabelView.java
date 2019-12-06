package org.pepstock.charba.showcase.client.cases.jsinterop;

import java.util.List;

import org.pepstock.charba.client.BarChart;
import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.DatasetItem;
import org.pepstock.charba.client.items.DatasetMetaItem;
import org.pepstock.charba.client.plugins.AbstractPlugin;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.cases.commons.Colors;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class VerticalBarPluginLabelView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, VerticalBarPluginLabelView> {
	}

	@UiField
	BarChart chart;
	
	public VerticalBarPluginLabelView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		DivElement wrapper = Document.get().createDivElement();
		DivElement element = Document.get().createDivElement();
		element.setAttribute("xmlns", Document.get().getDocumentElement().getAttribute("xmlns"));
		wrapper.appendChild(element);
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.TOP);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Bar Chart");
		
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
		chart.getData().setLabels(getLabels());
		chart.getData().setDatasets(dataset1, dataset2);
		
		AbstractPlugin p = new AbstractPlugin() {
			
			@Override
			public String getId() {
				return "test";
			}
			
			@Override
			public void onAfterDatasetsDraw(IsChart chart, double easing) {
				final int fontSize = 16;
				final int padding = 5;
				final Context2d ctx = chart.getCanvas().getContext2d();

				List<Dataset> dss = chart.getData().getDatasets();
				for (int i=0; i<dss.size(); i++){
					DatasetMetaItem metadata = chart.getDatasetMeta(i);
					if (!metadata.isHidden()){
						Dataset ds = dss.get(i);
						List<DatasetItem> items = metadata.getDatasets();
						for (int k=0; k<items.size(); k++){
							DatasetItem item = items.get(k);
							String dataString = ds.getData().get(k).toString();
							ctx.setFillStyle("rgb(0, 0, 0)");
							ctx.setFont("16px normal Helvetica Neue");
							ctx.setTextAlign(TextAlign.CENTER);
							ctx.setTextBaseline(TextBaseline.MIDDLE);
							ctx.fillText(dataString, item.getView().getX(), item.getView().getY() - (fontSize /2) - padding);
						}
					}
				}
			}
		};
		chart.getPlugins().add(p);
	}


	@UiHandler("randomize")
	protected void handleRandomize(ClickEvent event) {
		for (Dataset dataset : chart.getData().getDatasets()){
			dataset.setData(getRandomDigits(months));
		}
		chart.update();
	}

	
	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}
}
