package org.pepstock.charba.showcase.client.samples;

import org.pepstock.charba.client.AbstractChart;
import org.pepstock.charba.client.StackedBarChart;
import org.pepstock.charba.client.callbacks.TooltipItemSortHandler;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.StackedBarDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.enums.Position;
import org.pepstock.charba.client.items.TooltipItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;


/**

 * @author Andrea "Stock" Stocchero
 */
public class StackedGroupBarView extends BaseComposite{
	
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, StackedGroupBarView> {
	}

	@UiField
	StackedBarChart chart;
	
	public StackedGroupBarView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		chart.getOptions().setResponsive(true);
		chart.getOptions().getLegend().setPosition(Position.top);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Charba Stacked Bar Chart");
		chart.getOptions().getTooltips().setMode(InteractionMode.index);
		chart.getOptions().getTooltips().setIntersect(false);
		chart.getOptions().getTooltips().setItemSortHandler(new TooltipItemSortHandler() {
			
			@Override
			public int onItemSort(AbstractChart<?, ?> chart, TooltipItem item1, TooltipItem item2) {
				// desc order
				return 1;
			}
		});
	
		StackedBarDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		IsColor color1 = Colors.ALL[0];
		
		dataset1.setBackgroundColor(color1.alpha(0.2));
		dataset1.setBorderColor(color1.toHex());
		dataset1.setBorderWidth(1);
		dataset1.setData(getRandomDigits(months));
		dataset1.setStackGroup("group1");

		StackedBarDataset dataset2 = chart.newDataset();
		dataset2.setLabel("dataset 2");
		
		IsColor color2 = Colors.ALL[1];
		
		dataset2.setBackgroundColor(color2.alpha(0.2));
		dataset2.setBorderColor(color2.toHex());
		dataset2.setBorderWidth(1);
		dataset2.setData(getRandomDigits(months));
		dataset2.setStackGroup("group1");
		
		StackedBarDataset dataset3 = chart.newDataset();
		dataset3.setLabel("dataset 3");
		
		IsColor color3 = Colors.ALL[2];
		
		dataset3.setBackgroundColor(color3.alpha(0.2));
		dataset3.setBorderColor(color3.toHex());
		dataset3.setBorderWidth(2);
		dataset3.setData(getRandomDigits(months));
		dataset3.setStackGroup("group2");
		
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
