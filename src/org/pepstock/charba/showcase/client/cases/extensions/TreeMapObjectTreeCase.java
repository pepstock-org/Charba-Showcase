package org.pepstock.charba.showcase.client.cases.extensions;

import java.util.Arrays;
import java.util.List;

import org.pepstock.charba.client.callbacks.ColorCallback;
import org.pepstock.charba.client.callbacks.DatasetContext;
import org.pepstock.charba.client.callbacks.FontsCallback;
import org.pepstock.charba.client.colors.GwtMaterialColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.commons.NativeObject;
import org.pepstock.charba.client.commons.NativeObjectContainer;
import org.pepstock.charba.client.enums.ContextType;
import org.pepstock.charba.client.gwt.widgets.TreeMapChartWidget;
import org.pepstock.charba.client.impl.plugins.enums.GwtMaterialScheme;
import org.pepstock.charba.client.items.FontItem;
import org.pepstock.charba.client.treemap.TreeMapDataPoint;
import org.pepstock.charba.client.treemap.TreeMapDataset;
import org.pepstock.charba.client.treemap.callbacks.FormatterCallback;
import org.pepstock.charba.client.treemap.enums.Align;
import org.pepstock.charba.client.utils.JSON;
import org.pepstock.charba.client.utils.Utilities;
import org.pepstock.charba.showcase.client.cases.commons.BaseComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class TreeMapObjectTreeCase extends BaseComposite {

	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

	interface ViewUiBinder extends UiBinder<Widget, TreeMapObjectTreeCase> {
	}

	private static final String DATA = "{\"analytics\":{\"cluster\":{\"agglomerative\":{\"value\":3938},\"communityStructure\":{\"value\":3812},\"hierarchical\":{\"value\":6714},\"mergeEdge\":{\"value\":743}},\"graph\":{\"betweennessCentrality\":{\"value\":3534},\"linkDistance\":{\"value\":5731},\"maxFlowMinCut\":{\"value\":7840},\"shortestPaths\":{\"value\":5914},\"spanningTree\":{\"value\":3416}},\"optimization\":{\"aspectRatioBanker\":{\"value\":7074}}},\"animate\":{\"easing\":{\"value\":17010},\"functionSequence\":{\"vaue\":5842},\"interpolate\":{\"arrayInterpolator\":{\"value\":1983},\"colorInterpolator\":{\"value\":2047},\"dateInterpolator\":{\"value\":1375},\"interpolator\":{\"value\":8746},\"matrixInterpolator\":{\"value\":2202},\"numberInterpolator\":{\"value\":1382},\"objectInterpolator\":{\"value\":1629},\"pointInterpolator\":{\"value\":1675},\"rectangleInterpolator\":{\"value\":2042}},\"schedulable\":{\"value\":1041}}}";
	
	@UiField
	TreeMapChartWidget chart;

	public TreeMapObjectTreeCase() {
		initWidget(uiBinder.createAndBindUi(this));

		NativeObject tree = JSON.parseForObject(DATA);
		
		chart.getOptions().setMaintainAspectRatio(false);
		chart.getOptions().getLegend().setDisplay(false);
		chart.getOptions().getTitle().setDisplay(true);
		chart.getOptions().getTitle().setText("Object tree on a tree map chart");

		TreeMapDataset dataset1 = chart.newDataset();
		
		dataset1.setTreeObject(new TreeMapObject(tree));
		
		dataset1.setKey("value");
		dataset1.setGroups("0", "1");
		dataset1.setSpacing(1);
		dataset1.setBorderWidth(2);
		dataset1.setBorderRadius(8);
		dataset1.setBorderColor(GwtMaterialColor.BLUE_DARKEN_2);
		dataset1.setBackgroundColor(new ColorCallback<DatasetContext>() {
			
			private List<IsColor> colors = GwtMaterialScheme.BLUE.getColors();

			@Override
			public Object invoke(DatasetContext context) {
				if (context.getDataIndex() < 0) {
					return null;
				}
				int index = context.getDataIndex() >= colors.size() ? context.getDataIndex() - colors.size() : context.getDataIndex();
				return colors.get(index);
			}
			
		});
		dataset1.getCaptions().setAlign(Align.CENTER);
		dataset1.getCaptions().getFont().setSize(16);
		dataset1.getCaptions().getHoverFont().setSize(16);
		dataset1.getCaptions().setColor(HtmlColor.BLACK);
		dataset1.getLabels().setDisplay(true);
		dataset1.getLabels().setFont(new FontsCallback<DatasetContext>() {
			
			private FontItem item1 = new FontItem();
			private FontItem item2 = new FontItem();
			private List<FontItem> result = Arrays.asList(item1, item2);
			
			@Override
			public List<FontItem> invoke(DatasetContext context) {
				item1.setSize(18);
				item2.setSize(10);
				return result;
			}
		});
		dataset1.getLabels().setColor(HtmlColor.WHITE_SMOKE, HtmlColor.BLACK);
		dataset1.getLabels().setFormatter(new FormatterCallback() {
			
			@Override
			public List<String> invoke(DatasetContext context) {
				if (ContextType.DATA.equals(context.getType())) {
					TreeMapDataPoint point = dataset1.getDataPoints().get(context.getDataIndex());
					return Arrays.asList(point.getData().getLabel()+":" + Utilities.applyPrecision(point.getValue(), 0));
				}
				return null;
			}
		});
		
		chart.getData().setDatasets(dataset1);
	}

	@UiHandler("source")
	protected void handleViewSource(ClickEvent event) {
		Window.open(getUrl(), "_blank", "");
	}

	private static class TreeMapObject extends NativeObjectContainer {

		private TreeMapObject(NativeObject nativeObject) {
			super(nativeObject);
		}
		
	}

}
