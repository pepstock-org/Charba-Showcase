package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.pepstock.charba.client.IsChart;
import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.TooltipLabelCallback;
import org.pepstock.charba.client.callbacks.TooltipTitleCallback;
import org.pepstock.charba.client.commons.ArrayListHelper;
import org.pepstock.charba.client.commons.ArrayObject;
import org.pepstock.charba.client.commons.Key;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.gwt.widgets.SankeyChartWidget;
import org.pepstock.charba.client.items.TooltipItem;
import org.pepstock.charba.client.items.TooltipLabelColor;
import org.pepstock.charba.client.sankey.Priority;
import org.pepstock.charba.client.sankey.SankeyDataPoint;
import org.pepstock.charba.client.sankey.SankeyDataset;
import org.pepstock.charba.client.sankey.enums.Size;
import org.pepstock.charba.client.utils.JSON;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;
import org.pepstock.charba.showcase.client.resources.MyResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class SankeyTreeCase extends BaseComposite {

	private static List<SankeyDataPoint> datapoints = null;

	private static TreeColors colors = null;

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, SankeyTreeCase> {
	}

	@UiField
	SankeyChartWidget chart;

	public SankeyTreeCase() {
		initWidget(uiBinder.createAndBindUi(this));

		chart.getOptions().setResponsive(true);
		chart.getOptions().getLayout().getPadding().setRight(26);
		chart.getOptions().getFont().setSize(9);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Tree on sankey chart");
		chart.getOptions().getTooltips().getCallbacks().setTitleCallback(new TooltipTitleCallback() {

			@Override
			public List<String> onTitle(IsChart chart, List<TooltipItem> items) {
				return Collections.emptyList();
			}

		});
		chart.getOptions().getTooltips().getCallbacks().setLabelCallback(new TooltipLabelCallback() {

			@Override
			public List<String> onLabel(IsChart chart, TooltipItem item) {
				SankeyDataset dataset = (SankeyDataset) chart.getData().retrieveDataset(item);
				SankeyDataPoint v = dataset.getDataPoints().get(item.getDataIndex());
				return Arrays.asList(v.getFrom() + " -> " + v.getTo()+ ": "+v.getFlow());
			}

			@Override
			public TooltipLabelColor onLabelColor(IsChart chart, TooltipItem item) {
				SankeyDataset dataset = (SankeyDataset) chart.getData().retrieveDataset(item);
				SankeyDataPoint v = dataset.getDataPoints().get(item.getDataIndex());
				TooltipLabelColor result = new TooltipLabelColor();
				result.setBackgroundColor(getColor(v.getFrom()));
				return result;
			}

		});
		SankeyDataset dataset1 = chart.newDataset();
		dataset1.setLabel("dataset 1");
		
		Priority priority = new Priority();
		priority.set("one", 0);
		dataset1.setPriority(priority);
		
		dataset1.setNodeWidth(25);

		if (datapoints == null) {
			ArrayObject array = JSON.parseForArray(MyResources.INSTANCE.treeData().getText());
			datapoints = ArrayListHelper.list(array, SankeyDataset.DATAPOINTS_FACTORY);
		}
		dataset1.setDataPoints(datapoints);

		if (colors == null) {
			colors = new TreeColors(JSON.parseForObject(MyResources.INSTANCE.treeColors().getText()));
		}

		dataset1.setColorFrom(new ColorCallback<DatasetContext>() {

			@Override
			public Object invoke(DatasetContext context) {
				if (!ContextType.DATA.equals(context.getType())) {
					return null;
				}
				SankeyDataset dataset = (SankeyDataset) context.getChart().getData().getDatasets().get(context.getDatasetIndex());
				SankeyDataPoint point = dataset.getDataPoints().get(context.getDataIndex());
				return getColor(point.getFrom());
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
				return getColor(point.getTo());
			}

		});
		dataset1.setSize(Size.MIN);

		chart.getData().setDatasets(dataset1);

	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private String getColor(String category) {
		return colors.get(category);
	}

	private static class TreeColors extends NativeObjectContainer {

		private TreeColors(NativeObject nativeObject) {
			super(nativeObject);
		}

		private String get(String key) {
			return getValue(Key.create(key), "#6c5ce7");
		}

	}

}
