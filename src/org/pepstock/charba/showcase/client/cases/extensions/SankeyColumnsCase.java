package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.gwt.widgets.SankeyChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.sankey.Column;
import org.pepstock.charba.client.sankey.Priority;
import org.pepstock.charba.client.sankey.SankeyDataPoint;
import org.pepstock.charba.client.sankey.SankeyDataset;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SankeyColumnsCase extends BaseComposite {
	
	private static final Map<String, IsColor> categoryIndexes = new HashMap<>();

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SankeyColumnsCase> {
	}

	@UiField
	SankeyChartWidget chart;

	public SankeyColumnsCase() {
		initWidget(uiBinder.createAndBindUi(this));
		
		if (categoryIndexes.isEmpty()) {
			categoryIndexes.put("Oil", HtmlColor.BLACK);
			categoryIndexes.put("Coal", HtmlColor.GRAY);
			categoryIndexes.put("Fossil Fuels", HtmlColor.SLATE_GRAY);
			categoryIndexes.put("Electricity", HtmlColor.BLUE);
			categoryIndexes.put("Energy", HtmlColor.ORANGE);
		}

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLayout().getPadding().setRight(26);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Sankey chart setting columns of nodes");
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				return Collections.emptyList();
			}

		});

		SankeyDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		List<SankeyDataPoint> datapoints = dataset1.getDataPoints(true);
		datapoints.add(new SankeyDataPoint("Oil", "Fossil Fuels", 15));
		datapoints.add(new SankeyDataPoint("Natural Gas", "Fossil Fuels", 20));
		datapoints.add(new SankeyDataPoint("Coal", "Fossil Fuels", 25));

		datapoints.add(new SankeyDataPoint("Coal", "Electricity", 25));
		datapoints.add(new SankeyDataPoint("Fossil Fuels", "Energy", 60));
		datapoints.add(new SankeyDataPoint("Solar", "Energy", 25));
		
		Column columns = new Column();
		columns.set("Electricity", 1);
		columns.set("Solar", 1);
		dataset1.setColumn(columns);
		
		Priority priority = new Priority();
		priority.set("Oil", 1);
		priority.set("Natural Gas", 2);
		priority.set("Coal", 3);
		priority.set("Fossil Fuels", 1);
		priority.set("Electricity", 2);
		priority.set("Energy", 1);
		priority.set("Solar", 1);
		dataset1.setPriority(priority);

		dataset1.setColorFrom(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				if (!ContextType.DATA.equals(context.getType())) {
					return null;
				}
				SankeyDataset dataset = (SankeyDataset) context.getChart().getData().getDatasets().get(context.getDatasetIndex());
				SankeyDataPoint point = dataset.getDataPoints().get(context.getDataIndex());
				if (categoryIndexes.containsKey(point.getFrom())) {
					return categoryIndexes.get(point.getFrom());
				}
				return HtmlColor.GREEN;
			}

		});

		dataset1.setColorTo(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				if (!ContextType.DATA.equals(context.getType())) {
					return null;
				}
				SankeyDataset dataset = (SankeyDataset) context.getChart().getData().getDatasets().get(context.getDatasetIndex());
				SankeyDataPoint point = dataset.getDataPoints().get(context.getDataIndex());
				if (categoryIndexes.containsKey(point.getTo())) {
					return categoryIndexes.get(point.getTo());
				}
				return HtmlColor.GREEN;
			}

		});
		chart.getData().setDatasets(dataset1);

	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

}
